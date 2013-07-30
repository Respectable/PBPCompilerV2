package nba.playType.turnover;

public class OOBLostBallTO extends TurnoverType
{

	public OOBLostBallTO()
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
		return "Out of Bounds Lost Ball";
	}
}
