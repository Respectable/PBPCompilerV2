package nba.playType.turnover;

public class JumpBallTO extends TurnoverType 
{

	public JumpBallTO()
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
		return "Jump Ball";
	}
}
