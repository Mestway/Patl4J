package patl4j.patl.ast.full;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;

import patl4j.matcher.Matcher;

public class FullQuotedString implements FullExpression{
	String string;
	
	public FullQuotedString(String input) {
		this.string = input;
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	@Override
	public Expression toJavaExp(Matcher m) {
		StringLiteral stringNode = AST.newAST(AST.JLS8).newStringLiteral();
		stringNode.setEscapedValue(this.string);
		return stringNode;
	}
}
