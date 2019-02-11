package jeopardywebapp;


public class Player {

	private String name;
	private int score = 0;
	private int totalSkipped = 0;
	private int totalRight = 0;
	private int totalWrong = 0;
	
	private Player() {}
	
	public String getName() {
		return this.name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public void addScore(int value, boolean isRight) {
		this.score = score + (isRight ? value : -value);
	}
	
	public String getScore() {
		return this.score < 0 ? "<font color=\"red\">-$" + String.valueOf(score).replace("-", "") + 
				"</font>": "<font color=\"green\">+$" + score + "</font>";
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
