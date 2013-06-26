package nba.play;

import nba.ContextInfo;
import nba.playType.PlayType;

public class Play 
{
	protected PlayType playType;
	protected ContextInfo contextInfo;
	
	public Play(PlayType playType, ContextInfo contextInfo) 
	{
		this.playType = playType;
		this.contextInfo = contextInfo;
	}

	public PlayType getPlayType() { return playType; }
	public ContextInfo getContextInfo() { return contextInfo; }
	public boolean missed() { return false; }
}
