<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
	<link rel="stylesheet" href="css/css.css">
	<!-- Material Design for Bootstrap fonts and icons -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
	<!-- Material Design for Bootstrap CSS -->
	<link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css">
	<title>Jeopardy</title>
</head>
<body class="text-center">
	<h1 class="display-1">Jeopardy</h1>
	<br><br>
	<form name = "player_input" method="post" action="start" accept-charset=utf-8>
		<p><input type = "text" name = "player_name" id = "player_name" placeholder="Enter player name" onkeyup = "checkForm()">
		<br><br><br>
		<input id = "startButton" class="btn btn-dark btn-lg" type="submit" value= "START" disabled = "disabled">
		</p>
	</form>
	<script src="js/jquery-3.3.1.js"></script>
	<script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js"></script>
	<script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js"></script>
	<script>$(document).ready(function() { $('body').bootstrapMaterialDesign(); });</script>
	<script>function checkForm() {
		var input = document.getElementById("player_name").value;
		document.getElementById('startButton').disabled = !(input.match(/^[a-z0-9]+$/i));
	};</script>
</body>
</html>