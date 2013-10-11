package nba.playType.substitution;

import visitor.Visitor;
import nba.Player;
import nba.playType.PlayType;

public class Substitution extends PlayType
{
	private Player out, in;
	
	public Substitution(Player in, Player out)
	{
		this.out = out;
		this.in = in;
	}

	public Player getOut() { return out; }
	public Player getIn() { return in; }

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}

	@Override
	public boolean identifiesOffense() { return false; }

	@Override
	public boolean terminatesPossession() { return false; }
	
}
