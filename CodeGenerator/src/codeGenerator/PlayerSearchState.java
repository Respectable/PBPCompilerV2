package codeGenerator;

public enum PlayerSearchState 
{
	FOUND (0, ""),
	NOT_FOUND (-1, "#NOT_FOUND"),
	DUPLICATE_HOME (-2, "#DUPLICATE_HOME"),
	DUPLICATE_AWAY (-3, "#DUPLICATE_AWAY"),
	SINGLE_HOME_AWAY (-4, "#SINGLE_HOME_AWAY"),
	MULT_HOME_AWAY (-5, "#MULT_HOME_AWAY");
	
	private final int value;
	private final String text;
	
	PlayerSearchState(int value, String text)
	{
		this.value = value;
		this.text = text;
	}
	
	public int getValue() { return value; }
	public String getText() { return text; }
	
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
