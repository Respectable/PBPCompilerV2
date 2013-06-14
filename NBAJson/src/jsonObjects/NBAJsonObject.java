package jsonObjects;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class NBAJsonObject 
{
	public static String cleanJson(String json)
	{
		json = json.substring(json.indexOf('(') + 1);
		json = json.substring(0, json.length()-1);
		
		return json;
	}
	
	public static String readJson(BufferedReader reader)
	{
		String json = "";
		try {
			 json = reader.readLine();
			 reader.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(0).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
}
