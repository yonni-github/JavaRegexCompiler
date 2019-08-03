package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constructor {
	private String constructorName;
	private String visibility;
	private Signature signature;
	private boolean isValid;
	
	public Constructor(String str) {
		Pattern vis=Pattern.compile("public\\s+");
		Pattern name=Pattern.compile("\\w+\\s*\\(");
		Pattern par=Pattern.compile("\\(.*\\)");
	    Matcher matchVis = vis.matcher(str);
	    Matcher matchName = name.matcher(str);
	    Matcher matchPar = par.matcher(str);
	    
		this.visibility="";
		int countChanges=0;
		if (matchVis.find())
		{
			visibility=matchVis.group().trim();
			countChanges++;
		}
			
		if(matchName.find())
		{
			String temp=matchName.group();
		    constructorName=temp.substring(0, temp.length()-1).trim();
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
		
		isValid=(changes==expectedChanges)&& signature.isValid();
	}
	public boolean equals(Constructor obj)
	{
		return this.getSignature().equals(obj.getSignature());
	}
	public Signature getSignature() {
		return signature;
	}

	public void setSignature(String pars) {
		if(pars!=null)
			signature = new Signature(constructorName, pars);
	}

	public String getConstructorName() {
		return constructorName;
	}

	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
}
