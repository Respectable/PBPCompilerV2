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
		playerID = -1;
	}
	
	public Player(String playerName, int playerID) 
	{
		this.playerName = playerName;
		this.playerID = playerID;
	}

	public String getPlayerName() { return playerName; }
	public int getPlayerID() { return playerID; }
	
	public void setPlayerName(String name)
	{
		this.playerName = name;
	}
	
	public void setPlayerID(int id)
	{
		this.playerID = id;
	}

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) 
		{
	        return false;
	    }
		
	    if (getClass() != obj.getClass()) 
	    {
	        return false;
	    }
	    
	    final Player other = (Player) obj;
	    
	    if ((this.playerName == null) ? (other.playerName != null) : 
	    	!this.playerName.equals(other.playerName)) 
	    {
	        return false;
	    }
	    
	    if (this.playerID != other.playerID) 
	    {
	        return false;
	    }
	    
	    return true;
	}
	

	@Override
	public int hashCode() 
	{
		return this.playerID * this.playerName.hashCode();
	}
}
