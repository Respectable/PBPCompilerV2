package nba.playType.turnover;

public class NoTurnoverTO extends TurnoverType
{

	public NoTurnoverTO()
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
		return "No Turnover";
	}
}
