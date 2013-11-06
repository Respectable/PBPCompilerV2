package visitors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import jsonObjects.BoxJson;
import jsonObjects.PBPJson;
import jsonObjects.boxScoreObjects.PlayerStatsJson;
import codeGenerator.PlayerParser;
import codeGenerator.RosterSQLGenerator;
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
import nbaDownloader.NBADownloader;
import visitor.Visitor;
import visitors.TalliedDuplicatePlayerVisitor.CheckTeam;

public class TimePlayerVisitor implements Visitor
{
	private RosterSQLGenerator rosters;
	private ArrayList<PBPJson> pbp;
	private int homeID, awayID;
	private Period currentPeriod;
	private ContextInfo currentContext;
	private HashMap<Player, Integer> playingTimes;
	
	
	public TimePlayerVisitor(RosterSQLGenerator rosters, ArrayList<PBPJson> pbp,
			int homeID, int awayID) 
	{
		this.rosters = rosters;
		this.pbp = new ArrayList<PBPJson>(pbp);
		this.homeID = homeID;
		this.awayID = awayID;
		Collections.sort(this.pbp, PBPJson.COMPARE_BY_PLAY_ID);
		playingTimes = new HashMap<Player, Integer>();
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
		int index, time, currentTeamID;
		PBPJson relevantPlay = new PBPJson();
		ArrayList<PlayerStatsJson> pbpData;
		ArrayList<Player> possiblePlayers, matchingPlayers;
		HashMap<Player, Integer> map;
		relevantPlay.setEventNum(currentContext.getPlayID());
		
		if (sub.getIn().getPlayerID() >= 0 && sub.getOut().getPlayerID() >= 0)
		{
			return;
		}
		
		currentTeamID = -1;
		
		if (currentContext.getPlayRole().equals(PlayRole.HOME))
			currentTeamID = homeID;
		else if (currentContext.getPlayRole().equals(PlayRole.AWAY))
			currentTeamID = awayID;
			
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
			
			possiblePlayers = TalliedDuplicatePlayerVisitor.parsePlayers(currentTeamID, pbpData, new CheckTeam());
			matchingPlayers = RosterSQLGenerator.getMatchingPlayers(possiblePlayers, sub.getIn());
			
			map = setPlayingTimes(currentTeamID, pbpData, matchingPlayers, new CheckTeam());
			
			int numOfPlayers = 0;
			for (int min : map.values())
			{
				if (min > 240)
					numOfPlayers++;
			}
			if (numOfPlayers == 1)
			{
				for (Entry<Player,Integer> item : map.entrySet())
				{
					if (item.getValue() > 240)
					{
						sub.getIn().setPlayerID(item.getKey().getPlayerID());
						sub.getIn().setPlayerName(item.getKey().getPlayerName());
					}
				}
				return;
			}
			
			pbpData = BoxJson.getDownloadedBoxScorePlayers(
					NBADownloader.downloadCustomBox(pbp.get(0).getGameID(),
							periodEndTime(currentPeriod), time));
			
			possiblePlayers = TalliedDuplicatePlayerVisitor.parsePlayers(currentTeamID, pbpData, new CheckTeam());
			matchingPlayers = RosterSQLGenerator.getMatchingPlayers(possiblePlayers, sub.getIn());
			
			map = setPlayingTimes(currentTeamID, pbpData, matchingPlayers, new CheckTeam());
			
			numOfPlayers = 0;
			for (int min : map.values())
			{
				if (min <= 240)
					numOfPlayers++;
			}
			if (numOfPlayers == 1)
			{
				for (Entry<Player,Integer> item : map.entrySet())
				{
					if (item.getValue() <= 240)
					{
						sub.getIn().setPlayerID(item.getKey().getPlayerID());
						sub.getIn().setPlayerName(item.getKey().getPlayerName());
					}
				}
				return;
			}
		}
		else if (sub.getOut().getPlayerID() == -1)
		{
			pbpData = BoxJson.getDownloadedBoxScorePlayers(
					NBADownloader.downloadCustomBox(pbp.get(0).getGameID(),
							periodStartTime(currentPeriod), time));
			
			possiblePlayers = TalliedDuplicatePlayerVisitor.parsePlayers(currentTeamID, pbpData, new CheckTeam());
			matchingPlayers = RosterSQLGenerator.getMatchingPlayers(possiblePlayers, sub.getOut());
			
			map = setPlayingTimes(currentTeamID, pbpData, matchingPlayers, new CheckTeam());
			
			int numOfPlayers = 0;
			for (int min : map.values())
			{
				if (min > 240)
					numOfPlayers++;
			}
			if (numOfPlayers == 1)
			{
				for (Entry<Player,Integer> item : map.entrySet())
				{
					if (item.getValue() > 240)
					{
						sub.getIn().setPlayerID(item.getKey().getPlayerID());
						sub.getIn().setPlayerName(item.getKey().getPlayerName());
					}
				}
				return;
			}
			
			pbpData = BoxJson.getDownloadedBoxScorePlayers(
					NBADownloader.downloadCustomBox(pbp.get(0).getGameID(),
							time, periodEndTime(currentPeriod)));
			
			possiblePlayers = TalliedDuplicatePlayerVisitor.parsePlayers(currentTeamID, pbpData, new CheckTeam());
			matchingPlayers = RosterSQLGenerator.getMatchingPlayers(possiblePlayers, sub.getOut());
			
			map = setPlayingTimes(currentTeamID, pbpData, matchingPlayers, new CheckTeam());
			
			numOfPlayers = 0;
			for (int min : map.values())
			{
				if (min <= 240)
					numOfPlayers++;
			}
			if (numOfPlayers == 1)
			{
				for (Entry<Player,Integer> item : map.entrySet())
				{
					if (item.getValue() <= 240)
					{
						sub.getIn().setPlayerID(item.getKey().getPlayerID());
						sub.getIn().setPlayerName(item.getKey().getPlayerName());
					}
				}
				return;
			}
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
	
	private HashMap<Player, Integer> setPlayingTimes(int teamID, 
			ArrayList<PlayerStatsJson> pbpData,
			ArrayList<Player> matchingPlayers,
			PlayerParser<PlayerStatsJson> parser)
	{
		HashMap<Player, Integer> map = new HashMap<Player, Integer>();
		
		for(PlayerStatsJson player : pbpData)
		{
			if(parser.check(teamID, player))
			{
				Player currentPlayer = new Player(player.getPlayerName(), 
						player.getPlayerID());
				if (matchingPlayers.contains(currentPlayer))
				{
					map.put(currentPlayer, player.getMinutes());
				}
			}
		}
		
		return map;
	}
	
	public class CheckTeam implements PlayerParser<PlayerStatsJson>
	{
		@Override
		public boolean check(int teamID, PlayerStatsJson player) 
		{
			return player.getTeamID() == teamID;
		}
	}

}
