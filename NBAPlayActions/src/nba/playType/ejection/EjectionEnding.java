package nba.playType.ejection;

public class EjectionEnding {
	
	protected String EjectionType;
	
	public EjectionEnding()
	{
		EjectionType = "";
	}
	
	public EjectionEnding(String EjectionType)
	{
		this.EjectionType = EjectionType;
	}
	
	public String GetEjectionType() { return EjectionType; }

}
