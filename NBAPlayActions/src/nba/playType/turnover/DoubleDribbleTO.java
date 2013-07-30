package nba.playType.turnover;

public class DoubleDribbleTO extends TurnoverType
{

	public DoubleDribbleTO()
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
		return "Double Dribble";
	}
	
}
