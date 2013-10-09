package dataService;

import java.sql.Connection;

public abstract class DataService 
{
	protected Connection conn;
	
	public DataService(Connection conn)
	{
		this.conn = conn;
	}
}
