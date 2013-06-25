package nba.playType.foul;

public class Foul 
{
	private FoulType foulType;
	
	public Foul(FoulType foulType)
	{
		this.foulType = foulType;
	}

	public FoulType getFoulType() { return foulType; }
	
	
}