<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/css.css">
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		${player_name}
	</nav>
	<div class="main-panel">
		<div class="question-panel">
		<div class="d-flex flex-column">
				<div class="d-flex justify-content-center h1 category-title">${categoryTitle}</div>
				<div class="d-flex justify-content-center h2">${question}</div>
				<div class="d-flex justify-content-end">${value}</div>
		</div>
		</div>
	<div class="d-flex flex-column justify-content-center">
				<input type="text" class="form-control" placeholder="Enter your answer">
				<button class="btn btn-primary">SUBMIT</button>
				<button class="btn btn-primary">SKIP</button>
				<button class="btn btn-primary">SHOW ANSWER</button>
	</div>
	</div>
</body>
</html>