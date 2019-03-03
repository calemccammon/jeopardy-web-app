package jeopardywebapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StartServlet
 */
@WebServlet("/start")
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartServlet() {
        super();
    }
	
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String name = request.getParameter("player_name");
		
        if(name != null && checkName(name)) {
        	Player player = new Player(name);
 			session.setAttribute("player", player);
			session.setAttribute("skip", "false");
			response.sendRedirect("main");
        } else {
        	response.sendRedirect("index.jsp");
        }
	}
	
	/**
	 * Server-Side Player Name Checking
	 */
	private boolean checkName(String playerName) {
		return (playerName.length() > 0 && playerName.length() < 20 && playerName.matches("(?i:[a-z0-9]+)"));
	}
	
}