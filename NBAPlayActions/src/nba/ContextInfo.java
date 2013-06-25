package nba;

public class ContextInfo 
{
	private int playID;
	private PlayRole playRole;
	
	public ContextInfo(int playID, PlayRole playRole) 
	{
		this.playID = playID;
		this.playRole = playRole;
	}

	public int getPlayID() { return playID; }
	public PlayRole getPlayRole() { return playRole; }
	
	
	
	
}
