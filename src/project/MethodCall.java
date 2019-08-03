package project;

import java.util.LinkedList;

 class MethodCall extends Statement {
	
	MethodCall(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}
	private String methodName;
	LinkedList<Object> argumentList;
 	
}
 
 class MethodDecleration extends Statement {
		
		MethodDecleration(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}
		private String methodName;
		private String visibility;
		private boolean isStatic;
		private String returnType;
		LinkedList<Variable> parameterList;
		
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
		
		
		
	}
