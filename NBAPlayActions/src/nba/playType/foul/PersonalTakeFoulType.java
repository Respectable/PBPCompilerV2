package nba.playType.foul;

public class PersonalTakeFoulType extends FoulType{
	
	public PersonalTakeFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Personal Take";
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
