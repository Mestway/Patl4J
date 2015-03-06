package patl4j.patl.ast.full;

public class FullBinaryOp implements FullExpression {
	
	String operator;
	FullExpression first;
	FullExpression second;

	public FullBinaryOp(String operator, FullExpression a, FullExpression b) {
		this.first = a;
		this.second = b;
		this.operator = operator;
	}
	
}
