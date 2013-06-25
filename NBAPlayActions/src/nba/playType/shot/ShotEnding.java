package nba.playType.shot;

public class ShotEnding 
{
	private Assist assist;
	
	public ShotEnding()
	{
		this.assist = null;
	}
	
	public ShotEnding(Assist assist)
	{
		this.assist = assist;
	}

	public Assist getAssist() { return assist; }
	
	
}
