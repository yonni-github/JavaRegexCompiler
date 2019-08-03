package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conditional {
	
	private String conditionalName;
	private String expression;
	
	public Conditional(String line) {
		
		Pattern name1=Pattern.compile("\\w+\\s*\\(");
		Pattern name2=Pattern.compile("else\\s+if");
	    Matcher matchName1 = name1.matcher(line);
	    Matcher matchName2 = name2.matcher(line);
	  
		expression="";
		if(matchName1.find())
		{
			String temp=matchName1.group();
		    conditionalName=temp.substring(0, temp.length()-1).trim();
		}
		if(matchName2.find())
		{
			String temp=matchName2.group();
		    conditionalName=temp;
		}
	}

	public boolean isExpValid()
	{
		return false;
	}

	public String getConditionalName() {
		return conditionalName;
	}

	public void setConditionalName(String conditionalName) {
		this.conditionalName = conditionalName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
