package patl4j.normalize.core.normalizer.visitor;

import org.eclipse.jdt.core.dom.*;

import patl4j.normalize.core.normalizer.Normalizer;

public class NormalizeVisitor extends ASTVisitor {
	
	private CompilationUnit source;
	
	public NormalizeVisitor(CompilationUnit source) {
		this.source = source;
	}

	// We only need to visit top level node, do not need to dive into statements with "visit"
	// TODO: What are all of the necessary "top-level" nodes?
	public boolean visit(MethodDeclaration node) {
		
		System.out.println("NormalizeVisitor >> method >> visit >> @17 normalize MethodDeclaration:"+node.toString());
		
		node.setBody(
				(Block) ASTNode.copySubtree(
						node.getAST(), 
						Normalizer.normalizeStmt(node.getBody()).getStatement()));
		return false;
	}
	
	public boolean visit(Initializer node) {
		
		System.out.println("NormalizeVisitor >> method >> visit >> @28 normalize Initializer:"+node.toString());
		
		
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
