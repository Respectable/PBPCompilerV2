package nba.playType.turnover;

public class KickedBallTO extends TurnoverType
{

	public KickedBallTO()
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
		return "Kicked Ball";
	}
}
