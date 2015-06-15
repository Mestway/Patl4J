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
	// Should take care that after normalization, 
	// 	ast is same as normalizedAST
	CompilationUnit ast;
	CompilationUnit normalizedAST; 
	String originalASTString;
	
	public JavaFile(ICompilationUnit f) {
		fileCU = f;

		ast = genASTFromICU(fileCU);
		originalASTString = ast.toString();
		
		NormalizeVisitor jn = new NormalizeVisitor(ast);
		System.out.println("Waht?");
		System.out.println(ast);
		ast.accept(jn);
		// Maybe not, just for simplicity consideration
		// VariableGenerator.reset();
		normalizedAST = jn.getCU();

	}
	
	/** Generate AST from ICompilationUnit:
	 * 		Use to generate an AST with type bindings from ICU
	 * @param icu: a ICompilationUnit representing the source
	 * @return the corresponding AST
	 */
	public CompilationUnit genASTFromICU(ICompilationUnit icu) {
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		Map<?,?> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
		astParser.setCompilerOptions(options);
		astParser.setSource(icu);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setResolveBindings(true);
		return (CompilationUnit) astParser.createAST(null);
	}
	
	public void reGenNormalizedAST(ICompilationUnit icu) {
		this.normalizedAST = genASTFromICU(icu);
	}
	
	/**
	 * Before normalization, ast is the AST for the original file
	 * After normalization, ast is the normalized AST.
	 * @return ast
	 */
	public ASTNode getAST() {
		return ast;
	}
	
	public ICompilationUnit getCU() {
		return fileCU;
	}
	
	public CompilationUnit getNormalizedAST() {
		return normalizedAST;
	}
	
	// This is the only way to get the original AST string
	public String getOriginalASTString() {
		return originalASTString;
	}

}
