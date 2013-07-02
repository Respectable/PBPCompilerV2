package nba.play;

import visitor.Visitable;
import visitor.Visitor;
import nba.ContextInfo;
import nba.playType.PlayType;

public class Play implements Visitable
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

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
