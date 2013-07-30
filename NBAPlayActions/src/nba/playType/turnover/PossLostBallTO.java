package nba.playType.turnover;

public class PossLostBallTO extends TurnoverType
{

	public PossLostBallTO()
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
		return "Possession Lost Ball";
	}
}
