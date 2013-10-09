package dataService;

import relationalMapping.BlockPlayerRM;
import relationalMapping.BlockRM;
import relationalMapping.BlockShotRM;

public interface IBlockDataService 
{
	void writeBlock(BlockRM block);
	void writeBlockPlayer(BlockPlayerRM blockPlayer);
	void writeBlockShot(BlockShotRM blockShot);
}
