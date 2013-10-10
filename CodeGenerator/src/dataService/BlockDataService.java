package dataService;

import java.sql.Connection;

import relationalMapping.BlockPlayerRM;
import relationalMapping.BlockRM;
import relationalMapping.BlockShotRM;

public class BlockDataService extends DataService implements IBlockDataService
{

	public BlockDataService(Connection conn) 
	{
		super(conn);
	}

	@Override
	public void writeBlock(BlockRM block) 
	{
		
	}

	@Override
	public void writeBlockPlayer(BlockPlayerRM blockPlayer) 
	{
		
	}

	@Override
	public void writeBlockShot(BlockShotRM blockShot) 
	{
		
	}

}
