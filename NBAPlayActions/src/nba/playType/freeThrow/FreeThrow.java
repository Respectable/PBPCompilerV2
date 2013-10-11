package nba.playType.freeThrow;

import visitor.Visitor;
import nba.playType.PlayType;

public class FreeThrow extends PlayType
{
	private String freeThrowType;
	private FreeThrowPredicate predicate;
	
	public FreeThrow(String freeThrowType, FreeThrowPredicate predicate) {
		this.freeThrowType = freeThrowType;
		this.predicate = predicate;
	}

	public String getFreeThrowType() { return freeThrowType; }
	public FreeThrowPredicate getPredicate() { return predicate; }

	@Override
	public void accept(Visitor visitor) throws Exception 
	{
		visitor.visit(this);
	}
	
	public boolean TerminatingFreeThrowType()
	{
		return !this.freeThrowType.equals("Technical") && 
				!this.freeThrowType.equals("Clear Path");
	}
	
	@Override
	public boolean terminatesPossession()
	{
		return predicate.lastFreeThrow() && 
				this.TerminatingFreeThrowType() &&
				this.madeFT();
	}

	@Override
	public boolean identifiesOffense() 
	{
		return this.freeThrowType.equals("") || 
				this.freeThrowType.equals("Clear Path");
	}
	
	public boolean lastFreeThrow()
	{
		return predicate.lastFreeThrow();
	}
	
	public boolean madeFT() { return predicate.madeFT(); }
	public int currentFTNumber() { return predicate.getCurrentNumber(); }
	public int outOfFTNumber() { return predicate.getOutOf(); }
	
	
	
}
