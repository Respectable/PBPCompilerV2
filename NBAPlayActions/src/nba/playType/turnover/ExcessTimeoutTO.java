package nba.playType.turnover;

public class ExcessTimeoutTO extends TurnoverType 
{
	
	public ExcessTimeoutTO()
	{
		
	}
	
	@Override
	public boolean stealable() 
	{
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Excess Timeout";
	}

}
