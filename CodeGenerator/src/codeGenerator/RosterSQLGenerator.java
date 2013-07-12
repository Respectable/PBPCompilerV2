package codeGenerator;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.BoxJson;
import jsonObjects.PBPJson;
import jsonObjects.boxScoreObjects.InactiveJson;
import jsonObjects.boxScoreObjects.PlayerStatsJson;

import nba.play.Play;
import nba.playType.foul.Foul;
import nba.playType.freeThrow.FreeThrow;
import nba.playType.rebound.Rebound;
import nba.playType.shot.Shot;
import nba.playType.turnover.Turnover;
import nbaDownloader.NBADownloader;
import nba.playType.block.*;
import nba.playType.steal.*;

import nba.Player;

public class RosterSQLGenerator 
{
	private ArrayList<Player> homeStarters, awayStarters,
								homeBench, awayBench,
								homeInactive, awayInactive,
								homeDNP, awayDNP;
	private int homeID, awayID;
	private String gameID;
	private ArrayList<PBPJson> pbp;
	
	public RosterSQLGenerator(int homeID, int awayID, String gameID, ArrayList<InactiveJson> inactive,
								ArrayList<PlayerStatsJson> players, ArrayList<PBPJson> pbp)
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
		this.pbp = pbp;
		Collections.sort(this.pbp, PBPJson.COMPARE_BY_PLAY_ID);
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
	
