package nba.play;

import visitor.Visitor;
import nba.ContextInfo;
import nba.Player;
import nba.playType.PlayType;

public class PlayerPlay extends Play
{
	protected Player player;

	public PlayerPlay(PlayType playType, ContextInfo contextInfo, Player player) 
	{
		super(playType, contextInfo);
		this.player = player;
	}

	public Player getPlayer() { return player; }
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
