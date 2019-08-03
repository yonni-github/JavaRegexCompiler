package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportStatement extends Statement{
	
	private String importName;
	
	ImportStatement(String line) {
		super(line);
		Pattern name=Pattern.compile("(?<=\\s+).+\\s*;");
	    Matcher matchName = name.matcher(line);
		
		//String[] temp=line.split("\\s+");
	    //importName= temp[1].substring(0, temp[1].length()-1);
	    if(matchName.find())
	    {
	    	importName= matchName.group().substring(0, matchName.group().length()-1).trim();
	    }
		
	    
	}
	public boolean equals(ImportStatement obj)
	{
		return this.getImportName().equals(obj.getImportName());
	}
	public String getImportName() {
		return importName;
	}
}
