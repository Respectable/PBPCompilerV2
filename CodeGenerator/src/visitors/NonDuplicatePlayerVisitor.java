package visitors;

import java.util.ArrayList;

import nba.Player;
import codeGenerator.RosterSQLGenerator;

public class NonDuplicatePlayerVisitor extends PlayerVisitor 
{

	public NonDuplicatePlayerVisitor(RosterSQLGenerator rosters) 
	{
		super(rosters);
	}

	@Override
	protected void setPlayer(Player player, ArrayList<Player> possiblePlayers) 
	{
		ArrayList<Player> matchingPlayers;
		
		matchingPlayers = RosterSQLGenerator.getMatchingPlayers(possiblePlayers, player);
		
		if (matchingPlayers.size() < 1)
		{
			System.out.println("Could not find player: " + player.getPlayerName());
			player.setPlayerID(-1);
		}
		else if (matchingPlayers.size() == 1)
		{
			player.setPlayerID(matchingPlayers.get(0).getPlayerID());
			player.setPlayerName(matchingPlayers.get(0).getPlayerName());
		}
	}

}
