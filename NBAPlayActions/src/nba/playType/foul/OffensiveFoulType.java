package nba.playType.foul;

public class OffensiveFoulType extends FoulType{
	
	public OffensiveFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Offensive";
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
