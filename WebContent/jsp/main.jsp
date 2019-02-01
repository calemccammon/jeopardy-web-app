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
<title>Jeopardy</title>
</head>
<body>
	<nav class="navbar navbar-light bg-light">
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    		 <span class="navbar-toggler-icon"></span>
   		</button>
   		<span class="navbar-brand">Jeopardy</span>
   		<div>
		    <span class="navbar-text">${player_name}</span>
		    <button type="button" class="close" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</nav>
		<div class ="collapse" id="navbarText">
			<div class="bg-light p-4">
			<ul class="nav flex-column">
			  <li class="nav-item">
			    <a class="nav-link active" href="#">Score</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" href="#">Leaderboard</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" href="#">About</a>
			  </li>
			</ul>
	   		</div>
		</div>
	
		<div class="container">
		<div class="d-flex flex-column align-items-center mb-3 col-lg-4" style="min-height: 200px; background-color:darkblue; color:gold;">
				<div class="p-2"><h5>${categoryTitle}</h5></div>
				<div class="p-2">${question}</div>
				<div class="mt-auto p-2 align-self-end">$${value}</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
					<input type="text" class="form-control" placeholder="Enter your answer">
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
					<button class="btn btn-secondary btn-block">SUBMIT</button>
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
					<button class="btn btn-secondary btn-block">SKIP</button>
				</div>
			</div>
			<div class="row padded">
				<div class="col-lg-4">
				<button class="btn btn-secondary btn-block">SHOW ANSWER</button>
				</div>
			</div>
		</div>
</body>
</html>