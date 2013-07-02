package nba;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

import nba.play.Play;

public class Period implements Visitable
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

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
