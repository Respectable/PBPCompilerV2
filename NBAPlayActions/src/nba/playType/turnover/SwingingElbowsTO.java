package nba.playType.turnover;

public class SwingingElbowsTO extends TurnoverType
{

	public SwingingElbowsTO()
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
		return "Swinging Elbows";
	}
}
