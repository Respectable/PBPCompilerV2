package nba;

public abstract class Play 
{
	protected PlayRole role;
	protected int playID;
	
	public Play()
	{
		this.role = PlayRole.NEUTRAL;
		this.playID = -1;
	}
	
	public Play(PlayRole role, int playID)
	{
		this.role = role;
		this.playID = playID;
	}

	public PlayRole getRole() { return role; }
	public int getPlayID() { return playID; }
	
}
