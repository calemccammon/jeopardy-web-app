package jeopardywebapp;

import org.json.JSONObject;

public class Player implements Comparable <Player> {

	private String name;
	private int score = 0;
	private int totalSkipped = 0;
	private int totalRight = 0;
	private int totalWrong = 0;
	private static Player INSTANCE = null;
	
	@SuppressWarnings("unused")
	private Player() {}
	
	public Player(String name) {
		this.name = name;
	}
	
    public Player(String name, int score, int totalSkipped, int totalRight, int totalWrong) {
        this.name = name;
        this.score = score;
        this.totalSkipped = totalSkipped;
        this.totalRight = totalRight;
        this.totalWrong = totalWrong;
    }
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getTotalRight() {
		return this.totalRight;
	}
	
	public int getTotalWrong() {
		return this.totalWrong;
	}
	
	public int getTotalSkipped() {
		return this.totalSkipped;
	}
	
	//TODO remove this after bogus leaderboard creation is gone
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Player other) {
		if (this.score < other.score) {
			return -1;
		}
		if (this.score == other.score) {
			return 0;
		}
		return 1;
	}
	
	public void addScore(int value, boolean isRight) {
		this.score += (isRight ? value : -value);
		this.totalRight += (isRight ? 1 : 0);
		this.totalWrong += (isRight ? 0 : 1);
	}
	
    public String getScore(int scoreInt) {
        return this.score < 0 ? "<font color=\"red\">-$" + String.valueOf(scoreInt).replace("-", "") + 
                "</font>": "<font color=\"green\">+$" + scoreInt + "</font>";
    }
    
	public static Player getInstance(String name) {
		if(INSTANCE == null) {
			INSTANCE = new Player(name);
		}
		
		return INSTANCE;
	}
	
	public static Player getInstance() {
		return INSTANCE;
	}
	
	public static void makeNull() {
		INSTANCE = null;
	}
	
	//Return JSON with formatted Score
	public JSONObject getScoreData() {
		JSONObject obj = new JSONObject();
		
		obj.put("name", getName());
		obj.put("score_int", getScore());
		obj.put("score", (getScore()>0 ? "" : "-") + "$" + Math.abs(getScore()));
		obj.put("total_right", getTotalRight());
		obj.put("total_wrong", getTotalWrong());
		obj.put("total_skipped", getTotalSkipped());
		
		return obj;
	}
	
	//Convert given JSON to Player
	public static Player getPlayerFromJSON(JSONObject json) {
		String name = json.getString("name");
		int score = json.getInt("score_int");
		int right = json.getInt("total_right");
		int wrong = json.getInt("total_wrong");
		int skipped = json.getInt("total_skipped");
		
		return new Player(name, score, skipped, right, wrong);
	}
}
