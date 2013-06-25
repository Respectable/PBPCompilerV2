package nba.playType.technical;

public class Technical 
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
	
}
