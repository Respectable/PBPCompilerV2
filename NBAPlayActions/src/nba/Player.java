package nba;

public class Player 
{
	private String playerName;
	private int PlayerID;
	
	public Player(String playerName) 
	{
		this.playerName = playerName;
		PlayerID = 0;
	}
	
	public Player(String playerName, int playerID) 
	{
		this.playerName = playerName;
		PlayerID = playerID;
	}

	public String getPlayerName() { return playerName; }
	public int getPlayerID() { return PlayerID; }
	
	
	
	
}
