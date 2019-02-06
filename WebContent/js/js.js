var answer;
var cateogory;
var question;
var value;

$(document).on('hidden.bs.modal', '#answerModal', function () {
    location.reload();
});

function checkForm() {
	var input = document.getElementById("player_name").value;
	document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
};

$(document).ready("#answer-button").click(function() {
    $('#answer').text(removeHTML(answer));
    $("#wiki-link").attr("href", "https://en.wikipedia.org/w/index.php?search=" + answer);
});

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

$("#submit-button").click(function() {
	var entry = $("#entry").val();
	var sanitizedEntry = sanitizeInput(entry);
	var sanitizedAnswer = sanitizeInput(answer);
	setSnackbar(sanitizedEntry === sanitizedAnswer);
});

function setSnackbar(isRight) {
	if(isRight) {
		$("#submit-button").attr("data-content", "The answer is right.");
	} else {
		$("#submit-button").attr("data-content", "The answer is wrong.");
	}
}

function sanitizeInput(input) {
	var inputAnswer = removeHTML(input);
	inputAnswer = removeTrailingAndLeadingSpaces(inputAnswer);
	inputAnswer = removePluralization(inputAnswer);
	return removeFirstWords(inputAnswer);
}

function removePluralization(input) {
	if(input[input.length - 1] === "es") {
		input = input.slice(-2);
	} else if (input[input.length] === "s") {
		input = input.slice(-1);
	}
	return input;
}

function removeHTML(input) {
	return input.replace(/<\/*[a-zA-Z]\/*>/g, "");
}

function removeTrailingAndLeadingSpaces(input) {
	return input.toUpperCase().trim().replace(/^\s+/g, "");
}

function removeFirstWords(input) {
	var spaceChar = input.indexOf(" ");
	var firstWord = input.substring(0, spaceChar);
	
	if(firstWord === "THE" || firstWord === "AN" || firstWord === "A") {
		input = input.substring(spaceChar + 1);
	}
	
	return input;
}