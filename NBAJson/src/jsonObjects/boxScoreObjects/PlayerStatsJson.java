package jsonObjects.boxScoreObjects;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class PlayerStatsJson 
{
	private int teamID, playerID;
	private String teamAbbr, playerName, startPosition;
	
	public PlayerStatsJson()
	{
		
	}

	public PlayerStatsJson(int teamID, int playerID, String teamAbbr,
			String playerName, String startPosition) 
	{
		this.teamID = teamID;
		this.playerID = playerID;
		this.teamAbbr = teamAbbr;
		this.playerName = playerName;
		this.startPosition = startPosition;
	}

	public int getTeamID() { return teamID; }
	public int getPlayerID() { return playerID; }
	public String getTeamAbbr() { return teamAbbr; }
	public String getPlayerName() { return playerName; }
	public String getStartPosition() { return startPosition; }
	
	public static ArrayList<PlayerStatsJson> parsePlayerStats(JsonArray array)
	{
		Gson gson = new Gson();
		int teamID, playerID;
		String teamAbbr, playerName, startPosition;
		JsonArray tempArray;
		ArrayList<PlayerStatsJson> players = new ArrayList<PlayerStatsJson>();
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			teamID = gson.fromJson(tempArray.get(1), int.class);
			playerID = gson.fromJson(tempArray.get(4), int.class);
			teamAbbr =  gson.fromJson(tempArray.get(2), String.class);
			playerName =  gson.fromJson(tempArray.get(5), String.class);
			startPosition =  gson.fromJson(tempArray.get(6), String.class);
			players.add(new PlayerStatsJson(teamID, playerID, teamAbbr, 
					playerName, startPosition));
		}
		
		return players;
	}
	
	
}
