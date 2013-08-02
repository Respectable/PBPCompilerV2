package playChecker;

import nba.play.Play;
import nba.playType.block.Block;

public class BlockChecker implements PlayChecker
{
	@Override
	public boolean playTypeMatches(Play ply) 
	{
		return (ply.getPlayType() instanceof Block);
	}
}
