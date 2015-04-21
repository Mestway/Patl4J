package patl4j.patl.ast;

public class VarDecl {
	
	private String variable;
	// the type of the variable in the old context
	private String c; 
	// the type of the variable in the new context
	private String d;

	public VarDecl(String v, String c, String d) {
		this.variable = v;
		this.c = c;
		this.d = d;
	}
	
	public String getName() {
		return variable;
	}
	
	public String getOldType() {
		return c;
	}
	
	public String getNewType() {
		return d;
	}
	
	@Override
	public String toString() {
		return variable + ":" + c + "->" + d;
	}
	
}
