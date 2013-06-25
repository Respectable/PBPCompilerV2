package nba.playType.foul;

import nba.Player;

public class DoublePersonalFoul extends FoulType {

	private Player player1, player2;
	
	public DoublePersonalFoul(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
	}

	public Player getPlayer1() { return player1; }
	public Player getPlayer2() { return player2; }
}
