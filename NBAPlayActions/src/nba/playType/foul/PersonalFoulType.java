package nba.playType.foul;

public class PersonalFoulType extends FoulType{
	
	public PersonalFoulType()
	{
		
	}
	
	@Override
	public String getFoulType()
	{
		return "Personal";
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
