package visitors;

import visitor.Visitor;
import nba.*;
import nba.play.*;
import nba.playType.*;
import nba.playType.block.*;
import nba.playType.ejection.*;
import nba.playType.foul.*;
import nba.playType.freeThrow.*;
import nba.playType.jumpBall.*;
import nba.playType.rebound.*;
import nba.playType.review.*;
import nba.playType.shot.*;
import nba.playType.steal.*;
import nba.playType.substitution.*;
import nba.playType.technical.*;
import nba.playType.timeout.*;
import nba.playType.turnover.*;
import nba.playType.violation.*;

public class PlayerPatchUpVisitor implements Visitor
{

	@Override
	public void visit(ContextInfo contextInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Game game) 
	{
		
		
		for (Period p : game.getPeriods())
		{
			for (Possession poss : p.getPossessions())
			{
				if(poss.getHomePlayers().size() < 5)
				{
					System.out.println("Home unit not filled");
					System.out.println("Possession------------------------------");
					System.out.println("Home Players:");
					for (Player player : poss.getHomePlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("Away Players:");
					for (Player player : poss.getAwayPlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("----------------------------------------");
				}
				if(poss.getAwayPlayers().size() < 5)
				{
					System.out.println("Away unit not filled");
					System.out.println("Possession------------------------------");
					System.out.println("Home Players:");
					for (Player player : poss.getHomePlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("Away Players:");
					for (Player player : poss.getAwayPlayers())
					{
						System.out.println(player.getPlayerName());
					}
					System.out.println("----------------------------------------");
				}
				
			}
		}
	}

	@Override
	public void visit(Period period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Play play) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlayerPlay play) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MissedPlay play) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlayType playType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Ejection ejection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Foul foul) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoublePersonalFoul foul) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FreeThrow freeThrow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpBall jumpBall) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Rebound rebound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Review review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Shot shot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Assist assist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Steal steal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Substitution sub) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Technical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleTechnical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TauntingTechnical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Timeout timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Turnover turnover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Violation violation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Possession possession) {
		// TODO Auto-generated method stub
		
	}
	
}