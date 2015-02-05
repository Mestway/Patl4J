package patl4j.patl.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

import patl4j.util.Pair;

public class MethodPattern implements PEPattern {

	String target;
	String methodName;
	List<String> argList;
	
	public MethodPattern(String target, String methodName, List<String> argList) {
		this.target = target;
		this.methodName = methodName;
		this.argList = argList;
	}
	
	@Override
	public List<Pair<String, String>> syntaxMatch(Expression exp) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		String args = "(";
		boolean flag = true;
		for (String i : argList) {
			if (!flag)
				args += "," + i;
			else {
				args += i;
				flag = false;
			}
		}
		args += ")";
		return target + "." + methodName + args;
	}

}
