package nba.playType.foul;

public abstract class FoulType 
{
	public String getFoulType()
	{
		return "";
	}
	
	public Boolean teamFoul()
	{
		return false;
	}
	
	public Boolean personalFoul()
	{
		return false;
	}
}
