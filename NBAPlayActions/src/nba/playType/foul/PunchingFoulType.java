package nba.playType.foul;

public class PunchingFoulType extends FoulType
{
	public PunchingFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Punching Foul";
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
