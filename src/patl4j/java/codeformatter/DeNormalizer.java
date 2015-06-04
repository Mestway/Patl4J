package patl4j.java.codeformatter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

// This visitor is used in patl4j.tools.ProjectTransformer
public class DeNormalizer extends ASTVisitor {
	
	// Only visit top level statements, note that the constructor is also a method declaration statement.
	public boolean visit(VariableDeclarationStatement node) {
		// TODO: collect the generated variables
		return false;
	}
	
	public boolean visit(ExpressionStatement node) {
		// TODO: replace the expressions inside with the value of the generated variables
		return false;
	}

}
