package jeopardywebapp;

public interface ClueConstants {

	String RANDOM = "http://jservice.io/api/random";
	String CATEGORY = "http://jservice.io/api/clues?category=";
	
	/**
	 * enum stores all nodes we find in the JSON
	 */
	enum ClueAPI {
		Answer("answer"),
		Question("question"),
		Value("value"),
		InvalidCount("invalid_count"),
		AirDate("airdate"),
		Category("category"),
		CategoryId("category_id"),
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
	
	enum Status {
		pending, revealed, incorrect, correct;
	}
}
