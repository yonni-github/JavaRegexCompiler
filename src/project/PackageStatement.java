package project;

public class PackageStatement extends Statement{
	
	private String packageName;
	private static int numOfPackages=0;
	
	PackageStatement(String line) {
		super(line);
		String[] temp=line.split(" ");
		packageName= temp[1].substring(0, temp[1].length()-1);
		numOfPackages++;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
}
