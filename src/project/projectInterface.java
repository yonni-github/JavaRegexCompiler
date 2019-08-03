/**
 * 
 */
package project;

import java.io.FileNotFoundException;

public interface projectInterface {
			
	    boolean packageCheck(String statement);
		boolean importCheck(String statement) throws FileNotFoundException;
		boolean variableCheck(String line) throws FileNotFoundException;
		boolean statementCheck(String statement);
		boolean methodCheck(String line);
		boolean loopCheck(String line);
		boolean constructorCheck(String line);
	
}
