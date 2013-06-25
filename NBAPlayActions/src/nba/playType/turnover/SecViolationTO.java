package nba.playType.turnover;

public class SecViolationTO extends TurnoverType {
	
	private int seconds;
	
	public SecViolationTO(int seconds)
	{
		this.seconds = seconds;
	}
	
	public int getSeconds() { return seconds; }

}
