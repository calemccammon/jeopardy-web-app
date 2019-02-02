$(document).on('hidden.bs.modal', '#answerModal', function () {
    location.reload();
});

function checkForm() {
	var input = document.getElementById("player_name").value;
	document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
};