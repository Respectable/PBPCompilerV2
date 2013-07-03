package nba.playType.foul;

import visitor.Visitor;
import nba.playType.PlayType;

public class Foul extends PlayType
{
	private FoulType foulType;
	
	public Foul()
	{
		this.foulType = null;
	}
	
	public Foul(FoulType foulType)
	{
		this.foulType = foulType;
	}

	public FoulType getFoulType() { return foulType; }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	
}