package jeopardywebapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClueServlet
 */
@WebServlet("/main")
public class ClueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClueServlet() {
        super();
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Player player = (Player) request.getSession().getAttribute("player");
		
		Clue clue = new Clue();;
		request.setAttribute("player_name", player.getName());
		request.setAttribute("categoryTitle", clue.getCategoryTitle());
		request.setAttribute("question", clue.getQuestion());
		request.setAttribute("value", clue.getValue());
		request.setAttribute("answer", clue.getAnswer());
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/main.jsp");
		dispatcher.forward(request, response);
	}

}
