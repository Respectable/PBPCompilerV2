package visitors;

import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.PBPJson;
import jsonObjects.ShotJson;
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

public class ShotLocationVisitor implements Visitor {

	private ArrayList<ShotJson> shots;
	private ArrayList<PBPJson> pbp;
	private Play currentPlay;
	
	public ShotLocationVisitor(ArrayList<ShotJson> shots, ArrayList<PBPJson> pbp)
	{
		this.shots = shots;
		this.pbp = pbp;
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
			this.currentPlay = p;
			p.accept(this);
		}
	}

	@Override
	public void visit(Player player) {}

	@Override
	public void visit(Play play) {}

	@Override
	public void visit(PlayerPlay play) 
	{
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) 
	{
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
	public void visit(FreeThrow freeThrow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpBall jumpBall) {}

	@Override
	public void visit(Rebound rebound) {}

	@Override
	public void visit(Review review) {}

	@Override
	public void visit(Shot shot) 
	{
		ArrayList<ShotJson> possibleShots = new ArrayList<ShotJson>(shots);
		ShotJson foundShot;
		
		Collections.sort(possibleShots, ShotJson.COMPARE_BY_PLAY_ID);
		
		ShotJson dummy = new ShotJson();
		dummy.setPlayID(currentPlay.getPlayID());
		int index = Collections.binarySearch(possibleShots, dummy, 
				ShotJson.COMPARE_BY_PLAY_ID);
		if (index < 0)
		{
			System.out.println("Game: " + pbp.get(0).getGameID() + " " +
					"Play: " + currentPlay.getPlayID() + 
					" Shot location not found.");
		}
		else
		{
			foundShot = possibleShots.get(index);
			shot.setX(foundShot.getX());
			shot.setY(foundShot.getY());
		}
	}

	@Override
	public void visit(Assist assist) {}

	@Override
	public void visit(Steal steal) {}

	@Override
	public void visit(Substitution sub) {}

	@Override
	public void visit(Technical technical) {}

	@Override
	public void visit(DoubleTechnical technical) {}

	@Override
	public void visit(TauntingTechnical technical) {}

	@Override
	public void visit(Timeout timeout) {}

	@Override
	public void visit(Turnover turnover) {}

	@Override
	public void visit(Violation violation) {}

	@Override
	public void visit(DoublePersonalFoul foul) {}

	@Override
	public void visit(Possession possession) {}

}
