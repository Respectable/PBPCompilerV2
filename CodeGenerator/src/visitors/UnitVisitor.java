package visitors;

import java.util.ArrayList;

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

public class UnitVisitor implements Visitor 
{

	
	private RosterSQLGenerator rosters;
	private ArrayList<Possession> backPossessions;
	private Player currentPlayer;
	private boolean subInPossession, currentPlayerOnCourt, currentPlayerIsHome;
	
	public UnitVisitor(RosterSQLGenerator rosters)
	{
		this.rosters = rosters;
		this.backPossessions = new ArrayList<Possession>();
		this.currentPlayer = null;
		this.subInPossession = false;
		this.currentPlayerOnCourt = false;
		this.currentPlayerIsHome = true;
	}
	
	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) throws Exception 
	{
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Period period) throws Exception 
	{
		for (Player player : rosters.getHomeActive())
		{
			currentPlayer = player;
			for (Possession p : period.getPossessions())
			{
				p.accept(this);
				if (!currentPlayerOnCourt & !subInPossession)
				{
					backPossessions.add(p);
				}
				else if (currentPlayerOnCourt)
				{
					for (Possession poss : backPossessions)
					{
						poss.addHomePlayer(currentPlayer);
					}
					backPossessions = new ArrayList<Possession>();
					p.addHomePlayer(currentPlayer);
				}
				subInPossession = false;
			}
			backPossessions = new ArrayList<Possession>();
			currentPlayerOnCourt = false;
		}
		
		this.currentPlayerIsHome = false;
		
		for (Player player : rosters.getAwayActive())
		{
			currentPlayer = player;
			for (Possession p : period.getPossessions())
			{
				p.accept(this);
				if (!currentPlayerOnCourt & !subInPossession)
				{
					backPossessions.add(p);
				}
				else if (currentPlayerOnCourt)
				{
					for (Possession poss : backPossessions)
					{
						poss.addAwayPlayer(currentPlayer);
					}
					backPossessions = new ArrayList<Possession>();
					p.addAwayPlayer(currentPlayer);
				}
				subInPossession = false;
			}
			backPossessions = new ArrayList<Possession>();
			currentPlayerOnCourt = false;
		}
		this.currentPlayerIsHome = true;
	}

	@Override
	public void visit(Player player) 
	{
		if (player.equals(currentPlayer))
		{
			currentPlayerOnCourt = true;
		}
	}

	@Override
	public void visit(Play play) throws Exception 
	{
		if (!(play.getPlayType() instanceof Technical) &&
				!(play.getPlayType() instanceof Ejection))
		{
			play.getPlayType().accept(this);
		}
	}

	@Override
	public void visit(PlayerPlay play) throws Exception 
	{
		if (!(play.getPlayType() instanceof Technical) &&
				!(play.getPlayType() instanceof Ejection))
		{
			play.getPlayer().accept(this);
			play.getPlayType().accept(this);
		}
	}

	@Override
	public void visit(MissedPlay play) throws Exception 
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
	public void visit(DoublePersonalFoul foul) throws Exception 
	{
		foul.getPlayer1().accept(this);
		foul.getPlayer2().accept(this);
	}

	@Override
	public void visit(FreeThrow freeThrow) {}

	@Override
	public void visit(JumpBall jumpBall) throws Exception 
	{
		jumpBall.getPlayer1().accept(this);
		jumpBall.getPlayer2().accept(this);
		if (jumpBall.getEnding().getTippedTo() != null)
			jumpBall.getEnding().getTippedTo().accept(this);
	}

	@Override
	public void visit(Rebound rebound) {}

	@Override
	public void visit(Review review) {}

	@Override
	public void visit(Shot shot) throws Exception 
	{
		if (shot.getShotEnding().getAssist() != null)
			shot.getShotEnding().getAssist().accept(this);
	}

	@Override
	public void visit(Assist assist) throws Exception 
	{
		if (assist.getPlayer() != null)
			assist.getPlayer().accept(this);
	}

	@Override
	public void visit(Steal steal) {}

	@Override
	public void visit(Substitution sub) throws Exception 
	{
		//TODO need logic for a sub that takes place after score in possession
		
		if (!currentPlayerOnCourt & sub.getOut().equals(currentPlayer))
		{
			if (this.currentPlayerIsHome)
			{
				for (Possession poss : backPossessions)
				{
					poss.addHomePlayer(currentPlayer);
				}
			}
			else
			{
				for (Possession poss : backPossessions)
				{
					poss.addAwayPlayer(currentPlayer);
				}
			}
			backPossessions = new ArrayList<Possession>();
			subInPossession = true;
		}
		else if (!currentPlayerOnCourt & sub.getIn().equals(currentPlayer))
		{
			backPossessions = new ArrayList<Possession>();
			currentPlayerOnCourt = true;
		}
		else if (currentPlayerOnCourt & sub.getIn().equals(currentPlayer))
		{
			System.out.println("A player already on court has subbed in: " +
							currentPlayer.getPlayerName());
			throw new Exception();
		}
		else if (currentPlayerOnCourt & sub.getOut().equals(currentPlayer))
		{
			currentPlayerOnCourt = false;
			subInPossession = true;
		}
	}

	@Override
	public void visit(Technical technical) throws Exception 
	{
		ThreeSecTechnical threeSecTec;
		if (technical.getPredicate() != null)
		{
			if (technical.getPredicate().getTechType() != null)
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
	public void visit(DoubleTechnical technical) throws Exception 
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

	@Override
	public void visit(Possession possession) throws Exception 
	{
		for(Play p : possession.getPossessionPlays())
		{
			p.accept(this);
		}
	}

}
