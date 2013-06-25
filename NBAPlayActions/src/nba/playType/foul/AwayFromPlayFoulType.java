package nba.playType.foul;

public class AwayFromPlayFoulType extends FoulType{
	
	public AwayFromPlayFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Away From Play";
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
