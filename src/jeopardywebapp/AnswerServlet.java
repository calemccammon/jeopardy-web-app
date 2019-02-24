package jeopardywebapp;

import java.io.IOException;
import java.text.Normalizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.debatty.java.stringsimilarity.*;
import org.json.JSONObject;

/**
 * Servlet implementation class AnswerServlet
 */
@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Player player = (Player) request.getSession().getAttribute("player");
			
			if(player != null) {
				JSONObject json = new JSONObject(request.getParameter("para"));
				String entry = json.getString("entry");
				String actualAnswer = json.getString("actualAnswer");
				int value = json.getInt("value");
				entry = sanitizeInput(entry);
				actualAnswer = sanitizeInput(actualAnswer);
				
				boolean isRight = compareAnswer(entry, actualAnswer);
				JSONObject responseJson = new JSONObject();
				responseJson.put("isRight", isRight);
				responseJson.put("result", formatValue(value, isRight));
				
				player.addScore(value, isRight);
				responseJson.put("score", player.getScore(player.getScore()));
				
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Compare user-submitted answer to actual answer
	 * Uses Levenshtein Algorithm and matches optional text from actual answer string
	 * @param entry, actualAnswer
	 * @return boolean - tells us if it's a match
	 */
	boolean compareAnswer(String entry, String actualAnswer) {
		boolean isRight = false;
		NormalizedLevenshtein nl = new NormalizedLevenshtein();
		Double maxDistance = 0.3;
		
		//basic comparison
		if (nl.distance(entry, actualAnswer) < maxDistance) isRight = true;
		
		//removing parentheses around optional text
		else if (nl.distance(entry, actualAnswer.replaceAll("(||)","")) < maxDistance) isRight = true;
		
		//removing optional text inside of parentheses from actual answer
		else if (nl.distance(entry, removeTrailingAndLeadingSpaces(actualAnswer.replaceAll("(.*)","").replace("  ", " "))) < maxDistance) isRight = true;
		
		//removing dashes
		else if (nl.distance(entry, actualAnswer.replace("-","")) < maxDistance) isRight = true;
		
		
		return isRight;
	}
	
	/**
	 * Sanitize the input for comparing the Entered Answer vs the Actual Answer -
	 * -Remove trailing and leading spaces
	 * -Remove pluralizations
	 * -Remove accents
	 * -Remove first words
	 * @param input
	 * @return
	 */
	String sanitizeInput(String input) {
		String inputAnswer = removeHTML(input);
		inputAnswer = removeTrailingAndLeadingSpaces(inputAnswer);
		inputAnswer = removePluralization(inputAnswer);
		inputAnswer = removeAccents(inputAnswer);
		inputAnswer = removeBrackets(inputAnswer);
		inputAnswer = removeAmpersand(inputAnswer);
		inputAnswer = removeBackslash(inputAnswer);
		return removeFirstWords(inputAnswer);
	}

	/**
	 * Remove pluralization -- we need to perform some length checks to avoid index out of bounds
	 * exceptions
	 * @param input
	 * @return
	 */
	String removePluralization(String input) {
		String lastLetters = (input.length() > 2) ?
				input.substring(input.length() - 2, input.length()) :
					input.substring(input.length() - 1, input.length());
		
		if(input.length() != 2 && lastLetters.equals("es")) {
			return input.substring(0, input.length() - 2);
		} else if (input.length() != 1 && lastLetters.charAt(lastLetters.length() - 1) == 's') {
			return input.substring(0, input.length() - 1);
		}
		
		return input;
	}

	/**
	 * Remove html via regex <i></i>
	 * @param input
	 * @return
	 */
	String removeHTML(String input) {
		return input.replaceAll("<\\/*[a-zA-Z]\\/*>", "");
	}

	/**
	 * remove trailing and leaving spaces
	 * @param input
	 * @return
	 */
	String removeTrailingAndLeadingSpaces(String input) {
		return input.toUpperCase().trim().replace("^\\s+", "");
	}

	/**
	 * remove THE, AN, and A first words -- assumes we've already changed the string
	 * to upper case
	 * @param input
	 * @return
	 */
	String removeFirstWords(String input) {
		String firstWord = input.split(" ")[0];
		
		if(firstWord.equals("THE") || firstWord.equals("AN") ||
				firstWord.equals("A")) {
			return input.split(" ", 2)[1];
		}
		
		return input;
	}

	/**
	 * remove accent characters
	 * @param input
	 * @return
	 */
	String removeAccents(String input){
		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * remove square brackets []
	 * @param input
	 * @return
	 */
	String removeBrackets(String input){
		return input.replaceAll("\\[|\\]", "");
	}

	/**
	 * remove quotation marks ""
	 * @param input
	 * @return
	 */
	String removeQuotes(String input){
		return input.replace("\"", "");
	}

	/**
	 * fix ampersand & to be "and"
	 * @param input
	 * @return
	 */
	String removeAmpersand(String input){
		return input.replace("&", "and");
	}

	/**
	 * remove backslash \
	 * @param input
	 * @return
	 */
	String removeBackslash(String input){
		return input.replace("\\", "");
	}

	
	/**
	 * Format the value
	 * @param value
	 * @param isRight
	 * @return
	 */
	String formatValue(int value, boolean isRight) {
		return isRight ? "<font color=\"green\">+$" + value + "</font>" : 
			 "<font color=\"red\">-$" + value + "</font>";
	}

}
