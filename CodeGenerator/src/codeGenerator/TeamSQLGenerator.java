package codeGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import nba.Player;

import jsonObjects.TeamJson;

public class TeamSQLGenerator 
{
	
	private int teamId, gameId;
	private ArrayList<Player> players;
	
	public TeamSQLGenerator(int teamId, int gameId, ArrayList<Player> players)
	{
		this.teamId = teamId;
		this.gameId = gameId;
		this.players = players;
	}
	
	public int getTeamId() { return teamId; }
	public int getGameId() { return gameId; }
	public ArrayList<Player> getPlayers() { return players; }
	
	public boolean hasPlayer(String playerName)
	{
		//TODO
		return true;
	}
	
	public boolean hasPlayer(int playerID)
	{
		//TODO
		return true;
	}
	
	public void compile(String path,
			String userName, String password)
	{
		Connection conn;
		PreparedStatement stmt;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(path,userName,password);
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`game_players` (`game_id`,`team_id`,`player_id`," +
					"`active`) VALUES (?,?,?,?);");
			
			for (Player player : players)
			{
				stmt.setInt(1, this.gameId);
				stmt.setInt(2, this.teamId);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, player.isActive());
				stmt.executeUpdate();
			}
			
			stmt.close();
			conn.close();
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void updateTeams(ArrayList<TeamJson> teams, String path,
			String userName, String password)
	{
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
		
		Collections.sort(teams, TeamJson.COMPARE_BY_ID);
		ArrayList<TeamJson> newTeams = new ArrayList<TeamJson>(teams);
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(path,userName,password);
			
			stmt = conn.prepareStatement("SELECT * FROM `nba2`.`team`");
			rs = stmt.executeQuery();
			
			while(rs.next())
		    {
		    	newTeams.remove(Collections.binarySearch(newTeams,
		    			new TeamJson("", rs.getInt("team_id"), 0, 0, ""), TeamJson.COMPARE_BY_ID));
		    }
			
			for(TeamJson team : newTeams)
			{
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`team` (`team_id`,`league_id`,`start_year`," +
						"`end_year`, `abbreviation`) VALUES (?,?,?,?,?);");
			    stmt.setInt(1, team.getTeamID());
			    stmt.setString(2, team.getLeagueID());
			    stmt.setInt(3, team.getTeamStartYear());
			    stmt.setInt(4, team.getTeamEndYear());
			    stmt.setString(5, team.getAbbr());
			    stmt.executeUpdate();
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