	public void setHomePlayer(Player player, Play currentPlay)
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
			break;
		case FOUND:
			player.setPlayerID(returnPlayer.getPlayerID());
			player.setPlayerName(returnPlayer.getPlayerName());
			break;
		case DUPLICATE_HOME:
			duplicateHomeSearch(player, currentPlay);
		default:
			System.out.println("Error finding player" + player.getPlayerName());
			System.exit(-1);
			break;
		}
	}
	
	public boolean searchHomePlayers(Player player)
	{
		return getHomeActive().contains(player);
	}
	
	private void duplicateHomeSearch(Player player, Play currentPlay)
	{
		int index, startTime, endTime;
		PBPJson relevantPlay = new PBPJson();
		ArrayList<PlayerStatsJson> relevantPlayers;
		ArrayList<Player> homePlayers;
		
		relevantPlay.setEventNum(currentPlay.getPlayID());
		index = Collections.binarySearch(this.pbp, relevantPlay, 
				PBPJson.COMPARE_BY_PLAY_ID);
		
		if (index == -1)
		{
			System.out.println("Game: " + this.gameID + " " +
					"Play: " + currentPlay.getPlayID() + 
					" Play not found.");
		}
		else
		{
			relevantPlay = this.pbp.get(index);
		}
		startTime = convertStringTime(relevantPlay.getGameTime());
		startTime += addPeriodTime(relevantPlay.getPeriod());
		startTime -= 5;
		endTime = convertStringTime(relevantPlay.getGameTime());
		endTime += addPeriodTime(relevantPlay.getPeriod());
		endTime += 5;
		relevantPlayers = downloadCustomBoxScore(startTime, endTime);
		homePlayers = parseStarters(homeID, relevantPlayers);
		homePlayers.addAll(parseBench(homeID, relevantPlayers));
		singleTeamDuplicate(player, currentPlay, homePlayers,
				relevantPlayers);
	}
	
	private void singleTeamDuplicate(Player player, Play currentPlay,
			ArrayList<Player> players, ArrayList<PlayerStatsJson> playerStats)
	{
		String[] playerNameArray;
		Player tempPlayer = new Player("Dummy", -1);
		Player returnPlayer = new Player("Dummy", -1);
		ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
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
			break;
		case FOUND:
			player.setPlayerID(returnPlayer.getPlayerID());
			player.setPlayerName(returnPlayer.getPlayerName());
			break;
		case DUPLICATE_HOME:
			if (currentPlay.getPlayType() instanceof Rebound)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof Turnover)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof Shot)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof Foul)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof FreeThrow)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof Steal)
			{
				
			}
			else if (currentPlay.getPlayType() instanceof Block)
			{
				
			}
		default:
			System.out.println("Error finding player" + player.getPlayerName());
			System.exit(-1);
			break;
		}
	}
	
	
	public PlayerSearchState setAwayPlayer(Player player, Play currentPlay)
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
	
	public PlayerSearchState setPlayer(Player player, Play currentPlay)
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
	
	public class CheckStarters implements PlayerParser<PlayerStatsJson>
	{
		@Override
		public boolean check(int teamID, PlayerStatsJson player) 
		{
			return (player.getTeamID() == teamID) && 
				(!player.getStartPosition().equals(""));
		}
	}
	
	public class CheckBench implements PlayerParser<PlayerStatsJson>
	{
		@Override
		public boolean check(int teamID, PlayerStatsJson player) 
		{
			return (player.getTeamID() == teamID) &&
					(player.getStartPosition().equals(""));
		}
	}
	
	public class CheckDNP implements PlayerParser<PlayerStatsJson>
	{
		@Override
		public boolean check(int teamID, PlayerStatsJson player) 
		{
			return (player.getTeamID() == teamID) && 
					(player.getComment().contains("DNP"));
		}
	}
	
	public class CheckInactive implements PlayerParser<InactiveJson>
	{
		@Override
		public boolean check(int teamID, InactiveJson player) 
		{
			return player.getTeamID() == teamID;
		}
	}
	
	private ArrayList<Player> parsePlayers(int teamID, 
			ArrayList<PlayerStatsJson> players, 
			PlayerParser<PlayerStatsJson> playerParser)
	{
		ArrayList<Player> starters = new ArrayList<Player>();
		
		for(PlayerStatsJson player : players)
		{
			if(playerParser.check(teamID, player))
				starters.add(new Player(player.getPlayerName(), 
										player.getPlayerID()));
		}
		
		return starters;
	}
	
	private ArrayList<Player> parseInactivePlayers(int teamID, 
			ArrayList<InactiveJson> players, 
			PlayerParser<InactiveJson> playerParser)
	{
		ArrayList<Player> inactives = new ArrayList<Player>();
		
		for(InactiveJson player : players)
		{
			if(playerParser.check(teamID, player))
				inactives.add(new Player(player.getFirstName() + " " +
										player.getLastName(),
										player.getPlayerID()));
		}
		
		return inactives;
	}
	
	private ArrayList<Player> parseStarters(int teamID,
			ArrayList<PlayerStatsJson> players)
	{
		return parsePlayers(teamID, players, new CheckStarters());
	}
	
	private ArrayList<Player> parseBench(int teamID,
			ArrayList<PlayerStatsJson> players)
	{
		return parsePlayers(teamID, players, new CheckBench());
	}
	
	private ArrayList<Player> parseDNP(int teamID,
			ArrayList<PlayerStatsJson> players)
	{
		return parsePlayers(teamID, players, new CheckDNP());
	}
	
	private ArrayList<Player> parseInactive(int teamID,
			ArrayList<InactiveJson> players)
	{
		return parseInactivePlayers(teamID, players, new CheckInactive());
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
	
	private int convertStringTime(String time)
	{
		String[] timeParts = time.split(":");
		String min = timeParts[0];
		String tens = timeParts[1].substring(0,1);
		String singles = timeParts[1].substring(1, 2);
		return ((Integer.parseInt(min) * 60) + (Integer.parseInt(tens) * 10) +
				Integer.parseInt(singles)) * 10;
	}
	
	private int addPeriodTime(int period)
	{
		return (period - 1) * (12 * 60 * 10);
	}
	
	private ArrayList<PlayerStatsJson> downloadCustomBoxScore(int startTime,
															  int endTime)
	{
		try 
		{
			Thread.sleep(3000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		BufferedReader br = NBADownloader.downloadCustomBox(
				gameID, startTime, endTime);
		return BoxJson.getBoxScore(br).getPlayerStats();
	}
}
