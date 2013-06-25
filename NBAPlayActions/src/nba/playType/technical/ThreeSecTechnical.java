package nba.playType.technical;

import nba.Player;

public class ThreeSecTechnical extends TechnicalFoulType 
{
	private Player player;

	public ThreeSecTechnical(Player player) 
	{
		this.player = player;
	}

	public Player getPlayer() {return player;}
	
	
}
