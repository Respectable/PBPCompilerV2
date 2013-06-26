package nba.playType.foul;

import nba.playType.PlayType;

public class Foul extends PlayType
{
	private FoulType foulType;
	
	public Foul(FoulType foulType)
	{
		this.foulType = foulType;
	}

	public FoulType getFoulType() { return foulType; }
	
	
}