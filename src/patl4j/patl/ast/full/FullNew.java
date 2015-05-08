package patl4j.patl.ast.full;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

import patl4j.matcher.Matcher;

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

	@SuppressWarnings("unchecked")
	@Override
	public Expression toJavaExp(Matcher m) {
		AST tAST = AST.newAST(AST.JLS8);
		ClassInstanceCreation ci = tAST.newClassInstanceCreation();
		ci.setType(tAST.newSimpleType(tAST.newSimpleName(className)));
		for (FullExpression i : argList) {
			ci.arguments().add(ASTNode.copySubtree(tAST, i.toJavaExp(m)));
		}
		return ci;
	}
	
}
