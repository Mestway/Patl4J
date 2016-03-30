package patl4j.transform.core.transformer.phases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import patl4j.common.tools.ErrorManager;
import patl4j.common.util.Pair;
import patl4j.core.matcher.MatcherSet;
import patl4j.core.shifter.Shifter;
import patl4j.core.shifter.datastructure.BlockSTreeNode;

public class CodeAdapter {

	Block body;
	MatcherSet matchers;
	Shifter shifter;
	
	public CodeAdapter(MatcherSet bindedMatcher, Shifter shifter) {
		this.body = shifter.getBody();
		this.matchers = bindedMatcher;
		this.shifter = shifter;
	}

	public Statement adaptCode() {
		Optional<BlockSTreeNode> rootNode = shifter.retrieveBlockNode(body, shifter.getTreeRoot());
		if (rootNode.isPresent()) {
			Pair<List<Statement>, Boolean> adaptResult = this.adapt(body, rootNode.get());
			return wrap(adaptResult);
		} else {
			ErrorManager.error("CodeAdapter@44", "The root level block node does not exist.");
			return null;
		}
	}

	/**
	 * Promote the adaptation to the different functions
	 * @param stmt: The statement to be adapted.
	 * @return the adapted statement
	 */
	private Pair<List<Statement>,Boolean> adapt(Statement stmt, BlockSTreeNode currentBlock) {

		List<Statement> stmtList = new ArrayList<Statement>();
		if (stmt instanceof Block) {
			return adapt((Block) stmt, currentBlock);
		} else if (stmt instanceof ExpressionStatement) {
			return adapt((ExpressionStatement) stmt, currentBlock);
		} else if (stmt instanceof VariableDeclarationStatement) {
			return adapt((VariableDeclarationStatement) stmt, currentBlock);
		} else if (stmt instanceof IfStatement) {
			return adapt((IfStatement) stmt, currentBlock);
		} else if (stmt instanceof WhileStatement) {
			return adapt((WhileStatement) stmt, currentBlock);
		} else if (stmt instanceof ForStatement) {
			return adapt((ForStatement) stmt, currentBlock);
		} else if (stmt instanceof EnhancedForStatement) {
			return adapt((EnhancedForStatement) stmt, currentBlock);
		} else if (stmt instanceof DoStatement) {
			return adapt((DoStatement) stmt, currentBlock);
		} else {
			return new Pair<List<Statement>, Boolean>(stmtList, false);
		}	
	}
	
	@SuppressWarnings("unchecked")
	private Pair<List<Statement>, Boolean> adapt(Block stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		
		Optional<BlockSTreeNode> nextBlockNode = shifter.retrieveBlockNode(stmt, currentBlock);
		if (! nextBlockNode.isPresent()) {
			ErrorManager.error("CodeAdapter@84", "The next block node does not exist.");
		}
		
		// Some statements may be added to the begining of the block.
		for (Statement s : nextBlockNode.get().getStatementsToBeAddedAtBeginning()) {
			Pair<List<Statement>, Boolean> result = adapt(s, nextBlockNode.get());
			for (Statement i : result.getFirst()) {
				if (i instanceof EmptyStatement)
					continue;
				stmtList.add(i);
			}
		}
		
		// Continue adapting the body of the block
		for (Statement s : (List<Statement>) stmt.statements()) {
		
			// The children of the current block are supposed to be in the blockNode containing the current block.
			Pair<List<Statement>, Boolean> result = adapt(s, nextBlockNode.get());
			
			// It is not from a block, added all modified statements into the list
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
		
		// Then continue working on the statements to be added at the end of the block
		for (Statement s : nextBlockNode.get().getStatementsToBeAddedAtTheEnd()) {
			Pair<List<Statement>, Boolean> result = adapt(s, nextBlockNode.get());
			for (Statement i : result.getFirst()) {
				if (i instanceof EmptyStatement)
					continue;
				stmtList.add(i);
			}
		}
		
		return new Pair<List<Statement>, Boolean>(stmtList, true);
	}
	
	private Pair<List<Statement>, Boolean> adapt(ExpressionStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		
		/* This is the old version without shifting, now we dont use this*/
		/* if (!matchers.stmtMathedToMinus(stmt)) {
			stmtList.add(stmt);
		}*/
		
		// Whether the statement is supposed to be deleted is determined by the block.
		if (!currentBlock.toBeDeleted(stmt)) {
			stmtList.add(stmt);
		}
		
		if (matchers.stmtMatchedToGenPoint(stmt)) {
			List<Statement> gen = matchers.generateFromStatement(stmt);
			for (Statement i : gen) {
				stmtList.add(i);
			}
		}
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(VariableDeclarationStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		
		String oldTypeName = stmt.getType().toString();
		stmt.setType((Type) ASTNode.copySubtree(stmt.getAST(), matchers.mappedType(oldTypeName)));
		
		if (!currentBlock.toBeDeleted(stmt)) {
			stmtList.add(stmt);
		}
		
		if (matchers.stmtMatchedToGenPoint(stmt)) {
			List<Statement> gen = matchers.generateFromStatement(stmt);
			for (Statement i : gen) {
				stmtList.add(i);
			}
		}
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}

	private Pair<List<Statement>, Boolean> adapt(IfStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		
		stmt.setThenStatement((Statement) ASTNode.copySubtree(
				stmt.getAST(), 
				wrap(adapt(stmt.getThenStatement(), currentBlock))));
		
		// The else statement may be null
		if (stmt.getElseStatement() != null) {
			
			Statement adaptedBlock = wrap(adapt(stmt.getElseStatement(), currentBlock));
			
			// The block may be a block with only empty statement, in this case, we do set the else statement as null. 
			if (! this.isABlockWithOnlyEmptyStatement(adaptedBlock)) {
				stmt.setElseStatement((Statement) ASTNode.copySubtree(
					stmt.getAST(), 
					adaptedBlock));
			} else {
				stmt.setElseStatement(null);
			}
		}
		
		stmtList.add(stmt);
		return new Pair<List<Statement>, Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(WhileStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody(), currentBlock))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(ForStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody(), currentBlock))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(EnhancedForStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody(), currentBlock))));
		stmtList.add(stmt);
		return new Pair<List<Statement>,Boolean>(stmtList, false);
	}
	
	private Pair<List<Statement>, Boolean> adapt(DoStatement stmt, BlockSTreeNode currentBlock) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmt.setBody((Statement) ASTNode.copySubtree(
				body.getAST(), 
				wrap(adapt(stmt.getBody(), currentBlock))));
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
	
	@SuppressWarnings("unchecked")
	private boolean isABlockWithOnlyEmptyStatement(Statement blk) {
		if (!(blk instanceof Block))
			return false;
			
		for (Statement s : (List<Statement>)((Block)blk).statements()) {
			if (! (s instanceof EmptyStatement))
				return false;
		}
		return true;
	}
	
}
