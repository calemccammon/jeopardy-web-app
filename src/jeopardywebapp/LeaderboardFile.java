package jeopardywebapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

public class LeaderboardFile extends File {
	
	private static final long serialVersionUID = 1L;
	private static final String FILE = System.getProperty("catalina.base") + 
			"\\leaderboardtesting.json";
	private Player player = Player.getInstance();
	
	private LeaderboardFile() {
		super(FILE);
	}
	
	//Write leaders to the file
	void writeToFile() {
		try {
			JSONArray leaders = getSortedLeaders(player);
			FileWriter fileWriter = new FileWriter(FILE);
			JSONWriter writer = new JSONWriter(fileWriter);
			writer.object();
			writer.key("leaders");
			writer.value(leaders);
			writer.endObject();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Get the current leaders from the file.
	//If the file doesn't exist then we return an empty array
	JSONArray getLeaders() {
		JSONArray leaders = new JSONArray();
		
		if(this.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(FILE));
		        StringBuilder builder = new StringBuilder();
		        String line = reader.readLine();
		        while (line != null) {
		            builder.append(line);
		            line = reader.readLine();
		        }
		        
		        JSONObject result = new JSONObject(builder.toString());
		        leaders = result.getJSONArray("leaders");
		        reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return leaders;
	}
	
	//Sort the leaders with the current player
	//We remove the last player if we have more than 6
	JSONArray getSortedLeaders(Player player) {
		JSONArray leaders = getLeaders();
		List<Player> players = new ArrayList<Player>();
		players.add(player);
		
		for(int i = 0; i < leaders.length(); i++) {
			JSONObject json = leaders.getJSONObject(i);
			players.add(Player.getPlayerFromJSON(json));
		}
		
		if(players.size() > 1) {
			Collections.sort(players, Collections.reverseOrder());
		}
		
		if(players.size() >= 6) {
			players.remove(players.size() - 1);
		}
		
		JSONArray sortedArray = new JSONArray();
		for(Player leader : players) {
			sortedArray.put(leader.getScoreData());
		}
		
		return sortedArray;
	}
	
	//Singleton pattern - we expect to only ever instantiate this class once
	public static void createFile() {
		LeaderboardFile file = new LeaderboardFile();
		file.writeToFile();
	}
	
	//Declare keys in our JSON
	enum DataKey {
		leaders, player, name, score
	}
}
