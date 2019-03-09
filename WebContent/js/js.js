var clues;
var snackbarTimeout = 3000;

function getCurrentClueIndex() {
	return parseInt($('.carousel-item.active').attr('id').replace('clue', ''));
}

function getCurrentClue() {
	return JSON.stringify(clues[getCurrentClueIndex()]);
}

function updateClue(json) {
	clues[getCurrentClueIndex()] = json;
	setClueIcon(json);
	toggleSubmit(json);
}

function getAnswer() {
	return JSON.parse(getCurrentClue()).answer;
}

function getValue() {
	return JSON.parse(getCurrentClue()).value;
}

// Show Answer button pressed
$("#answer-button").click(function() {
	if($(".lds-ring").is(":hidden")) {
		var json = ({"clue": getCurrentClue()});
		$.ajax({
			data: json,
			dataType: 'json',
			url: './show',
			type: 'POST',
			success: function(json) {
				var answer = json.formattedAnswer;
				$('#answer').text("The answer is: " + answer + ".");
				$('#wiki-link').attr("href", "https://en.wikipedia.org/w/index.php?search=" + answer);
				updateClue(json.clue);
			},
			error: function() {
				$('#answer').text("Something went wrong while fetching the clue's answer.");
			}
		});
	}
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
		success: function(data) {
			if(!('Error' in data)) {
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
			} else {
				$('.carousel-inner').text("Something went wrong while fetching the clues.");
			}
		},
		error: function() {
			$('.carousel-inner').text("Something went wrong while fetching the clues.");
		},
		complete: function() {
			$(".lds-ring").hide();
		},
		timeout: 20000
	});
	$("#entry").prop("readonly", true);
	$("#entry").focus();
	$("#entry").prop("readonly", false);
}


//Clean the page - we set the text box and category to be empty
//We remove all clues from the carousel and indicators
function cleanPage() {
	$("#entry").val("");
	$("#category").text("");
	$(".carousel-item").remove();
	$(".carousel-indicators li").remove();
	$('#submit-button').prop('disabled', false);
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
	$(jqueryId).append("<div class='py-2 px-4 d-flex justify-content-center' id='question'>" + clueQuestion + "</div>");
	
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
	if($(".lds-ring").is(":hidden")) {
		var entry = $("#entry").val();
		if(entry.trim() != '') {
			var json = ({"entry": entry, "clue": getCurrentClue()});
			$.ajax({
				data: json,
				dataType: 'json',
				url: './submit',
				type: 'POST',
				success: function(json) {
					setSnackbar(json.isRight, json.result, json.score);
					updateClue(json.clue);
				},
				error: function() {
					$.snackbar({content: "Something went wrong while processing your answer.",
						timeout: snackbarTimeout});
				}
			});
		}
	}
});

// Next category button pressed
$("#next-button").click(function() {
	if($(".lds-ring").is(":hidden")) {
		loadClue();
	}
});

// Update score modal with current score data
$("#score-link, #score-link-top").click(function() {
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
		animateCSS('.navbar-brand', 'bounce');
		$.snackbar({htmlAllowed: true, content: "Your answer is correct!   " + result,
			timeout: snackbarTimeout});
	} else {
		animateCSS('.navbar-brand', 'shake');
		$.snackbar({htmlAllowed: true, content: "Your answer is incorrect.   " + result, 
			timeout: snackbarTimeout});
	}
}

function setClueIcon(clue) {
	var status = clue.status;
	switch(status) {
		case "correct":
			$(".carousel-item.active .far").attr('class', 'far fa-check-circle fa_green');
			break;
		case "incorrect":
			$(".carousel-item.active .far").attr('class', 'far fa-times-circle fa_red');
			break
		case "revealed":
			$(".carousel-item.active .far").attr('class', 'far fa-stop-circle fa_red');
			break;
		default:
			$(".carousel-item.active .far").attr('class', 'far fa-question-circle fa-fw');
			break;
	}
}

//Toggle the submit button while swiping through carousel
$('#carousel').on('slid.bs.carousel', function() {
	toggleSubmit(JSON.parse(getCurrentClue()));
	$("#entry").val("");
	$("#entry").prop("readonly", true);
	$("#entry").focus();
	$("#entry").prop("readonly", false);
});

function toggleSubmit(clue) {
	$('#submit-button').prop('disabled', !clue.enabled);
}

var path = 'main';
history.pushState(null, null, path + window.location.search);

window.addEventListener('popstate', function (event) {
    $("#exitModal").modal('show');
    history.pushState(null, null, path + window.location.search);
});

function animateCSS(element, animationName, callback) {
    const node = document.querySelector(element)
    node.classList.add('animated', animationName)

    function handleAnimationEnd() {
        node.classList.remove('animated', animationName)
        node.removeEventListener('animationend', handleAnimationEnd)

        if (typeof callback === 'function') callback()
    }

    node.addEventListener('animationend', handleAnimationEnd)
}
