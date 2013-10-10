package dataService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DataService 
{
	protected Connection conn;
	protected PreparedStatement stmt;
	protected ResultSet rs;
	
	public DataService(Connection conn)
	{
		this.conn = conn;
	}
}
