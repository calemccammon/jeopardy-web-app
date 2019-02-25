package jeopardywebapp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class instantiates the Clue we receive from jService.
 * 
 * We only care about a clue's answer, question, value, category title,
 * and invalid count. We'll need the invalid count to skip questions
 * with bad data.
 */
public class Clue extends JSONObject implements ClueConstants, Comparable<Clue> {
	
	Clue() {
		super();
	}
	
	Clue(JSONObject json) {
		super(json.toString());
	}
	
	public String getAnswer() {
		return this.getString(ClueAPI.Question.getNode());
	}
	
	public String getQuestion() {
		return this.getString(ClueAPI.Question.getNode());
	}
	
	public int getValue() {
		Object valueFromJSON = this.get(
				ClueAPI.Value.getNode());
		return (valueFromJSON.equals(JSONObject.NULL) ? 0 : 
			(Integer) valueFromJSON);
	}
	
	private JSONObject getCategory() {
		return this.getJSONObject(ClueAPI.Category.getNode());	
	}
	
	public String getCategoryTitle() {
		JSONObject category = getCategory();
		return category.getString(ClueAPI.Title.getNode());
	}
	
	public int getCategoryId() {
		Object categoryIdFromJSON = this.get(
				ClueAPI.CategoryId.getNode());
		return (categoryIdFromJSON.equals(JSONObject.NULL) ? 0 :
			(Integer) categoryIdFromJSON);
	}
	
	public String getAirDate() {
		return this.getString(ClueAPI.AirDate.getNode());
	}
	
	public int getInvalidCount() {
		Object invalidCountFromJSON = this.get(
				ClueAPI.InvalidCount.getNode());
		return (invalidCountFromJSON.equals(JSONObject.NULL) ? 0 :
			(Integer) invalidCountFromJSON);
	}
	
	/**
	 * @return JSONObject - API call response
	 */
	static Clue callRandomClue() {
		try {
			HttpGet request = new HttpGet(RANDOM);
			request.addHeader("content-type", "application/json");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(request);
			
			//The response is coming to us as an array.
			//We expect it only to ever be of length 1
			JSONArray responseJSON = new JSONArray(EntityUtils.toString(
					response.getEntity()));
			
			Clue clue = new Clue(responseJSON.getJSONObject(0));
			
			if(clue.hasBadData()) {
				callRandomClue();
			} else {
				return clue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * We want to skip any clue that has null or empty data.
	 * @return boolean - tells us to skip
	 */
	boolean hasBadData() {
		return (hasBadAnswer() || hasBadQuestion() || hasBadValue() ||
				hasBadCategoryTitle() || hasBadInvalidCount() ||
				hasBadCategoryId() || hasBadAirDate());
	}
	
	private boolean hasBadAnswer() {
		String answer = getAnswer();
		return (answer == null || answer.isEmpty());
	}
	
	private boolean hasBadQuestion() {
		String question = getQuestion();
		return (question == null || question.isEmpty() || 
				question.contains("[audio clue]") ||
				question.toLowerCase().contains("seen here") || 
				question.toLowerCase().contains("shown here"));
	}
	
	private boolean hasBadCategoryTitle() {
		String categoryTitle = getCategoryTitle();
		return categoryTitle == null || categoryTitle.isEmpty();
	}
	
	private boolean hasBadInvalidCount() {
		int invalidCount = getInvalidCount();
		return invalidCount > 0;
	}
	
	private boolean hasBadCategoryId() {
		int categoryId = getCategoryId();
		return categoryId == 0;
	}
	
	private boolean hasBadValue() {
		int value = getValue();
		return value == 0;
	}
	
	private boolean hasBadAirDate() {
		String airDate = getAirDate();
		return airDate == null || airDate.isEmpty();
	}

	@Override
	public int compareTo(Clue clue) {
		int thisValue = this.getValue();
		int otherValue = clue.getValue();
		
		if(thisValue < otherValue) {
			return -1;
		} else if(thisValue == otherValue) {
			return 0;
		} else {
			return 1;
		}
	}
	
}
