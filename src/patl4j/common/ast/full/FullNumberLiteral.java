package patl4j.common.ast.full;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

import patl4j.core.matcher.Matcher;

public class FullNumberLiteral implements FullExpression {

	String literal;
	
	public FullNumberLiteral(String input) {
		this.literal = input;
	}
	
	@Override
	public String toString() {
		return literal;
	}
	
	@Override
	public Expression toJavaExp(Matcher m) {
		NumberLiteral numberNode = AST.newAST(AST.JLS8).newNumberLiteral();
		numberNode.setToken(this.literal);
		return numberNode;
	}
}
