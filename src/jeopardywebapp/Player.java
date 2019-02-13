package jeopardywebapp;

public class Player implements Comparable <Player>{

	private String name;
	private int score = 0;
	private int totalSkipped = 0;
	private int totalRight = 0;
	private int totalWrong = 0;
	
	private Player() {}
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
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
	
	private void setName(String name) {
		this.name = name;
	}
	
	public void addScore(int value, boolean isRight) {
		this.score = score + (isRight ? value : -value);
	}
	
	public String getScore(int scoreInt) {
		return this.score < 0 ? "<font color=\"red\">-$" + String.valueOf(scoreInt).replace("-", "") + 
				"</font>": "<font color=\"green\">+$" + scoreInt + "</font>";
	}
	
	public static Player getInstance(String name) {
		PlayerHolder.INSTANCE.setName(name);;
		return PlayerHolder.INSTANCE;
	}
	
	public static Player getInstance() {
		return PlayerHolder.INSTANCE;
	}
	
	public static class PlayerHolder {
		public static final Player INSTANCE = new Player();
	}
}
