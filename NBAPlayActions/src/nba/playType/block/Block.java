package nba.playType.block;

import visitor.Visitor;
import nba.playType.PlayType;

public class Block extends PlayType 
{
	public Block()
	{
		
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
