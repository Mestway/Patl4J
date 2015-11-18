package patl4j.common.ast.full;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

import patl4j.core.matcher.Matcher;

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

	@SuppressWarnings("unchecked")
	@Override
	public Expression toJavaExp(Matcher m) {
		AST tAST = AST.newAST(AST.JLS8);
		MethodInvocation mi = tAST.newMethodInvocation();
		mi.setName(tAST.newSimpleName(methodName));
		mi.setExpression((Expression) ASTNode.copySubtree(tAST, target.toJavaExp(m)));
		for (FullExpression i : argList) {
			mi.arguments().add((Expression)ASTNode.copySubtree(tAST, i.toJavaExp(m)));
		}
		return mi;
	}
	
}