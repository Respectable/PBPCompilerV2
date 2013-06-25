package nba.playType.shot;

public class Shot 
{
	private ShotType shotType;
	private ShotEnding shotEnding;
	
	public Shot(ShotType shotType, ShotEnding shotEnding) 
	{
		this.shotType = shotType;
		this.shotEnding = shotEnding;
	}

	public ShotType getShotType() { return shotType; }
	public ShotEnding getShotEnding() { return shotEnding; }
	
	
}
