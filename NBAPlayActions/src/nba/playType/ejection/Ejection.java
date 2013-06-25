package nba.playType.ejection;

import nba.playType.PlayType;

public class Ejection extends PlayType 
{
	private EjectionEnding ending;

	public Ejection(EjectionEnding ending) 
	{
		this.ending = ending;
	}

	public EjectionEnding getEnding() { return ending; }
	
}
