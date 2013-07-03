package codeGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import jsonObjects.boxScoreObjects.InactiveJson;
import jsonObjects.boxScoreObjects.PlayerStatsJson;

import nba.Player;

public class RosterSQLGenerator 
{
	private ArrayList<Player> homeStarters, awayStarters,
								homeBench, awayBench,
								homeInactive, awayInactive;
	private int homeID, awayID;
	private String gameID;
	
	public RosterSQLGenerator(int homeID, int awayID, String gameID, ArrayList<InactiveJson> inactive,
								ArrayList<PlayerStatsJson> players)
	{
		this.homeStarters = parseStarters(homeID, players);
		this.homeBench = parseBench(homeID, players);
		this.homeInactive = parseInactive(homeID, inactive);
		this.awayStarters = parseStarters(awayID, players);
		this.awayBench = parseBench(awayID, players);
		this.awayInactive = parseInactive(awayID, inactive);
		this.homeID = homeID;
		this.awayID = awayID;
		this.gameID = gameID;
	}

	public ArrayList<Player> getHomeStarters() { return homeStarters; }
	public ArrayList<Player> getAwayStarters() { return awayStarters; }
	public ArrayList<Player> getHomeBench() { return homeBench; }
	public ArrayList<Player> getAwayBench() { return awayBench; }
	public ArrayList<Player> getHomeInactive() { return homeInactive; }
	public ArrayList<Player> getAwayInactive() { return awayInactive; }
	
	public ArrayList<Player> getHomeTeam()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(homeStarters);
		teamPlayers.addAll(homeBench);
		teamPlayers.addAll(homeInactive);
		return teamPlayers;
	}
	
	public ArrayList<Player> getHomeActive()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(homeStarters);
		teamPlayers.addAll(homeBench);
		return teamPlayers;
	}
	
	public ArrayList<Player> getAwayTeam()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(awayStarters);
		teamPlayers.addAll(awayBench);
		teamPlayers.addAll(awayInactive);
		return teamPlayers;
	}
	
	public ArrayList<Player> getAwayActive()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(awayStarters);
		teamPlayers.addAll(awayBench);
		return teamPlayers;
	}
	
	public ArrayList<Player> getActive()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(awayStarters);
		teamPlayers.addAll(awayBench);
		teamPlayers.addAll(homeStarters);
		teamPlayers.addAll(homeBench);
		return teamPlayers;
	}
	
	public boolean findHomePlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
		
		tempPlayer = searchPlayers(getHomeActive(), playerNameArray);
		
		if(tempPlayer != null)
		{
			player.setPlayerID(tempPlayer.getPlayerID());
			player.setPlayerName(tempPlayer.getPlayerName());
			return true;
		}
		else
		{
			player.setPlayerID(-1);
			player.setPlayerName("#NOT_FOUND");
			return false;
		}
	}
	
	public boolean findAwayPlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
		
		tempPlayer = searchPlayers(getAwayActive(), playerNameArray);
		
		if(tempPlayer != null)
		{
			player.setPlayerID(tempPlayer.getPlayerID());
			player.setPlayerName(tempPlayer.getPlayerName());
			return true;
		}
		else
		{
			player.setPlayerID(-1);
			player.setPlayerName("#NOT_FOUND");
			return false;
		}
	}
	
	public boolean findPlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
		
		tempPlayer = searchPlayers(getActive(), playerNameArray);
		
		if(tempPlayer != null)
		{
			player.setPlayerID(tempPlayer.getPlayerID());
			player.setPlayerName(tempPlayer.getPlayerName());
			return true;
		}
		else
		{
			player.setPlayerID(-1);
			player.setPlayerName("#NOT_FOUND");
			return false;
		}
	}
	
	private String[] cleanPlayerName(String playerName)
	{
		String tempPlayerName;
		
		tempPlayerName = playerName.replace('.', ' ');
		tempPlayerName = playerName.replace('-', ' ');
		tempPlayerName = tempPlayerName.trim();
		return tempPlayerName.split(" ");
	}
	
	private Player searchPlayers(ArrayList<Player> players, String[] playerName)
	{
		boolean found = false;
		for(Player player : players)
		{
			for(String namePart : playerName)
			{
				if (player.getPlayerName().contains(namePart))
				{
					found = true;
				}
				else
				{
					found = false;
					break;
				}
			}
			if (found)
			{
				return player;
			}
		}
		
		return null;
	}
	
	private ArrayList<Player> parseStarters(int teamID, ArrayList<PlayerStatsJson> players)
	{
		ArrayList<Player> starters = new ArrayList<Player>();
		
		for(PlayerStatsJson player : players)
		{
			if ((player.getTeamID() == teamID) && (!player.getStartPosition().equals("")))
			{
				starters.add(new Player(player.getPlayerName(), player.getPlayerID()));
			}
		}
		
		return starters;
	}
	
	private ArrayList<Player> parseBench(int teamID, ArrayList<PlayerStatsJson> players)
	{
		ArrayList<Player> bench = new ArrayList<Player>();
		
		for(PlayerStatsJson player : players)
		{
			if ((player.getTeamID() == teamID) && (player.getStartPosition().equals("")))
			{
				bench.add(new Player(player.getPlayerName(), player.getPlayerID()));
			}
		}
		
		return bench;
	}
	
	private ArrayList<Player> parseInactive(int teamID, ArrayList<InactiveJson> players)
	{
		ArrayList<Player> inactive = new ArrayList<Player>();
		
		for(InactiveJson player : players)
		{
			if (player.getTeamID() == teamID)
			{
				inactive.add(new Player(player.getFirstName() + " " + player.getLastName(),
											player.getPlayerID()));
			}
		}
		
		return inactive;
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
			
			for (Player player : homeStarters)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.homeID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, true);
				stmt.executeUpdate();
			}
			
			for (Player player : homeBench)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.homeID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, true);
				stmt.executeUpdate();
			}
			
			for (Player player : homeInactive)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.homeID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, false);
				stmt.executeUpdate();
			}
			
			for (Player player : awayStarters)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.awayID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, true);
				stmt.executeUpdate();
			}
			
			for (Player player : awayBench)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.awayID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, true);
				stmt.executeUpdate();
			}
			
			for (Player player : awayInactive)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, this.awayID);
				stmt.setInt(3, player.getPlayerID());
				stmt.setBoolean(4, false);
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
