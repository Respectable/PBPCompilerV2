package jsonObjects.boxScoreObjects;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class InactiveJson 
{
	private int playerID, teamID;
	private String firstName, lastName, teamAbbr;
	
	public InactiveJson()
	{
		this.playerID = 0;
		this.teamID = 0;
		this.firstName = "";
		this.lastName = "";
		this.teamAbbr = "";
	}

	public InactiveJson(int playerID, int teamID, String firstName,
			String lastName, String teamAbbr) 
	{
		this.playerID = playerID;
		this.teamID = teamID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.teamAbbr = teamAbbr;
	}

	public int getPlayerID() { return playerID; }
	public int getTeamID() { return teamID; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getTeamAbbr() { return teamAbbr; }
	
	public static ArrayList<InactiveJson> parseInactive(String json)
	{
		Gson gson = new Gson();
		int playerID, teamID;
		String firstName, lastName, teamAbbr;
		JsonArray array, tempArray;
		ArrayList<InactiveJson> inactives = new ArrayList<InactiveJson>();
		
		array = preProcessJson(json);
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			playerID = gson.fromJson(tempArray.get(0), int.class);
			teamID = gson.fromJson(tempArray.get(4), int.class);
			firstName =  gson.fromJson(tempArray.get(1), String.class);
			lastName =  gson.fromJson(tempArray.get(2), String.class);
			teamAbbr =  gson.fromJson(tempArray.get(7), String.class);
			inactives.add(new InactiveJson(playerID, teamID, firstName, 
					lastName, teamAbbr));
		}
		
		return inactives;
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(9).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
	
}
