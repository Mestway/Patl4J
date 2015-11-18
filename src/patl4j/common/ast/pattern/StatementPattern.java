package patl4j.common.ast.pattern;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.common.util.Pair;
import patl4j.common.util.VariableContext;
import patl4j.core.matcher.Matcher;

public interface StatementPattern {
	
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type, VariableContext context);

	/**
	 * Translate a statement pattern to a Java statement, 
	 * by substituting the meta-variables with the expression they binded to. 
	 * @param m: The matched binded to the statement.
	 * @return the statement generated from the pattern.
	 */
	public Statement toJavaStatement(Matcher m);
	
}
