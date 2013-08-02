package playChecker;

import nba.play.Play;
import nba.playType.turnover.Turnover;

public class TurnoverChecker implements PlayChecker
{
	@Override
	public boolean playTypeMatches(Play ply) 
	{
		return (ply.getPlayType() instanceof Turnover);
	}
}
