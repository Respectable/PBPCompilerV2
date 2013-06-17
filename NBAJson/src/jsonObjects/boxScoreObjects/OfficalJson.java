package jsonObjects.boxScoreObjects;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class OfficalJson 
{
	private int officalID;
	private String firstName, lastName, jerseyNum;
	
	public OfficalJson()
	{
		
	}

	public OfficalJson(int officalID, String firstName, String lastName,
			String jerseyNum) 
	{
		this.officalID = officalID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jerseyNum = jerseyNum;
	}

	public int getOfficalID() { return officalID; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getJerseyNum() { return jerseyNum; }
	
	public static ArrayList<OfficalJson> parseOfficals(JsonArray array)
	{
		Gson gson = new Gson();
		int officalID;
		String firstName, lastName, jerseyNum;
		JsonArray tempArray;
		ArrayList<OfficalJson> officals = new ArrayList<OfficalJson>();
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			officalID = gson.fromJson(tempArray.get(1), int.class);
			firstName =  gson.fromJson(tempArray.get(2), String.class);
			lastName =  gson.fromJson(tempArray.get(5), String.class);
			jerseyNum =  gson.fromJson(tempArray.get(6), String.class);
			officals.add(new OfficalJson(officalID, firstName, lastName, 
					jerseyNum));
		}
		
		return officals;
	}
}
