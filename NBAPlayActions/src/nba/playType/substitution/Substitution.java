package nba.playType.substitution;

import nba.Player;

public class Substitution 
{
	private Player out, in;
	
	public Substitution(Player out, Player in)
	{
		this.out = out;
		this.in = in;
	}

	public Player getOut() { return out; }
	public Player getIn() { return in; }
	
}
