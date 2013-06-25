package nba.playType.foul;

public class DefenseThreeSecFoulType extends FoulType{
	
	public DefenseThreeSecFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Defensive Three Second";
	}
	
	@Override
	public Boolean teamFoul()
	{
		return false;
	}
	
	@Override
	public Boolean personalFoul()
	{
		return false;
	}

}
