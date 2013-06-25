package nba.playType.foul;

public class ShootingBlockFoulType extends FoulType{
	
	public ShootingBlockFoulType()
	{
		
	}

	@Override
	public String getFoulType()
	{
		return "Shooting Block";
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
