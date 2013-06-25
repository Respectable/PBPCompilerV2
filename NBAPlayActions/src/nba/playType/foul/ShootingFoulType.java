package nba.playType.foul;

public class ShootingFoulType extends FoulType{
	
	public ShootingFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Shooting";
	}
	
	@Override
	public Boolean teamFoul()
	{
		return true;
	}
	
	@Override
	public Boolean personalFoul()
	{
		return true;
	}

}
