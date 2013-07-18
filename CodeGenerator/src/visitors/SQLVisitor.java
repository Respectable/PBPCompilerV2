package visitors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.PBPJson;

import nba.ContextInfo;
import nba.Game;
import nba.Period;
import nba.Player;
import nba.Possession;
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

public class SQLVisitor implements Visitor {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	private ArrayList<PBPJson> pbp;
	private String gameID;
	private int currentPeriodID, currentPossessionID, 
					currentShotID, currentFoulID, currentTechnicalID,
					currentStealID, currentTurnoverID, currentPlayerID;
	private boolean missed;
	private ContextInfo currentContext;
	
	public SQLVisitor(String path, String userName, String password, ArrayList<PBPJson> pbp)
	{
		this.pbp = pbp;
		Collections.sort(this.pbp, PBPJson.COMPARE_BY_PLAY_ID);
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(path,userName,password);
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void visit(ContextInfo contextInfo) {}

	@Override
	public void visit(Game game) 
	{
		this.gameID = pbp.get(0).getGameID();
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Period period) 
	{
		try 
		{
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`period` (`period_identifier`)" +
					"VALUES (?);");
			stmt.setInt(1, period.getPeriod());
		    stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) 
		    {
		        this.currentPeriodID = rs.getInt(1);
		    } 
		    else 
		    {
		        //TODO throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba2`.`game_periods` (`game_id`,`period_id`)" +
					"VALUES (?,?);");
			stmt.setString(1, this.gameID);
			stmt.setInt(2, this.currentPeriodID);
		    stmt.executeUpdate();
		    
		    for(Possession p : period.getPossessions())
		    {
		    	currentShotID = -1;
		    	currentFoulID = -1;
		    	currentTechnicalID = -1;
		    	currentStealID = -1;
		    	currentTurnoverID = -1;
		    	currentPlayerID = -1;
		    	p.accept(this);
		    }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Player player) {}

	@Override
	public void visit(Play play) 
	{
		currentContext = play.getContextInfo();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) 
	{
		currentContext = play.getContextInfo();
		this.currentPlayerID = play.getPlayer().getPlayerID();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) 
	{
		currentContext = play.getContextInfo();
		this.missed = true;
		this.currentPlayerID = play.getPlayer().getPlayerID();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayType playType) {}

	@Override
	public void visit(Block block) 
	{
		int blockID = -1;
		
		if(this.currentShotID != -1)
		{
			try 
			{
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`block` VALUES (DEFAULT);");
				stmt.executeUpdate();
				
				rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			    if (rs.next()) 
			    {
			    	blockID = rs.getInt(1);
			    }
			    else 
			    {
			    	//TODO throw an exception from here
			    }
				
			    stmt = conn.prepareStatement("INSERT INTO `nba2`.`block_player` (`block_id`,`player_id`)" +
						"VALUES (?,?);");
				stmt.setInt(1, blockID);
				stmt.setInt(2, this.currentPlayerID);
				stmt.executeUpdate();
				
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`block_shot` (`shot_id`,`block_id`)" +
						"VALUES (?,?);");
				stmt.setInt(1, this.currentShotID);
				stmt.setInt(2, blockID);
				stmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Unable to find shot associated with block");
			//TODO error on shot not being found
		}
	}

