package patl4j.java.analyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import patl4j.handlers.PatlOption;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

public class Analyzer {
	
	// TODO: to be done
	final char NEWLINE = '\n';
	
	DataDependency dependency;
	String classStr;
	String cls = "";
	
	
	/**
	 * 
	 * @param className the class name
	 * @param _classStr the class code literal
	 */
	public Analyzer(String className, String _classStr) {
		cls = className;
		classStr = _classStr;
		FileWriter fileWriter;
		SootClass s = null;
		for (SootClass x : Scene.v().getClasses()) {
			if(x.getName().equals(className))
				s = x;
        }
		// = Scene.v().getSootClass(className);
		if (s != null)
			dependency = new DataDependency(s);
	}

	Set<String> getVariables(Statement t) {
		CollectVariables visitor = new CollectVariables();
		t.accept(visitor);
		return visitor.getVariables();
	}
	
	boolean checkIsDeclar(Statement s, Statement t) {
		if (s instanceof VariableDeclarationStatement) {
			String sName = ((VariableDeclarationFragment) ((VariableDeclarationStatement) s).fragments().get(0)).getName().getIdentifier();
			Set<String> tVariables = getVariables(t);
			if (tVariables.contains(sName)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Whether the statement s depends on the statement t.
	 * @param methodName the method name. "<init>" for constructor method
	 * @param s
	 * @param t
	 * @return
	 */
	public boolean analyze(String methodName, Statement s, Statement t) {	 
		if (checkIsDeclar(s, t) || checkIsDeclar(t, s)) {
			return true;
		}		
		int sOffset = s.getStartPosition(), tOffset = t.getStartPosition();
		int cntLines = 1, sLine = 0, tLine = 0;
		for (int i = 0; i <= Math.max(sOffset, tOffset); i++) {
			if (classStr.charAt(i) == NEWLINE) cntLines++;
			if (i == sOffset) sLine = cntLines;
			if (i == tOffset) tLine = cntLines;
		}
		boolean ret = dependency.isDependent(methodName, sLine, tLine);
		return ret;
	}
	
	public String getClassName() {
		return this.cls;
	}
	
}

