package patl4j.core.transformer;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import patl4j.java.analyzer.Analyzer;
import patl4j.patl.ast.Rule;

public class TransformationVisitor extends ASTVisitor {
	
	// Patl Rules
	private List<Rule> rules;
	
	private CompilationUnit source;
	private Analyzer analyzer;
	
	public TransformationVisitor(List<Rule> rules, CompilationUnit source) {
		this.rules = rules;
		this.source = source;
		this.analyzer = new Analyzer(source.toString());
	}
	
	// We only need to visit top level node, do not need to dive into statements with "visit"
	// TODO: Again, What are all of the necessary "top-level" nodes?
	public boolean visit(MethodDeclaration node) {
		
		Transformer transformer = new Transformer(rules);
		
		ASTNode newBody = transformer.execute(node.getBody(), this.analyzer);
		
		node.setBody(
			(Block) ASTNode.copySubtree(
				node.getAST(), 
				newBody));
		
		return false;
	}
	
	public boolean visit(Initializer node) {
		Transformer transformer = new Transformer(rules);
		
		node.setBody(
				(Block) ASTNode.copySubtree(
						node.getAST(),
						transformer.execute(node.getBody(), this.analyzer)));
		return false;
	}
	
}
