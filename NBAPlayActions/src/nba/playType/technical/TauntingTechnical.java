package nba.playType.technical;

import visitor.Visitor;

public class TauntingTechnical extends Technical
{
	public TauntingTechnical()
	{
		super();
	}
	
	@Override
	public void accept(Visitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public String technicalType() { return "Taunting"; }
}
