package nba.playType.freeThrow;

public class FreeThrowPredicate 
{
	private int currentNumber, outOf;

	public FreeThrowPredicate() 
	{
		this.currentNumber = 0;
		this.outOf = 0;
	}

	public FreeThrowPredicate(int currentNumber, int outOf) 
	{
		this.currentNumber = currentNumber;
		this.outOf = outOf;
	}

	public int getCurrentNumber() { return currentNumber; }
	public int getOutOf() { return outOf; }
	
}
