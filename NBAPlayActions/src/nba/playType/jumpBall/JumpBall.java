package nba.playType.jumpBall;

import visitor.Visitor;
import nba.Player;
import nba.playType.PlayType;

public class JumpBall extends PlayType
{
	private Player player1, player2;
	private JumpBallEnding ending;
	
	public JumpBall(Player player1, Player player2, JumpBallEnding ending) 
	{
		this.player1 = player1;
		this.player2 = player2;
		this.ending = ending;
	}

	public Player getPlayer1() { return player1; }
	public Player getPlayer2() { return player2; }
	public JumpBallEnding getEnding() { return ending; }
	
	public Player getPossessionPlayer()
	{
		if (ending.getTippedTo() != null)
		{
			return ending.getTippedTo();
		}
		else
		{
			return null;
		}
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminatesPossession() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
