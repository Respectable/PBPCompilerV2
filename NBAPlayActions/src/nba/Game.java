package nba;

import java.util.ArrayList;

import visitor.Visitable;
import visitor.Visitor;

public class Game implements Visitable
{
	private ArrayList<Period> periods;

	public Game(ArrayList<Period> periods) {
		this.periods = periods;
	}

	public ArrayList<Period> getPeriods() { return periods; }

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
