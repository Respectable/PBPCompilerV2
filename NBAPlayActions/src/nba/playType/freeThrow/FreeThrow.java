package nba.playType.freeThrow;

public class FreeThrow 
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