	@Override
	public void visit(Ejection ejection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Foul foul) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoublePersonalFoul foul) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FreeThrow freeThrow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpBall jumpBall) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Rebound rebound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Review review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Shot shot) 
	{
		boolean threePointShot = shot.getShotType() instanceof ThreePointShot;
		try 
		{
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`shot` (`x`,`y`,`shot_made`," +
					"`three_pointer`, `shot_type`) VALUES (?,?,?,?,?);");
			stmt.setInt(1, shot.getX());
			stmt.setInt(2, shot.getY());
			stmt.setBoolean(3, !missed);
			stmt.setBoolean(4, threePointShot);
			stmt.setString(5, shot.getShotType().getDescription());
			stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) 
		    {
		        this.currentShotID = rs.getInt(1);
		    }
		    else 
		    {
		    	//TODO throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba2`.`shot_player` (`shot_id`,`player_id`)" +
					"VALUES (?,?);");
			stmt.setInt(1, this.currentShotID);
			stmt.setInt(2, this.currentPlayerID);
			stmt.executeUpdate();
		    
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`shot_possession` (`shot_id`," +
					"`possession_id`, `time_of_shot`) VALUES (?,?,?);");
			stmt.setInt(1, this.currentShotID);
			stmt.setInt(2, this.currentPossessionID);
			stmt.setInt(3, getConvertedPlayTime(currentContext.getPlayID()));
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if (shot.getShotEnding().getAssist() != null)
			shot.getShotEnding().getAssist().accept(this);
	}

	@Override
	public void visit(Assist assist) 
	{
		int assistID = -1;
		
		if(this.currentShotID != -1)
		{
			try 
			{
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`assist` VALUES (DEFAULT);");
				stmt.executeUpdate();
				
				rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			    if (rs.next()) 
			    {
			    	assistID = rs.getInt(1);
			    }
			    else 
			    {
			    	//TODO throw an exception from here
			    }
				
			    stmt = conn.prepareStatement("INSERT INTO `nba2`.`assist_player` (`assist_id`,`player_id`)" +
						"VALUES (?,?);");
				stmt.setInt(1, assistID);
				stmt.setInt(2, assist.getPlayer().getPlayerID());
				stmt.executeUpdate();
				
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`assist_shot` (`shot_id`,`assist_id`)" +
						"VALUES (?,?);");
				stmt.setInt(1, this.currentShotID);
				stmt.setInt(2, assistID);
				stmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Unable to find shot associated with assist");
			//TODO error on shot not being found
		}
	}

	@Override
	public void visit(Steal steal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Substitution sub) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Technical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleTechnical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TauntingTechnical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Timeout timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Turnover turnover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Violation violation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Possession possession) 
	{
		ArrayList<Player> players = possession.getAwayPlayers();
		players.addAll(possession.getHomePlayers());
		
		try 
		{
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`possession` VALUES (DEFAULT);");
			stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) 
		    {
		        this.currentPossessionID = rs.getInt(1);
		    } 
		    else 
		    {
		    	//TODO throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba2`.`period_possessions`" +
		    " (`period_id`,`possession_id`) VALUES (?,?);");
			stmt.setInt(1, this.currentPeriodID);
			stmt.setInt(2, this.currentPossessionID);
		    stmt.executeUpdate();
		    
		    for (Player player : players)
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba2`.`possession_players`" +
		    		    " (`possession_id`,`player_id`) VALUES (?,?);");
		    			stmt.setInt(1, this.currentPossessionID);
		    			stmt.setInt(2, player.getPlayerID());
		    		    stmt.executeUpdate();
		    }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		for(Play play : possession.getPossessionPlays())
		{
			this.missed = false;
			play.accept(this);
		}
	}
	
	private int convertStringTime(String time)
	{
		String[] timeParts = time.split(":");
		String min = timeParts[0];
		String tens = timeParts[1].substring(0,1);
		String singles = timeParts[1].substring(1, 2);
		return ((Integer.parseInt(min) * 60) + (Integer.parseInt(tens) * 10) +
				Integer.parseInt(singles)) * 10;
	}
	
	private String getPlayTime(int playID)
	{
		PBPJson relevantPlay = new PBPJson();
		relevantPlay.setEventNum(playID);
		int index = Collections.binarySearch(this.pbp, relevantPlay, 
				PBPJson.COMPARE_BY_PLAY_ID);
		
		if (index == -1)
		{
			System.out.println("Game: " + this.gameID + " " +
					"Play: " + playID + " Play not found.");
			System.exit(-1);
		}
		else
		{
			relevantPlay = this.pbp.get(index);
		}
		
		return relevantPlay.getActualTime();
	}
	
	private int getConvertedPlayTime(int playID)
	{
		return convertStringTime(getPlayTime(playID));
	}

}
