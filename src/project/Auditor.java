package project;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.LinkedList;
//import java.util.Stack;

public class Auditor implements projectInterface{
	
	String[] dataType = { "int", "float", "boolean", "char", "String", "double"};
	String[] accessType= {"public", "private", "protected"};
	String[] keywords={"abstract","continue","for","new","switch","assert","default",
			"goto",	"package",	"synchronized","boolean","do","if","private","this",
			"break","double","implements","protected","throw","byte","else","import",
			"public","throws","case",	"enum",	"instanceof","return","transient",
			"catch","extends",	"int",	"short","try","char",	"final","interface",
			"static","void","class","finally","long",	"strictfp",	"volatile",
			"const","float","native","super","while"};
	LinkedList<ImportStatement> holdImports=new LinkedList<ImportStatement>();
	//LinkedList<Class> holdClasses= new LinkedList<>();//within the same java file(not in use momentarily)
	static LinkedList<Class> validClasses= new LinkedList<>();//all classes that passed compilation in package
	static LinkedList<Class> failedClasses= new LinkedList<>();//all classes that failed compilation in package
	LinkedList<Method> holdMethods=new LinkedList<>();
	LinkedList<Constructor> holdConstructors=new LinkedList<>();
	LinkedList<Variable> holdVariables=new LinkedList<>();
	PackageStatement ps;
	Class c;
	File currentFile;

	
	public Auditor(File javaFile)//when checking whole java file
	{
		ps=null;
		c=null;
		currentFile=javaFile;
	}
	
	public Auditor() //when using a specific method only(methods can be used individually)
	{
		ps=null;
		c=null;
	}

	public boolean packageCheck(String statement)
	{

		if (ps==null && holdImports.size()==0 && c==null) //package name should be first and only
		{
			ps= new PackageStatement(statement);
			return true;
		}	
		
		return false;
	}
	
	public boolean importCheck(String statement) 
	{
		ImportStatement is = new ImportStatement(statement);
		
		boolean duplicateImport = false;
		for (int a = 0; a < holdImports.size(); a++) 
		{
			if (is.equals(holdImports.get(a))) 
			{
				duplicateImport = true;
			}
		}
		if (!duplicateImport)
		{
			String new1 = is.getImportName();
			if(!new1.matches("\\s*java\\..+"))
			{
				String[] splitImport=new1.split("\\.");
				String new2 = new1.replace('.', '\\');
				String projectDir="";
				String temp[]=Compiler3.path.split(("(?<=\\\\)"));
				for(int a=0; a<temp.length-1;a++)
				{
					projectDir=projectDir+temp[a];//get project directory
				}
				
				if(splitImport[splitImport.length-1].equals("*"))//for implicit import
				{
					String newPackageDir=projectDir;
					for(int a=0; a<splitImport.length-1;a++)
					{
						newPackageDir=newPackageDir+splitImport[a];//get new packed path within project
					}
					try{
						 File dir = new File(newPackageDir);
						 File[] files = dir.listFiles(new FilenameFilter() {
				             
					            @Override
					            public boolean accept(File dir, String name) {
					                if(name.toLowerCase().endsWith(".java")){
					                    return true;
					                } else {
					                    return false;
					                }
					            }
					        });
						 for(File f:files){
							 if(!currentFile.getPath().equals(f.getPath()))
					        	Compiler3.compileUnit(f);					        	
					        }
						 
					}catch(Exception e){
						return false;
					}
					holdImports.add(is);
					return true;
				}
				else//for explicit import
				{
					try{
						File text = new File(projectDir + new2 + ".java");//imported Class must be in the project
						if(Compiler3.compileUnit(text))
						{
							holdImports.add(is);
							return true;
						}
					}catch(FileNotFoundException e){
						return false;
					}
				}
			}
			else //assuming any regular java library import is valid
			{
				holdImports.add(is);
				return true;
			}
			
		}					 
		return false;
	}
	
