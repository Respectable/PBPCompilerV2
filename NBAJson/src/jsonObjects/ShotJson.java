package jsonObjects;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ShotJson extends NBAJsonObject
{
	private String gameID, playerName, teamName, actionType,
					shotType, shotZoneBasic, shotZoneArea, shotZoneRange;
	private int gameEventID, playerID, teamID, period, minutesRemaining, 
				secondsRemaining, shotDistance, x, y;
	private boolean shotAttempted, shotMade;
	
	public ShotJson()
	{
		this.gameID = "";
		this.playerName = "";
		this.teamName = "";
		this.actionType = "";
		this.shotType = "";
		this.shotZoneBasic = "";
		this.shotZoneArea = "";
		this.shotZoneRange = "";
		this.gameEventID = 0;
		this.playerID = 0;
		this.teamID = 0;
		this.period = 0;
		this.minutesRemaining = 0;
		this.secondsRemaining = 0;
		this.shotDistance = 0;
		this.x = 0;
		this.y = 0;
		this.shotAttempted = false;
		this.shotMade = false;
	}
	
	public ShotJson(String gameID, String playerName, String teamName,
			String actionType, String shotType, String shotZoneBasic,
			String shotZoneArea, String shotZoneRange, int gameEventID,
			int playerID, int teamID, int period, int minutesRemaining,
			int secondsRemaining, int shotDistance, int x, int y,
			boolean shotAttempted, boolean shotMade) 
	{
		this.gameID = gameID;
		this.playerName = playerName;
		this.teamName = teamName;
		this.actionType = actionType;
		this.shotType = shotType;
		this.shotZoneBasic = shotZoneBasic;
		this.shotZoneArea = shotZoneArea;
		this.shotZoneRange = shotZoneRange;
		this.gameEventID = gameEventID;
		this.playerID = playerID;
		this.teamID = teamID;
		this.period = period;
		this.minutesRemaining = minutesRemaining;
		this.secondsRemaining = secondsRemaining;
		this.shotDistance = shotDistance;
		this.x = x;
		this.y = y;
		this.shotAttempted = shotAttempted;
		this.shotMade = shotMade;
	}

	public String getGameID() { return gameID; }
	public String getPlayerName() { return playerName; }
	public String getTeamName() { return teamName; }
	public String getActionType() { return actionType; }
	public String getShotType() { return shotType; }
	public String getShotZoneBasic() { return shotZoneBasic; }
	public String getShotZoneArea() { return shotZoneArea; }
	public String getShotZoneRange() { return shotZoneRange; }
	public int getGameEventID() { return gameEventID; }
	public int getPlayerID() { return playerID; }
	public int getTeamID() { return teamID; }
	public int getPeriod() { return period; }
	public int getMinutesRemaining() { return minutesRemaining; }
	public int getSecondsRemaining() { return secondsRemaining; }
	public int getShotDistance() { return shotDistance; }
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isShotAttempted() { return shotAttempted; }
	public boolean isShotMade() { return shotMade; }
	
	public static ArrayList<ShotJson> parseShotJson(String json)
	{
		Gson gson = new Gson();
		ArrayList<ShotJson> shots = new ArrayList<ShotJson>();
		String gameID, playerName, teamName, actionType,
			shotType, shotZoneBasic, shotZoneArea, shotZoneRange;
		int gameEventID, playerID, teamID, period, minutesRemaining, 
			secondsRemaining, shotDistance, x, y, shotAttempted, shotMade;
		
		JsonArray array = preProcessJson(json);
		
		for(JsonElement element : array)
		{
			JsonArray shotArray = element.getAsJsonArray();
			gameID = gson.fromJson(shotArray.get(1), String.class);
			gameEventID = gson.fromJson(shotArray.get(2), int.class);
			playerID = gson.fromJson(shotArray.get(3), int.class);
			playerName = gson.fromJson(shotArray.get(4), String.class);
			teamID = gson.fromJson(shotArray.get(5), int.class);
			teamName = gson.fromJson(shotArray.get(6), String.class);
			period = gson.fromJson(shotArray.get(7), int.class);
			minutesRemaining = gson.fromJson(shotArray.get(8), int.class);
			secondsRemaining = gson.fromJson(shotArray.get(9), int.class);
			actionType = gson.fromJson(shotArray.get(11), String.class);
			shotType = gson.fromJson(shotArray.get(12), String.class);
			shotZoneBasic = gson.fromJson(shotArray.get(13), String.class);
			shotZoneArea = gson.fromJson(shotArray.get(14), String.class);
			shotZoneRange = gson.fromJson(shotArray.get(15),String.class);
			shotDistance = gson.fromJson(shotArray.get(16), int.class);
			x = gson.fromJson(shotArray.get(17), int.class);
			y = gson.fromJson(shotArray.get(18), int.class);
			shotAttempted = gson.fromJson(shotArray.get(19), int.class);
			shotMade = gson.fromJson(shotArray.get(20), int.class);
			
			shots.add(new ShotJson(gameID, playerName, teamName, actionType,
					shotType, shotZoneBasic, shotZoneArea, shotZoneRange,
					gameEventID, playerID, teamID, period, minutesRemaining,
					secondsRemaining, shotDistance, x, y, (shotAttempted == 1),
					(shotMade == 1)));
		}
		
		return shots;
	}
	
	public static ArrayList<ShotJson> getShots(BufferedReader reader)
	{
		String json = readJson(reader);
		return parseShotJson(json);
	}
}
