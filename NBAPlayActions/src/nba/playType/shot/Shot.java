package nba.playType.shot;

import visitor.Visitor;
import nba.playType.PlayType;

public class Shot extends PlayType
{
	private ShotType shotType;
	private ShotEnding shotEnding;
	private int x,y;
	
	public Shot(ShotType shotType, ShotEnding shotEnding) 
	{
		this.shotType = shotType;
		this.shotEnding = shotEnding;
		this.x = -1;
		this.y = -1;
	}
	
	
	public ShotType getShotType() { return shotType; }
	public ShotEnding getShotEnding() { return shotEnding; }
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}


	@Override
	public boolean identifiesOffense() { return true; }

	@Override
	public boolean terminatesPossession() { return false; }
	
	
}
