package patl4j.patl.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.Pair;

public class AssignStmtPattern implements StatementPattern {
	
	String variable;
	RHSPattern expression;
	
	public AssignStmtPattern(String variable, RHSPattern expression) {
		this.variable = variable;
		this.expression = expression;
	}
	
	@Override
	/**
	 * @return 	null: if the pattern does not match the statement.
	 * 			List: binding list of meta-variables and program variables.
	 */
	public List<Pair<Name, String>> syntaxMatch(Statement s) {
		
		return null;
	}
	
	@Override
	public String toString() {
		return variable + "=" + expression.toString() + ";";
	}
	
}
