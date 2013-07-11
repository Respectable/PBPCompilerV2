package codeGenerator;

public enum PlayerSearchState 
{
	FOUND (0),
	NOT_FOUND (-1),
	DUPLICATE_HOME (-2),
	DUPLICATE_AWAY (-3),
	SINGLE_HOME_AWAY (-4),
	MULT_HOME_AWAY (-5);
	
	private final int value;
	
	PlayerSearchState(int value)
	{
		this.value = value;
	}
	
	public int getValue() { return value; }
	
	public PlayerSearchState getState(int value)
	{
		switch (value)
		{
		case -1:
			return NOT_FOUND;
		case -2:
			return DUPLICATE_HOME;
		case -3:
			return DUPLICATE_AWAY;
		case -4:
			return SINGLE_HOME_AWAY;
		case -5:
			return MULT_HOME_AWAY;
		default:
			return FOUND;
		}
	}
}
