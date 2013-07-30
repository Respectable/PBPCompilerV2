package compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

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
	private final static String password = "******";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ArrayList<PlayerJson> players;
		BufferedReader br = NBADownloader.downloadPlayerData(currentSeason);
		players = PlayerJson.getPlayers(br);
		PlayerSQLGenerator.updatePlayers(players, dbPath, userName, password);
		
		ArrayList<TeamJson> teams;
		br = NBADownloader.downloadTeamData();
		teams = TeamJson.getTeams(br);
		TeamSQLGenerator.updateTeams(teams, dbPath, userName, password);
		
		for(String s : args)
		{
			String playText = "";
			try
			{
				try 
				{
				    Thread.sleep(3000);
				} 
				catch(InterruptedException ex) 
				{
				    Thread.currentThread().interrupt();
				}
				
				ArrayList<PBPJson> pbp;
				br = NBADownloader.downloadPBP(s);
				pbp = PBPJson.getPBP(br);
				
				BoxJson boxScore;
				br = NBADownloader.downloadBoxScore(s);
				boxScore = BoxJson.getBoxScore(br);
				
				ArrayList<ShotJson> shots;
				br = NBADownloader.downloadShotData(boxScore.getGameSeason(), s);
				shots = ShotJson.getShots(br);
				
				for (PBPJson play : pbp)
				{
					if (play.printPBP() != "")
					{
						playText += play.printPBP() + "\n";
					}
				}
				
				writePBPData(playText);
				
				parser p = new parser(new Yylex(new BufferedReader(new StringReader(playText))));
				Symbol parse_tree = p.parse();
				Game game = (Game)parse_tree.value;
				
				String gameID = pbp.get(0).getGameID();
				int homeID = boxScore.getGameSummary().getHomeID();
				int awayID = boxScore.getGameSummary().getAwayID();
				
				GameSQLGenerator gameGen = new GameSQLGenerator(boxScore.getGameInfo(),
						boxScore.getGameSummary());
				
				RosterSQLGenerator rosters = new RosterSQLGenerator(gameGen.getGameID(), homeID, awayID,
						boxScore.getInactives(), boxScore.getPlayerStats(), pbp, gameID);
				
				OfficalSQLGenerator officals = new OfficalSQLGenerator(gameGen.getGameID(), 
						boxScore.getOfficals());
				
				PlayerVisitor playerVisitor = new PlayerVisitor(rosters);
				game.accept(playerVisitor);
				
				ShotLocationVisitor shotVisitor = new ShotLocationVisitor(shots, pbp);
				game.accept(shotVisitor);
				
				PossessionVisitor possessionVisitor = new PossessionVisitor(rosters);
				game.accept(possessionVisitor);
				
				UnitVisitor unitVisitor = new UnitVisitor(rosters);
				game.accept(unitVisitor);
				
				gameGen.compile(dbPath, userName, password);
				rosters.compile(dbPath, userName, password);
				officals.compile(dbPath, userName, password);
				
				SQLVisitor sql = new SQLVisitor(dbPath, userName, password,
						pbp, homeID, awayID, gameGen.getGameID());
				game.accept(sql);
				
			}
			catch(Exception e)
			{
				System.out.println(e.getLocalizedMessage());
				System.out.println("Current Game:" + s);
				e.printStackTrace();
			}
			
		}
	}
	
	private static void writePBPData(String data)
	{
		try {
 
			File file = new File("/home/anthony/Desktop/Link to Dropbox/NBA 3.0/Scanner & Parser/PBP.txt");
 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
