package nba.playType.foul;

import visitor.Visitor;
import nba.Player;

public class DoublePersonalFoul extends Foul {

	private Player player1, player2;
	
	public DoublePersonalFoul(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
	}

	public Player getPlayer1() { return player1; }
	public Player getPlayer2() { return player2; }
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
