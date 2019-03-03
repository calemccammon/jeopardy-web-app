package jeopardywebapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ShowAnswerServlet
 */
@WebServlet("/show")
public class ShowAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAnswerServlet() {
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
				String answer = clue.getAnswer();
				answer = answer.trim();
				answer = AnswerManipulator.removeHTML(answer);
				answer = AnswerManipulator.removeBackslash(answer);
				answer = AnswerManipulator.removeBrackets(answer);
				
				clue.put("formattedAnswer", answer);
				clue.setEnabled(false);
				
				if(clue.isPending()) {
					clue.setStatus(ClueConstants.Status.revealed);
				}
				
				JSONObject responseJson = new JSONObject();
				responseJson.put("formattedAnswer", answer);
				responseJson.put("clue", clue);
				
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
