package nba.playType.technical;

import visitor.Visitor;
import nba.Player;

public class DoubleTechnical extends Technical
{
	private Player player1, player2;

	public DoubleTechnical(Player player1, Player player2) 
	{
		super();
		this.player1 = player1;
		this.player2 = player2;
	}

	public Player getPlayer1() {return player1;}

	public Player getPlayer2() {return player2;}
	
	@Override
	public void accept(Visitor visitor)
	{
		visitor.visit(this);
	}
	
}
