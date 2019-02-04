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
public class Clue {

	private static final String ENDPOINT = "http://jservice.io/api/random";
	private String answer;
	private String question;
	private int value;
	private String categoryTitle;
	private int invalidCount;
	private JSONObject clueJSON;
	
	Clue() {
		initializeClue();
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getCategoryTitle() {
		return this.categoryTitle;
	}
	
	public int getInvalidCount() {
		return this.invalidCount;
	}
	
	public JSONObject getJSON() {
		return this.clueJSON;
	}
	
	/**
	 * @return JSONObject - API call response
	 */
	private JSONObject callClue() {
		JSONObject clueData = null;
		
		try {
			HttpGet request = new HttpGet(ENDPOINT);
			request.addHeader("content-type", "application/json");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse response = httpClient.execute(request);
			
			//The response is coming to us as an array.
			//We expect it only to ever be of length 1
			JSONArray responseJSON = new JSONArray(EntityUtils.toString(
					response.getEntity()));;
			clueData = responseJSON.getJSONObject(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clueData;
	}
	
	/**
	 * set all the instance variables for the clue
	 */
	private void initializeClue() {
		try {
			this.clueJSON = callClue();
			this.answer = clueJSON.getString(ClueAPI.Answer.getNode());
			this.question = clueJSON.getString(ClueAPI.Question.getNode());
			
			//These fields can be null but we want to treat them as integers.
			Object valueFromJSON = clueJSON.get(
					ClueAPI.Value.getNode());
			this.value = (valueFromJSON.equals(JSONObject.NULL) ? 0 : 
				(Integer) valueFromJSON);
			
			Object invalidCountFromJSON = clueJSON.get(
					ClueAPI.InvalidCount.getNode());
			this.invalidCount = (invalidCountFromJSON.equals(JSONObject.NULL) ? 0 :
				(Integer) invalidCountFromJSON);
			
			JSONObject category = clueJSON.getJSONObject(ClueAPI.Category.getNode());
			this.categoryTitle = category.getString(ClueAPI.Title.getNode());
			
			if(hasBadData()) {
				initializeClue();
			}
		} catch (Exception e) {
			System.out.println(clueJSON.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * We want to skip any clue that has null or empty data.
	 * @return boolean - tells us to skip
	 */
	private boolean hasBadData() {
		return (answer == null || answer.isEmpty() ||
				question == null || question.isEmpty() || question.equals("[audio clue]") ||
				value == 0 ||invalidCount > 0 ||
				categoryTitle == null || categoryTitle.isEmpty());
	}
	/**
	 * enum stores all nodes we find in the JSON
	 */
	static enum ClueAPI {
		Answer("answer"),
		Question("question"),
		Value("value"),
		InvalidCount("invalid_count"),
		Category("category"),
		Title("title");
		
		private String node;
		
		ClueAPI(String node) {
			setNode(node);
		}
		
		private void setNode(String node) {
			this.node = node;
		}
		
		public String getNode() {
			return this.node;
		}
	}
}
