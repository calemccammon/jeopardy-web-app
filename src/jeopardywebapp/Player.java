package jeopardywebapp;

public class Player {

	private String name;
	private int score = 0;
	private int totalSkipped = 0;
	private int totalRight = 0;
	private int totalWrong = 0;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
