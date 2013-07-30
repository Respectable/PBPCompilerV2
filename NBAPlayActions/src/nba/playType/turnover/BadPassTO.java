package nba.playType.turnover;

public class BadPassTO extends TurnoverType
{
	
	public BadPassTO()
	{
		
	}

	@Override
	public boolean stealable() 
	{
		return true;
	}
	
	@Override
	public String toString()
	{
		return "Bad Pass";
	}
}
