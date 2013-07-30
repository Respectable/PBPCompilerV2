package nba.playType.turnover;

public class LostBallTO extends TurnoverType 
{

	public LostBallTO()
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
		return "Lost Ball";
	}
}
