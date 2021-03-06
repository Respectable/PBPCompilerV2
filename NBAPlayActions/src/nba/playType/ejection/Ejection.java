package nba.playType.ejection;

import visitor.Visitor;
import nba.playType.PlayType;

public class Ejection extends PlayType 
{
	private EjectionEnding ending;

	public Ejection(EjectionEnding ending) 
	{
		this.ending = ending;
	}

	public EjectionEnding getEnding() { return ending; }

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return false; }

	@Override
	public boolean terminatesPossession() { return false; }
	
}
