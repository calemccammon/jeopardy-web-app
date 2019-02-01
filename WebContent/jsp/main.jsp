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
		    <button type="button" class="close" aria-label="Close" data-toggle="modal" data-target="#exitModal">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</nav>
		<div class ="collapse" id="navbarText">
			<div class="bg-light p-4">
			<ul class="nav flex-column">
			  <li class="nav-item">
			    <a class="nav-link" data-toggle="modal" href="#scoreModal">Score</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" data-toggle="modal" href="#leaderboardModal">Leaderboard</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" data-toggle="modal" href="#aboutModal">About</a>
			  </li>
			</ul>
	   		</div>
		</div>
	
		<div class="main-panel col-lg-4">
		<div class="question-panel d-flex flex-column align-items-center">
				<div class="p-2"><h5>${categoryTitle}</h5></div>
				<div class="p-2">${question}</div>
				<div class="mt-auto p-2 align-self-end">$${value}</div>
			</div>
			<div class="row padded">
				<input type="text" class="form-control" placeholder="Enter your answer">
			</div>
			<div class="row padded">
				<button class="btn btn-secondary btn-block">SUBMIT</button>
			</div>
			<div class="row padded">
				<button class="btn btn-secondary btn-block">SKIP</button>
			</div>
			<div class="row padded">
				<button class="btn btn-secondary btn-block">SHOW ANSWER</button>
			</div>
		</div>
		
		
		<!-- Score Modal -->
		<div class="modal fade" id="scoreModal" tabindex="-1" role="dialog" aria-labelledby="scoreModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="scoreModalTitle">Score</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		<!-- Leaderboard Modal -->
		<div class="modal fade" id="leaderboardModal" tabindex="-1" role="dialog" aria-labelledby="leaderboardModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="leaderboardModalTitle">Leaderboard</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		<!-- About Modal -->
		<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog" aria-labelledby="aboutModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="aboutModalTitle">About</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        The quick brown fox jumped over the lazy dog.
		        
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Exit Modal -->
		<div class="modal fade" id="exitModal" tabindex="-1" role="dialog" aria-labelledby="exitModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exitModalLabel">Quit</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        Are you sure you want to quit?
		      </div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-primary">Yes</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
		      </div>
		    </div>
		  </div>
		</div>
</body>
</html>