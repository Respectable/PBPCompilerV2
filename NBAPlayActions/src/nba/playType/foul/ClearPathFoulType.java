package nba.playType.foul;

public class ClearPathFoulType extends FoulType{
	
	public ClearPathFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Clear Path";
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
