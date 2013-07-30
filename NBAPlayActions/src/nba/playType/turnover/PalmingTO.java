package nba.playType.turnover;

public class PalmingTO extends TurnoverType
{

	public PalmingTO()
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
		return "Palming";
	}
}
