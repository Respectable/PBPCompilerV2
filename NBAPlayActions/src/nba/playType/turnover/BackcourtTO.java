package nba.playType.turnover;

public class BackcourtTO extends TurnoverType
{

	public BackcourtTO()
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
		return "Backcourt";
	}

}
