package nba.playType.shot;

public class ThreePointShot extends ShotType
{

	public ThreePointShot(String description) 
	{
		super(description);
	}

	@Override
	public boolean isThreePoints() { return true; }
}
