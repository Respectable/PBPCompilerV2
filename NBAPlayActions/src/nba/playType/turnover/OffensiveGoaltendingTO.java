package nba.playType.turnover;

public class OffensiveGoaltendingTO extends TurnoverType
{

	public OffensiveGoaltendingTO()
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
		return "Offensive Goaltending";
	}
}
