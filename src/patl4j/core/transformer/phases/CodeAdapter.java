package patl4j.core.transformer.phases;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import patl4j.matcher.MatcherSet;
import patl4j.util.Pair;

public class CodeAdapter {

	Block body;
	MatcherSet matchers;
	
	public CodeAdapter(Block body, MatcherSet bindedMatcher) {
		this.body = body;
		this.matchers = bindedMatcher;
	}

	public Statement adaptCode() {
		Pair<List<Statement>, Boolean> adaptResult = this.adapt(body);
		return wrap(adaptResult);		
	}

	/**
	 * Promote the adaptation to the different funcitons
	 * @param stmt: The statement to be adapted.
	 * @return the adapted statement
	 */
	private Pair<List<Statement>,Boolean> adapt(Statement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		if (stmt instanceof Block) {
			return adapt((Block) stmt);
		} else if (stmt instanceof ExpressionStatement) {
			return adapt((ExpressionStatement) stmt);
		} else if (stmt instanceof VariableDeclarationStatement) {
			return adapt((VariableDeclarationStatement) stmt);
		} else if (stmt instanceof IfStatement) {
			return adapt((IfStatement) stmt);
		} else if (stmt instanceof WhileStatement) {
			return adapt((WhileStatement) stmt);
		} else if (stmt instanceof ForStatement) {
			return adapt((ForStatement) stmt);
		} else if (stmt instanceof EnhancedForStatement) {
			return adapt((EnhancedForStatement) stmt);
		} else if (stmt instanceof DoStatement) {
			return adapt((DoStatement) stmt);
		} else {
			return new Pair<List<Statement>, Boolean>(stmtList, false);
		}	
	}
	
	@SuppressWarnings("unchecked")
	private Pair<List<Statement>, Boolean> adapt(Block stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		for (Statement s : (List<Statement>) stmt.statements()) {
			Pair<List<Statement>, Boolean> result = adapt(s);
			// It is not from a block, added all modifed statements into the list
			if (result.getSecond() == false) {
				for (Statement i : result.getFirst()) {
					if (i instanceof EmptyStatement)
						continue;
					stmtList.add(i);
				}
			} else {
				Statement tempStmt = wrap(result);
				stmtList.add(tempStmt);
			}
		}
		return new Pair<List<Statement>, Boolean>(stmtList, true);
	}
	
	private Pair<List<Statement>, Boolean> adapt(ExpressionStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		if (matchers.stmtMathedToMinus(stmt))
			stmtList.add(stmt);
		
		if (matchers.stmtMatchedToLastStmt(stmt)) {
			List<Statement> gen = matchers.generateFromStatement(stmt);
			for (Statement i : gen) {
				stmtList.add(i);
			}
		}
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(VariableDeclarationStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		if (matchers.stmtMathedToMinus(stmt))
			stmtList.add(stmt);
		
		if (matchers.stmtMatchedToLastStmt(stmt)) {
			List<Statement> gen = matchers.generateFromStatement(stmt);
			for (Statement i : gen) {
				stmtList.add(i);
			}
		}
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}

	private Pair<List<Statement>, Boolean> adapt(IfStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		
		stmt.setThenStatement((Statement) ASTNode.copySubtree(
				stmt.getAST(), 
				wrap(adapt(stmt.getThenStatement()))));
		
		stmt.setElseStatement((Statement) ASTNode.copySubtree(
				stmt.getAST(), 
				wrap(adapt(stmt.getElseStatement()))));
		
		stmtList.add(stmt);
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(WhileStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody()))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(ForStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody()))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(EnhancedForStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody()))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(DoStatement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody()))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	/**
	 * It is used to wrap a list of statements into blocks if necessary.
	 * @param pair: 
	 * 	First field: A list of statement to be wrapped
	 * 	Second field: Whether the list came from a block, if it is true, the result should absolutely returned in the form of Block
	 * @return a statement, of either a block or other statement forms depending on the input
	 */
	@SuppressWarnings("unchecked")
	private Statement wrap(Pair<List<Statement>, Boolean> pair) {
		List<Statement> stmtList = pair.getFirst();
		boolean fromBlock = pair.getSecond();
		if (stmtList.size() == 0) {
			Statement emptyStmt = AST.newAST(AST.JLS8).newEmptyStatement();
			if (fromBlock) {
				AST tAST = AST.newAST(AST.JLS8);
				Block newBlock = tAST.newBlock();
				newBlock.statements().add(ASTNode.copySubtree(tAST, emptyStmt));
				return newBlock;
			} else {
				return emptyStmt;
			}
		} else if (stmtList.size() == 1) {
			Statement stmt = stmtList.get(0);
			if (fromBlock) {
				AST tAST = AST.newAST(AST.JLS8);
				Block newBlock = tAST.newBlock();
				newBlock.statements().add(ASTNode.copySubtree(tAST, stmt));
				return newBlock;
			} else {
				return stmt;
			}
		} else {
			AST tAST = AST.newAST(AST.JLS8);
			Block newBlock = tAST.newBlock();
			for (Statement s : stmtList) {
				newBlock.statements().add(ASTNode.copySubtree(tAST, s));
			}
			return newBlock;
		}
	}
	
	/**
	 * Check whether the statement in the context should be deleted.
	 * @param stmt
	 * @return whether the statement should be deleted
	 */
	private boolean shouldBeDeleted(Statement stmt) {
		if (this.matchers.stmtMathedToMinus(stmt)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isTheGenPoint(Statement stmt) {
		if (this.matchers.stmtMatchedToLastStmt(stmt)) {
			return true;
		} else {
			return false;
		}
	}
}
