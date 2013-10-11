package visitors;

import nba.*;
import nba.play.*;
import nba.playType.PlayType;
import nba.playType.block.Block;
import nba.playType.ejection.Ejection;
import nba.playType.foul.*;
import nba.playType.freeThrow.FreeThrow;
import nba.playType.jumpBall.JumpBall;
import nba.playType.rebound.Rebound;
import nba.playType.review.Review;
import nba.playType.shot.*;
import nba.playType.steal.Steal;
import nba.playType.substitution.Substitution;
import nba.playType.technical.*;
import nba.playType.timeout.Timeout;
import nba.playType.turnover.Turnover;
import nba.playType.violation.Violation;
import visitor.Visitor;

public class DebugVisitor implements Visitor
{

	private int possessionCounter;
	
	public DebugVisitor()
	{
		this.possessionCounter = 0;
	}
	
	@Override
	public void visit(ContextInfo contextInfo) 
	{
		System.out.print(contextInfo.getPlayRole());
	}

	@Override
	public void visit(Game game) throws Exception 
	{
		this.possessionCounter = 0;
		
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Period period) throws Exception 
	{
		System.out.println();
		System.out.println("+++++++++++++++++++++++");
		System.out.println("     Period " + period.getPeriod());
		System.out.println("+++++++++++++++++++++++");
		System.out.println();
		for (Possession p : period.getPossessions())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Player player) 
	{
		System.out.print(player.getPlayerName() + " ");
	}

	@Override
	public void visit(Play play) throws Exception 
	{
		System.out.println();
		play.getPlayType().accept(this);
		play.getContextInfo().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) throws Exception 
	{
		System.out.println();
		play.getPlayer().accept(this);
		play.getPlayType().accept(this);
		System.out.print(play.getContextInfo().getPlayRole());
	}

	@Override
	public void visit(MissedPlay play) throws Exception 
	{
		System.out.println();
		System.out.print("Missed ");
		play.getPlayer().accept(this);
		play.getPlayType().accept(this);
		System.out.print(play.getContextInfo().getPlayRole());
	}

	@Override
	public void visit(PlayType playType) {}

	@Override
	public void visit(Block block) 
	{
		System.out.print("Block ");
	}

	@Override
	public void visit(Ejection ejection) 
	{
		System.out.print("Ejection ");
	}

	@Override
	public void visit(Foul foul) 
	{
		System.out.print("Foul ");
	}

	@Override
	public void visit(DoublePersonalFoul foul) throws Exception 
	{
		System.out.print("Foul ");
		foul.getPlayer1().accept(this);
		System.out.print(", ");
		foul.getPlayer2().accept(this);
		
	}

	@Override
	public void visit(FreeThrow freeThrow) 
	{
		System.out.print("Free Throw " + 
				freeThrow.getPredicate().getCurrentNumber() + " out of " +
				freeThrow.getPredicate().getOutOf());
	}

	@Override
	public void visit(JumpBall jumpBall) throws Exception 
	{
		System.out.print("Jump Ball ");
		jumpBall.getPlayer1().accept(this);
		System.out.print(", ");
		jumpBall.getPlayer2().accept(this);
	}

	@Override
	public void visit(Rebound rebound) 
	{
		System.out.print("Rebound ");
	}

	@Override
	public void visit(Review review) 
	{
		System.out.print("Review ");
	}

	@Override
	public void visit(Shot shot) throws Exception 
	{
		System.out.print("Shot ");
		if (shot.getShotEnding().getAssist() != null)
			if (shot.getShotEnding().getAssist().getPlayer() != null)
				shot.getShotEnding().getAssist().accept(this);
	}

	@Override
	public void visit(Assist assist) throws Exception 
	{
		System.out.print("Assist - ");
		assist.getPlayer().accept(this);
	}

	@Override
	public void visit(Steal steal) 
	{
		System.out.print("Steal ");
	}

	@Override
	public void visit(Substitution sub) throws Exception 
	{
		System.out.print("Sub - In: ");
		sub.getIn().accept(this);
		System.out.print("Sub - Out: ");
		sub.getOut().accept(this);
	}

	@Override
	public void visit(Technical technical) 
	{
		System.out.print("Technical ");
	}

	@Override
	public void visit(DoubleTechnical technical) throws Exception 
	{
		System.out.print("Technical ");
		technical.getPlayer1().accept(this);
		System.out.print(", ");
		technical.getPlayer2().accept(this);
	}

	@Override
	public void visit(TauntingTechnical technical) 
	{
		System.out.print("Technical ");
	}

	@Override
	public void visit(Timeout timeout) 
	{
		System.out.print("Timeout ");
	}

	@Override
	public void visit(Turnover turnover) 
	{
		System.out.print("Turnover ");
	}

	@Override
	public void visit(Violation violation) 
	{
		System.out.print("Violation ");
	}

	@Override
	public void visit(Possession possession) throws Exception 
	{
		this.possessionCounter++;
		
		System.out.println();
		System.out.println("Possession " + possessionCounter + ":------------------------------------------");
		System.out.println();
		System.out.println("Plays:");
		
		for (Play p : possession.getPossessionPlays())
		{
			p.accept(this);
		}
		
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Home Players:");
		
		for (Player p : possession.getHomePlayers())
		{
			p.accept(this);
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Away Players:");
		
		for (Player p : possession.getAwayPlayers())
		{
			p.accept(this);
			System.out.println();
		}
	}

}