	public boolean classCheck(String line) {
		
		Pattern pattern = Pattern.compile("((?<!\\S)|\\})(public\\s+|private\\s+|protected\\s+)?"
						+ "(abstract\\s+|final\\s+)?class\\s+\\w+\\s*\\{");
	    Matcher matcher = pattern.matcher(line);
		
	    if(matcher.matches())
	    {
	    	 c= new Class(line);
	    	 if(!keywordCheck(c.getClassName()))
	    		return true;
	    }
		return false;
	}
	
	
	public boolean returnCheck(String line) {
		ReturnStatement rs=new ReturnStatement(line);
		if(rs.isExpValid())
			//if(rs.getReturnType().equals(holdMethods.get(holdMethods.size()-1).getReturnType()))
				return true;
		
		return false;
	}

	@Override
	public boolean variableCheck(String statement)  {
		
		Variable v=new Variable(statement);
		boolean flag = false;
		if(!v.isValid()||v.getDataType()==null||keywordCheck(v.getVariableName()))
			return flag;
		for (String s : dataType) { //check if datatype is a primitive data type
			if (v.getDataType().equals(s)) {
				flag = true;
				holdVariables.add(v);
			}
		}
		for (int i=0; i<validClasses.size();i++) //check if dataType is one of the previously compiled/imported classes
		{
			if (v.getDataType().equals(validClasses.get(i).getClassName())) {
				flag = true;
				holdVariables.add(v);
			}
		}
		
		if(!flag){
			
			try{
				File text = new File(Compiler3.path + v.getDataType() + ".java");// Class must be in the same package
				if( text!=null && Compiler3.compileUnit(text))//check if class is in the same package and compiles successfully
				{
					holdVariables.add(v);
					flag= true;
				}
			}catch(FileNotFoundException e){
				
			}
			
		}
		return flag;
	}
	
	public boolean varAssignCheck(String statement) {
		boolean flag = false;
		String[] temp=statement.split("=");
		for (int i=0; i<holdVariables.size();i++) 
		{
			if (temp[0].trim().equals(holdVariables.get(i).getVariableName())) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public boolean accessTypeCheck(String word) {
		
		boolean flag = false;
		for (String s : accessType) {
			if (word.equals(s)) {
				flag = true;
			}
		}
		
		return flag;
	}
public boolean keywordCheck(String word) {
		
		boolean flag = false;
		for (String s : keywords) {
			if (word.equals(s)) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	@Override
	public boolean statementCheck(String statement) {
		Statement s=new Statement(statement);
		return s.getHasSemicolon();
	}

	@Override
	public boolean methodCheck(String line) {
		
		boolean duplicateMethod=false;
	    
		Method m=new Method(line);
		boolean flag = false;
		if(m.isValid()){
			for (String s : dataType) {
				if (m.getReturnType()!=null &&(m.getReturnType().equals(s)||m.getReturnType().equals("void"))) {
					flag = true;
					break;
				}
			}
		}
		if(flag && !keywordCheck(m.getMethodName()))
		{
			for (int a = 0; a < holdMethods.size(); a++) {
				if (m.equals(holdMethods.get(a))) 
				{
					duplicateMethod = true;
				}
			}
			if(!duplicateMethod)
			{
				holdMethods.add(m);
				return true;
			}
		}
		return false;
	}
	
	@Override
    public boolean constructorCheck(String line) {
		
		boolean duplicateConstructor=false;
	    Constructor con=new Constructor(line);	
	    
		if(c!=null && con.isValid()&&!keywordCheck(con.getConstructorName())&&con.getConstructorName().equals(c.getClassName()))
		{
			for (int a = 0; a < holdConstructors.size(); a++) {
				if (con.equals(holdConstructors.get(a))) 
				{
					duplicateConstructor = true;
				}
			}
			if(!duplicateConstructor)
			{
				holdConstructors.add(con);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean loopCheck(String statement) {//loops and if's
		
		Conditional loop= new Conditional(statement);
		
		return loop!=null;
	}
	
}
