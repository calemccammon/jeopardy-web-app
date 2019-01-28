<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jeopardy</title>
</head>
<body>
	<script>
	function checkForm(){
		var input = document.getElementById("player_name").value;
		document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
	}</script>
	<h1>Jeopardy</h1>
	
	<form name = "player_input" action="JeopardyServlet">
	<p>Player Name: <input type = "text" name = "player_name" id = "player_name" onkeyup = "checkForm()">
	<br>
	<br>
	<input id = "startButton" type="submit" value= "START" disabled = "disabled">
	</p>
	</form>
</body>
</html>