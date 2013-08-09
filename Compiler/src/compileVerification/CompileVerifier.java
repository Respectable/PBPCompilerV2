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
							System.out.println("Field Goals Made is incorrect");
						if (rs.getInt("FGA") != currentPlayer.getFgm())
							System.out.println("Field Goals Attempted is incorrect");
						if (rs.getInt("FTM") != currentPlayer.getFgm())
							System.out.println("Free Throws Made is incorrect");
						if (rs.getInt("FTA") != currentPlayer.getFgm())
							System.out.println("Free Throws Attempted is incorrect");
						if (rs.getInt("OREB") != currentPlayer.getFgm())
							System.out.println("Off. Rebound is incorrect");
						if (rs.getInt("DREB") != currentPlayer.getFgm())
							System.out.println("Def. Rebound  is incorrect");
						if (rs.getInt("AST") != currentPlayer.getFgm())
							System.out.println("Assists  are incorrect");
						if (rs.getInt("TO") != currentPlayer.getFgm())
							System.out.println("Turnovers  are incorrect");
						if (rs.getInt("STL") != currentPlayer.getFgm())
							System.out.println("Steals  are incorrect");
						if (rs.getInt("BLK") != currentPlayer.getFgm())
							System.out.println("Blocks  are incorrect");
						if (rs.getInt("PF") != currentPlayer.getFgm())
							System.out.println("Personal Fouls are incorrect");
						if (rs.getInt("PTS") != currentPlayer.getFgm())
							System.out.println("Points are incorrect");
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
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void delete(String gameID, String path,
			String userName, String password)
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(path,userName,password);
			CallableStatement cStmt = conn.prepareCall("{call delete_game(?)}");
			cStmt.setString(1, gameID);
			cStmt.executeUpdate();
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
