package dataService;

import java.sql.Connection;

import relationalMapping.FoulPlayerRM;
import relationalMapping.FoulPossessionRM;
import relationalMapping.FoulRM;

public class FoulDataService extends DataService implements IFoulDataService
{

	public FoulDataService(Connection conn) 
	{
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeFoul(FoulRM foul) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFoulPlayer(FoulPlayerRM foulPlayer) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFoulPossession(FoulPossessionRM foulPossession) 
	{
		// TODO Auto-generated method stub
		
	}

}
