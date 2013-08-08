package jsonObjects.boxScoreObjects;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerStatsJson 
{
	private int teamID, playerID, reb, ast, stl, blk, to, pf, pts, fga, fta,
					fgm, ftm, oReb, dReb;
	private String teamAbbr, playerName, startPosition, comment, gameID;
	
	public PlayerStatsJson()
	{
		
	}

	public PlayerStatsJson(int teamID, int playerID, String teamAbbr,
			String playerName, String startPosition, String comment,
			int reb, int ast, int stl, int blk, int to, int pf, int pts,
			int fga, int fta, int fgm, int ftm, int oReb, int dReb, 
			String gameID) 
	{
		this.teamID = teamID;
		this.playerID = playerID;
		this.teamAbbr = teamAbbr;
		this.playerName = playerName;
		this.startPosition = startPosition;
		this.comment = comment;
		this.reb = reb;
		this.ast = ast;
		this.stl = stl;
		this.blk = blk;
		this.to = to;
		this.pf = pf;
		this.pts = pts;
		this.fga = fga;
		this.fta = fta;
		this.gameID = gameID;
		this.fgm = fgm;
		this.ftm = ftm;
		this.oReb = oReb;
		this.dReb = dReb;
	}

	public int getTeamID() { return teamID; }
	public int getPlayerID() { return playerID; }
	public String getTeamAbbr() { return teamAbbr; }
	public String getPlayerName() { return playerName; }
	public String getStartPosition() { return startPosition; }
	public String getComment() { return comment; }
	public String getGameID() { return gameID; }
	public int getReb() { return reb; }
	public int getAst() { return ast; }
	public int getStl() { return stl; }
	public int getBlk() { return blk; }
	public int getTo() { return to; }
	public int getPf() { return pf; }
	public int getPts() { return pts; }
	public int getFga() { return fga; }
	public int getFta() { return fta; }
	public int getFgm() { return fgm; }
	public int getFtm() { return ftm; }
	public int getOReb() { return oReb; }
	public int getDReb() { return dReb; }

	public static ArrayList<PlayerStatsJson> parsePlayerStats(String json)
	{
		Gson gson = new Gson();
		int teamID, playerID, reb, ast, stl, blk, to, pf, pts, fga, fta, fgm, ftm,
				oReb, dReb;
		String teamAbbr, playerName, startPosition, comment, gameID;
		JsonArray array, tempArray;
		ArrayList<PlayerStatsJson> players = new ArrayList<PlayerStatsJson>();
		
		array = preProcessJson(json);
		
		for (JsonElement element : array)
		{
			tempArray = element.getAsJsonArray();
			gameID = gson.fromJson(tempArray.get(0), String.class);
			teamID = gson.fromJson(tempArray.get(1), int.class);
			playerID = gson.fromJson(tempArray.get(4), int.class);
			teamAbbr =  gson.fromJson(tempArray.get(2), String.class);
			playerName =  gson.fromJson(tempArray.get(5), String.class);
			startPosition =  gson.fromJson(tempArray.get(6), String.class);
			comment =  gson.fromJson(tempArray.get(7), String.class);
			if (tempArray.get(10) != JsonNull.INSTANCE)
			{
				fgm = gson.fromJson(tempArray.get(9), int.class);
				fga = gson.fromJson(tempArray.get(10), int.class);
				ftm = gson.fromJson(tempArray.get(16), int.class);
				fta = gson.fromJson(tempArray.get(16), int.class);
				oReb = gson.fromJson(tempArray.get(18), int.class);
				dReb = gson.fromJson(tempArray.get(19), int.class);
				reb = gson.fromJson(tempArray.get(20), int.class);
				ast = gson.fromJson(tempArray.get(21), int.class);
				stl = gson.fromJson(tempArray.get(22), int.class);
				blk = gson.fromJson(tempArray.get(23), int.class);
				to = gson.fromJson(tempArray.get(24), int.class);
				pf = gson.fromJson(tempArray.get(25), int.class);
				pts = gson.fromJson(tempArray.get(26), int.class);
				
			}
			else
			{
				fgm = 0;
				fga = 0;
				ftm = 0;
				fta = 0;
				oReb = 0;
				dReb = 0;
				reb = 0;
				ast = 0;
				stl = 0;
				blk = 0;
				to = 0;
				pf = 0;
				pts = 0;
			}
			players.add(new PlayerStatsJson(teamID, playerID, teamAbbr, 
					playerName, startPosition, comment, reb, ast, stl,
					blk, to, pf, pts, fga, fta, fgm, ftm, oReb, dReb, gameID));
		}
		
		return players;
	}
	
	protected static JsonArray preProcessJson(String json)
	{

		JsonParser parser = new JsonParser();
		
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray array = jsonObject.get("resultSets").getAsJsonArray();
		jsonObject = array.get(4).getAsJsonObject();
		array = jsonObject.get("rowSet").getAsJsonArray();
		
		return array;
	}
	
	
}
