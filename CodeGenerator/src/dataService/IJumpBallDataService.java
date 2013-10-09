package dataService;

import relationalMapping.JumpBallPlayersRM;
import relationalMapping.JumpBallPossessionRM;
import relationalMapping.JumpBallRM;

public interface IJumpBallDataService 
{
	void writeJumpBall(JumpBallRM jumpBall);
	void writeJumpBallPlayers(JumpBallPlayersRM jumpBallPlayer);
	void writeJumpBallPossession(JumpBallPossessionRM jumpBallPossession);
}
