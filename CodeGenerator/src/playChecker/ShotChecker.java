package playChecker;

import nba.play.Play;
import nba.playType.shot.Shot;

public class ShotChecker implements PlayChecker
{
	@Override
	public boolean playTypeMatches(Play ply) 
	{
		return (ply.getPlayType() instanceof Shot);
	}
}
