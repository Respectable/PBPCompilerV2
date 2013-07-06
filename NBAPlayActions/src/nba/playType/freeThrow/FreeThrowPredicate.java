package nba.playType.freeThrow;

public class FreeThrowPredicate 
{
	private int currentNumber, outOf;
	private boolean made;

	public FreeThrowPredicate(boolean made) 
	{
		this.currentNumber = 0;
		this.outOf = 0;
		this.made = made;
	}

	public FreeThrowPredicate(int currentNumber, int outOf, boolean made) 
	{
		this.currentNumber = currentNumber;
		this.outOf = outOf;
		this.made = made;
	}

	public int getCurrentNumber() { return currentNumber; }
	public int getOutOf() { return outOf; }
	public boolean madeFT() { return made; }
	
	public boolean lastFreeThrow()
	{
		return currentNumber == outOf;
	}
	
}
