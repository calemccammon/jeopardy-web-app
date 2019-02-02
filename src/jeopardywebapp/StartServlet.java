package jeopardywebapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartServlet
 */
@WebServlet("/StartServlet")
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Player player = new Player(request.getParameter("player_name"));
		request.setAttribute("player_name", player.getName());
		
		Clue clue = new Clue();
		request.setAttribute("categoryTitle", clue.getCategoryTitle());
		request.setAttribute("question", clue.getQuestion());
		request.setAttribute("value", clue.getValue());
		request.setAttribute("answer", clue.getAnswer());
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/main.jsp");
		dispatcher.forward(request, response);
	}

}