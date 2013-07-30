package nba.playType.turnover;

public class OOBTO extends TurnoverType
{

	public OOBTO()
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
		return "Out of Bounds";
	}
}
