package patl4j.java.analyzer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Iterator;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.dom.Statement;

import soot.Scene;
import soot.SootClass;

public class Analyzer {
	
	// TODO: to be done
	final char NEWLINE = '\n';
	
	DataDependency dependency;
	String classStr;
	
	/**
	 * 
	 * @param className the class name
	 * @param _classStr the class literal code
	 */
	public Analyzer(String className, String _classStr) {
		/* the class path should be configured beforehand (e.g. write in patl file) */
		String classPath = "/Users/Vani/Patl4J/sample-test-projects/testAB/";	
		classStr = _classStr;

		int totalLines = 1;
		for (char it: classStr.toCharArray()) {
			if (it == NEWLINE) totalLines++;
		}
		dependency = new DataDependency(classPath, className, totalLines);
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

