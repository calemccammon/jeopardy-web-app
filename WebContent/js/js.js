var answer;
var category;
var question;
var value;
var clues;

// Reload document on exiting show answer modal
$(document).on('hidden.bs.modal', '#answerModal', function () {
    loadClue();
});

// Show Answer button pressed
$("#answer-button").click(function() {
    $('#answer').text(removeHTML(answer));
    $("#wiki-link").attr("href", "https://en.wikipedia.org/w/index.php?search=" + removeHTML(answer));
});

// Load clue on page load
function loadClue() {
$(document).ready(function() {
	
	$("#entry").val("");
	
	$.get('clue', function(data) {
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
	});
	
	$.get('leader', function(data){
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
	});
	
	$.post('skip');

	updateScore();
	
	$.get('skip', {"skip": "true"});
})};

function addClue(clue, index) {
	createIndicator(index);
	createCarouselItem(clue, index);
}

var currentSlide;

function createCarouselItem(clue, index) {
	var json = JSON.stringify(clue);
	var clueQuestion = JSON.parse(json).question;
	var clueValue = JSON.parse(json).value;
	var id = "clue" + index;
	var jqueryId = "#" + id;
	
	$(".carousel-inner").append("<div class='carousel-item' id='" + id + "'>");
	$(jqueryId).append("<div class='p-2 d-flex justify-content-center' id='question'>" + clueQuestion + "</div>");
	$(jqueryId).append("<div class='p-2 d-flex justify-content-end' id='value'>$" + clueValue +
			"<i class='far fa-question-circle fa-fw'></i>" + "</div>");
	$(jqueryId).append("</div>");
	
	if(index == 0) {
		$(jqueryId).addClass("active");
		currentSlide = 0;
	}
}

$('#carousel').on('slid.bs.carousel', function() {
	currentSlide = $('.carousel-item.active').attr('id').replace('clue', '');
});

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
});

// Bind submit button to hitting enter in text box
$("#entry").keypress(function(event) {
    if (event.keyCode === 13) {
    	event.preventDefault();
        $("#submit-button").click();
    }
});

// Submit button pressed
$("#submit-button").click(function(event) {
	event.preventDefault();
	var entry = $("#entry").val();
	if(entry.trim() != '') {
		var request = ({"entry": entry, "actualAnswer": answer, "value": value});
		var json = JSON.stringify(request);
		$.ajax({
			data: {para: json},
			dataType: 'json',
			url: './answer',
			type: 'POST',
			success: function(json) {
				setSnackbar(json.isRight, json.result, json.score);
				if(json.isRight) {
					loadClue();
					$.get('skip', {"skip": "false"});
					return;
				} else {
					updateScore();
				}
			},
			error: function() {
				$.snackbar({content: "Something went wrong while processing your answer."});
				return;
			}
		});
	}
});


// Skip button pressed
$("#skip-button").click(function(event) {
	event.preventDefault();
	loadClue();
});

// Update score modal with current score data
function updateScore(){
	$(document).ready(function() {
		$.get('score', function(data) {
			var json = JSON.stringify(data);
			$("#score").text(JSON.parse(json).score);
			$("#total_right").text(JSON.parse(json).total_right);
			$("#total_wrong").text(JSON.parse(json).total_wrong);
			$("#total_skipped").text(JSON.parse(json).total_skipped);
		});
	});
}

// Set the text for the snackbar
function setSnackbar(isRight, result, score) {
	var result = result + " : " + score;
	if(isRight) {
		$.snackbar({htmlAllowed: true, content: "Your answer is correct!   " + result});
	} else {
		$.snackbar({htmlAllowed: true, content: "Your answer is incorrect.   " + result});
	}
}

// Clean show answer modal
function removeHTML(input) {
    return input.replace(/<\/*[a-zA-Z]\/*>/g, "");
}