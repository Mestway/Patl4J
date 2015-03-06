package patl4j.patl.ast.full;

public class FullVariable implements FullExpression {
	String variable;
	
	public FullVariable(String variable) {
		this.variable = variable;
	}
	
	@Override
	public String toString() {
		return variable;
	}
	
}
