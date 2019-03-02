<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
<!-- Material Design for Bootstrap fonts and icons -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
<!-- Material Design for Bootstrap CSS -->
<link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css">
<!-- Bootstrap Table -->
<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.13.4/dist/bootstrap-table.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<link rel="stylesheet" href="css/css.css">
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
		    <a data-toggle="modal" href="#scoreModal"><span><i class="fas fa-user-alt"></i></span></a>
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
			  <li class="nav-item">
			    <a class="nav-link" data-toggle="modal" href="#exitModal">Quit</a>
			  </li>
			</ul>
	   		</div>
		</div>
		<div class="main-panel col-lg-4">
		<div class="question-panel d-flex flex-column">
			<div class='p-2 h5 text-capitalize d-flex justify-content-center' id='category'></div>
		<div id="carousel" class="carousel slide d-flex" data-ride="carousel" data-interval="false">
				<ol class="carousel-indicators"></ol>		
				<div class="carousel-inner d-flex"><div class="lds-ring"><div></div><div></div><div></div><div></div></div></div>
				<a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span></a>
			  	<a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="sr-only">Next</span></a>
		</div>
		</div>
		<form accept-charset=utf-8>
			<div class="row padded form-group">
				<input type="text" id="entry" class="form-control" placeholder="Enter your answer">				</div>
			<div class="row padded">
				<button type="button" id="submit-button" class="btn btn-secondary btn-block">
					SUBMIT
				</button>
			</div>
			<div class="row padded">
				<button type="button" class="btn btn-secondary btn-block" id="next-button">NEXT CATEGORY</button>
			</div>
			<div class="row padded">
				<button type="button" id="answer-button" class="btn btn-secondary btn-block" data-toggle="modal" data-target="#answerModal">
					SHOW ANSWER
				</button>
			</div>
		</form>
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
		      	<div class="container">
			      	<div class = "row py-2">
				        <div class="col-sm-4"><strong>Player Name: </strong></div>
				        <div class="col-sm-4 text-sm-right">${player_name}</div>
			        </div>
			        <div class = "row py-2">
			        	<div class="col-sm-4"><strong>Current Score: </strong></div>
				        <div class="col-sm-4 text-sm-right" id="score"></div>
				    </div>
				    <div class = "row py-2">
			        	<div class="col-sm-4"><strong>Total Right: </strong></div>
				        <div class="col-sm-4 text-sm-right" id="total_right"></div>
				    </div>
				    <div class = "row py-2">
			        	<div class="col-sm-4"><strong>Total Wrong: </strong></div>
				        <div class="col-sm-4 text-sm-right" id="total_wrong"></div>
				    </div>
				    <div class = "row py-2">
			        	<div class="col-sm-4"><strong>Total Categories: </strong></div>
				        <div class="col-sm-4 text-sm-right" id="total_categories"></div>
				    </div>
		        </div>
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
		      	<div id="no-leaders"></div>
		        <table class="table" id="leaderTable">
		        	<thead>
			        	<tr>
			        		<th scope = "col" data-field = "rank">Rank</th>
			        		<th scope = "col" data-field = "name">Player Name</th>
			        		<th scope = "col" data-field = "score">Score</th>
			        	</tr>
		        	</thead>
		        </table>
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
		      	<p>"Jeopardy!" is a trivia game where contestants are given the answer and supply the question.
		      	The Jeopardy Web Application (JWA) uses a freely available <a target="_blank" href="http://jservice.io/">API</a> to randomly pull questions and answers from the gameshow's archives.
		      	</p>
		      	<p>The JWA keeps a running total of the your score. If you submit a correct answer, your score increases. If the you submit an incorrect answer, your score decreases.
		      	You can access the leaderboard to see how well your score ranks among the top five players.</p>
		      	<p>Find this project on <a target="_blank" href="https://github.com/calemccammon/jeopardy-web-app">Github</a>.</p>
		        <br>
		        <h6>Developers</h6>
		        <p>
		        	<a target="_blank" href="https://github.com/calemccammon">Cale McCammon</a>
					<br><a target="_blank" href="https://github.com/stephannapolis">Steph Anderson</a>
				</p>
		        <h6>QA</h6>
		        <p>Sherida Dewitt-Smith</p>
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
		      <form method="post">
		      	<button class="btn btn-primary" id="exitYes" formaction="quit">Yes</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Show Answer Modal -->
		<div class="modal fade" id="answerModal" tabindex="-1" role="dialog" aria-labelledby="answerModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="answerModalLabel">Answer</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        	<div id="answer"></div>
		        	<div>Learn more about it on
		         		<a target="_blank" id="wiki-link">Wikipedia!</a></div>
		      </div>
			      <div class="modal-footer">
			      	<button class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>
		    </div>
		  </div>
		</div>
<script src="js/jquery-3.3.1.js"></script>
<script src="js/js.js"></script>
<script src="https://unpkg.com/popper.js@1.12.6/dist/umd/popper.js"></script>
<script src="https://cdn.rawgit.com/FezVrasta/snackbarjs/1.1.0/dist/snackbar.min.js"></script>
<script src="https://unpkg.com/bootstrap-material-design@4.1.1/dist/js/bootstrap-material-design.js"></script>
<script src="https://unpkg.com/bootstrap-table@1.13.4/dist/bootstrap-table.min.js"></script>
<script>$(document).ready(function() { $('body').bootstrapMaterialDesign(); });</script>
</body>
</html>