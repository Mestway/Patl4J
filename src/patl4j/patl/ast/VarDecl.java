package patl4j.patl.ast;

public class VarDecl {
	
	String variable;
	// the type of the variable in the old context
	String c; 
	// the type of the variable in the new context
	String d;

	public VarDecl(String v, String c, String d) {
		this.variable = v;
		this.c = c;
		this.d = d;
	}
	
	@Override
	public String toString() {
		return variable + ":" + c + "->>" + d;
	}
	
}
