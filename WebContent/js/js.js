var answer;

$(document).on('hidden.bs.modal', '#answerModal', function () {
    location.reload();
});

function checkForm() {
	var input = document.getElementById("player_name").value;
	document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
};

$(document).ready(function() {
	answer = $('#answer').text();
    $('#answer').text("Are you trying to cheat?");
});

$(document).ready("#answer-button").click(function() {
    $('#answer').text(answer);
    $("#wiki-link").attr("href", "https://en.wikipedia.org/w/index.php?search=" + answer);
});

function loadClue() {
$(document).ready(function() {
	$.get('clue', function(data) {
		var json = JSON.stringify(data);
		$("#category").text(JSON.parse(json).category.title);
		$("#question").text(JSON.parse(json).question);
		$("#value").text("$" + JSON.parse(json).value);
		$("#answer").text("Are you trying to cheat?");
		answer = JSON.parse(json).answer;
	});
})};