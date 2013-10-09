package dataService;

import relationalMapping.*;

public interface IEjectionDataService 
{
	void writeEjection(EjectionRM ejection);
	void writeEjectionPlayer(EjectionPlayerRM ejectionPlayer);
	void writeEjectionPossession(EjectionPossessionRM ejectionPossession);
}
