package dataService;

import relationalMapping.*;

public interface ITimeoutDataService 
{
	void writeTimeout(TimeoutRM timeout);
	void writeTimeoutPossession(TimeoutPossessionRM timeoutPossession);
	void writeTimeoutTeam(TimeoutTeamRM timeoutTeam);
}
