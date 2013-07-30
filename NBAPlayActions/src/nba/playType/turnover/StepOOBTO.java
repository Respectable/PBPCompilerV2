package nba.playType.turnover;

public class StepOOBTO extends TurnoverType
{

	public StepOOBTO()
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
		return "Step Out of Bounds";
	}
}
