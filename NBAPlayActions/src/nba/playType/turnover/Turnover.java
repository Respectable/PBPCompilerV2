package nba.playType.turnover;

import visitor.Visitor;
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

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return true; }

	@Override
	public boolean terminatesPossession() { return true; }
	
	public boolean stolen() { return turnoverType.stealable(); }
	public boolean playerTurnover() { return (ending instanceof PlayerTurnover); }
	
}
