package nba.playType.turnover;

public class TravelingTO extends TurnoverType 
{
	
	public TravelingTO()
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
		return "Traveling";
	}

}
