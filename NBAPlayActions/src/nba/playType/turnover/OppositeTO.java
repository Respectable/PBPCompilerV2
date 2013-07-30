package nba.playType.turnover;

public class OppositeTO extends TurnoverType
{
	public OppositeTO()
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
		return "Opposite";
	}
}
