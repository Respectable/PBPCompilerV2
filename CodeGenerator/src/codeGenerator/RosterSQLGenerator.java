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
								homeInactive, awayInactive,
								homeDNP, awayDNP;
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
		this.homeDNP = parseDNP(homeID, players);
		this.awayDNP = parseDNP(awayID, players);
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
	public ArrayList<Player> getHomeDNP() { return homeDNP; }
	public ArrayList<Player> getAwayDNP() { return awayDNP; }
	public int getHomeID() { return this.homeID; }
	public int getAwayID() { return this.awayID; }
	
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
		teamPlayers.removeAll(homeDNP);
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
		teamPlayers.removeAll(awayDNP);
		return teamPlayers;
	}
	
	public ArrayList<Player> getActive()
	{
		ArrayList<Player> teamPlayers = new ArrayList<Player>(awayStarters);
		teamPlayers.addAll(awayBench);
		teamPlayers.addAll(homeStarters);
		teamPlayers.addAll(homeBench);
		teamPlayers.removeAll(homeDNP);
		teamPlayers.removeAll(awayDNP);
		return teamPlayers;
	}
	
	public PlayerSearchState findHomePlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer = new Player("Dummy", -1);
		Player returnPlayer = new Player("Dummy", -1);
		ArrayList<Player> tempPlayers = new ArrayList<Player>(getHomeActive());
		PlayerSearchState currentState = PlayerSearchState.NOT_FOUND;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
			
		while(tempPlayer != null)
		{
			switch(currentState)
			{
			case NOT_FOUND:
				returnPlayer = searchPlayers(tempPlayers, playerNameArray);
				tempPlayer = returnPlayer;
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.FOUND;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			case FOUND:
				tempPlayer = searchPlayers(tempPlayers, playerNameArray);
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.DUPLICATE_HOME;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			default:
				tempPlayer = null;
				break;
			}
		}
		
		switch(currentState)
		{
		case NOT_FOUND:
			System.out.println("Could not find player: " + player.getPlayerName());
			player.setPlayerID(currentState.getValue());
			return currentState;
		case FOUND:
			player.setPlayerID(returnPlayer.getPlayerID());
			player.setPlayerName(returnPlayer.getPlayerName());
			return currentState;
		case DUPLICATE_HOME:
			System.out.println("Multiple players with name " + 
					player.getPlayerName() + " on home team");
			player.setPlayerID(currentState.getValue());
			return currentState;
		default:
			System.out.println("Error finding player" + player.getPlayerName());
			System.exit(-1);
			return currentState;
		}
	}
	
	public boolean searchHomePlayers(Player player)
	{
		return getHomeActive().contains(player);
	}
	
	public PlayerSearchState findAwayPlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer = new Player("Dummy", -1);
		Player returnPlayer = new Player("Dummy", -1);
		ArrayList<Player> tempPlayers = new ArrayList<Player>(getAwayActive());
		PlayerSearchState currentState = PlayerSearchState.NOT_FOUND;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
			
		while(tempPlayer != null)
		{
			switch(currentState)
			{
			case NOT_FOUND:
				returnPlayer = searchPlayers(tempPlayers, playerNameArray);
				tempPlayer = returnPlayer;
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.FOUND;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			case FOUND:
				tempPlayer = searchPlayers(tempPlayers, playerNameArray);
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.DUPLICATE_AWAY;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			default:
				tempPlayer = null;
				break;
			}
		}
		
		switch(currentState)
		{
		case NOT_FOUND:
			System.out.println("Could not find player: " + player.getPlayerName());
			player.setPlayerID(currentState.getValue());
			return currentState;
		case FOUND:
			player.setPlayerID(returnPlayer.getPlayerID());
			player.setPlayerName(returnPlayer.getPlayerName());
			return currentState;
		case DUPLICATE_AWAY:
			System.out.println("Multiple players with name " + 
					player.getPlayerName() + " on away team");
			player.setPlayerID(currentState.getValue());
			return currentState;
		default:
			System.out.println("Error finding player" + player.getPlayerName());
			System.exit(-1);
			return currentState;
		}
	}
	
	public boolean searchAwayPlayers(Player player)
	{
		return getAwayActive().contains(player);
	}
	
	public PlayerSearchState findPlayer(Player player)
	{
		String[] playerNameArray;
		Player tempPlayer = new Player("Dummy", -1);
		Player returnPlayer = new Player("Dummy", -1);
		ArrayList<Player> tempPlayers = new ArrayList<Player>(getAwayActive());
		PlayerSearchState currentState = PlayerSearchState.NOT_FOUND;
		
		playerNameArray = cleanPlayerName(player.getPlayerName());
			
		while(tempPlayer != null)
		{
			switch(currentState)
			{
			case NOT_FOUND:
				returnPlayer = searchPlayers(tempPlayers, playerNameArray);
				tempPlayer = returnPlayer;
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.FOUND;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			case FOUND:
				tempPlayer = searchPlayers(tempPlayers, playerNameArray);
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.DUPLICATE_AWAY;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			default:
				tempPlayer = null;
				break;
			}
		}
		
		tempPlayer = new Player("Dummy", -1);
		tempPlayers = new ArrayList<Player>(getHomeActive());
		
		while(tempPlayer != null)
		{
			switch(currentState)
			{
			case NOT_FOUND:
				returnPlayer = searchPlayers(tempPlayers, playerNameArray);
				tempPlayer = returnPlayer;
				if (tempPlayer != null)
				{
					currentState = PlayerSearchState.FOUND;
					tempPlayers.remove(tempPlayer);
				}
				else
				{
					break;
				}
			case FOUND:
				tempPlayer = searchPlayers(tempPlayers, playerNameArray);
				if (tempPlayer != null)
				{
					tempPlayers.remove(tempPlayer);
					if(searchHomePlayers(returnPlayer))
						currentState = PlayerSearchState.DUPLICATE_HOME;
					else
						currentState = PlayerSearchState.SINGLE_HOME_AWAY;
				}
				else
				{
					break;
				}
			case SINGLE_HOME_AWAY: case DUPLICATE_AWAY:
				tempPlayer = searchPlayers(tempPlayers, playerNameArray);
				if (tempPlayer != null)
				{
					tempPlayers.remove(tempPlayer);
					currentState = PlayerSearchState.MULT_HOME_AWAY;
				}
				else
				{
					break;
				}
			default:
				tempPlayer = null;
				break;
			}
		}
		
		switch(currentState)
		{
		case NOT_FOUND:
			System.out.println("Could not find player: " + player.getPlayerName());
			player.setPlayerID(currentState.getValue());
			return currentState;
		case FOUND:
			player.setPlayerID(returnPlayer.getPlayerID());
			player.setPlayerName(returnPlayer.getPlayerName());
			return currentState;
		case DUPLICATE_HOME:
			System.out.println("Multiple players with name " + 
					player.getPlayerName() + " on home team");
			player.setPlayerID(currentState.getValue());
			return currentState;
		case DUPLICATE_AWAY:
			System.out.println("Multiple players with name " + 
					player.getPlayerName() + " on away team");
			player.setPlayerID(currentState.getValue());
			return currentState;
		case SINGLE_HOME_AWAY:
			System.out.println("Single players with name " + 
					player.getPlayerName() + " on both teams");
			player.setPlayerID(currentState.getValue());
			return currentState;
		case MULT_HOME_AWAY:
			System.out.println("Multiple players with name " + 
					player.getPlayerName() + " on both teams");
			player.setPlayerID(currentState.getValue());
			return currentState;
		default:
			System.out.println("Error finding player" + player.getPlayerName());
			System.exit(-1);
			return currentState;
		}
	}
	
	public boolean searchPlayers(Player player)
	{
		return getActive().contains(player);
	}
	
	private String[] cleanPlayerName(String playerName)
	{
		String tempPlayerName;
		
		tempPlayerName = playerName.replace('.', ' ');
		tempPlayerName = tempPlayerName.trim();
		return tempPlayerName.split(" ");
	}
	
	private Player searchPlayers(ArrayList<Player> players, String[] playerName)
	{
		boolean found = false;
		String[] reversedName, beingSearched, currentPlayerName;
		
		reversedName = new String[playerName.length];
		for (int i =0; i < playerName.length; i++)
		{
			reversedName[i] = playerName[playerName.length - (1 + i)];
		}
		
		for(Player player : players)
		{
			currentPlayerName = cleanPlayerName(player.getPlayerName());
			beingSearched = new String[currentPlayerName.length];
			for (int i =0; i < currentPlayerName.length; i++)
			{
				beingSearched[i] = 
						currentPlayerName[currentPlayerName.length - (1 + i)];
			}
			found = reversedName[0].equals(
					beingSearched[0]);
					
			if (found)
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
	
	private ArrayList<Player> parseDNP(int teamID, ArrayList<PlayerStatsJson> players)
	{
		ArrayList<Player> bench = new ArrayList<Player>();
		
		for(PlayerStatsJson player : players)
		{
			if ((player.getTeamID() == teamID) && (player.getComment().contains("DNP")))
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
