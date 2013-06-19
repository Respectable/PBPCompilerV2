package compiler;

import java.io.BufferedReader;
import java.util.ArrayList;

import nbaDownloader.NBADownloader;

import jsonObjects.*;

public class Compiler {

	private final static String currentSeason = "2012-13";
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ArrayList<PlayerJson> players;
		BufferedReader br = NBADownloader.downloadPlayerData(currentSeason);
		players = PlayerJson.getPlayers(br);
		
		ArrayList<TeamJson> teams;
		br = NBADownloader.downloadTeamData();
		teams = TeamJson.getTeams(br);
		
		for(String s : args)
		{
			try
			{
				ArrayList<PBPJson> pbp;
				br = NBADownloader.downloadPBP(s);
				pbp = PBPJson.getPBP(br);
				
				BoxJson boxScore;
				br = NBADownloader.downloadBoxScore(s);
				boxScore = BoxJson.getBoxScore(br);
				
				ArrayList<ShotJson> shots;
				br = NBADownloader.downloadShotData(boxScore.getGameSeason(), s);
				shots = ShotJson.getShots(br);
				
				
				
				String test = "finished";
				System.out.println(test);
				
				//parser p = new parser(new Yylex(new BufferedReader(new FileReader(s))));
				//Symbol parse_tree = p.parse();
				//game = (Game)parse_tree.value;
				//ShotCompiler shotCompiler = new ShotCompiler();
				//shotCompiler.main("/home/anthony/Desktop/Data/2010-2011/Shots");
				//CodeGenerator generator = new CodeGenerator("jdbc:mysql://localhost/nba", "root",
				//												"ao4132", game, s, shotCompiler);
				//generator.generateCode();
		        //game.CompileGame(s);
			}
			catch(Exception e)
			{
				System.out.println(e.getLocalizedMessage());
			}
			
		}
	}

}
