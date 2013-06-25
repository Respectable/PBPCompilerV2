package nba;

public class PeriodIdentification 
{
	private String periodNumber;
	private String periodType;
	
	public PeriodIdentification(String periodNumber, String periodType) 
	{
		this.periodNumber = periodNumber;
		this.periodType = periodType;
	}

	public String getPeriodNumber() 
	{
		return periodNumber;
	}

	public String getPeriodType() 
	{
		return periodType;
	}
	
	
	
	
}
