package nba.playType.turnover;

public class ShotClockTO extends TurnoverType 
{
	
	public ShotClockTO()
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
		return "Shot Clock";
	}

}
