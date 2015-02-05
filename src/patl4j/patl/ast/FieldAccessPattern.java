package patl4j.patl.ast;

public class FieldAccessPattern implements RHSPattern {
	
	String target;
	String field;
	
	public FieldAccessPattern(String target, String field) {
		this.target = target;
		this.field = field;
	}
	
	@Override
	public String toString() {
		return target + "." + field;
	}
	
}
