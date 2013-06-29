package compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import java_cup.runtime.Symbol;

import parser.parser;
import scanner.Yylex;

import nba.Game;
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
				Symbol parse_tree = p.debug_parse();
				Game game = (Game)parse_tree.value;
				
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
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
