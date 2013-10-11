package nba.playType.block;

import visitor.Visitor;
import nba.playType.PlayType;

public class Block extends PlayType 
{
	public Block()
	{
		
	}

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return false; }

	@Override
	public boolean terminatesPossession() { return false; }
}
