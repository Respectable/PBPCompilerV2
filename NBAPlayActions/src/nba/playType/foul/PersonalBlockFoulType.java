package nba.playType.foul;

public class PersonalBlockFoulType extends FoulType{
	
	public PersonalBlockFoulType()
	{
		
	}

	@Override
	public String getFoulType()
	{
		return "Personal Block";
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
