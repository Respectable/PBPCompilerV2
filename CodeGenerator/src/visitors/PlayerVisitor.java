package visitors;

import codeGenerator.RosterSQLGenerator;
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
import visitor.Visitor;

public class PlayerVisitor implements Visitor
{

	private RosterSQLGenerator rosters;
	private ContextInfo currentContext;
	
	public PlayerVisitor(RosterSQLGenerator rosters)
	{
		this.rosters = rosters;
	}
	
	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) 
	{
		for(Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Period period) 
	{
		for(Play p : period.getPlays())
		{
			p.accept(this);
			this.currentContext = p.getContextInfo();
		}
	}

	@Override
	public void visit(Player player) 
	{
		if (currentContext.equals(PlayRole.HOME))
		{
			if(!rosters.findHomePlayer(player))
				System.out.println("Could not find player: " + player.getPlayerName() +
						" play: " + currentContext.getPlayID());
		}
		else if (currentContext.equals(PlayRole.AWAY))
		{
			if(!rosters.findAwayPlayer(player))
				System.out.println("Could not find player: " + player.getPlayerName() +
						" play: " + currentContext.getPlayID());
		}
		else
		{
			if(!rosters.findPlayer(player))
				System.out.println("Could not find player: " + player.getPlayerName() +
						" play: " + currentContext.getPlayID());
		}
	}

	@Override
	public void visit(Play play) 
	{
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) 
	{
		play.getPlayer().accept(this);
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) 
	{
		play.getPlayer().accept(this);
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayType playType) {}

	@Override
	public void visit(Block block) {}

	@Override
	public void visit(Ejection ejection) {}

	@Override
	public void visit(Foul foul) {}
	
	@Override
	public void visit(DoublePersonalFoul foul) 
	{
		foul.getPlayer1().accept(this);
		foul.getPlayer2().accept(this);
	}

	@Override
	public void visit(FreeThrow freeThrow) {}

	@Override
	public void visit(JumpBall jumpBall) 
	{
		jumpBall.getPlayer1().accept(this);
		jumpBall.getPlayer2().accept(this);
		if (!jumpBall.getEnding().getTippedTo().equals(null))
			jumpBall.getEnding().getTippedTo().accept(this);
	}

	@Override
	public void visit(Rebound rebound) {}

	@Override
	public void visit(Review review) {}

	@Override
	public void visit(Shot shot) 
	{
		if (!shot.getShotEnding().getAssist().equals(null))
			shot.getShotEnding().getAssist().accept(this);
	}

	@Override
	public void visit(Assist assist) 
	{
		if (!assist.getPlayer().equals(null))
			assist.getPlayer().accept(this);
	}

	@Override
	public void visit(Steal steal) {}

	@Override
	public void visit(Substitution sub) 
	{
		sub.getIn().accept(this);
		sub.getOut().accept(this);
	}

	@Override
	public void visit(Technical technical) 
	{
		ThreeSecTechnical threeSecTec;
		if (!technical.getPredicate().equals(null))
		{
			if (!technical.getPredicate().getTechType().equals(null))
			{
				if (technical.getPredicate().getTechType() instanceof ThreeSecTechnical)
				{
					threeSecTec = (ThreeSecTechnical)technical.getPredicate().getTechType();
					threeSecTec.getPlayer().accept(this);
				}
			}
		}
	}

	@Override
	public void visit(DoubleTechnical technical) 
	{
		technical.getPlayer1().accept(this);
		technical.getPlayer2().accept(this);
	}

	@Override
	public void visit(TauntingTechnical technical) {}

	@Override
	public void visit(Timeout timeout) {}

	@Override
	public void visit(Turnover turnover) {}

	@Override
	public void visit(Violation violation) {}

	

}
