var answer;
var category;
var question;
var value;


// Reload document on exiting show answer modal
$(document).on('hidden.bs.modal', '#answerModal', function () {
    location.reload();
});

// Show Answer button pressed
$("#answer-button").click(function() {
    $('#answer').text(removeHTML(answer));
    $("#wiki-link").attr("href", "https://en.wikipedia.org/w/index.php?search=" + removeHTML(answer));
});

// Load clue on page load
function loadClue() {
$(document).ready(function() {
	$.get('clue', function(data) {
		var json = JSON.stringify(data);
		category = JSON.parse(json).category.title;
		question = JSON.parse(json).question;
		value = JSON.parse(json).value;
		answer = JSON.parse(json).answer;
		
		$("#category").text(category);
		$("#question").text(question);
		$("#value").text("$" + value);
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
	updateScore();
})};

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
					return;
				}
			},
			error: function() {
				$.snackbar({content: "Something went wrong while processing your answer."});
				return;
			}
		});
		updateScore();
	}
});

$("#skip-button").click(function(event) {
	event.preventDefault();
	loadClue();
});

//Update score modal with current score data
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

//Set the text for the snackbar
function setSnackbar(isRight, result, score) {
	var result = result + " : " + score;
	if(isRight) {
		$.snackbar({htmlAllowed: true, content: "Your answer is correct!   " + result});
	} else {
		$.snackbar({htmlAllowed: true, content: "Your answer is incorrect.   " + result});
	}
}

function removeHTML(input) {
    return input.replace(/<\/*[a-zA-Z]\/*>/g, "");
}