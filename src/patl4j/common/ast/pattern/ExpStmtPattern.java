package patl4j.common.ast.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.common.tools.ErrorManager;
import patl4j.common.util.Pair;
import patl4j.common.util.VariableContext;
import patl4j.core.matcher.Matcher;

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
			Map<String, String> var2type,
			VariableContext context) {
	
		// Promote to the match of its expression
		if (s instanceof ExpressionStatement) {
			ExpressionStatement exps = (ExpressionStatement) s;
			return this.expression.tryMatch(exps.getExpression(), var2type, context);
		} else {
			return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
		}
	}

	@Override
	public Statement toJavaStatement(Matcher m) {
		ErrorManager.error("ExpStmtPattern@43", "The method should only be call on Full-series classes, this one is only for src pattern.");
		return null;
	}
	
}
