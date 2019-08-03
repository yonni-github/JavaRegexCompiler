package project;

class Error {
	private int lineNumber;
	String message;
	
	Error(int aLineNumber, String msg)
	{
		lineNumber=aLineNumber;
		message=msg;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getMessage() {
		return message;
	}

}
