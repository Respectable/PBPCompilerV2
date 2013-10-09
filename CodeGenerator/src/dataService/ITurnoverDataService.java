package dataService;

import relationalMapping.TurnoverPlayerRM;
import relationalMapping.TurnoverPossessionRM;
import relationalMapping.TurnoverRM;

public interface ITurnoverDataService 
{
	void writeTurnover(TurnoverRM turnover);
	void writeTurnoverPlayer(TurnoverPlayerRM turnoverPlayer);
	void writeTurnoverPossession(TurnoverPossessionRM turnoverPossession);
}
