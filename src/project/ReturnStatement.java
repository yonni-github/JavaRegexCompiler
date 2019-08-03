package project;

public class ReturnStatement extends Statement {
	
	private String returnType;
	private String returnExp;
	
	ReturnStatement(String line) {
		super(line);
		String[] temp=line.split(" ");
		returnExp= temp[1].substring(0, temp[1].length()-1);
		returnType=null;
	}

	public String getReturnExp() {
		return returnExp;
	}
	public String getReturnType()
	{
		return returnType;
	}
	
	public boolean isExpValid()
	{
		return true;
	}
	
}
