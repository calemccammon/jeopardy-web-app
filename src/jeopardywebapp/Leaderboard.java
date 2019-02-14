package jeopardywebapp;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

public class Leaderboard {
	ArrayList<Player> leaders;
	
	public Leaderboard() {
		leaders = new ArrayList<Player>();
		fillFakeLeaders();
	}
	
	private void fillFakeLeaders(){
		for (int i = 0; i<60; i++) {
			Player player = new Player ("Player"+i);
			player.setScore((int)(Math.random()*100));
			addPlayer(player);
		}
	}
	
	private void sort() {
		Collections.sort(leaders, Collections.reverseOrder());
	}
	
	public void addPlayer(Player player) {
		leaders.add(player);
		sort();
		int k = leaders.size();
		if (k > 5)
			leaders.subList(5, k).clear();
	}
	
	public JSONArray getJSON() {
		JSONArray list = new JSONArray();
		int rank = 1;
		for (Player player : leaders) {
			JSONObject obj = new JSONObject();
			obj.put("rank", rank++);
			obj.put("name", player.getName());
			obj.put("score", player.getScore());
			list.put(obj);
		}
		return list;
	}
}
