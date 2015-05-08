package patl4j.patl.ast.full;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import patl4j.matcher.Matcher;

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

	@Override
	public Expression toJavaExp(Matcher m) {
		AST tAST = AST.newAST(AST.JLS8);
		FieldAccess fa = tAST.newFieldAccess();
		fa.setExpression(target.toJavaExp(m));
		fa.setName(tAST.newSimpleName(field));;
		return fa;
	}
	
}
