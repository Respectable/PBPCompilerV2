package nba.playType.foul;

public class LooseBallFoulType extends FoulType{
	
	public LooseBallFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Loose Ball";
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
