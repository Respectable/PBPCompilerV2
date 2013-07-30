package nba.playType.turnover;

public class DiscontinueDribbleTO extends TurnoverType
{

	public DiscontinueDribbleTO()
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
		return "Discontinue Dribble";
	}
	
}
