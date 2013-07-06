package nba;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

import nba.play.Play;

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
		homePlayers.add(player);
	}
	
	public void addAwayPlayer(Player player)
	{
		awayPlayers.add(player);
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
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
