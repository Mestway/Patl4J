package patl4j.java.normalizer;

import org.eclipse.jdt.core.dom.*;

public class NormalizeVisitor extends ASTVisitor {
	
	private CompilationUnit source;
	
	public NormalizeVisitor(CompilationUnit source) {
		this.source = source;
	}

	// We only need to visit top level node, do not need to dive into statements with "visit"
	// TODO: What are all of the necessary "top-level" nodes?
	public boolean visit(MethodDeclaration node) {
		node.setBody(
				(Block) ASTNode.copySubtree(
						node.getAST(), 
						Normalizer.normalizeStmt(node.getBody()).getStatement()));
		return false;
	}
	
	public boolean visit(Initializer node) {
		node.setBody(
				(Block) ASTNode.copySubtree(
						node.getAST(),
						Normalizer.normalizeStmt(node.getBody()).getStatement()));
		return false;
	}
	
	public CompilationUnit getCU() {
		return source;
	}
}
