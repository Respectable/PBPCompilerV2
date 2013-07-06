package nba.playType.rebound;

import visitor.Visitor;
import nba.playType.PlayType;

public class Rebound extends PlayType
{
	private boolean playerRebound;
	
	public Rebound(boolean playerRebound)
	{
		this.playerRebound = playerRebound;
	}
	
	public boolean isPlayerRebound() { return playerRebound; }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return false; }

	@Override
	public boolean terminatesPossession() { return true; }
}
