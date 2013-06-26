package nba.playType.turnover;

import nba.playType.PlayType;

public class Turnover extends PlayType
{
	private TurnoverType turnoverType;
	private TurnoverEnding ending;
	
	public Turnover(TurnoverType turnoverType, TurnoverEnding ending) 
	{
		this.turnoverType = turnoverType;
		this.ending = ending;
	}
	
	public TurnoverType getTurnoverType() {return turnoverType;}
	public TurnoverEnding getEnding() {return ending;}
	
}
