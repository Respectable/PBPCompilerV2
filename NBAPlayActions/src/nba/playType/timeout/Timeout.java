package nba.playType.timeout;

import nba.playType.PlayType;

public class Timeout extends PlayType
{
	private String timeoutType;

	public Timeout(String timeoutType) 
	{
		this.timeoutType = timeoutType;
	}

	public String getTimeoutType() { return timeoutType; }
	
}
