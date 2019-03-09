var leadersLoaded = false;

$("#leader-link").click(function() {
	loadLeaders("./leader");
})

$("#index-leader-link").click(function(){
	loadLeaders("leader");
})

function loadLeaders(leaderServlet) {
	if(!leadersLoaded) {
		$.ajax({
			url: leaderServlet,
			type: 'GET',
			success: function(data) {
				var json = JSON.stringify(data);
				var leaders = JSON.parse(json);
				
				if(leaders.length >= 1) {
					$("#leaderTable").bootstrapTable({
						data: data
					}).css("visibility", "visible");
				} else {
					$("#no-leaders").text("There are no leaders yet! Submit your score by quitting " +
							"the game, and see how well you rank.");
				}
				leadersLoaded = true;
			}
		});
	}
	$("#leaderboardModal").modal("show");
}