package visitors;

import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.PBPJson;
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
	private PlayerVisitorState state;
	private PlayRole currentTeam;
	private Play currentPlay;
	private PBPJson nextActivePlay;
	private ArrayList<PBPJson> pbp;
	
	public PlayerVisitor(RosterSQLGenerator rosters, ArrayList<PBPJson> pbp)
	{
		this.rosters = rosters;
		this.pbp = pbp;
		Collections.sort(this.pbp, PBPJson.COMPARE_BY_GAME_TIME);
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
		else if (play.getPlayType() instanceof Block)
		{
			this.state = PlayerVisitorState.BLOCK;
		}
		else if (play.getPlayType() instanceof Steal)
		{
			this.state = PlayerVisitorState.STEAL;
		}
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
			changeState(p);
			currentPlay = p;
			this.currentTeam = p.getContextInfo().getPlayRole();
			p.accept(this);
		}
	}

	@Override
	public void visit(Player player) 
	{
		switch (state)
		{
		case JUMPBALL: case DOUBLEFOUL: case DOUBLETECH: 
			rosters.setPlayer(player, currentPlay, PlayRole.NEUTRAL);
			break;
		case PLAYERREBOUND: case PLAYERTURNOVER: case PLAYERVIOLATION:
		case SHOT: case PLAYERFOUL: case FREETHROW: case PLAYERTECH:
		case EJECTION: case BLOCK: case STEAL:
			rosters.setPlayer(player, currentPlay, currentTeam);
			break;
		case SUB:
			nextActivePlay = getNextPlay(currentPlay.getPlayID());
			rosters.setPlayer(player, currentPlay, currentTeam, nextActivePlay);
			break;
		case TEAMREBOUND: case TEAMTURNOVER: case TEAMVIOLATION:
		case TIMEOUT: case TEAMFOUL: case TEAMTECH:
			player.setPlayerID(-1);
			player.setPlayerName("#TEAM");
			break;
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
	public void visit(Possession possession) {}
	
	private PBPJson getNextPlay(int playID)
	{
		PBPJson currentPlay = new PBPJson();
		currentPlay.setEventNum(playID);
		
		int index = Collections.binarySearch(this.pbp, currentPlay, 
				PBPJson.COMPARE_BY_PLAY_ID);
		
		if (index == -1)
		{
			System.out.println("Play: " + playID + 
					" Play not found.");
			System.exit(-1);
		}
		else
		{
			currentPlay = this.pbp.get(index);
		}
		
		for (PBPJson play : this.pbp)
		{
			if (play.getConvertedStringTime() > currentPlay.getConvertedStringTime())
			{
				return play;
			}
		}
		
		return null;
	}

	

}
