package nba;

public class Player 
{
	private String playerName;
	private int playerID;
	private boolean active;
	
	public Player(String playerName) 
	{
		this.playerName = playerName;
		playerID = 0;
		active = true;
	}
	
	public Player(String playerName, int playerID) 
	{
		this.playerName = playerName;
		this.playerID = playerID;
		active = true;
	}
	
	public Player(String playerName, int playerID, boolean active) 
	{
		this.playerName = playerName;
		this.playerID = playerID;
		this.active = active;
	}

	public String getPlayerName() { return playerName; }
	public int getPlayerID() { return playerID; }
	public boolean isActive() { return active; }
	
	
	
}
