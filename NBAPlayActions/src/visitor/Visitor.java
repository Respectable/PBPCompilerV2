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
	public void visit(ContextInfo contextInfo);
	public void visit(Game game);
	public void visit(Period period);
	public void visit(Player player);
	public void visit(Play play);
	public void visit(PlayerPlay play);
	public void visit(MissedPlay play);
	public void visit(PlayType playType);
	public void visit(Block block);
	public void visit(Ejection ejection);
	public void visit(Foul foul);
	public void visit(FreeThrow freeThrow);
	public void visit(JumpBall jumpBall);
	public void visit(Rebound rebound);
	public void visit(Review review);
	public void visit(Shot shot);
	public void visit(Assist assist);
	public void visit(Steal steal);
	public void visit(Substitution sub);
	public void visit(Technical technical);
	public void visit(DoubleTechnical technical);
	public void visit(TauntingTechnical technical);
	public void visit(Timeout timeout);
	public void visit(Turnover turnover);
	public void visit(Violation violation);
}
