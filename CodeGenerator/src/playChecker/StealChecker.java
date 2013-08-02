package playChecker;

import nba.play.Play;
import nba.playType.steal.Steal;

public class StealChecker implements PlayChecker
{
	@Override
	public boolean playTypeMatches(Play ply) 
	{
		return (ply.getPlayType() instanceof Steal);
	}
}
