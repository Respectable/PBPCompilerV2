package dataService;

import relationalMapping.ShotPlayerRM;
import relationalMapping.ShotPossessionRM;
import relationalMapping.ShotRM;

public interface IShotDataService 
{
	void writeShot(ShotRM shot);
	void writeShotPlayer(ShotPlayerRM shotPlayer);
	void writeShotPossession(ShotPossessionRM shotPossession);
	
}
