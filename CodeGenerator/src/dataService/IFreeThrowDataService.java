package dataService;

import relationalMapping.*;

public interface IFreeThrowDataService 
{
	void writeFreeThrow(FreeThrowRM freeThrow);
	void writeFreeThrowPlayer(FreeThrowPlayerRM freeThrowPlayer);
	void writeFreeThrowTechnical(FreeThrowTechnicalRM freeThrowTechnical);
	void writeFreeThrowFoul(FreeThrowFoulRM freeThrowFoul);
	void writeFreeThrowPossession(FreeThrowPossessionRM freeThrowPossession);
}
