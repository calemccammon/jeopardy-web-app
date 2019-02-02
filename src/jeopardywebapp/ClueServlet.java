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
@WebServlet("/ClueServlet")
public class ClueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Clue clue = new Clue();
		request.setAttribute("categoryTitle", clue.getCategoryTitle());
		request.setAttribute("question", clue.getQuestion());
		request.setAttribute("value", clue.getValue());
		request.setAttribute("answer", clue.getAnswer());
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/main.jsp");
		dispatcher.forward(request, response);
	}

}
