package nba.playType.turnover;

public class IllegalAssistTO extends TurnoverType 
{

	public IllegalAssistTO()
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
		return "Illegal Assist";
	}
}
