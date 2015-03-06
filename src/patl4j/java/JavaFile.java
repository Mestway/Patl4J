package patl4j.java;

import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import patl4j.java.normalizer.NormalizeVisitor;

public class JavaFile {

	ICompilationUnit fileCU;
	ASTParser astParser;
	CompilationUnit ast;
	CompilationUnit normalizedAST;
	
	public JavaFile(ICompilationUnit f) {
		fileCU = f;
		astParser = ASTParser.newParser(AST.JLS8);
		Map<?, ?> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
		astParser.setCompilerOptions(options);
		astParser.setSource(fileCU);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setResolveBindings(true);
		ast = (CompilationUnit) astParser.createAST(null);
		
		NormalizeVisitor jn = new NormalizeVisitor(ast);
		ast.accept(jn);
		// Maybe not, just for simplicity consideration
		// VariableGenerator.reset();
		normalizedAST = jn.getCU();
	}
	
	public ASTNode getAST() {
		return ast;
	}
	
	public ICompilationUnit getCU() {
		return fileCU;
	}
	
	public CompilationUnit getNormlizedAST() {
		return normalizedAST;
	}

}
