package nba.playType.review;

import visitor.Visitor;
import nba.playType.PlayType;

public class Review extends PlayType
{
	public Review()
	{
		
	}

	@Override
	public void accept(Visitor visitor) throws Exception 
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
