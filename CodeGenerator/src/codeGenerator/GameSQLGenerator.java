package codeGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nba.Player;
import jsonObjects.boxScoreObjects.GameInfoJson;
import jsonObjects.boxScoreObjects.GameSummaryJson;

public class GameSQLGenerator 
{
	private int attendance, homeTeamID, awayTeamID;
	private String gameID, gameTime, gameDate, season, broadcaster;
	
	public GameSQLGenerator(GameInfoJson gameInfo, GameSummaryJson gameSummary)
	{
		this.attendance = gameInfo.getAttendance();
		this.gameTime = gameInfo.getGameTime();
		this.gameID = gameSummary.getGameID();
		this.homeTeamID = gameSummary.getHomeID();
		this.awayTeamID = gameSummary.getAwayID();
		this.gameDate = gameSummary.getGameDate();
		this.season = gameSummary.getSeason();
		this.broadcaster = gameSummary.getBroadcaster();
	}

	public int getAttendance() { return attendance; }
	public int getHomeTeamID() { return homeTeamID; }
	public int getAwayTeamID() { return awayTeamID; }
	public String getGameID() { return gameID; }
	public String getGameTime() { return gameTime; }
	public String getGameDate() { return gameDate; }
	public String getSeason() { return season; }
	public String getBroadcaster() { return broadcaster; }
	
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
	
}
