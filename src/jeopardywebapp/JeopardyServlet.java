package jeopardywebapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JeopardyServlet
 */
@WebServlet("/JeopardyServlet")
public class JeopardyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JeopardyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(request.getParameter("player_name"));
		
		Clue clue = new Clue();
		out.println("Category: " + clue.getCategoryTitle());
		out.println("Question: " + clue.getQuestion());
		out.println("Value: " + clue.getValue());
		out.println("Answer: " + clue.getAnswer());
	}

}