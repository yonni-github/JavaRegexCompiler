package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {
	private String variableName;
	private String visibility;
	private String dataType;
	private boolean isStatic;
	private boolean isFinal;
	private boolean isValid;
	private String data;
	
	public Variable(String str) {
		str=str.trim();
		Pattern vis=Pattern.compile("((?<!\\S)|\\{|\\})(public\\s+|private\\s+|protected\\s+)");
		Pattern sta=Pattern.compile("((?<!\\S)|\\{|\\})static\\s+");
		Pattern fin=Pattern.compile("((?<!\\S)|\\{|\\})final\\s+");
		Pattern type=Pattern.compile("\\w+\\s+\\w+\\s*((=\\s*.+\\s*;)|;)");
		Pattern name=Pattern.compile("\\w+\\s*((=\\s*.+\\s*;)|;)");
	    Matcher matchVis = vis.matcher(str);
	    Matcher matchSta = sta.matcher(str);
	    Matcher matchFin = fin.matcher(str);
	    Matcher matchType = type.matcher(str);
	    Matcher matchName = name.matcher(str);
	    
		visibility="";
		isStatic=false;
		isFinal=false;
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
			if (matchFin.find())
			{
				isFinal=true;
				countChanges++;
			}
		    if(matchType.find())
			{
		    	String[] temp=matchType.group().split("\\s+");
				dataType=temp[0];
				countChanges++;
			}
		    if(matchName.find())
		    {
		    	String temp=matchName.group();
		    	String[] temp2=temp.split("(=)|(;)");
		    	variableName=temp2[0].trim();
		    	countChanges++;
		    }
		    validate(str, countChanges);
	}
	private void validate(String s, int changes)
	{
		String[] temp=s.split("(=)|(;)");
		String[] temp2=temp[0].split("\\s+");
		int expectedChanges=temp2.length;
		
		isValid=(changes==expectedChanges);
	}
	public boolean isValid() {
		return isValid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String fieldName) {
		this.variableName = fieldName;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
}
