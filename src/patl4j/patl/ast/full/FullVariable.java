package patl4j.patl.ast.full;

import java.util.Optional;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;

import patl4j.matcher.Matcher;
import patl4j.matcher.WrappedName;
import patl4j.util.Pair;

public class FullVariable implements FullExpression {
	String variable;
	
	public FullVariable(String variable) {
		this.variable = variable;
	}
	
	@Override
	public String toString() {
		return variable;
	}
	
	@Override
	public Expression toJavaExp(Matcher m) {
		// Depending on the binding of the variable, return its corresponding value.
		Pair<Optional<WrappedName>, Pair<String, String>> vi = m.getMetaVariableImage(this.variable);
		if (vi.getFirst() == null || !vi.getFirst().isPresent() || vi.getFirst().get().equals("%empty%")) {
			System.out.println("Çã¹úÇã³Ç£º " + this.variable);
			return AST.newAST(AST.JLS8).newSimpleName(this.variable);
		} else {
			return vi.getFirst().get().getName();
		}
	}
	
}
