package patl4j.java.analyzer;

import java.util.List;

import org.eclipse.jdt.core.dom.Statement;

import patl4j.handlers.PatlOption;

public class Analyzer {
	
	// TODO: to be done
	final char NEWLINE = '\n';
	
	DataDependency dependency;
	String classStr;
	
	/**
	 * 
	 * @param className the class name
	 * @param _classStr the class code literal
	 */
	public Analyzer(String className, String _classStr, PatlOption option) {
		/* the class path should be configured beforehand (e.g. write in patl file) */
		
		// To Vani: You used to use this, I change it into reading it from the option
		// String classPath = "/Users/Vani/Patl4J/sample-test-projects/testAB/";
		
		List<String> classPath = option.getClassPath();
		String concatPath = "";
		for (String st: classPath) {
			concatPath += ":" + st;
		}
		
		
		System.out.println("class path: " + concatPath);
		
		className = "testAB." + className;
		System.out.println("class name: " + className);
		
		classStr = _classStr;

		int totalLines = 1;
		for (char it: classStr.toCharArray()) {
			if (it == NEWLINE) totalLines++;
		}
		dependency = new DataDependency(concatPath, className, totalLines);
	}

	/**
	 * Whether the statement s depends on the statement t.
	 * @param methodName the method name. "<init>" for constructor method
	 * @param s
	 * @param t
	 * @return
	 */
	public boolean analyze(String methodName, Statement s, Statement t) {
		System.out.println("Analyze statements at " + methodName + ":");
		
		int sOffset = s.getStartPosition(), tOffset = s.getStartPosition();
		int cntLines = 1, sLine = 0, tLine = 0;
		for (int i = 0; i <= Math.max(sOffset, tOffset); i++) {
			if (classStr.charAt(i) == NEWLINE) cntLines++;
			if (i == sOffset) sLine = cntLines;
			if (i == tOffset) tLine = cntLines;
		}
		
		System.out.println("\t@" + sLine + "  " + s);
		System.out.println("\t@" + tLine + "  " + t);
		
		boolean ret = dependency.isDependent(methodName, sLine, tLine);
		return ret;
	}
	
}

