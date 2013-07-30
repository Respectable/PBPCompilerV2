package nba.playType.turnover;

public class PunchedBallTO extends TurnoverType
{

	public PunchedBallTO()
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
		return "Punched Ball";
	}
}
