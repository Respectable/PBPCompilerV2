package nba.playType.shot;

import nba.Player;

public class Assist 
{
	private Player player;
	
	public Assist()
	{
		this.player = null;
	}
	
	public Assist(Player player)
	{
		this.player = player;
	}

	public Player getPlayer() { return player; }
	
}
