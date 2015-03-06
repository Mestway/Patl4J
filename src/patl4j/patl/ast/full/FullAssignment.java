package patl4j.patl.ast.full;

import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.Pair;

public class FullAssignment implements FullStatement{

	// TODO: Could be field access????
	String variable;
	FullExpression exp;
	
	public FullAssignment(String variable, FullExpression exp) {
		this.variable = variable;
		this.exp = exp;
	}
	
	@Override
	public Optional<List<Pair<Name, String>>> syntaxMatch(Statement s) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(null);
	}

	@Override
	public String toString() {
		return variable + "=" + exp.toString() + ";";
	}
	
}
