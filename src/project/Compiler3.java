package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler3 {
	static CompilerWindow cw;
	static String path="C:\\Users\\Yonni\\Desktop\\javaTestFiles\\";//package directory
	public static void main(String args[]) {
		//Scanner input=new Scanner(System.in);
		boolean invalidPath=true;
		
	    cw= new CompilerWindow("COMPILER");
		cw.setVisible(true);
		//only used with CompilerWindow
		while(cw.isEnabled()){
			while(!cw.isDone){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
			while(invalidPath){
				//System.out.println("Enter package directory eg.[C:\\Users\\me\\project\\javaTestFiles\\] ");
					//path= input.nextLine();
				try{
					 File dir = new File(path);
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
				        	if(compileUnit(f))
				        	{
				        		System.out.println();
				        		System.out.println(f);
				        		System.out.println("Compilation Successful!");
				        		cw.textArea.append("\n");
				        		cw.textArea.append(f.toString());
				        		cw.textArea.append("\nCompilation Successful!");
				        	}
				        }
					 invalidPath=false;
				}catch(Exception e){
					System.out.println("Invalid Package Directory!");
					cw.textArea.append("\nInvalid Package Directory!");
				}
			}
			//input.close();
			System.out.println("Done!");
			cw.textArea.append("\nDone!");
			cw.isDone=false;
			invalidPath=true;//only needed when using CompilerWindow
		}
			
	}
	
	public static boolean compileUnit(File javaFile) throws FileNotFoundException 
	{		
		for (int i=0; i<Auditor.failedClasses.size();i++) //check if class is one of the previously failed classes(e.g when imported by other class)
		{
			if (javaFile.getPath().toString().toLowerCase().equals((path+Auditor.failedClasses.get(i).getClassName()+".java").toLowerCase())) {
				return false;
			}
		}
		
		LinkedList<Error> errorList= new LinkedList<Error>();
		Scanner sc = new Scanner(javaFile);
		
		sc.useDelimiter("(?<=})");//read until } 
		Auditor p=new Auditor(javaFile);
		String className="";
		int lineNumber = 1;
		
		while (sc.hasNextLine()) {
			String str = sc.next();//read until a closing curly bracket			
			str.trim();
			int forFlag=0;//used if a for loop is coming
			String forTemp="";
			String[] strSplited= str.split("(?<=\\{)|(?<=;)|}");
			
			for(String line: strSplited)
			{
				//get class name if available
				if(p.c!=null){
					className=p.c.getClassName();
				}
				
				if(line.matches("\\s*for\\s*\\(.*;")||forFlag>0)//re-construct for loop
				{
					if(line.matches("\\s*for\\s*\\(.*;"))
						forFlag=3;
					forTemp=forTemp + line;
					forFlag--;
					if(forFlag!=0)
						continue;
					else
						line=forTemp;
				}
				for(int a=0;a<line.length();a++)//keeps track of line numbers
				{
					if(line.charAt(a)=='\n')
						lineNumber++;
				}
				//check if a segment looks like a method
				Pattern methodPat =  Pattern.compile("\\s*(public\\s+|private\\s+|protected\\s+)?(static\\s+|abstract\\s+|final\\s+)?"
						+ "(int\\s+|char\\s+|float\\s+|String\\s+|double\\s+|boolean\\s+|void\\s+)\\w+\\s*\\(.*\\)\\s*\\{");
				//check if a segment looks like a constructor
				Pattern constPat =  Pattern.compile("\\s*(public\\s+)?"+ className +"\\s*\\(.*\\)\\s*\\{");
				//check if segment is a loop/if declaration
				Pattern loopPat =  Pattern.compile("\\s*(for\\s*|while\\s*|if\\s*|else\\s+if\\s*)\\(.+\\)\\s*\\{?");
				//check if segment is a for loop declaration
				//Pattern forPat =  Pattern.compile("\\s*(for\\s*){1}\\(.*[^;].*[^;].*\\)\\s*\\{?");
				//check if segment is a variable declaration
				Pattern varDecPat =  Pattern.compile("\\s*(public\\s+|private\\s+|protected\\s+)?(static\\s+final\\s+|static\\s+|final\\s+)?"
						+ "\\w+\\s+\\w+\\s*(=\\s*.+\\s*)?;");
				//check if segment is an assignment statement
				Pattern varAssignPat =  Pattern.compile("\\s*\\w+\\s*=\\s*.+\\s*;");
			  line=line.trim();
			  Matcher matchMethod =  methodPat.matcher(line);
			  Matcher matchConst =  constPat.matcher(line);
			  Matcher matchLoop =  loopPat.matcher(line);
			  Matcher matchVarDec =  varDecPat.matcher(line);
			  Matcher matchVarAssign =  varAssignPat.matcher(line);
			  
			  if(line.length()>0 && line.charAt(0)!='/'&& line.charAt(0)!='*'&& line.charAt(0)!='}')//skip empty lines and comments
			  {
			      String[] temp = line.split("\\s+");
				   if(p.statementCheck(line))//(1)check statements
				    {
				       if(temp[0].trim().equals("package"))//check if statement is valid package statement
					    {
							if(!p.packageCheck(line))
							{
								Error anError= new Error(lineNumber, "Package Error");
								errorList.add(anError);
							}
					    }
						else if(temp[0].trim().equals("import"))//check if statement is valid import statement
						{
							if(!p.importCheck(line))
							{
								Error anError= new Error(lineNumber, "Import Error");
								errorList.add(anError);
							}
						}
						else if(temp[0].trim().equals("return"))//check if statement is valid return statement
						{
							if(!p.returnCheck(line))
							{
								Error anError= new Error(lineNumber, "Return Error");
								errorList.add(anError);
							}
						}
						else if(matchVarDec.matches()) 
						{
							if(!p.variableCheck(line))//check if statement is valid declaration/assignment statement
							{
								Error anError= new Error(lineNumber, "Variable Declaration Error");
								errorList.add(anError);
							}
						}
						else if(matchVarAssign.matches()) 
						{
							if(!p.varAssignCheck(line))//check if statement is valid assignment statement
							{
								Error anError= new Error(lineNumber, "Invalid Assignment!");
								errorList.add(anError);
							}
						}
						else//if statement not recognized above
						{
							Error anError= new Error(lineNumber, "Invalid Statement!");
							errorList.add(anError);
						}
				    }
				   //if not a statement
			 
					else if(line.contains("class"))
					{
						if(!p.classCheck(line))
						  {
							Error anError= new Error(lineNumber, "Class decleration Error");
							errorList.add(anError);
						  }
					}
					else if(matchMethod.find())//(2)check methods
					  {
						if(!p.methodCheck(line))
						  {
							Error anError= new Error(lineNumber, "Method Error");
							errorList.add(anError);
						  }
					  }
					else if(matchConst.find())//(2)check constructors
					  {
						if(!p.constructorCheck(line))
						  {
							Error anError= new Error(lineNumber, "Constructor Error");
							errorList.add(anError);
						  }
					  }	
					else if(matchLoop.find())//(2)check conditionals
					  {
						if(!p.loopCheck(line))
						  {
							Error anError= new Error(lineNumber, "loop/if Error");
							errorList.add(anError);
						  }
					  }
					else//if segment not recognized above
					{
						char ch= '\u2620';
						Error anError= new Error(lineNumber, ch+"! yo, U tripping or What?!! Error.");
						errorList.add(anError);
					}
			  	}
			}
			 
		}
		
		if(errorList.size()>0 || p.c==null)
		{
			System.out.println("\nError: "+ javaFile.toString());
			cw.textArea.append("\nError: "+ javaFile.toString());
			if(p.c==null)
			{
				  System.out.println("No class definition found!");
				  cw.textArea.append("\nNo class definition found!");
			}	
			for(Error e: errorList)
			{
				System.out.println("Line " + e.getLineNumber() +": "+ e.getMessage());
				cw.textArea.append("\nLine " + e.getLineNumber() +": "+ e.getMessage());
			}
			
			if(p.c!=null)
				Auditor.failedClasses.add(p.c);	
		}
		else 
		{
			Auditor.validClasses.add(p.c);
			sc.close();
			return true;
		}
		sc.close();
		return false;
	}
	
}