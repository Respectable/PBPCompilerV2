package dataService;

import relationalMapping.ViolationPlayerRM;
import relationalMapping.ViolationPossessionRM;
import relationalMapping.ViolationRM;

public interface IViolationDataService 
{
	void writeViolation(ViolationRM violation);
	void writeViolationPlayer(ViolationPlayerRM violationPlayer);
	void writeViolationPossession(ViolationPossessionRM violationPossession);
}
