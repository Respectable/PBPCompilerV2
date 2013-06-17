package jsonObjects.boxScoreObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class GameInfoJson 
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
	
	public static GameInfoJson parseGameInfo(JsonArray array)
	{
		Gson gson = new Gson();
		int attendance;
		String gameTime;
		JsonElement element;
		JsonArray tempArray;
		
		element = array.get(0);
		tempArray = element.getAsJsonArray();
		
		attendance =  gson.fromJson(tempArray.get(1), int.class);
		gameTime =  gson.fromJson(tempArray.get(2), String.class);
		
		return new GameInfoJson(attendance, gameTime);
	}
	
	
}
