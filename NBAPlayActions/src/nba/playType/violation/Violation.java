package nba.playType.violation;

public class Violation 
{
	private ViolationType violationType;
	
	public Violation(ViolationType violationType)
	{
		this.violationType = violationType;
	}
	
	public ViolationType getViolationType() { return violationType; }
}
