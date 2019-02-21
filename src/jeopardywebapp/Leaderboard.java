package jeopardywebapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Leaderboard extends File {
	
	private static final long serialVersionUID = 1L;
	private static final String FILE = System.getProperty("catalina.base") + 
			"\\leaderboard\\leaderboard.json";
	private Player player = Player.getInstance();
	private static Leaderboard instance = null;
	
	public Leaderboard() {
		super(FILE);
	}
	
	//Write leaders to the file
	void writeToFile() {
		try {
			if(!Files.exists(Paths.get(FILE).getParent())) {
				Files.createDirectories(Paths.get(FILE).getParent());
			}
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
	
	JSONObject readFile() {
		JSONObject result = null;
		
		if(this.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(FILE));
		        StringBuilder builder = new StringBuilder();
		        String line = reader.readLine();
		        while (line != null) {
		            builder.append(line);
		            line = reader.readLine();
		        }
		        
		        result = new JSONObject(builder.toString());
		        reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//Get the current leaders from the file.
	//If the file doesn't exist then we return an empty array
	JSONArray getLeaders() {
		if(this.exists()) {
			JSONObject fileContent = readFile();
			return fileContent.getJSONArray("leaders");
		} else {
			return new JSONArray();
		}
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
		for(int i = 0; i < players.size(); i++) {
			Player leader = players.get(i);
			JSONObject data = leader.getScoreData();
			data.put("rank", i + 1);
			sortedArray.put(data);
		}
		
		return sortedArray;
	}
	
	//Singleton pattern - we expect to only ever instantiate this class once
	public static void createFile() {
		if(instance == null) {
			instance = new Leaderboard();
		}
		
		instance.writeToFile();
	}
	
	//Declare keys in our JSON
	enum DataKey {
		leaders, player, name, score
	}
}
