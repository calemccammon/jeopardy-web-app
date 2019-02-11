var answer;
var category;
var question;
var value;


// Reload document on exiting show answer modal
$(document).on('hidden.bs.modal', '#answerModal', function () {
    location.reload();
});

// Disable start button when invalid player name input
function checkForm() {
	var input = document.getElementById("player_name").value;
	document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
};


// Show Answer button pressed
$(document).ready("#answer-button").click(function() {
    $('#answer').text(removeHTML(answer));
    $("#wiki-link").attr("href", "https://en.wikipedia.org/w/index.php?search=" + answer);
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
				}
			},
			error: function() {
				$.snackbar({content: "Something went wrong while processing your answer."});
			}
		});
	}
});

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