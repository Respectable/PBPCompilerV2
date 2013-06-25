package nba.playType.foul;

public class InboundFoulType extends FoulType{
	
	public InboundFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Inbound";
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
