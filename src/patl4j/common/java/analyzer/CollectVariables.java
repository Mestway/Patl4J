package patl4j.common.java.analyzer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;

public class CollectVariables extends ASTVisitor {
	Set<String> variables;
	
	public CollectVariables() {
		variables = new HashSet<String>();
	}
	
	public boolean visit(SimpleName name) {
		variables.add(name.getIdentifier());
		return true;
	}
	
	public Set<String> getVariables() {
		return variables;
	}
}
