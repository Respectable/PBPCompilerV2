package codeGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.boxScoreObjects.OfficalJson;

public class OfficalSQLGenerator 
{
	private ArrayList<OfficalJson> officals;
	private String gameID;
	
	public OfficalSQLGenerator(String gameID, ArrayList<OfficalJson> officals) 
	{
		this.gameID = gameID;
		this.officals = officals;
	}

	public void compile(String path,
			String userName, String password)
	{
		Connection conn;
		PreparedStatement stmt;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(path,userName,password);
			stmt = conn.prepareStatement("INSERT INTO `nba2`.`game_officals` (`game_id`,`offical_id`)" +
					"VALUES (?,?);");
			
			for (OfficalJson offical : this.officals)
			{
				stmt.setString(1, this.gameID);
				stmt.setInt(2, offical.getOfficalID());
				stmt.executeUpdate();
			}
			
			
			stmt.close();
			conn.close();
			
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
	
	public static void updateOfficals(ArrayList<OfficalJson> officals, String path,
			String userName, String password)
	{
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
		
		Collections.sort(officals, OfficalJson.COMPARE_BY_ID);
		ArrayList<OfficalJson> newOfficals = new ArrayList<OfficalJson>(officals);
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(path,userName,password);
			
			stmt = conn.prepareStatement("SELECT * FROM `nba2`.`offical`");
			rs = stmt.executeQuery();
			
			while(rs.next())
		    {
				newOfficals.remove(Collections.binarySearch(newOfficals,
		    			new OfficalJson(rs.getInt("offical_id"), "", "", ""), OfficalJson.COMPARE_BY_ID));
		    }
			
			for(OfficalJson offical : newOfficals)
			{
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`offical` (`offical_id`,`first_name`,`last_name`," +
						"`jersey_number`) VALUES (?,?,?,?);");
			    stmt.setInt(1, offical.getOfficalID());
			    stmt.setString(2, offical.getFirstName());
			    stmt.setString(3, offical.getLastName());
			    stmt.setString(4, offical.getJerseyNum());
			    stmt.executeUpdate();
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
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
}
