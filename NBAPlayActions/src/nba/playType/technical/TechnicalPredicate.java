package nba.playType.technical;

public class TechnicalPredicate 
{
	private TechnicalFoulType techType;

	public TechnicalPredicate()
	{
		this.techType = null;
	}
	
	public TechnicalPredicate(TechnicalFoulType techType) 
	{
		this.techType = techType;
	}

	public TechnicalFoulType getTechType() {return techType;}
	
}
