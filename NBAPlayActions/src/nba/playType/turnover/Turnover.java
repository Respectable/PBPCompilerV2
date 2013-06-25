package nba.playType.turnover;

public class Turnover 
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
