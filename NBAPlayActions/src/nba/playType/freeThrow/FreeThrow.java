package nba.playType.freeThrow;

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
	
	
}
