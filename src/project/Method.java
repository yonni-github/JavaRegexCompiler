package project;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method {
	private String methodName;
	private String visibility;
	private boolean isStatic;
	private String returnType;
	private Signature signature;
	private boolean isValid;
	
	
	LinkedList<Variable> parameterList;
	LinkedList<Variable> holdLocalVariables;
	LinkedList<ReturnStatement> holdReturns;
	
	LinkedList<Conditional> holdConditionals;
	LinkedList<MethodCall> holdMethodCalls;
	
	public Method(String str) {
		
		Pattern vis=Pattern.compile("((?<!\\S)|\\})public\\s+|private\\s+|protected\\s+");
		Pattern sta=Pattern.compile("((?<!\\S)|\\})static\\s+");
		Pattern ret=Pattern.compile("((?<!\\S)|\\})\\w+\\s+\\w+\\s*\\(");
		Pattern name=Pattern.compile("\\w+\\s*\\(");
		Pattern par=Pattern.compile("\\(.*\\)");
	    Matcher matchVis = vis.matcher(str);
	    Matcher matchPar = par.matcher(str);
	    Matcher matchSta = sta.matcher(str);
	    Matcher matchRet = ret.matcher(str);
	    Matcher matchName = name.matcher(str);
	   // signature=str.substring(0, str.length()-1).trim();
		visibility="";
		isStatic=false;
		
		int countChanges=0;
		    if (matchVis.find())
			{
				visibility=matchVis.group().trim();
				countChanges++;
			}
			if (matchSta.find())
			{
				isStatic=true;
				countChanges++;
			}
		    if(matchRet.find())
			{
		    	String[] temp=matchRet.group().split("\\s+");
				returnType=temp[0];
				countChanges++;
			}
		    if(matchName.find())
		    {
		    	String temp=matchName.group();
		    	methodName=temp.substring(0, temp.length()-1).trim();
		    	countChanges++;
		    }
		    if (matchPar.find())
		    {
				String parameters=matchPar.group();
				setSignature(parameters.substring(1, parameters.length()-1));
			}
		    
		    validate(str, countChanges);
		    
	}
	
	private void validate(String s, int changes)
	{
		String[] temp=s.split("\\(");
		String[] temp2=temp[0].split("\\s+");
		int expectedChanges=temp2.length;
		
		this.isValid=(changes==expectedChanges)&& signature.isValid();
	}
	
	public boolean equals(Method obj)
	{
		return this.getSignature().equals(obj.getSignature());
	}
	public Signature getSignature() {
		return signature;
	}

	public void setSignature(String pars) {
		if(pars!=null)
			signature = new Signature(methodName, pars);
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
}
