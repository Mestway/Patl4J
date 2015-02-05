package patl4j.patl.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.Pair;

public interface StatementPattern {
	
	public List<Pair<Name, String>> syntaxMatch(Statement s);

}
