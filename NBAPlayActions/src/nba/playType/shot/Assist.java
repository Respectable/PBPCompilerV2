package nba.playType.shot;

import visitor.Visitable;
import visitor.Visitor;
import nba.Player;

public class Assist implements Visitable
{
	private Player player;
	
	public Assist()
	{
		this.player = null;
	}
	
	public Assist(Player player)
	{
		this.player = player;
	}

	public Player getPlayer() { return player; }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
}
