package project;

import java.util.LinkedList;

public class Class {
	private String visibility;
	private String className;
	private boolean isAbstract;
	private boolean isFinal;
	
	LinkedList<Variable> holdFields;
	LinkedList<Constructor> holdConstructors;
	LinkedList<Method> holdMethods;
	
	Class(String str)
	{
		
		String[] splited=str.split("(\\s+)|(\\{)");
		className=splited[splited.length-1];
		visibility="";
		isFinal=false;
		isAbstract=false;
		for(String s: splited){
			if (s.equals("public")||s.equals("protected")||s.equals("private"))
			{
				visibility=s;
			}
			else if (s.equals("final"))
			{
				isFinal=true;
			}
			else if(s.equals("abstract"))
			{
				isAbstract=true;
			}
		}
	}
	
	
	public String getClassName() {
		return className;
	}
	
	
}
