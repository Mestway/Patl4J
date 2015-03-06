package patl4j.patl.ast.full;

import java.util.List;

public class FullMethodInvk implements FullExpression {
	FullExpression target;
	String methodName;
	List<FullExpression> argList;
	
	public FullMethodInvk(FullExpression target, String method, List<FullExpression> args) {
		this.target = target;
		this.methodName = method;
		this.argList = args;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += methodName + "(";
		boolean flag = false;
		for (FullExpression f : this.argList) {
			if (flag) str += ", ";
			str += f.toString();
			flag = true;
		}
		str += ")";
		return target + "." + str;
	}
	
}