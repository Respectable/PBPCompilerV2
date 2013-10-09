package visitors;

import java.util.ArrayList;
import java.util.ListIterator;

import visitor.Visitable;
import visitor.Visitor;

import nba.ContextInfo;
import nba.Game;
import nba.Period;
import nba.PlayRole;
import nba.Player;
import nba.Possession;
import nba.play.MissedPlay;
import nba.play.Play;
import nba.play.PlayerPlay;
import nba.playType.PlayType;
import nba.playType.block.Block;
import nba.playType.ejection.Ejection;
import nba.playType.foul.DoublePersonalFoul;
import nba.playType.foul.Foul;
import nba.playType.freeThrow.FreeThrow;
import nba.playType.jumpBall.JumpBall;
import nba.playType.rebound.Rebound;
import nba.playType.review.Review;
import nba.playType.shot.Assist;
import nba.playType.shot.Shot;
import nba.playType.steal.Steal;
import nba.playType.substitution.Substitution;
import nba.playType.technical.DoubleTechnical;
import nba.playType.technical.TauntingTechnical;
import nba.playType.technical.Technical;
import nba.playType.timeout.Timeout;
import nba.playType.turnover.Turnover;
import nba.playType.violation.Violation;
import codeGenerator.RosterSQLGenerator;

public class SubstitutionPlayerVisitor implements Visitor
{

	protected RosterSQLGenerator rosters;
	protected Player currentPlayPlayer;
	protected PlayRole currentRole;
	protected ArrayList<Player> playersOnFloor;
	protected boolean reversed;
	
