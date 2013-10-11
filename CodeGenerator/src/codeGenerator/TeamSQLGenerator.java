package codeGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import jsonObjects.TeamJson;

public class TeamSQLGenerator 
{

	public static void updateTeams(ArrayList<TeamJson> teams, Connection conn)
	{
		PreparedStatement stmt;
		ResultSet rs;
		
		Collections.sort(teams, TeamJson.COMPARE_BY_ID);
		ArrayList<TeamJson> newTeams = new ArrayList<TeamJson>(teams);
		
		try 
		{
			stmt = conn.prepareStatement("SELECT * FROM `nba2`.`team`");
			rs = stmt.executeQuery();
			
			while(rs.next())
		    {
		    	newTeams.remove(Collections.binarySearch(newTeams,
		    			new TeamJson("", rs.getInt("team_id"), 0, 0, ""), TeamJson.COMPARE_BY_ID));
		    }
			
			for(TeamJson team : newTeams)
			{
				stmt = conn.prepareStatement("INSERT INTO `nba2`.`team` (`team_id`,`league_id`,`start_year`," +
						"`end_year`, `abbreviation`) VALUES (?,?,?,?,?);");
			    stmt.setInt(1, team.getTeamID());
			    stmt.setString(2, team.getLeagueID());
			    stmt.setInt(3, team.getTeamStartYear());
			    stmt.setInt(4, team.getTeamEndYear());
			    stmt.setString(5, team.getAbbr());
			    stmt.executeUpdate();
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
