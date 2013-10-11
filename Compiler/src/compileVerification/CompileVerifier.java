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
	public static boolean verify(ArrayList<PlayerStatsJson> stats, Connection conn)
	{
		PlayerStatsJson currentPlayer;
		try 
		{
			CallableStatement cStmt = conn.prepareCall("{call box_score(?)}");
			cStmt.setString(1, stats.get(0).getGameID());
			ResultSet rs;
			
			boolean hadResults = cStmt.execute();
			
			while(hadResults)
			{
				rs = cStmt.getResultSet();
				
				while(rs.next())
			    {
					//need to check for inactive player
					currentPlayer = findPlayer(rs.getInt("ID"), stats);
					if (currentPlayer == null)
					{
						return false;
					}
					if (!currentPlayer.getComment().equals(""))
					{
						continue;
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
						System.out.println(currentPlayer.getGameID() +
								" Error on player " + currentPlayer.getPlayerName());
						if (rs.getInt("FGM") != currentPlayer.getFgm())
							System.out.println("Field Goals Made is incorrect- Database:"
									+ rs.getInt("FGM") + " Box: " + currentPlayer.getFgm());
						if (rs.getInt("FGA") != currentPlayer.getFga())
							System.out.println("Field Goals Attempted is incorrect- Database:"
									+ rs.getInt("FGA") + " Box: " + currentPlayer.getFga());
						if (rs.getInt("FTM") != currentPlayer.getFtm())
							System.out.println("Free Throws Made is incorrect- Database:"
									+ rs.getInt("FTM") + " Box: " + currentPlayer.getFtm());
						if (rs.getInt("FTA") != currentPlayer.getFta())
							System.out.println("Free Throws Attempted is incorrect- Database:"
									+ rs.getInt("FTA") + " Box: " + currentPlayer.getFta());
						if (rs.getInt("OREB") != currentPlayer.getOReb())
							System.out.println("Off. Rebound is incorrect- Database:"
									+ rs.getInt("OREB") + " Box: " + currentPlayer.getOReb());
						if (rs.getInt("DREB") != currentPlayer.getDReb())
							System.out.println("Def. Rebound is incorrect- Database:"
									+ rs.getInt("DREB") + " Box: " + currentPlayer.getDReb());
						if (rs.getInt("AST") != currentPlayer.getAst())
							System.out.println("Assists are incorrect- Database:"
									+ rs.getInt("AST") + " Box: " + currentPlayer.getAst());
						if (rs.getInt("TO") != currentPlayer.getTo())
							System.out.println("Turnovers are incorrect- Database:"
									+ rs.getInt("TO") + " Box: " + currentPlayer.getTo());
						if (rs.getInt("STL") != currentPlayer.getStl())
							System.out.println("Steals are incorrect- Database:"
									+ rs.getInt("STL") + " Box: " + currentPlayer.getStl());
						if (rs.getInt("BLK") != currentPlayer.getBlk())
							System.out.println("Blocks are incorrect- Database:"
									+ rs.getInt("BLK") + " Box: " + currentPlayer.getBlk());
						if (rs.getInt("PF") != currentPlayer.getPf())
							System.out.println("Personal Fouls are incorrect- Database:"
									+ rs.getInt("PF") + " Box: " + currentPlayer.getPf());
						if (rs.getInt("PTS") != currentPlayer.getPts())
							System.out.println("Points are incorrect- Database:"
									+ rs.getInt("PTS") + " Box: " + currentPlayer.getPts());
						return false;
					}
			    }
				
				hadResults = cStmt.getMoreResults();
			}
			cStmt.close();
			conn.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void delete(String gameID, Connection conn)
	{
		try 
		{
			CallableStatement cStmt = conn.prepareCall("{call delete_game(?)}");
			cStmt.setString(1, gameID);
			cStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
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
