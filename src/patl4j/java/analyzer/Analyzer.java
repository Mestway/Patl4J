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
		
		String seperator = ":";
		if (option.getPlatform().equals("windows")) {
			seperator = ";";
		}
		
		List<String> classPath = option.getClassPath();
		String concatPath = "";
		for (String st: classPath) {
			concatPath += seperator + st;
		}
		
		
		System.out.println("class path: " + concatPath);
		
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

		int sOffset = s.getStartPosition(), tOffset = t.getStartPosition();
		int cntLines = 1, sLine = 0, tLine = 0;
		for (int i = 0; i <= Math.max(sOffset, tOffset); i++) {
			if (classStr.charAt(i) == NEWLINE) cntLines++;
			if (i == sOffset) sLine = cntLines;
			if (i == tOffset) tLine = cntLines;
		}

		if (sLine == 12 && tLine == 13) {
			System.out.println("Hello World");
		}
		boolean ret = dependency.isDependent(methodName, sLine, tLine);

		System.out.println("Query statements:");
		System.out.println("@" + sLine + "\t" + s);
		System.out.println("@" + tLine + "\t" + t);
		System.out.println("result is: " + ret);
		return ret;
	}
	
}

