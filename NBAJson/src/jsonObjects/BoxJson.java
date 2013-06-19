package jsonObjects;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jsonObjects.boxScoreObjects.*;

public class BoxJson extends NBAJsonObject
{
	private GameInfoJson gameInfo;
	private GameSummaryJson gameSummary;
	private ArrayList<InactiveJson> inactives;
	private ArrayList<OfficalJson> officals;
	private ArrayList<PlayerStatsJson> playerStats;
	
	public BoxJson() 
	{
		
	}
	
	public BoxJson(GameInfoJson gameInfo, GameSummaryJson gameSummary,
			ArrayList<InactiveJson> inactives, ArrayList<OfficalJson> officals, 
			ArrayList<PlayerStatsJson> playerStats)
	{
		this.gameInfo = gameInfo;
		this.gameSummary = gameSummary;
		this.inactives = inactives;
		this.officals = officals;
		this.playerStats = playerStats;
	}

	public GameSummaryJson getGameSummary() { return gameSummary; }
	public GameInfoJson getGameInfo() { return gameInfo; }
	public ArrayList<InactiveJson> getInactives() { return inactives; }
	public ArrayList<OfficalJson> getOfficals() { return officals; }
	public ArrayList<PlayerStatsJson> getPlayerStats() { return playerStats; }
	
	public static BoxJson parseBoxJson(String json)
	{
		GameSummaryJson gameSummary;
		GameInfoJson gameInfo;
		ArrayList<InactiveJson> inactive;
		ArrayList<OfficalJson> officals;
		ArrayList<PlayerStatsJson> playerStats;
		
		gameSummary = GameSummaryJson.parseGameSummary(json);
		gameInfo = GameInfoJson.parseGameInfo(json);
		inactive = InactiveJson.parseInactive(json);
		officals = OfficalJson.parseOfficals(json);
		playerStats = PlayerStatsJson.parsePlayerStats(json);
		
		return new BoxJson(gameInfo, gameSummary, inactive, officals,
				playerStats);
	}
	
	public static BoxJson getBoxScore(BufferedReader reader)
	{
		String json = readJson(reader);
		return parseBoxJson(json);
	}
	
	protected static JsonArray preProcessJson(String json)
	{
		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		
		return array;
	}
	
	public String getGameSeason()
	{
		String season = this.gameSummary.getSeason();
		int year = Integer.parseInt(season.substring(2, 4));
		return season + "-" + (year + 1);
	}
	
	
}
