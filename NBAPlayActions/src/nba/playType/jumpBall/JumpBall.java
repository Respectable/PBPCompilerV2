package nba.playType.jumpBall;

import nba.Player;

public class JumpBall 
{
	private Player player1, player2;
	private JumpBallEnding ending;
	
	public JumpBall(Player player1, Player player2, JumpBallEnding ending) 
	{
		this.player1 = player1;
		this.player2 = player2;
		this.ending = ending;
	}

	public Player getPlayer1() { return player1; }
	public Player getPlayer2() { return player2; }
	public JumpBallEnding getEnding() { return ending; }
	
	
}
