package compileVerification;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jsonObjects.boxScoreObjects.PlayerStatsJson;

public class CompileVerifier 
{
	public static boolean verify(ArrayList<PlayerStatsJson> stats, String path,
			String userName, String password)
	{
		PlayerStatsJson currentPlayer;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(path,userName,password);
			CallableStatement cStmt = conn.prepareCall("{call box_score(?)}");
			cStmt.setString(1, stats.get(0).getGameID());
			
			boolean hadResults = cStmt.execute();
			
			while(hadResults)
			{
				ResultSet rs = cStmt.getResultSet();
				
				while(rs.next())
			    {
					//need to check for inactive player
					currentPlayer = findPlayer(rs.getInt("ID"), stats);
					if (currentPlayer == null)
					{
						return false;
					}
					if ((rs.getInt("FGM") != currentPlayer.getFgm())
						|| (rs.getInt("FGA") != currentPlayer.getFga())
						|| (rs.getInt("FTM") != currentPlayer.getFtm())
						|| (rs.getInt("FTA") != currentPlayer.getFta())
						|| (rs.getInt("OREB") != currentPlayer.getOReb())
						|| (rs.getInt("DREB") != currentPlayer.getDReb())
						|| (rs.getInt("AST") != currentPlayer.getAst())
						|| (rs.getInt("TO") != currentPlayer.getTo())
						|| (rs.getInt("STL") != currentPlayer.getStl())
						|| (rs.getInt("BLK") != currentPlayer.getBlk())
						|| (rs.getInt("PF") != currentPlayer.getPf())
						|| (rs.getInt("PTS") != currentPlayer.getPts()))
					{
						return false;
					}
			    }
				
				hadResults = cStmt.getMoreResults();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void delete(String gameID)
	{
		//TODO
	}
	
	private static PlayerStatsJson findPlayer(int ID, 
											  ArrayList<PlayerStatsJson> stats)
	{
		for (PlayerStatsJson ps : stats)
		{
			if (ps.getPlayerID() == ID)
			{
				return ps;
			}
		}
		
		return null;
	}
	
	
	
}
