package nba.playType.turnover;

public class LaneViolationTO extends TurnoverType
{

	public LaneViolationTO()
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
		return "Lane Violation";
	}
}
