package patl4j.patl.ast;

import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
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
	 * @return 	Optional.of(null): if the pattern does not match the statement.
	 * 			Optional.of(List): binding list of meta-variables and program variables.
	 */
	public Optional<List<Pair<Name, String>>> syntaxMatch(Statement s) {
		if (s instanceof ExpressionStatement) {
			Expression exp = ((ExpressionStatement)s).getExpression();
			if (exp instanceof Assignment) {
				Assignment assignment = (Assignment) exp;
				assignment.getLeftHandSide();
			}
		}
		return Optional.of(null);
	}
	
	@Override
	public String toString() {
		return variable + "=" + expression.toString() + ";";
	}
	
}
