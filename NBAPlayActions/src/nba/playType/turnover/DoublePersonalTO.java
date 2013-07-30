package nba.playType.turnover;

public class DoublePersonalTO extends TurnoverType
{

	public DoublePersonalTO()
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
		return "Double Personal";
	}
}
