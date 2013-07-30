package nba.playType.turnover;

public class OffensiveFoulTO extends TurnoverType
{

	public OffensiveFoulTO()
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
		return "Offensive Foul";
	}
}
