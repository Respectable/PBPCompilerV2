package nba.playType.shot;

public class ShotType 
{
	protected String description;

	public ShotType(String description) 
	{
		this.description = description;
	}

	public String getDescription() { return description; }
	public boolean isThreePoints() { return false; }
	
}
