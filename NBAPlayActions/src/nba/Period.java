package nba;

import java.util.ArrayList;

import nba.play.Play;

public class Period 
{
	private PeriodIdentification identification;
	private ArrayList<Play> plays;
	
	public Period(PeriodIdentification identification, ArrayList<Play> plays) 
	{
		this.identification = identification;
		this.plays = plays;
	}

	public PeriodIdentification getIdentification() 
	{
		return identification;
	}

	public ArrayList<Play> getPlays() 
	{
		return plays;
	}
	
	
	
	
}
