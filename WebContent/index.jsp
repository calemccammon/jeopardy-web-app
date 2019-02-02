<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Jeopardy</title>
</head>
<body class="text-center">
	<script>
	function checkForm(){
		var input = document.getElementById("player_name").value;
		document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
	}</script>
	<h1 class="display-1">Jeopardy</h1>
	<br><br>
	<form name = "player_input" method="post" action="StartServlet" accept-charset=utf-8>
	<p><input type = "text" name = "player_name" id = "player_name" placeholder="Enter player name" onkeyup = "checkForm()">
	<br><br><br>
	<input id = "startButton" class="btn btn-dark btn-lg" type="submit" value= "START" disabled = "disabled">
	</p>
	</form>
</body>
</html>