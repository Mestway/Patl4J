package patl4j.patl.ast;

import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.Pair;

public class ExpStmtPattern implements StatementPattern {

	PEPattern expression;
	
	public ExpStmtPattern(PEPattern expression) {
		this.expression = expression;
	}
	
	@Override
	// TODO: implement the match method
	public Optional<List<Pair<Name, String>>> syntaxMatch(Statement s) {
		
		return Optional.of(null);
	}
	
	@Override
	public String toString() {
		return expression.toString() + ";";
	}

}
