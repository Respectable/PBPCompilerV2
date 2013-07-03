package nba.playType.violation;

import visitor.Visitor;
import nba.playType.PlayType;

public class Violation extends PlayType
{
	private ViolationType violationType;
	
	public Violation(ViolationType violationType)
	{
		this.violationType = violationType;
	}
	
	public ViolationType getViolationType() { return violationType; }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
