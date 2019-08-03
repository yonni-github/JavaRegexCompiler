package project;

public class AssignmentStatement extends Statement{
	
	AssignmentStatement(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public boolean assignData(String varName, Object data)
	{
		checkVar(varName); //check if variable exists in method or class
		checkData(data); //check if data can be stored in var/data type check
		
		//then assign data to variable
		return false;
	}
	
	public Variable checkVar(String statement)
	{
		Variable v= new Variable(statement);
		return v; 
	}
	
	public boolean checkData(Object value)
	{
		return false;
	}
	

}
