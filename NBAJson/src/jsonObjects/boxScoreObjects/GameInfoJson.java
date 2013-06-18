package jsonObjects.boxScoreObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GameInfoJson extends jsonObjects.NBAJsonObject
{
	private int attendance;
	private String gameTime;
	
	public GameInfoJson()
	{
		this.attendance = 0;
		this.gameTime = "";
	}
	
	public GameInfoJson(int attendance, String gameTime)
	{
		this.attendance = attendance;
		this.gameTime = gameTime;
	}

	public int getAttendance() { return attendance; }
	public String getGameTime() { return gameTime; }
	
	public static GameInfoJson parseGameInfo(String json)
	{
		Gson gson = new Gson();
		int attendance;
		String gameTime;
		JsonArray array;
		
		array = preProcessJson(json);
		array = array.get(0).getAsJsonArray();
		
		attendance =  gson.fromJson(array.get(1), int.class);
		gameTime =  gson.fromJson(array.get(2), String.class);
		
		return new GameInfoJson(attendance, gameTime);
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(8).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
	
	
}
