package patl4j.transform.core.transformer.visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import patl4j.common.ast.pattern.Rule;
import patl4j.common.config.PatlOption;
import patl4j.common.java.analyzer.Analyzer;
import patl4j.common.util.VariableContext;
import patl4j.transform.core.transformer.Transformer;

public class TransformationVisitor extends ASTVisitor {
	
	// Patl Rules
	private List<Rule> rules;
	
	private CompilationUnit source;
	private Analyzer currentAnalyzer;
	
	private PatlOption option;
	
	// This will be updated with the type declaration visitor
	private TypeDeclaration typeDecl = null; 
	
	private String packageName = "";
	
	public TransformationVisitor(List<Rule> rules, CompilationUnit source, PatlOption option) {
		this.rules = rules;
		this.source = source;
		if (this.source.getPackage() != null) {
			packageName = this.source.getPackage().getName().toString();
		}
		// We will not initialize the analyzer here, but every time get in to a class
		this.currentAnalyzer = null;
		this.option = option;
	}
	
	// Set the environment of the transformer
	public boolean visit(TypeDeclaration node) {
		typeDecl = node;
		this.currentAnalyzer = new Analyzer(packageName + "." + typeDecl.getName().toString(), source.toString());
		return true;
	}
	
	// We only need to visit top level node, do not need to dive into statements with "visit"
	// TODO: Again, What are all of the necessary "top-level" nodes?
	public boolean visit(MethodDeclaration node) {
		
		if (node.getBody() == null)
			return false;
		//the parameters should be finished while running 
		//according to the main class of the transformed project
		VariableContext vc = new VariableContext(option.getMainClassName(), "main");
		Transformer transformer = new Transformer(rules);
		
		ASTNode newBody = transformer.execute(
				node.getBody(), 
				this.currentAnalyzer, 
				node.getName().toString(),
				vc);
		
		node.setBody(
			(Block) ASTNode.copySubtree(
				node.getAST(), 
				newBody));
		
		return false;
	}
	
	public boolean visit(Initializer node) {
		
		//the parameters should be finished while running 
		//according to the main class of the transformed project
		VariableContext vc = new VariableContext(option.getMainClassName(),"main");
		
		Transformer transformer = new Transformer(rules);
		
		node.setBody(
				(Block) ASTNode.copySubtree(
						node.getAST(),

		transformer.execute(node.getBody(), this.currentAnalyzer, "$initializer$��Ҳ��֪��ʲô", vc)));
		return false;
	}
	
}
