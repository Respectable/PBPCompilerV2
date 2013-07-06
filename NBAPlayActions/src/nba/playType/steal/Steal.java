package nba.playType.steal;

import visitor.Visitor;
import nba.playType.PlayType;

public class Steal extends PlayType
{
	public Steal()
	{
		
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return false; }

	@Override
	public boolean terminatesPossession() { return false; }
}
