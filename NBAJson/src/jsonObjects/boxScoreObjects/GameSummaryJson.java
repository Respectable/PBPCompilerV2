package jsonObjects.boxScoreObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GameSummaryJson extends jsonObjects.NBAJsonObject
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

	public static GameSummaryJson parseGameSummary(String json)
	{
		Gson gson = new Gson();
		int homeID, awayID;
		String gameDate, gameID, season, broadcaster;
		JsonArray array;
		
		array = preProcessJson(json);
		array = array.get(0).getAsJsonArray();
		
		homeID =  gson.fromJson(array.get(6), int.class);
		awayID =  gson.fromJson(array.get(7), int.class);
		gameDate = gson.fromJson(array.get(0), String.class);
		gameID = gson.fromJson(array.get(2), String.class);
		season = gson.fromJson(array.get(8), String.class);
		broadcaster = gson.fromJson(array.get(10), String.class);
		
		return new GameSummaryJson(homeID, awayID, gameDate, gameID, 
				season, broadcaster);
	}
	
}