	public SubstitutionPlayerVisitor(RosterSQLGenerator rosters) 
	{
		this.rosters = rosters;
		this.playersOnFloor = new ArrayList<Player>();
		this.reversed = false;
	}

	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) 
	{
		currentRole = PlayRole.HOME;
		VisitEach(game.getPeriods());
		
		currentRole = PlayRole.AWAY;
		VisitEach(game.getPeriods());
		
		//go through game's plays in reverse
		reversed = true;
		currentRole = PlayRole.HOME;
		VisitEachInReverse(game.getPeriods());
		
		currentRole = PlayRole.AWAY;
		VisitEachInReverse(game.getPeriods());
	}

	@Override
	public void visit(Period period) 
	{
		this.playersOnFloor = new ArrayList<Player>();
		if (!this.reversed)
			VisitEach(period.getPlays());
		else
			VisitEachInReverse(period.getPlays());
	}

	@Override
	public void visit(Player player) {}

	@Override
	public void visit(Play play) 
	{
		if (play.getContextInfo().getPlayRole() == PlayRole.NEUTRAL ||
				play.getContextInfo().getPlayRole() == this.currentRole)
			play.getPlayType().accept(this);
		else
			return;
	}

	@Override
	public void visit(PlayerPlay play) 
	{
		if (play.getContextInfo().getPlayRole() == PlayRole.NEUTRAL ||
				play.getContextInfo().getPlayRole() == this.currentRole)
		{
			currentPlayPlayer = play.getPlayer();
			play.getPlayType().accept(this);
		}
		else
			return;
	}

	@Override
	public void visit(MissedPlay play) 
	{
		if (play.getContextInfo().getPlayRole() == PlayRole.NEUTRAL ||
				play.getContextInfo().getPlayRole() == this.currentRole)
		{
			currentPlayPlayer = play.getPlayer();
			play.getPlayType().accept(this);
		}
		else
			return;
	}

	@Override
	public void visit(PlayType playType) {}

	@Override
	public void visit(Block block) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Ejection ejection) {}

	@Override
	public void visit(Foul foul) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(DoublePersonalFoul foul){}

	@Override
	public void visit(FreeThrow freeThrow) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(JumpBall jumpBall) {}

	@Override
	public void visit(Rebound rebound) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Review review) {}

	@Override
	public void visit(Shot shot) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
		if (shot.getShotEnding().getAssist() != null)
		{
			shot.getShotEnding().getAssist().accept(this);
		}
	}

	@Override
	public void visit(Assist assist) 
	{
		if(assist.getPlayer() != null)
			addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Steal steal) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Substitution sub) 
	{
		if(!this.reversed)
		{
			if (sub.getOut().getPlayerID() == -1)
			{
				setOutgoingPlayer(sub.getOut());
			}
			else
			{
				playersOnFloor.remove(sub.getOut());
			}
			
			if (sub.getIn().getPlayerID() == -1)
			{
				setIncomingPlayer(sub.getIn());
			}
			else
			{
				addPlayerOnFloor(sub.getIn());
			}
		}
		else
		{
			//because game is being visited in reverse, outgoing players are
			// now incoming and vice versa
			if (sub.getIn().getPlayerID() == -1)
			{
				setOutgoingPlayer(sub.getIn());
			}
			else
			{
				playersOnFloor.remove(sub.getIn());
			}
			
			if (sub.getOut().getPlayerID() == -1)
			{
				setIncomingPlayer(sub.getOut());
			}
			else
			{
				addPlayerOnFloor(sub.getOut());
				
			}
			
			
		}
	}

	@Override
	public void visit(Technical technical) {}

	@Override
	public void visit(DoubleTechnical technical) {}

	@Override
	public void visit(TauntingTechnical technical) {}

	@Override
	public void visit(Timeout timeout) {}

	@Override
	public void visit(Turnover turnover) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Violation violation) 
	{
		addPlayerOnFloor(this.currentPlayPlayer);
	}

	@Override
	public void visit(Possession possession) {}
	
	protected void addPlayerOnFloor(Player player)
	{
		if (player.getPlayerID() != -1 && !this.playersOnFloor.contains(player))
		{
			if (playersOnFloor.size() < 5)
				playersOnFloor.add(player);
			else
			{
				System.out.println("More than 5 players on court");
				System.exit(-1);
			}
		}
	}
	
	protected void setIncomingPlayer(Player player)
	{
		ArrayList<Player> playersOnBench;
		ArrayList<Player> matchingPlayers;
		
		if (this.currentRole == PlayRole.HOME)
			playersOnBench = rosters.getHomeActive();
		else
			playersOnBench = rosters.getAwayActive();
		
		playersOnBench.removeAll(this.playersOnFloor);
		matchingPlayers = RosterSQLGenerator.getMatchingPlayers(playersOnBench, player);
		
		if(matchingPlayers.size() < 1)
		{
			System.out.println("Unable to find player " + player.getPlayerName() +
					" on 2nd pass");
		}
		else if (matchingPlayers.size() == 1)
		{
			player.setPlayerID(matchingPlayers.get(0).getPlayerID());
			player.setPlayerName(matchingPlayers.get(0).getPlayerName());
			addPlayerOnFloor(player);
		}
		else
		{
			System.out.println("Unable narrow down player " + player.getPlayerName() +
					" on 2nd pass");
		}
	}
	
	protected void setOutgoingPlayer(Player player)
	{
		ArrayList<Player> matchingPlayers; 
		matchingPlayers = RosterSQLGenerator.getMatchingPlayers(playersOnFloor, player);
		
		if(matchingPlayers.size() < 1)
		{
			System.out.println("Unable to find player " + player.getPlayerName() +
					" on 2nd pass");
		}
		else if (matchingPlayers.size() == 1)
		{
			player.setPlayerID(matchingPlayers.get(0).getPlayerID());
			player.setPlayerName(matchingPlayers.get(0).getPlayerName());
			playersOnFloor.remove(player);
		}
		else
		{
			//because we can't be sure what matching player remains on court, all must
			//be removed
			System.out.println("Unable narrow down player " + player.getPlayerName() +
					" on 2nd pass");
			playersOnFloor.removeAll(matchingPlayers);
		}
	}
	
	protected void VisitEach(ArrayList<? extends Visitable> list)
	{
		ListIterator<? extends Visitable> listIterator = list.listIterator();
		while(listIterator.hasNext())
		{
			listIterator.next().accept(this);
		}
	}
	
	protected void VisitEachInReverse(ArrayList<? extends Visitable> list)
	{
		ListIterator<? extends Visitable> reverseIterator = 
				list.listIterator(list.size());
		while(reverseIterator.hasPrevious())
		{
			reverseIterator.previous().accept(this);
		}
	}

}
