package dataService;

import relationalMapping.*;

public interface IReboundDataService 
{
	void writeRebound(ReboundRM rebound);
	void writeReboundFreeThrow(ReboundFreeThrowRM reboundFreeThrow);
	void writeReboundPlayer(ReboundPlayerRM reboundPlayer);
	void writeReboundShot(ReboundShotRM reboundShot);
}
