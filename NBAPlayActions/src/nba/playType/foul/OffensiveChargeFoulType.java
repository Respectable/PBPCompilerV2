package nba.playType.foul;

public class OffensiveChargeFoulType extends FoulType{
	
	public OffensiveChargeFoulType()
	{
		
	}

	@Override
	public String getFoulType()
	{
		return "Offensive Charge";
	}
	
	@Override
	public Boolean teamFoul()
	{
		return false;
	}
	
	@Override
	public Boolean personalFoul()
	{
		return true;
	}
}
