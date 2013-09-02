package nba;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

import nba.play.*;

public class Possession implements Visitable
{

	private ArrayList<Play> possessionPlays;
	private ArrayList<Player> homePlayers;
	private ArrayList<Player> awayPlayers;
	private int possessionID;
	private int offenseTeamID, defenseTeamID;
	
	
	public Possession()
	{
		this.possessionPlays = new ArrayList<Play>();
		this.homePlayers = new ArrayList<Player>();
		this.awayPlayers = new ArrayList<Player>();
		this.possessionID = -1;
		this.offenseTeamID = -1;
		this.defenseTeamID = -1;
	}
	
	public void addPlay(Play play)
	{
		possessionPlays.add(play);
	}
	
	public void addHomePlayer(Player player)
	{
		if (homePlayers.size() < 5)
		{
			homePlayers.add(player);
		}
		else
		{
			System.out.println("Home Unit Full, cannot add player: " +
					player.getPlayerName());
			System.out.println("The unit already contains the following:");
			for (Player p : homePlayers)
			{
				System.out.println(p.getPlayerName());
			}
			System.exit(-1);
		}
	}
	
	public void addAwayPlayer(Player player)
	{
		if (awayPlayers.size() < 5)
		{
			awayPlayers.add(player);
		}
		else
		{
			System.out.println("Away Unit Full, cannot add player: " +
					player.getPlayerName());
			System.out.println("The unit already contains the following:");
			for (Player p : awayPlayers)
			{
				System.out.println(p.getPlayerName());
			}
			System.exit(-1);
		}
	}
	
	public boolean removeHomePlayer(Player player)
	{
		return homePlayers.remove(player);
	}
	
	public boolean removeAwayPlayer(Player player)
	{
		return awayPlayers.remove(player);
	}
	
	public void setTeamRoles(int offTeamID, int defTeamID)
	{
		this.offenseTeamID = offTeamID;
		this.defenseTeamID = defTeamID;
	}
	
	public boolean teamsSet()
	{
		return this.offenseTeamID != -1 &&
				this.defenseTeamID != -1;
	}
	
	public int getOffenseID() { return this.offenseTeamID; }
	public int getDefenseID() { return this.defenseTeamID; }
	public int getPossessionID() { return this.possessionID; }
	public ArrayList<Play> getPossessionPlays() { return this.possessionPlays; }
	public ArrayList<Player> getHomePlayers() { return this.homePlayers; }
	public ArrayList<Player> getAwayPlayers() { return this.awayPlayers; }
	public int getHomeUnitSize() { return this.homePlayers.size(); }
	public int getAwayUnitSize() { return this.awayPlayers.size(); }
	
	public boolean homeUnitContainsPlayer(Player player)
	{
		return homePlayers.contains(player);
	}
	
	public boolean awayUnitContainsPlayer(Player player)
	{
		return awayPlayers.contains(player);
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
