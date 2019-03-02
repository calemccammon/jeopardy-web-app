var clues;
var snackbarTimeout = 3000;

//Change the clue's icon after dismissing the answer modal
//and toggle the submit button
$(document).on('hidden.bs.modal', '#answerModal', function () {
	// TODO this if statement is showing true when it should be false
	if(!($(".carousel-item.active .far").hasClass('fa-check-circle')) ||
			!($(".carousel-item.active .far").hasClass('fa-times-circle'))) {
		$(".carousel-item.active .far").attr('class', 'far fa-stop-circle fa_red');
	}
	toggleSubmit();
});

function getCurrentClue() {
	var index = parseInt($('.carousel-item.active').attr('id').replace('clue', ''));
	return JSON.stringify(clues[index]);
}

function getAnswer() {
	return JSON.parse(getCurrentClue()).answer;
}

function getValue() {
	return JSON.parse(getCurrentClue()).value;
}

// Show Answer button pressed
$("#answer-button").click(function() {
	var request = ({"answer": getAnswer()});
	$.ajax({
		data: request,
		dataType: 'json',
		url: './answer',
		type: 'GET',
		success: function(response) {
			var json = JSON.stringify(response);
			var answer = JSON.parse(json).answer;
			$('#answer').text("The answer is: " + answer + ".");
			$('#wiki-link').attr("href", "https://en.wikipedia.org/w/index.php?search=" + answer);
		},
		error: function() {
			$('#answer').text("Something went wrong while fetching the clue's answer.");
		}
	});
});

//Call loadClue when the page loads
$(document).ready(function() {
	loadClue();
});

//Load clues - this is the central method for the main page
function loadClue() {
	cleanPage();
	$(".lds-ring").show();
	$.ajax({
		url: './clue',
		type: 'GET',
		async: false,
		success: function(data) {
			var json = JSON.stringify(data);
	        category = JSON.parse(json).category;
	        clues = JSON.parse(json).clues;
	        questions = JSON.parse(json).question;
	        value = JSON.parse(json).value;
	        answer = JSON.parse(json).answer;
	        
	        for(var i = 0; i < clues.length; i++) {
	            var clue = clues[i];
	            addClue(clue, i);
	        }
	        
	        $("#category").text(category);
	        $("#answer").text("Are you trying to cheat?");
		},
		error: function() {
			$('.carousel-inner').text("Something went wrong while fetching the clues.");
		},
		complete: function() {
			$(".lds-ring").hide();
		},
		timeout: 20000
	});
}

var leadersLoaded = false;

$("#leader-link").click(function() {
	if(!leadersLoaded) {
		$.ajax({
			url: './leader',
			type: 'GET',
			success: function(data) {
				var json = JSON.stringify(data);
				var leaders = JSON.parse(json);
				
				if(leaders.length >= 1) {
					$("#leaderTable").bootstrapTable({
						data: data
					}).css("visibility", "visible");
				} else {
					$("#no-leaders").text("There are no leaders yet! Submit your score by quitting " +
							"the game, and see how well you rank.");
				}
				leadersLoaded = true;
			}
		});
	}
})

//Clean the page - we set the text box and category to be empty
//We remove all clues from the carousel and indicators
function cleanPage() {
	$("#entry").val("");
	$("#category").text("");
	$(".carousel-item").remove();
	$(".carousel-indicators li").remove();
	toggleSubmit();
}

//Add the clue, passing the index to the
//indicator and carousel item methods to determine
//what should be active
function addClue(clue, index) {
	createIndicator(index);
	createCarouselItem(clue, index);
}

//Populate the carousel with the given clue
function createCarouselItem(clue, index) {
	var json = JSON.stringify(clue);
	var clueQuestion = JSON.parse(json).question;
	var clueValue = JSON.parse(json).value;
	var id = "clue" + index;
	var jqueryId = "#" + id;
	
	$(".carousel-inner").append("<div class='carousel-item' id='" + id + "'>");
	$(jqueryId).append("<div class='h6 p-2 d-flex justify-content-center' id='value'>$" + clueValue +
			"&nbsp<i class='far fa-question-circle fa-fw'></i>" + "</div>");
	$(jqueryId).append("<div class='p-2 d-flex justify-content-center' id='question'>" + clueQuestion + "</div>");
	
	$(jqueryId).append("</div>");
	
	if(index == 0) {
		$(jqueryId).addClass("active");
	}
}

