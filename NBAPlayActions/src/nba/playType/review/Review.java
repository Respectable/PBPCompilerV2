package nba.playType.review;

import visitor.Visitor;
import nba.playType.PlayType;

public class Review extends PlayType
{
	public Review()
	{
		
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
