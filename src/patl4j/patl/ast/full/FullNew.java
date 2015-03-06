package patl4j.patl.ast.full;

import java.util.List;

public class FullNew implements FullExpression {
	String className;
	List<FullExpression> argList;
	
	public FullNew(String name, List<FullExpression> args) {
		this.className = name;
		this.argList = args;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "new " + className + "(";
		boolean flag = false;
		for (FullExpression f : this.argList) {
			if (flag) str += ", ";
			str += f.toString();
			flag = true;
		}
		str += ")";
		return str;
	}
	
}
