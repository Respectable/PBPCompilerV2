package dataService;

import java.util.Collection;

import relationalMapping.PeriodPossessionRM;
import relationalMapping.PeriodRM;

public interface IPeriodDataService 
{
	void writePeriod(PeriodRM period);
	void writePeriodPossessions(Collection<PeriodPossessionRM> possessions);
}
