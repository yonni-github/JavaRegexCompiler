package project;

public class SubClass extends Class {
	
	private Class superClass;
	
	SubClass(String str) {
		super(str);
	}
	public Class getSuperClass() {
		return superClass;
	}
	public void setSuperClass(Class superClass) {
		this.superClass = superClass;
	}
	
}
