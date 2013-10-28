package visitors;

import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.BoxJson;
import jsonObjects.PBPJson;
import jsonObjects.boxScoreObjects.PlayerStatsJson;
import codeGenerator.RosterSQLGenerator;
import nba.ContextInfo;
import nba.Game;
import nba.Period;
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
import nbaDownloader.NBADownloader;
import visitor.Visitor;

public class TimePlayerVisitor implements Visitor
{
	private RosterSQLGenerator rosters;
	private ArrayList<PBPJson> pbp;
	private int homeID, awayID;
	private Period currentPeriod;
	private ContextInfo currentContext;
	
	
	public TimePlayerVisitor(RosterSQLGenerator rosters, ArrayList<PBPJson> pbp,
			int homeID, int awayID) 
	{
		this.rosters = rosters;
		this.pbp = new ArrayList<PBPJson>(pbp);
		this.homeID = homeID;
		this.awayID = awayID;
		Collections.sort(this.pbp, PBPJson.COMPARE_BY_PLAY_ID);
	}

	@Override
	public void visit(ContextInfo contextInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

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
		this.currentPeriod = period;
		for (Play p : period.getPlays())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Player player) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Play play) throws Exception 
	{
		currentContext = play.getContextInfo();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) throws Exception 
	{
		currentContext = play.getContextInfo();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) throws Exception 
	{
		currentContext = play.getContextInfo();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayType playType) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Block block) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Ejection ejection) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Foul foul) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoublePersonalFoul foul) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FreeThrow freeThrow) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpBall jumpBall) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Rebound rebound) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Review review) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Shot shot) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Assist assist) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Steal steal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Substitution sub) throws Exception 
	{
		int index, time;
		PBPJson relevantPlay = new PBPJson();
		ArrayList<PlayerStatsJson> pbpData;
		relevantPlay.setEventNum(currentContext.getPlayID());
		
		if (sub.getIn().getPlayerID() >= 0 && sub.getOut().getPlayerID() >= 0)
		{
			return;
		}
		
		index = -1;
		try
		{
			index = Collections.binarySearch(this.pbp, relevantPlay, 
				PBPJson.COMPARE_BY_PLAY_ID);
		}
		catch (Exception e)
		{
			
		}
		
		if (index < 0)
		{
			System.out.println("Play: " + currentContext.getPlayID() + 
					" Play not found.");
			throw new Exception();
		}
		else
		{
			relevantPlay = this.pbp.get(index);
		}
		
		time = PBPJson.convertStringTime(relevantPlay.getGameTime(), relevantPlay);
		
		if (sub.getIn().getPlayerID() == -1)
		{
			pbpData = BoxJson.getDownloadedBoxScorePlayers(
					NBADownloader.downloadCustomBox(pbp.get(0).getGameID(),
							time, periodEndTime(currentPeriod)));
		}
		else if (sub.getOut().getPlayerID() == -1)
		{
			pbpData = BoxJson.getDownloadedBoxScorePlayers(
					NBADownloader.downloadCustomBox(pbp.get(0).getGameID(),
							periodStartTime(currentPeriod), time));
		}
	}

	@Override
	public void visit(Technical technical) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleTechnical technical) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TauntingTechnical technical) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Timeout timeout) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Turnover turnover) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Violation violation) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Possession possession) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private int periodStartTime(Period period)
	{
		int periodNum = period.getPeriod();
		if (period.getPeriod() < 5)
		{
			return (periodNum - 1) * (12 * 60 * 10);
		}
		else
		{
			return ((periodNum - 5) * (5 * 60 * 10)) +
					(4 * (12 * 60 * 10));
		}
	}
	
	private int periodEndTime(Period period)
	{
		int periodNum = period.getPeriod();
		
		if (periodNum < 5)
		{
			return periodNum * (12 * 60 * 10);
		}
		else
		{
			return (periodNum * (5 * 60 * 10)) +
					(4 * (12 * 60 * 10));
		}
	}

}
