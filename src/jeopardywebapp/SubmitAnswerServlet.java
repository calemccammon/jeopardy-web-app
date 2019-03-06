package jeopardywebapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class AnswerServlet
 */
@WebServlet("/submit")
public class SubmitAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitAnswerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Player player = (Player) request.getSession().getAttribute("player");
			
			if(player != null) {
				Clue clue = new Clue(new JSONObject(request.getParameter("clue").toString()));
				String entry = request.getParameter("entry");
				String actualAnswer = clue.getAnswer();
				int value = clue.getValue();
				entry = AnswerManipulator.sanitizeInput(entry);
				actualAnswer = AnswerManipulator.sanitizeInput(actualAnswer);
				
				boolean isRight = AnswerManipulator.compareAnswer(entry, actualAnswer);
				clue.setEnabled(false);
				clue.setStatus(isRight);
				player.addScore(value, isRight);
				
				JSONObject responseJson = new JSONObject();
				responseJson.put("score", player.getScore(player.getScore()));
				responseJson.put("result", AnswerManipulator.formatValue(value, isRight));
				responseJson.put("clue", clue);
				responseJson.put("isRight", isRight);
				
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().print(responseJson);
			} else {
				response.sendRedirect("index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
