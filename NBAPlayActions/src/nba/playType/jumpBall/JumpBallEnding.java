package nba.playType.jumpBall;

import nba.Player;

public class JumpBallEnding 
{
	private Player tippedTo;
	
	public JumpBallEnding()
	{
		this.tippedTo = null;
	}

	public JumpBallEnding(Player tippedTo) 
	{
		this.tippedTo = tippedTo;
	}

	public Player getTippedTo() { return tippedTo; }
	
	public boolean IdentifiesOffense()
	{
		if (tippedTo != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
