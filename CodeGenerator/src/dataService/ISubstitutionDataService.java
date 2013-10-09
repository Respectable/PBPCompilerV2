package dataService;

import relationalMapping.*;

public interface ISubstitutionDataService 
{
	void writeSubstitution(SubstitutionRM substitution);
	void writeSubstititionPlayers(SubstitutionPlayersRM substitutionPlayers);
	void writeSubstitutionPossession(SubstitutionPossessionRM substitutionPossession);
}