//Create indicators that appear at the bottom of the carousel
function createIndicator(index) {
	$(".carousel-indicators").append("<li data-target='#carousel' data-to-slide='" + index + "'>");
	
	if(index == 0) {
		$(".carousel-indicators li").addClass("active");
	}
}

//Swipe through carousel
var touchSensitivity = 5;
$(".carousel").on("touchstart", function (event) {
    var xClick = event.originalEvent.touches[0].pageX;
    $(this).one("touchmove", function (event) {
        var xMove = event.originalEvent.touches[0].pageX;
        if (Math.floor(xClick - xMove) > touchSensitivity) {
            $(this).carousel('next');
        } else if (Math.floor(xClick - xMove) < -(touchSensitivity)) {
            $(this).carousel('prev');
        }
    });
    $(".carousel").on("touchend", function () {
        $(this).off("touchmove");
    });
});

//Cycle through carousel using arrow keys
$(document).keydown(function(e) {
	if(!e.altKey) {
	    switch(e.which) {
	        case 37:
	        	$('#carousel').carousel('prev');
	        	break;
	        case 39:
	        	$('#carousel').carousel('next');
	        	break;
	        default: 
	        	return;
	    }
	    e.preventDefault();
	}
});

// Bind submit button to hitting enter in text box
$("#entry").keypress(function(event) {
	if(event.keyCode === 13) {
		event.preventDefault();
		
		if(!$('#submit-button').prop('disabled')) {
			$("#submit-button").click();
		}
	}
});

// Submit button pressed
$("#submit-button").click(function(event) {
	event.preventDefault()
	var entry = $("#entry").val();
	if(entry.trim() != '') {
		var request = ({"entry": entry, "actualAnswer": getAnswer(), "value": getValue()});
		var json = JSON.stringify(request);
		$.ajax({
			data: {para: json},
			dataType: 'json',
			url: './answer',
			type: 'POST',
			success: function(json) {
				setSnackbar(json.isRight, json.result, json.score);
				setClueIcon(json.isRight);
				toggleSubmit();
			},
			error: function() {
				$.snackbar({content: "Something went wrong while processing your answer.",
					timeout: snackbarTimeout});
			}
		});
	}
});

// Next category button pressed
$("#next-button").click(function() {
	location.reload();
});

// Update score modal with current score data
$("#score-link,.fa-user-alt").click(function() {
		$.ajax({
			url: './score',
			type: 'GET',
			success: function(data) {
				var json = JSON.stringify(data);
				$("#score").text(JSON.parse(json).score);
				$("#total_right").text(JSON.parse(json).total_right);
				$("#total_wrong").text(JSON.parse(json).total_wrong);
				$("#total_categories").text(JSON.parse(json).total_categories);
				$("#scoreModal").modal("show");
			},
			error: function() {
				alert("Something went wrong fetching your score!");
			}
		});
});

// Set the text for the snackbar
function setSnackbar(isRight, result, score) {
	var result = result + " : " + score;
	if(isRight) {
		$.snackbar({htmlAllowed: true, content: "Your answer is correct!   " + result,
			timeout: snackbarTimeout});
	} else {
		$.snackbar({htmlAllowed: true, content: "Your answer is incorrect.   " + result, 
			timeout: snackbarTimeout});
	}
}

//Set the clue icon - changes if the question is right or wrong
function setClueIcon(isRight) {
	if(isRight) {
		$(".carousel-item.active .far").attr('class', 'far fa-check-circle fa_green');
	} else {
		$(".carousel-item.active .far").attr('class', 'far fa-times-circle fa_red');
	}
}

//Toggle the submit button while swiping through carousel
$('#carousel').on('slid.bs.carousel', function(e) {
	toggleSubmit();
});

//Toggle the submit button
function toggleSubmit() {
	$('#submit-button').prop('disabled', 
			$(".carousel-item.active .far").hasClass('fa-check-circle') ||
			$(".carousel-item.active .far").hasClass('fa-times-circle') ||
			$(".carousel-item.active .far").hasClass('fa-stop-circle'));
}

var path = 'main';
history.pushState(null, null, path + window.location.search);

window.addEventListener('popstate', function (event) {
    $("#exitModal").modal('show');
    history.pushState(null, null, path + window.location.search);
});
