package visitors;

import java.util.ArrayList;

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

	private RosterSQLGenerator rosters;
	//private Player currentSearchPlayer;
	private Player currentPlayPlayer;
	private PlayRole currentRole;
	private ArrayList<Player> playersOnFloor;
	
	public SubstitutionPlayerVisitor(RosterSQLGenerator rosters) 
	{
		this.rosters = rosters;
		this.playersOnFloor = new ArrayList<Player>();
	}

	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) 
	{
		currentRole = PlayRole.HOME;
		//for (Player p : rosters.gethomeMatching())
		//{
			//currentSearchPlayer = p;
			for (Period per : game.getPeriods())
			{
				per.accept(this);
			}
		//}
		
		currentRole = PlayRole.AWAY;
		//for (Player p : rosters.getawayMatching())
		//{
			//currentSearchPlayer = p;
			for (Period per : game.getPeriods())
			{
				per.accept(this);
			}
		//}
	}

	@Override
	public void visit(Period period) 
	{
		this.playersOnFloor = new ArrayList<Player>();
		for (Play p : period.getPlays())
		{
			if (p.getContextInfo().getPlayRole() == PlayRole.NEUTRAL ||
					p.getContextInfo().getPlayRole() == this.currentRole)
			p.accept(this);
		}
	}

	@Override
	public void visit(Player player) {}

	@Override
	public void visit(Play play) 
	{
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) 
	{
		currentPlayPlayer = play.getPlayer();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) 
	{
		currentPlayPlayer = play.getPlayer();
		play.getPlayType().accept(this);
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
	
	private void addPlayerOnFloor(Player player)
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
	
	private void setIncomingPlayer(Player player)
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
			System.exit(-1);
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
	
	private void setOutgoingPlayer(Player player)
	{
		ArrayList<Player> matchingPlayers;
		matchingPlayers = RosterSQLGenerator.getMatchingPlayers(playersOnFloor, player);
		
		if(matchingPlayers.size() < 1)
		{
			System.out.println("Unable to find player " + player.getPlayerName() +
					" on 2nd pass");
			System.exit(-1);
		}
		else if (matchingPlayers.size() == 1)
		{
			player.setPlayerID(matchingPlayers.get(0).getPlayerID());
			player.setPlayerName(matchingPlayers.get(0).getPlayerName());
			playersOnFloor.remove(player);
		}
		else
		{
			System.out.println("Unable narrow down player " + player.getPlayerName() +
					" on 2nd pass");
		}
	}

}
