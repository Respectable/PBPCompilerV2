package dataService;

import relationalMapping.*;

public interface IFoulDataService 
{
	void writeFoul(FoulRM foul);
	void writeFoulPlayer(FoulPlayerRM foulPlayer);
	void writeFoulPossession(FoulPossessionRM foulPossession);
}
