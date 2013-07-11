package jsonObjects.boxScoreObjects;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerStatsJson 
{
	private int teamID, playerID;
	private String teamAbbr, playerName, startPosition, comment;
	
	public PlayerStatsJson()
	{
		
	}

	public PlayerStatsJson(int teamID, int playerID, String teamAbbr,
			String playerName, String startPosition, String comment) 
	{
		this.teamID = teamID;
		this.playerID = playerID;
		this.teamAbbr = teamAbbr;
		this.playerName = playerName;
		this.startPosition = startPosition;
		this.comment = comment;
	}

	public int getTeamID() { return teamID; }
	public int getPlayerID() { return playerID; }
	public String getTeamAbbr() { return teamAbbr; }
	public String getPlayerName() { return playerName; }
	public String getStartPosition() { return startPosition; }
	public String getComment() {return comment; }
	
	public static ArrayList<PlayerStatsJson> parsePlayerStats(String json)
	{
		Gson gson = new Gson();
		int teamID, playerID;
		String teamAbbr, playerName, startPosition, comment;
		JsonArray array, tempArray;
		ArrayList<PlayerStatsJson> players = new ArrayList<PlayerStatsJson>();
		
		array = preProcessJson(json);
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			teamID = gson.fromJson(tempArray.get(1), int.class);
			playerID = gson.fromJson(tempArray.get(4), int.class);
			teamAbbr =  gson.fromJson(tempArray.get(2), String.class);
			playerName =  gson.fromJson(tempArray.get(5), String.class);
			startPosition =  gson.fromJson(tempArray.get(6), String.class);
			comment =  gson.fromJson(tempArray.get(7), String.class);
			players.add(new PlayerStatsJson(teamID, playerID, teamAbbr, 
					playerName, startPosition, comment));
		}
		
		return players;
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(4).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
	
	
}
