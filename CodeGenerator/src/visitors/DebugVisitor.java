package visitors;

import nba.*;
import nba.play.*;
import nba.playType.PlayType;
import nba.playType.block.Block;
import nba.playType.ejection.Ejection;
import nba.playType.foul.*;
import nba.playType.freeThrow.FreeThrow;
import nba.playType.jumpBall.JumpBall;
import nba.playType.rebound.Rebound;
import nba.playType.review.Review;
import nba.playType.shot.*;
import nba.playType.steal.Steal;
import nba.playType.substitution.Substitution;
import nba.playType.technical.*;
import nba.playType.timeout.Timeout;
import nba.playType.turnover.Turnover;
import nba.playType.violation.Violation;
import visitor.Visitor;

public class DebugVisitor implements Visitor
{

	private int possessionCounter;
	
	public DebugVisitor()
	{
		this.possessionCounter = 0;
	}
	
	@Override
	public void visit(ContextInfo contextInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Game game) 
	{
		this.possessionCounter = 0;
		
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(Period period) 
	{
		System.out.println();
		System.out.println("+++++++++++++++++++++++");
		System.out.println("     Period " + period.getPeriod());
		System.out.println("+++++++++++++++++++++++");
		System.out.println();
		for (Possession p : period.getPossessions())
		{
			p.accept(this);
		}
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
	public void visit(Possession possession) 
	{
		this.possessionCounter++;
		
		System.out.println("Possession " + possessionCounter + ":------------------------------------------");
		System.out.println();
		System.out.println("Plays:");
		
	}

}
