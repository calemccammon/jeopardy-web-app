package jeopardywebapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClueList extends JSONObject implements ClueConstants {

	ClueList(Clue seedClue) {
		super();
		
		if(seedClue == null) {
			seedClue = retryCall();
		}
		
		if(seedClue != null) {
			put("category", seedClue.getCategoryTitle());
			put("clues", callClues(seedClue));
		} else {
			put("Error", "Error fetching clues.");
		}
	}
	
	private Clue retryCall() {
		for(int i = 0; i < 5; i++) {
			Clue clue = Clue.callRandomClue();
			if(clue != null) {
				return clue;
			}
		}
		return null;
	}
	
	private JSONArray callClues(Clue seedClue) {
		try {
			HttpGet request = new HttpGet(CATEGORY + seedClue.getCategoryId());
			request.addHeader("content-type", "application/json");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(request);
			
			if(response != null && response.getStatusLine().getStatusCode() == 200 && 
					response.getEntity().getContentLength() > 0) {
				JSONArray clues = new JSONArray(EntityUtils.toString(
						response.getEntity()));
				JSONArray newArray = new JSONArray();
				
				if(clues != null && clues.length() > 0) {
					for(int i = 0; i < clues.length(); i++) {
						Clue clue = new Clue(clues.getJSONObject(i));
						
						//Some categories have well over 100 clues, but we can match them
						//by air date.
						String airDate = clue.getAirDate();
						if(!clue.hasBadData() && seedClue.getAirDate().equals(airDate)) {
							newArray.put(clue);
						}
					}
					
					newArray = removeDuplicates(newArray);
					newArray = sortClues(newArray);
					return newArray;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//We receive some duplicates. We identify these by the Question string.
	//Sometimes the clues' questions will actually be duplicated but differ in terms
	//of spaces and punctuation, so we need to remove spaces and convert to upper case.
	private JSONArray removeDuplicates(JSONArray clues) {
		Map<String, Clue> clueMap = new HashMap<String, Clue>();
		
		for(int i = 0; i < clues.length(); i++) {
			Clue clue = new Clue(clues.getJSONObject(i));
			String question = clue.getQuestion();
			
			String cleanedQuestion = question.replaceAll("\\s+", "").toUpperCase();
			Set<String> keys = clueMap.keySet();
			Set<String> cleanedKeys = new LinkedHashSet<>();
			
			for(String key : keys) {
				cleanedKeys.add(key.replaceAll("\\s+", "").toUpperCase());
			}
			
			if(!cleanedKeys.contains(cleanedQuestion)) {
				clueMap.put(question, clue);
			}
		}
		
		return new JSONArray(clueMap.values());
	}
	
	private JSONArray sortClues(JSONArray clues) {
		ArrayList<Clue> list = new ArrayList<Clue>();
		
		for(int i = 0; i < clues.length(); i++) {
			Clue clue = new Clue(clues.getJSONObject(i));
			list.add(clue);
		}
		
		Collections.sort(list);
		return new JSONArray(list);
	}
	
	public JSONArray getClues() {
		return this.getJSONArray("clues");
	}
	
	public String getCategory() {
		return this.getString("category");
	}
}
