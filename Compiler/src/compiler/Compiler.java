package compiler;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;

import nbaDownloader.NBADownloader;

import jsonObjects.PlayerJson;

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
		
		for(String s : args)
		{
			try
			{
				
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
