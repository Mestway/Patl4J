package patl4j.patl.ast.full;

public class FullFieldAccess implements FullExpression {
	
	FullExpression target;
	String field;

	public FullFieldAccess(FullExpression exp, String field) {
		this.target = exp;
		this.field = field;
	}
	
	@Override
	public String toString() {
		return target.toString() + "." + field;
	}
	
}
