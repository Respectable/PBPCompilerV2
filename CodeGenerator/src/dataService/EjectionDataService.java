package dataService;

import java.sql.Connection;

import relationalMapping.EjectionPlayerRM;
import relationalMapping.EjectionPossessionRM;
import relationalMapping.EjectionRM;

public class EjectionDataService extends DataService implements IEjectionDataService
{
	
	public EjectionDataService(Connection conn) 
	{
		super(conn);
	}

	@Override
	public void writeEjection(EjectionRM ejection) 
	{
		
	}

	@Override
	public void writeEjectionPlayer(EjectionPlayerRM ejectionPlayer) 
	{
		
	}

	@Override
	public void writeEjectionPossession(EjectionPossessionRM ejectionPossession) 
	{
		
	}
	
}
