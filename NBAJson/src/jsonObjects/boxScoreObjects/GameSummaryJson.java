package jsonObjects.boxScoreObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class GameSummaryJson 
{
	private int homeID, awayID;
	private String gameDate, gameID, season, broadcaster;
	
	public GameSummaryJson()
	{
		
	}
	
	public GameSummaryJson(int home, int away, String gameDate, String gameID,
			String season, String broadcaster)
	{
		this.homeID = home;
		this.awayID = away;
		this.gameDate = gameDate;
		this.gameID = gameID;
		this.season = season;
		this.broadcaster = broadcaster;
	}
	
	public int getHomeID() { return homeID; }
	public int getAwayID() { return awayID; }
	public String getGameDate() { return gameDate; }
	public String getGameID() { return gameID; }
	public String getSeason() { return season; }
	public String getBroadcaster() { return broadcaster; }

	public static GameSummaryJson parseGameSummary(JsonArray array)
	{
		Gson gson = new Gson();
		int homeID, awayID;
		String gameDate, gameID, season, broadcaster;
		JsonElement element;
		JsonArray tempArray;
		
		element = array.get(0);
		tempArray = element.getAsJsonArray();
		
		homeID =  gson.fromJson(tempArray.get(6), int.class);
		awayID =  gson.fromJson(tempArray.get(7), int.class);
		gameDate = gson.fromJson(tempArray.get(0), String.class);
		gameID = gson.fromJson(tempArray.get(2), String.class);
		season = gson.fromJson(tempArray.get(8), String.class);
		broadcaster = gson.fromJson(tempArray.get(10), String.class);
		
		return new GameSummaryJson(homeID, awayID, gameDate, gameID, 
				season, broadcaster);
	}
}
