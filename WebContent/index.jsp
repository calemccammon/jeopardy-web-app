<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
	<!-- Material Design for Bootstrap fonts and icons -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
	<!-- Material Design for Bootstrap CSS -->
	<link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css">
	<!-- Bootstrap Table -->
	<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.13.4/dist/bootstrap-table.min.css">
	<link rel="stylesheet" href="css/animate.css">
	<link rel="stylesheet" href="css/css.css">
	<link rel="stylesheet" href="css/start.css">
	<title>Jeopardy</title>
</head>
<body>
	<div class="container">
		<div class="box">
			<img src="img/logo.png" alt="Jeopardy" class="animated fadeInUp logo">
			<form name = "player_input" method="post" action="start" accept-charset=utf-8 class="animated fadeIn delay-1s">
				<p><input maxlength=20 class="pl-2 form-control" type = "text" name = "player_name" id = "player_name" placeholder="Enter player name" onkeyup = "checkForm()">
				<br/><br/><br/>
				<input id = "startButton" class="btn btn-dark btn-lg" type="submit" value= "START" disabled = "disabled">
				</p>
			</form>
			<button type = "button" class = "animated fadeIn delay-1s btn btn-dark btn-lg" data-toggle="modal" data-target="#leaderboardModal" id="index-leader-link">Leaderboard</button>
		</div>
	<%@include file="/jsp/leaderboard.html" %>
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/leaderboard.js"></script>
	<script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js"></script>
	<script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js"></script>
	<script src="https://unpkg.com/bootstrap-table@1.13.4/dist/bootstrap-table.min.js"></script>
	<script>$(document).ready(function() { $('body').bootstrapMaterialDesign(); });</script>
	<script>function checkForm() {
		var input = document.getElementById("player_name").value;
		document.getElementById('startButton').disabled = (!(input.match(/^[a-z0-9]+$/i)));
	};</script>
	<script>
		$(document).ready(function() {
			$("#player_name").focus();
		});
	</script>
	</div>
</body>
</html>