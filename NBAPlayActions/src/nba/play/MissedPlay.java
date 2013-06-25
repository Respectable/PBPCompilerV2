package nba.play;

import nba.ContextInfo;
import nba.Player;
import nba.playType.PlayType;

public class MissedPlay extends PlayerPlay
{

	public MissedPlay(PlayType playType, ContextInfo contextInfo, Player player) 
	{
		super(playType, contextInfo, player);
	}
	
	@Override
	public boolean missed() { return true; }

}
