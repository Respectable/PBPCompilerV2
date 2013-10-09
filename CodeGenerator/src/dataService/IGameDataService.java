package dataService;

import java.util.Collection;

import relationalMapping.*;

public interface IGameDataService 
{
	void writeGame(GameRM game);
	void writeGamePlayers(Collection<GamePlayerRM> players);
	void writeGameTeams(Collection<GameTeamRM> teams);
	void writeGameSeason(GameSeasonRM season);
	void writeGameOfficials(Collection<GameOfficialRM> officials);
	void writeGamePeriods(Collection<GamePeriodRM> periods);
}
