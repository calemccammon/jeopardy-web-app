package jeopardywebapp;

import java.text.Normalizer;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public interface AnswerManipulator {
	
	/**
	 * Compare user-submitted answer to actual answer
	 * Uses Levenshtein Algorithm and matches optional text from actual answer string
	 * @param entry, actualAnswer
	 * @return boolean - tells us if it's a match
	 */
	static boolean compareAnswer(String entry, String actualAnswer) {
		boolean isRight = false;
		NormalizedLevenshtein nl = new NormalizedLevenshtein();
		Double maxDistance = 0.3;
		
		//basic comparison
		if (nl.distance(entry, actualAnswer) < maxDistance) isRight = true;
		
		//compare X and Y to Y and X
		else if (actualAnswer.split(" AND ").length == 2 && entry.split(" AND ").length == 2) {
			String[] actualArray = actualAnswer.split(" AND ");
			String[] entryArray = entry.split(" AND ");
			
			if (nl.distance(actualArray[0], entryArray[1]) < maxDistance && nl.distance(actualArray[1], entryArray[0]) < maxDistance) isRight = true;
		}
		
		//compare using optional text inside parentheses
		if (actualAnswer.contains(")")) {
			String [] actualAnswerArray = actualAnswer.replace(")","").split("\\(");
			for (int i = 0; i < actualAnswerArray.length; i++) {
				actualAnswerArray[i] = removeOptionalWords(actualAnswerArray[i]);
				if(compareAnswer(entry, actualAnswerArray[i])) isRight = true;
			}
			if (compareAnswer(entry, String.join(" ", actualAnswerArray))) isRight = true;
		}
		
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
	static String sanitizeInput(String input) {
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
	static String removePluralization(String input) {
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
	static String removeHTML(String input) {
		return input.replaceAll("<\\/*[a-zA-Z]\\/*>", "");
	}

	/**
	 * remove trailing and leaving spaces
	 * @param input
	 * @return
	 */
	static String removeTrailingAndLeadingSpaces(String input) {
		return input.toUpperCase().trim().replace("^\\s+", "");
	}

	/**
	 * remove THE, AN, and A first words -- assumes we've already changed the string
	 * to upper case
	 * @param input
	 * @return
	 */
	static String removeFirstWords(String input) {
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
	static String removeAccents(String input){
		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * remove square brackets []
	 * @param input
	 * @return
	 */
	static String removeBrackets(String input){
		return input.replaceAll("\\[|\\]", "");
	}

	/**
	 * remove quotation marks ""
	 * @param input
	 * @return
	 */
	static String removeQuotes(String input){
		return input.replace("\"", "");
	}

	/**
	 * fix ampersand & to be "and"
	 * @param input
	 * @return
	 */
	static String removeAmpersand(String input){
		return input.replace("&", "AND");
	}

	/**
	 * remove backslash \
	 * @param input
	 * @return
	 */
	static String removeBackslash(String input){
		return input.replace("\\", "");
	}
	
	/**
	 * remove OR and IS ALSO ACCEPTED
	 * @param input
	 * @return
	 */
	static String removeOptionalWords(String str) {
		return str.replaceAll("^OR ","").replaceAll(" IS ALSO ACCEPTED$", "");
	}
	
	/**
	 * Format the value
	 * @param value
	 * @param isRight
	 * @return
	 */
	static String formatValue(int value, boolean isRight) {
		return isRight ? "<font color=\"green\">+$" + value + "</font>" : 
			 "<font color=\"red\">-$" + value + "</font>";
	}
}
