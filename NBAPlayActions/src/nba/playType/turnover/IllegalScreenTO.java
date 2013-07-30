package nba.playType.turnover;

public class IllegalScreenTO extends TurnoverType
{

	public IllegalScreenTO()
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
		return "Illegal Screen";
	}
}
