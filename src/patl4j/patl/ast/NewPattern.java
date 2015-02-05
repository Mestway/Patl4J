package patl4j.patl.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

import patl4j.util.Pair;

public class NewPattern implements PEPattern{

	String className;
	List<String> argList;
	
	public NewPattern(String className, List<String> argList) {
		this.className = className;
		this.argList = argList;
	}
	
	@Override
	public List<Pair<String, String>> syntaxMatch(Expression exp) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
		return "new" + " " + className + args;
	}
}
