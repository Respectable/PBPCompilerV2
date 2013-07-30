package nba.playType.turnover;

public class FoulTO extends TurnoverType
{

	public FoulTO()
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
		return "Foul";
	}
}
