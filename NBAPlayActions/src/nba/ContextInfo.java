package nba;

import visitor.Visitable;
import visitor.Visitor;

public class ContextInfo implements Visitable
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

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}
	
	
	
	
}
