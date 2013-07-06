package nba;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

import nba.play.Play;

public class Period implements Visitable
{
	private PeriodIdentification identification;
	private ArrayList<Play> plays;
	private ArrayList<Possession> possessions;
	
	public Period(PeriodIdentification identification, ArrayList<Play> plays) 
	{
		this.identification = identification;
		this.plays = plays;
		possessions = new ArrayList<Possession>();
	}

	public PeriodIdentification getIdentification() 
	{
		return identification;
	}

	public ArrayList<Play> getPlays() 
	{
		return plays;
	}
	
	public ArrayList<Possession> getPossessions()
	{
		return possessions;
	}
	
	public void addPossession(Possession possession)
	{
		possessions.add(possession);
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	public int getPeriod()
	{
		String periodNumber = identification.getPeriodNumber();
		String periodType = identification.getPeriodType();
		int currentPeriod = 0;
		
		switch(periodNumber)
		{
		case "1st": 
			currentPeriod = 1;
			break;
		case "2nd":
			currentPeriod = 2;
			break;
		case "3rd":
			currentPeriod = 3;
			break;
		case "4th":
			currentPeriod = 4;
			break;
		case "5th":
			currentPeriod = 5;
			break;
		case "6th":
			currentPeriod = 6;
			break;
		case "7th":
			currentPeriod = 7;
			break;
		}
		
		if (periodType.equals("Period"))
		{
			return currentPeriod;
		}
		else
		{
			return currentPeriod + 4;
		}
		
	}
	
	
	
	
}
