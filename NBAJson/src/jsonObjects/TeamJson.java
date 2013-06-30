package jsonObjects;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class TeamJson extends NBAJsonObject
{
	
	private String leagueID;
	private int teamID;
	private int startYear, endYear;
	private String abbr;
	
	public TeamJson()
	{
		this.leagueID = "00";
		this.teamID = 0;
		this.startYear = 0;
		this.endYear = 0;
		this.abbr = "XXX";
	}
	
	public TeamJson(String leagueID, int teamID, int startYear, 
			int endYear, String abbreviation)
	{
		this.leagueID = leagueID;
		this.teamID = teamID;
		this.startYear = startYear;
		this.endYear = endYear;
		this.abbr = abbreviation;
	}
	
	public String getLeagueID() { return this.leagueID; }
	public int getTeamID() { return this.teamID; }
	public int getTeamStartYear() { return this.startYear; }
	public int getTeamEndYear() { return this.endYear; }
	public String getAbbr() { return this.abbr; }
	
	public static ArrayList<TeamJson> parseTeamJson(String json)
	{
		Gson gson = new Gson();
		ArrayList<TeamJson> teams = new ArrayList<TeamJson>();
		int teamID, startYear, endYear;
		String abbr, leagueID;
		
		JsonArray array = preProcessJson(json);
		
		for(JsonElement element : array)
		{
			JsonArray teamArray = element.getAsJsonArray();
			leagueID = gson.fromJson(teamArray.get(0), String.class);
			teamID = gson.fromJson(teamArray.get(1), int.class);
			startYear = gson.fromJson(teamArray.get(2), int.class);
			endYear = gson.fromJson(teamArray.get(3), int.class);
			
			if (teamArray.get(4) != JsonNull.INSTANCE)
				abbr = gson.fromJson(teamArray.get(4), String.class);
			else
				abbr = "";
			
			teams.add(new TeamJson(leagueID, teamID, startYear, endYear, abbr));
		}
		
		return teams;
	}
	
	public static ArrayList<TeamJson> getTeams(BufferedReader reader)
	{
		String json = readJson(reader);
		json = cleanJson(json);
		return parseTeamJson(json);
	}
	
	//Sort by id
    public static Comparator<TeamJson> COMPARE_BY_ID = new Comparator<TeamJson>() {
        public int compare(TeamJson one, TeamJson other) {
            return Integer.compare(one.teamID , other.teamID);
        }
    };
	
	@Override
    public String toString() 
	{
      return String.format("(LEAGUE_ID=%s, TEAM_ID=%s, MIN_YEAR=%s, MAX_YEAR=%s,"
    		  + "ABBREVIATION=%s)", this.leagueID, this.teamID, this.startYear, 
    		  this.endYear, this.abbr);
    }

}
