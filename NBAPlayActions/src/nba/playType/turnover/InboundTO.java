package nba.playType.turnover;

public class InboundTO extends TurnoverType
{

	public InboundTO()
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
		return "Inbound";
	}
}
