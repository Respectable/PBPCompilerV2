package nba.playType.technical;

import visitor.Visitor;
import nba.playType.PlayType;

public class Technical extends PlayType
{
	protected TechnicalPredicate predicate;

	public Technical() 
	{
		this.predicate = null;
	}
	
	public Technical(TechnicalPredicate predicate) 
	{
		this.predicate = predicate;
	}

	public TechnicalPredicate getPredicate() { return predicate; }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
}
