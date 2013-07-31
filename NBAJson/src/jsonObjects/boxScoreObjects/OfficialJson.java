package jsonObjects.boxScoreObjects;

import java.util.ArrayList;
import java.util.Comparator;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OfficialJson 
{
	private int officalID;
	private String firstName, lastName, jerseyNum;
	
	public OfficialJson()
	{
		
	}

	public OfficialJson(int officalID, String firstName, String lastName,
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
	
	public static ArrayList<OfficialJson> parseOfficals(String json)
	{
		Gson gson = new Gson();
		int officalID;
		String firstName, lastName, jerseyNum;
		JsonArray array, tempArray;
		ArrayList<OfficialJson> officals = new ArrayList<OfficialJson>();
		
		array = preProcessJson(json);
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			officalID = gson.fromJson(tempArray.get(0), int.class);
			firstName =  gson.fromJson(tempArray.get(1), String.class);
			lastName =  gson.fromJson(tempArray.get(2), String.class);
			jerseyNum =  gson.fromJson(tempArray.get(3), String.class);
			officals.add(new OfficialJson(officalID, firstName, lastName, 
					jerseyNum));
		}
		
		return officals;
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(7).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
	
	//Sort by id
    public static Comparator<OfficialJson> COMPARE_BY_ID = new Comparator<OfficialJson>() {
        public int compare(OfficialJson one, OfficialJson other) {
            return Integer.compare(one.officalID , other.officalID);
        }
    };
}