package compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import compileVerification.CompileVerifier;

import codeGenerator.*;
import visitors.*;

import java_cup.runtime.Symbol;

import parser.parser;
import scanner.Yylex;

import nba.Game;
import nbaDownloader.NBADownloader;

import jsonObjects.*;

public class Compiler {

	private final static String currentSeason = "2012-13";
	private final static String dbPath = "jdbc:mysql://localhost/nba2";
	private final static String userName = "root";
	private final static String password = "***";
	private final static String useTextPBP = "-PBP";
	private final static String useTextShots = "-Shot";
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException
	{
		boolean usingTextPBP, usingTextShots;
		usingTextPBP = false;
		usingTextShots = false;
		Connection conn = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbPath,userName,password);
			
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.exit(-1);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		ArrayList<PlayerJson> players;
		BufferedReader br = NBADownloader.downloadPlayerData(currentSeason);
		players = PlayerJson.getPlayers(br);
		PlayerSQLGenerator.updatePlayers(players, conn);
		
		ArrayList<TeamJson> teams;
		br = NBADownloader.downloadTeamData();
		teams = TeamJson.getTeams(br);
		TeamSQLGenerator.updateTeams(teams, conn);
		
		conn.setAutoCommit(false);
		
		for(String s : args)
		{
			if (s.contains(useTextPBP))
			{
				usingTextPBP = true;
				continue;
			}
			
			if (s.contains(useTextShots))
			{
				usingTextShots = true;
				continue;
			}
			
			String playText = "";
			try
			{
				
				//download pbp data
				ArrayList<PBPJson> pbp;
				if (!usingTextPBP)
					br = NBADownloader.downloadPBP(s);
				else
				{
					//Create a file chooser
					final JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Choose PBP File - " + s);
					int returnVal = fc.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();
						br = new BufferedReader(new FileReader(file.getAbsolutePath()));
					}
					else
					{
						
					}
				}
					
				pbp = PBPJson.getPBP(br);
				
				//download box score data
				BoxJson boxScore;
				br = NBADownloader.downloadBoxScore(s);
				boxScore = BoxJson.getBoxScore(br);
				
				
				//download shot data
				ArrayList<ShotJson> shots;
				if (!usingTextShots)
					br = NBADownloader.downloadShotData(boxScore.getGameSeason(), s);
				else
				{
					//Create a file chooser
					final JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Choose Shot Location File");
					int returnVal = fc.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();
						br = new BufferedReader(new FileReader(file.getAbsolutePath()));
					}
					else
					{
						
					}
				}
				shots = ShotJson.getShots(br);
				
				for (PBPJson play : pbp)
				{
					if (play.printPBP() != "")
					{
						playText += play.printPBP() + "\n";
					}
				}
				
				//parse game into parse tree
				parser p = new parser(new Yylex(new BufferedReader(new StringReader(playText))));
				Symbol parse_tree = p.parse();
				Game game = (Game)parse_tree.value;
				
				String gameID = pbp.get(0).getGameID();
				int homeID = boxScore.getGameSummary().getHomeID();
				int awayID = boxScore.getGameSummary().getAwayID();
				
				GameSQLGenerator gameGen = new GameSQLGenerator(boxScore.getGameInfo(),
						boxScore.getGameSummary());
				
				//setup gameid, players, season, etc.
				gameGen.compile(conn);
				
				OfficialSQLGenerator.updateOfficials(boxScore.getOfficals(), dbPath, userName, password);
				
				RosterSQLGenerator rosters = new RosterSQLGenerator(homeID, awayID, gameGen.getGameID(),
						boxScore.getInactives(), boxScore.getPlayerStats());
				
				OfficialSQLGenerator officials = new OfficialSQLGenerator(gameGen.getGameID(), 
						boxScore.getOfficals());
				
				NonDuplicatePlayerVisitor playerVisitor = new NonDuplicatePlayerVisitor(rosters);
				game.accept(playerVisitor);
				
				TalliedDuplicatePlayerVisitor talliedVisitor = 
						new TalliedDuplicatePlayerVisitor(rosters, pbp, homeID, awayID);
				game.accept(talliedVisitor);
				
				SubstitutionPlayerVisitor subVisitor = new SubstitutionPlayerVisitor(rosters);
				game.accept(subVisitor);
				
				TimePlayerVisitor timeVisitor = new TimePlayerVisitor(rosters, pbp, homeID, awayID);
				game.accept(timeVisitor);
				
				//FinalPlayerVisitor finalVisitor = new FinalPlayerVisitor(rosters);
				//game.accept(finalVisitor);
				
				ShotLocationVisitor shotVisitor = new ShotLocationVisitor(shots, pbp);
				game.accept(shotVisitor);
				
				PossessionVisitor possessionVisitor = new PossessionVisitor(rosters);
				game.accept(possessionVisitor);
				
				UnitVisitor unitVisitor = new UnitVisitor(rosters);
				game.accept(unitVisitor);
				
				//DebugVisitor debug = new DebugVisitor();
				//game.accept(debug);
				
				rosters.compile(conn);
				officials.compile(conn);
				
				SQLVisitor sql = new SQLVisitor(conn,
						pbp, homeID, awayID, gameGen.getGameID());
				game.accept(sql);
				
				Boolean check = CompileVerifier.verify(boxScore.getPlayerStats(),
						conn);
				
				if (!check)
				{
					conn.rollback();
					writePBPData(s);
					continue;
					//CompileVerifier.delete(gameID,conn);
					//System.exit(-1);
				}
				else
				{
					conn.commit();
				}
				
					
				
			}
			catch(Exception e)
			{
				System.out.println(e.getLocalizedMessage());
				System.out.println("Current Game:" + s);
				e.printStackTrace();
				conn.rollback();
				writePBPData(s);
				continue;
			}
//			
		}
		conn.close();
	}
	
	private static void writePBPData(String data)
	{
		//try {
 
			try {
			    PrintWriter out = new PrintWriter(
			    		new BufferedWriter(new FileWriter("/home/anthony/Desktop/Link to Dropbox/NBA 3.0/Scanner & Parser/PBP.txt", true)));
			    out.println(data);
			    out.close();
			} catch (IOException e) {
			    //oh noes!
			}
			
			//File file = new File("/home/anthony/Desktop/Link to Dropbox/NBA 3.0/Scanner & Parser/PBP.txt");
 
			// if file doesn't exists, then create it
			//if (!file.exists()) {
			//	file.createNewFile();
			//}
 
			//FileWriter fw = new FileWriter(file.getAbsoluteFile());
			//BufferedWriter bw = new BufferedWriter(fw);
			//bw.write(data);
			//bw.close();
 
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}

}
