package project;

public class Statement {
	private boolean hasSemicolon;

	Statement(String line)
	{
		if(line.charAt(line.length() - 1) == ';')
			hasSemicolon=true;
		else
			hasSemicolon=false;
	}
	public boolean getHasSemicolon() {
		return hasSemicolon;
	}
}
