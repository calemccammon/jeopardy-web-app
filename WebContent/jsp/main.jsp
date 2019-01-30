<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
	<div>
		<div class="text-center">
			<p>${categoryTitle}</p>
			<p>${question}</p>
			<p>${value}</p>
		</div>
		<div class="container">
			<div class="row padded">
				<div class="col-lg-4">
					<input type="text" class="form-control" placeholder="Enter your answer">
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
					<button class="btn btn-primary">SUBMIT</button>
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
					<button class="btn btn-primary">SKIP</button>
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
				<button class="btn btn-primary">SHOW ANSWER</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>