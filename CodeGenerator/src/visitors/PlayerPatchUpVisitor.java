package visitors;

import java.util.ArrayList;

import jsonObjects.ShotJson;
import codeGenerator.RosterSQLGenerator;
import visitor.Visitor;
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

public class PlayerPatchUpVisitor implements Visitor
{

	private RosterSQLGenerator rosters;
	private ArrayList<ShotJson> shots;
	private Possession currentPossession;
	private Play currentPlay;
	private PlayerVisitorState state;
	
	public PlayerPatchUpVisitor(RosterSQLGenerator rosters, 
			ArrayList<ShotJson> shots)
	{
		this.rosters = rosters;
		this.shots = shots;
	}
	
	private void changeState(Play play)
	{
		if (play.getPlayType() instanceof JumpBall)
		{
			this.state = PlayerVisitorState.JUMPBALL;
		}
		else if (play.getPlayType() instanceof Rebound)
		{
			if (((Rebound)play.getPlayType()).isPlayerRebound())
				this.state = PlayerVisitorState.PLAYERREBOUND;
			else
				this.state = PlayerVisitorState.TEAMREBOUND;
		}
		else if (play.getPlayType() instanceof Turnover)
		{
			//TODO
			this.state = PlayerVisitorState.PLAYERTURNOVER;
		}
		else if (play.getPlayType() instanceof Violation)
		{
			//TODO
			this.state = PlayerVisitorState.PLAYERVIOLATION;
		}
		else if (play.getPlayType() instanceof Shot)
		{
			this.state = PlayerVisitorState.SHOT;
		}
		else if (play.getPlayType() instanceof Timeout)
		{
			this.state = PlayerVisitorState.TIMEOUT;
		}
		else if (play.getPlayType() instanceof Substitution)
		{
			this.state = PlayerVisitorState.SUB;
		}
		else if (play.getPlayType() instanceof Foul)
		{
			//TODO
			if (play.getPlayType() instanceof DoublePersonalFoul)
				this.state = PlayerVisitorState.DOUBLEFOUL;
			else
				this.state = PlayerVisitorState.PLAYERFOUL;
				
		}
		else if (play.getPlayType() instanceof FreeThrow)
		{
			this.state = PlayerVisitorState.FREETHROW;
		}
		else if (play.getPlayType() instanceof Technical)
		{
			//TODO
			if (play.getPlayType() instanceof DoubleTechnical)
				this.state = PlayerVisitorState.DOUBLETECH;
			else
				this.state = PlayerVisitorState.PLAYERTECH;
		}
		else if (play.getPlayType() instanceof Ejection)
		{
			this.state = PlayerVisitorState.EJECTION;
		}
	}
	
	private void singleHomeAwayCase(Player player)
	{
		Player player1, player2;
		switch(state)
		{
		case JUMPBALL:
			JumpBall jump = (JumpBall)currentPlay.getPlayType();
			player1 = jump.getPlayer1();
			player2 = jump.getPlayer2();
			Player tippedTo = null;
			if (jump.getEnding().getTippedTo() != null)
				tippedTo = jump.getEnding().getTippedTo();
			if (player1.getPlayerID() == -4 && player2.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player2))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player2))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player2.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player2.getPlayerID() == -4 && 
						player1.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player1))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player1))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player1.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player1.getPlayerID() == -4 &&
						player2.getPlayerID() == -4 &&
						player1.equals(player2))
			{
				rosters.findHomePlayer(player1);
				rosters.setAwayPlayer(player2);
			}
			//TODO handle unknown player whom is 'tipped to' in jump ball
			break;
		case DOUBLETECH:
			DoubleTechnical dt = (DoubleTechnical)currentPlay.getPlayType();
			player1 = dt.getPlayer1();
			player2 = dt.getPlayer2();
			if (player1.getPlayerID() == -4 && player2.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player2))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player2))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player2.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player2.getPlayerID() == -4 && 
						player1.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player1))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player1))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player1.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player1.getPlayerID() == -4 &&
						player2.getPlayerID() == -4 &&
						player1.equals(player2))
			{
				rosters.findHomePlayer(player1);
				rosters.setAwayPlayer(player2);
			}
			break;
		case DOUBLEFOUL:
			DoublePersonalFoul foul = (DoublePersonalFoul)currentPlay.getPlayType();
			player1 = foul.getPlayer1();
			player2 = foul.getPlayer2();
			if (player1.getPlayerID() == -4 && player2.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player2))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player2))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player2.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player2.getPlayerID() == -4 && 
						player1.getPlayerID() > 0)
			{
				if(rosters.searchHomePlayers(player1))
				{
					rosters.setAwayPlayer(player);
				}
				else if(rosters.searchAwayPlayers(player1))
				{
					rosters.findHomePlayer(player);
				}
				else
				{
					System.out.println("Was unable to find player " +
							player1.getPlayerName() + "while correcting player " + 
							player.getPlayerName());
					System.exit(-1);
				}
			}
			else if (player1.getPlayerID() == -4 &&
						player2.getPlayerID() == -4 &&
						player1.equals(player2))
			{
				rosters.findHomePlayer(player1);
				rosters.setAwayPlayer(player2);
			}
			break;
		}
	}
	
	private void singleTeamCase(Player player, boolean isHome)
	{
		
	}
	
	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) 
	{
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
		
		
		for (Period p : game.getPeriods())
		{
			for (Possession poss : p.getPossessions())
			{
				if(poss.getHomePlayers().size() < 5)
				{
					System.out.println("Home unit not filled");
					System.out.println("Possession------------------------------");
					System.out.println("Home Players:");
					for (Player player : poss.getHomePlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("Away Players:");
					for (Player player : poss.getAwayPlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("----------------------------------------");
				}
				if(poss.getAwayPlayers().size() < 5)
				{
					System.out.println("Away unit not filled");
					System.out.println("Possession------------------------------");
					System.out.println("Home Players:");
					for (Player player : poss.getHomePlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("Away Players:");
					for (Player player : poss.getAwayPlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("----------------------------------------");
				}
				
			}
		}
	}

	@Override
	public void visit(Period period) 
	{
		for (Possession poss : period.getPossessions())
		{
			currentPossession = poss;
			poss.accept(this);
		}
	}

	@Override
	public void visit(Player player) 
	{
		if (player.getPlayerID() == -4)
		{
			singleHomeAwayCase(player);
		}
		else if (player.getPlayerID() == -2)
		{
			singleTeamCase(player, true);
		}
		else if (player.getPlayerID() == -3)
		{
			singleTeamCase(player, false);
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
		if (jumpBall.getEnding().getTippedTo() != null)
			jumpBall.getEnding().getTippedTo().accept(this);
	}

	@Override
	public void visit(Rebound rebound) {}

	@Override
	public void visit(Review review) {}

	@Override
	public void visit(Shot shot) 
	{
		if (shot.getShotEnding().getAssist() != null)
			shot.getShotEnding().getAssist().accept(this);
	}

	@Override
	public void visit(Assist assist) 
	{
		if (assist.getPlayer() != null)
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

	@Override
	public void visit(Possession possession) 
	{
		for (Play p : possession.getPossessionPlays())
		{
			currentPlay = p;
			changeState(p);
			p.accept(this);
		}
	}
	
}
