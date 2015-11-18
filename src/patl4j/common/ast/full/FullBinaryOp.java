package patl4j.common.ast.full;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import patl4j.core.matcher.Matcher;

public class FullBinaryOp implements FullExpression {
	
	String operator;
	FullExpression first;
	FullExpression second;

	public FullBinaryOp(String operator, FullExpression a, FullExpression b) {
		this.first = a;
		this.second = b;
		this.operator = operator;
	}

	@Override
	public Expression toJavaExp(Matcher m) {
		AST tAST = AST.newAST(AST.JLS8);
		InfixExpression ie = tAST.newInfixExpression();
		ie.setLeftOperand((Expression) ASTNode.copySubtree(tAST, first.toJavaExp(m)));
		ie.setRightOperand((Expression) ASTNode.copySubtree(tAST, second.toJavaExp(m)));
		ie.setOperator(InfixExpression.Operator.toOperator(operator));
		return ie;
	}
}
