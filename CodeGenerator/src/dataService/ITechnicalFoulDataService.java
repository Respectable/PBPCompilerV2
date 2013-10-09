package dataService;

import relationalMapping.TechnicalFoulPlayerRM;
import relationalMapping.TechnicalFoulPossessionRM;
import relationalMapping.TechnicalFoulRM;

public interface ITechnicalFoulDataService 
{
	void writeTechnicalFoul(TechnicalFoulRM technicalFoul);
	void writeTechnicalFoulPlayer(TechnicalFoulPlayerRM technicalFoulPlayer);
	void writeTechnicalFoulPossession(TechnicalFoulPossessionRM technicalFoulPossession);
}
