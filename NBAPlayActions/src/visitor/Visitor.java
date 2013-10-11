package visitor;

import nba.*;
import nba.play.*;
import nba.playType.*;
import nba.playType.block.*;
import nba.playType.ejection.*;
import nba.playType.foul.*;
import nba.playType.freeThrow.*;
import nba.playType.jumpBall.*;
import nba.playType.rebound.*;
import nba.playType.review.*;
import nba.playType.shot.*;
import nba.playType.steal.*;
import nba.playType.substitution.*;
import nba.playType.technical.*;
import nba.playType.timeout.*;
import nba.playType.turnover.*;
import nba.playType.violation.*;

public interface Visitor 
{
	public void visit(ContextInfo contextInfo)throws Exception;
	public void visit(Game game)throws Exception;
	public void visit(Period period)throws Exception;
	public void visit(Player player)throws Exception;
	public void visit(Play play)throws Exception;
	public void visit(PlayerPlay play)throws Exception;
	public void visit(MissedPlay play)throws Exception;
	public void visit(PlayType playType)throws Exception;
	public void visit(Block block)throws Exception;
	public void visit(Ejection ejection)throws Exception;
	public void visit(Foul foul)throws Exception;
	public void visit(DoublePersonalFoul foul)throws Exception;
	public void visit(FreeThrow freeThrow)throws Exception;
	public void visit(JumpBall jumpBall)throws Exception;
	public void visit(Rebound rebound)throws Exception;
	public void visit(Review review)throws Exception;
	public void visit(Shot shot)throws Exception;
	public void visit(Assist assist)throws Exception;
	public void visit(Steal steal)throws Exception;
	public void visit(Substitution sub)throws Exception;
	public void visit(Technical technical)throws Exception;
	public void visit(DoubleTechnical technical)throws Exception;
	public void visit(TauntingTechnical technical)throws Exception;
	public void visit(Timeout timeout)throws Exception;
	public void visit(Turnover turnover)throws Exception;
	public void visit(Violation violation) throws Exception;
	public void visit(Possession possession)throws Exception;
}
