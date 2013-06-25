package nba.playType.foul;

public class FlagrantFoulType extends FoulType{
	
	private int flagrantNum;
	
	public FlagrantFoulType(int flagrantNum)
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Flagrant " + this.flagrantNum;
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
