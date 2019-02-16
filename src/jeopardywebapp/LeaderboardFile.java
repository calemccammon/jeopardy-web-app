package jeopardywebapp;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class LeaderboardFile {
	
	private static final long serialVersionUID = 1L;
	private static final String FILE_TARGET = System.getProperty("catalina.base") + 
			"\\leaderboardtesting.csv";
	private static File file = new File(FILE_TARGET);
	private Player player = Player.getInstance();
	
	private LeaderboardFile() {}
	
	void writeToFile() {
		try {
			ArrayList<Player> leaders = (ArrayList<Player>) sortLeaders(player);
			Writer writer = Files.newBufferedWriter(Paths.get(FILE_TARGET));
			
			StatefulBeanToCsv<Player> beanToCsv = new StatefulBeanToCsvBuilder<Player>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
					.build();
			
			beanToCsv.write(leaders);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	List<Player> getLeaders() {
		if(file.exists()) {
			try {
				Reader reader = Files.newBufferedReader(Paths.get(FILE_TARGET));
				CsvToBean<Player> bean = new CsvToBeanBuilder<Player>(reader)
						.withType(Player.class)
						.withIgnoreLeadingWhiteSpace(true)
						.build();

				return bean.parse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ArrayList<Player>();
	}
	
	List<Player> sortLeaders(Player player) {
		List<Player> leaders = getLeaders();
		leaders.add(player);
		
		if(leaders.size() > 1) {
			Collections.sort(leaders);
		}
		
		if(leaders.size() >= 6) {
			leaders.remove(leaders.size() - 1);
		}
		
		return leaders;
	}
	
	//Singleton pattern - we expect to only ever instantiate this class once
	public static void createFile() {
		LeaderboardFile file = FileHolder.INSTANCE;
		file.writeToFile();
	}
	
	private static class FileHolder {
		private static final LeaderboardFile INSTANCE = new LeaderboardFile();
	}
	
	//Declare keys in our JSON
	enum DataKey {
		leaders, player, name, score
	}
}
