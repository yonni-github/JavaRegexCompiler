package project;

import java.util.LinkedList;

public class Signature {
	private String methodName;
	private LinkedList<String> parameterTypes;
	private boolean isValid;
	
	Signature(String n, String par)
	{
		methodName=n;
		isValid=true;
		parameterTypes = creatParTypeList(par);
	}
	public boolean isValid() {
		return isValid;
	}
	public boolean equals(Signature obj) {
		boolean flag=false;
		if(this.methodName.equals(obj.methodName))
		{
			
			if(this.parameterTypes.size()==obj.parameterTypes.size())
			{
				flag = true;
				for(int a=0; a< this.parameterTypes.size(); a++)
				{
					if(this.parameterTypes.get(a).equals(obj.parameterTypes.get(a))==false)
					{
						flag = false;
						break;
					}
				}
				
			}
			
		}
		return flag;
	}
	public LinkedList<String> creatParTypeList(String p) {
		LinkedList<String> pt =new LinkedList<>();
		Auditor a =new Auditor();
		String[] temp=p.split(",");
		for(String s: temp)
		{
			
			if(!s.equals(""))
			{
				if(a.variableCheck(s+";"))
				{
					
					pt.add(a.holdVariables.getLast().getDataType());
				}
				else 
				{
					this.isValid=false;
				}
			}
				
		}
		return pt;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public LinkedList<String> getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(LinkedList<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

}
