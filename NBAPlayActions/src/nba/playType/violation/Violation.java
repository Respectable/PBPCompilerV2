package nba.playType.violation;

import nba.playType.PlayType;

public class Violation extends PlayType
{
	private ViolationType violationType;
	
	public Violation(ViolationType violationType)
	{
		this.violationType = violationType;
	}
	
	public ViolationType getViolationType() { return violationType; }
}
