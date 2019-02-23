package jeopardywebapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import jeopardywebapp.Leaderboard.DataKey;

/**
 * Servlet implementation class LeaderboardServlet
 */
@WebServlet("/leader")
public class LeaderboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Leaderboard leaderboard;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaderboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Player player = (Player) request.getSession().getAttribute("player");
		
		if(player != null) {
			Leaderboard file = new Leaderboard();
			JSONObject json = file.readFile();
			JSONArray leaders = new JSONArray();
			
			if(json != null) {
				leaders = json.getJSONArray(DataKey.leaders.name());
			}
			
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(leaders.toString());
		} else {
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
