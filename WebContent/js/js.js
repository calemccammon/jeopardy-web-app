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
