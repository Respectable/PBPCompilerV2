package nba.playType.timeout;

import visitor.Visitor;
import nba.playType.PlayType;

public class Timeout extends PlayType
{
	private String timeoutType;

	public Timeout(String timeoutType) 
	{
		this.timeoutType = timeoutType;
	}

	public String getTimeoutType() { return timeoutType; }

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
