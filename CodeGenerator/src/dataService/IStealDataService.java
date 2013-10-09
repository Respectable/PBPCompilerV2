package dataService;

import relationalMapping.StealPlayerRM;
import relationalMapping.StealPossessionRM;
import relationalMapping.StealRM;
import relationalMapping.StealTurnoverRM;

public interface IStealDataService 
{
	void writeSteal(StealRM steal);
	void writeStealPlayer(StealPlayerRM stealPlayer);
	void writeStealTurnover(StealTurnoverRM stealTurnover);
	void writeStealPossession(StealPossessionRM stealPossession);
}
