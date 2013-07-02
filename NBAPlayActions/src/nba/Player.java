package nba;

import visitor.Visitable;
import visitor.Visitor;

public class Player implements Visitable
{
	private String playerName;
	private int playerID;
	
	public Player(String playerName) 
	{
		this.playerName = playerName;
		playerID = 0;
	}
	
	public Player(String playerName, int playerID) 
	{
		this.playerName = playerName;
		this.playerID = playerID;
	}

	public String getPlayerName() { return playerName; }
	public int getPlayerID() { return playerID; }

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
