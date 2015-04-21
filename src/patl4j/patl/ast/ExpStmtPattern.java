package patl4j.patl.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.Pair;

public class ExpStmtPattern implements StatementPattern {

	PEPattern expression;
	
	public ExpStmtPattern(PEPattern expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return expression.toString() + ";";
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type) {
	
		// Promote to the match of its expression
		if (s instanceof ExpressionStatement) {
			ExpressionStatement exps = (ExpressionStatement) s;
			return this.expression.tryMatch(exps.getExpression(), var2type);
		} else {
			return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
		}
	}

}
