package visitors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private PBPJson pbp;
	private String gameID;
	private int currentPeriodID, currentPossessionID, 
					currentActionID, currentPlayerID;
	private boolean missed;
	
	public SQLVisitor(String path, String userName, String password, PBPJson pbp)
	{
		this.pbp = pbp;
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
		this.gameID = pbp.getGameID();
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
		    	p.accept(this);
		    }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Play play) 
	{
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayerPlay play) 
	{
		this.currentPlayerID = play.getPlayer().getPlayerID();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(MissedPlay play) 
	{
		this.missed = true;
		this.currentPlayerID = play.getPlayer().getPlayerID();
		play.getPlayType().accept(this);
	}

	@Override
	public void visit(PlayType playType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Block block) {
		// TODO Auto-generated method stub
		
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
	public void visit(Shot shot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Assist assist) {
		// TODO Auto-generated method stub
		
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
			stmt = conn.prepareStatement("INSERT INTO `nba`.`possession` VALUES (DEFAULT);");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Play play : possession.getPossessionPlays())
		{
			this.missed = false;
			play.accept(this);
		}
	}

}
