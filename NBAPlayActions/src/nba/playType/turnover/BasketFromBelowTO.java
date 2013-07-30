package nba.playType.turnover;

public class BasketFromBelowTO extends TurnoverType
{

	public BasketFromBelowTO()
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
		return "Basket From Below";
	}
	
}
