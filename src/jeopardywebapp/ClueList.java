package jeopardywebapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
		super.put("category", seedClue.getCategoryTitle());
		super.put("clues", callClues(seedClue));
	}
	
	private JSONArray callClues(Clue seedClue) {
		try {
			HttpGet request = new HttpGet(CATEGORY + seedClue.getCategoryId());
			request.addHeader("content-type", "application/json");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(request);
			
			JSONArray clues = new JSONArray(EntityUtils.toString(
					response.getEntity()));
			JSONArray newArray = new JSONArray();
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//We receive some duplicates. We identify these by the Question string.
	private JSONArray removeDuplicates(JSONArray clues) {
		Map<String, Clue> clueMap = new HashMap<String, Clue>();
		
		for(int i = 0; i < clues.length(); i++) {
			Clue clue = new Clue(clues.getJSONObject(i));
			String question = clue.getQuestion();
			if(!clueMap.containsKey(question)) {
				clueMap.put(clue.getQuestion(), clue);
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
