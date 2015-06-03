package patl4j.java.normalizer;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

// Add dump initializer to variable declarations
public class AnalysisPreprocessor extends ASTVisitor {
	public boolean visit(VariableDeclarationStatement node) {
		VariableDeclarationFragment nodeFrag = (VariableDeclarationFragment) node.fragments().get(0);
		Expression initializer = null;
		
		AST ast = node.getAST();
		
		if (node.getType().isPrimitiveType()) {
			PrimitiveType.Code typecode = ((PrimitiveType) node.getType()).getPrimitiveTypeCode();
			if (typecode == PrimitiveType.BOOLEAN)
				initializer = ast.newBooleanLiteral(false);
			else if (typecode == PrimitiveType.INT || typecode == PrimitiveType.DOUBLE || typecode == PrimitiveType.BYTE
					|| typecode ==PrimitiveType.LONG || typecode ==PrimitiveType.SHORT)
				initializer = ast.newNumberLiteral();
			else if (typecode == PrimitiveType.CHAR)
				initializer = ast.newCharacterLiteral();
		} else if (node.getType().isArrayType()) {
			initializer = ast.newArrayInitializer();
		} else 
			initializer = ast.newNullLiteral();
		
		if (initializer != null)
			nodeFrag.setInitializer(initializer);
		return true;
	}
}