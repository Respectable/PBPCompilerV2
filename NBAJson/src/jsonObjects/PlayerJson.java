package jsonObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

public class PlayerJson 
{
	
	private int id;
	private String name;
	private boolean active;
	private int startYear, endYear;
	
	public PlayerJson()
	{
		this.id = -1;
		this.name = "";
		this.active = false;
		this.startYear = 0;
		this.endYear = 0;
	}
	
	public PlayerJson(int id, String name, boolean active,
			int startYear, int endYear)
	{
		this.id = id;
		this.name = name;
		this.active = active;
		this.startYear = startYear;
		this.endYear = endYear;
	}
	
	public int getPlayerID() { return this.id; }
	public String getPlayerName() { return this.name; }
	public boolean isPlayerActive() { return this.active; }
	public int getPlayerStartYear() { return this.startYear; }
	public int getPlayerEndYear() { return this.endYear; }
	
	public static String cleanPlayerJson(BufferedReader br)
	{
		String json = "";
		try {
			 json = br.readLine();
			 br.close();
			 json = json.replace("playerinfocallback(", "");
			 json = json.substring(0, json.length()-1);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static ArrayList<PlayerJson> parsePlayerJson(String json)
	{
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		ArrayList<PlayerJson> players = new ArrayList<PlayerJson>();
		int id, startYear, endYear, active;
		String name;
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(0).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		for(JsonElement element : array)
		{
			array = element.getAsJsonArray();
			id = gson.fromJson(array.get(0), int.class);
			name = gson.fromJson(array.get(1), String.class);
			active = gson.fromJson(array.get(2), int.class);
			
			if (array.get(3) != JsonNull.INSTANCE)
				startYear = gson.fromJson(array.get(3), int.class);
			else
				startYear = 0;
			
			if (array.get(4) != JsonNull.INSTANCE)
				endYear = gson.fromJson(array.get(4), int.class);
			else
				endYear = 0;
			
			players.add(new PlayerJson(id, name, (active==1), startYear, endYear));
		}
		
		return players;
	}
	
	public static ArrayList<PlayerJson> getPlayers(BufferedReader reader)
	{
		String json = cleanPlayerJson(reader);
		return parsePlayerJson(json);
	}
	
	@Override
    public String toString() 
	{
      return String.format("(PERSON_ID=%s, DISPLAY_LAST_COMMA_FIRST=%s,"+
    		  "ROSTERSTATUS=%s, FROM_YEAR=%s, TO_YEAR=%s,)", this.id, this.name,
    		  this.active, this.startYear, this.endYear);
    }

}
