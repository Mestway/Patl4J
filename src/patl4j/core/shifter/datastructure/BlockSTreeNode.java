package patl4j.core.shifter.datastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.WhileStatement;

import patl4j.common.tools.ErrorManager;
import patl4j.core.matcher.MatcherSet;

public class BlockSTreeNode {
	
	static Integer idGenerator = 0;
	private static Integer genNewId() {
		idGenerator ++;
		return idGenerator;
	}
	
	// The id of the BlockTreeNode
	private Integer id;
	
	// The information of the current node
	private Block block;
	private int start;
	private int end;
	
	/*	The owner statement of the block, containing three kinds: 
	 *   	IfStatement
	 *   	WhileStatement
	 *  	Block 	
	 */
	private Statement blockOwner;
	public enum BlockType {
	    IFELSEBLOCK, LOOPBLOCK, NORMALBLOCK  
	}
	private BlockType blockType;
	
	// This only works when the blockOwner is IfStatement
	private BlockSTreeNode twinBlock;
	
	// The level of the node in the tree, starts with 0 (the root node)
	// Whenever the node is created, the level is set to 0, it should be modified on demand
	private int level = 0;
	
	// If the node is the root of the tree, then: parentNode = this
	private BlockSTreeNode parentNode;
	
	// The children of the blocknode
	private List<BlockSTreeNode> internalBlocks = new ArrayList<BlockSTreeNode>();
	
	@SuppressWarnings("unchecked")
	public BlockSTreeNode(Block blk, Statement blockOwner) {
		this.id = genNewId();
		this.block = blk;
		if (blockOwner instanceof Block)
			this.blockType = BlockType.NORMALBLOCK;
		else if (blockOwner instanceof IfStatement)
			this.blockType = BlockType.IFELSEBLOCK;
		else if (blockOwner instanceof WhileStatement || blockOwner instanceof ForStatement || blockOwner instanceof DoStatement) {
			this.blockType = BlockType.LOOPBLOCK;
		}
		this.start = this.block.getStartPosition();
		this.end = block.getLength() + this.start;
		
		// Create the children of the block by looping over all of the statements inside the block.
		for (Statement s : (List<Statement>)blk.statements()) {
			// If the child is a block, simply create a children block for it.
			if (s instanceof Block) {
				this.addChild(new BlockSTreeNode((Block)s, s));
			} else if (s instanceof WhileStatement) {
				Statement body = ((WhileStatement)s).getBody();
				if (body instanceof Block) 
					this.addChild(new BlockSTreeNode((Block)body, s));
				else {
					ErrorManager.error("BlockSTreeNode@52", "Error on block node: the body of the while statement is not a block.");
				}
			} else if (s instanceof IfStatement) {
				IfStatement ifstmt = (IfStatement) s;
				if (!(ifstmt.getThenStatement() instanceof Block)) {
					if (ifstmt.getElseStatement()!= null && (ifstmt.getElseStatement() instanceof Block)) {
						ErrorManager.error("BlockSTreeNode@60", "Error on block node: the body of the If statement is not a block. ");
						continue;
					}
				}
				BlockSTreeNode ifnode = new BlockSTreeNode((Block)ifstmt.getThenStatement(), ifstmt);
				if (ifstmt.getElseStatement() != null) {
					BlockSTreeNode elsenode = new BlockSTreeNode((Block)ifstmt.getElseStatement(), ifstmt);
					ifnode.twinBlock = elsenode;
					elsenode.twinBlock = ifnode;
					this.addChild(ifnode);
					this.addChild(elsenode);
				} else {
					ifnode.twinBlock = null;
					this.addChild(ifnode);
				}
				
			} else if (s instanceof ForStatement) {
				if (((ForStatement)s).getBody() instanceof Block) {
					this.addChild(new BlockSTreeNode((Block)((ForStatement)s).getBody(), s));
				} else {
					ErrorManager.error("BlockSTreeNode@72", "Error on block node: the body of the for statement is not a block.");
				}
			} else if (s instanceof WhileStatement) {
				if (((WhileStatement)s).getBody() instanceof Block) {
					this.addChild(new BlockSTreeNode((Block)((WhileStatement)s).getBody(), s));
				} else {
					ErrorManager.error("BlockSTreeNode@100", "Error on block node: the body of the while statement is not a block.");
				}
			} else if (s instanceof DoStatement) {
				if (((DoStatement)s).getBody() instanceof Block) {
					this.addChild(new BlockSTreeNode((Block)((DoStatement)s).getBody(), s));
				} else {
					ErrorManager.error("BlockSTreeNode@106", "Error on block node: the body of the do statement is not a block.");
				}
			} else if (s instanceof EnhancedForStatement) {
				if (((EnhancedForStatement)s).getBody() instanceof Block) {
					this.addChild(new BlockSTreeNode((Block)((EnhancedForStatement)s).getBody(), s));
				} else {
					ErrorManager.error("BlockSTreeNode@113", "Error on block node: the body of the enhanced for statement is not a block.");
				}
			}
		}
	}
	
	private void addChild(BlockSTreeNode btn) {
		btn.parentNode = this;
		btn.level = this.level + 1;
		internalBlocks.add(btn);
	}
	
	/**
	 * Find the least common ancestor of two BlockTreeNode.
	 * @param first the first node to be queried
	 * @param second the second node to be queried
	 * @return the least common ancestor
	 */
	public BlockSTreeNode leastCommonAncestor(BlockSTreeNode first, BlockSTreeNode second) {
		BlockSTreeNode ancestor1 = first;
		BlockSTreeNode ancestor2 = second;
		
		// The condition may be necessary to check
		while(!ancestor1.equals(ancestor2)) {
			if (ancestor1.level == 0 && ancestor2.level == 0) {
				ErrorManager.error("BlockTreeNode@44", "Queried two blocks are not in the same tree.");
				return null;
			}
			if (ancestor1.level <= ancestor2.level) {
				ancestor2 = ancestor2.parentNode;
			} else {
				ancestor1 = ancestor1.parentNode;
			}
		}
		return ancestor1;
	}
	
	/**
	 * Find the block that contains the statement s.
	 * @param s the statement
	 * @return Optional.ofNullable(null) => the block does not contain the statement s.
	 * 			Optional.of(blockNode) => the block that contains the statement s. 
	 */
	public Optional<BlockSTreeNode> retrieveDirectContainedBlock(Statement s) {
		if (s.getStartPosition() >= this.start && s.getStartPosition() <= this.end) {
			for (BlockSTreeNode btn : this.internalBlocks) {
				Optional<BlockSTreeNode> tryRetrieve = btn.retrieveDirectContainedBlock(s);
				if (tryRetrieve.isPresent()) {
					return tryRetrieve;
				}
			}
			return Optional.of(this);
		}
		return Optional.<BlockSTreeNode>empty();
	}

	public void printTree() {
		String blank = "  ";
		for (int i = 0; i < this.level; i ++) {
			blank += "  ";
		}
		System.out.println(blank + "CurrentBlock#" + this.id + ": " + this.block.getStartPosition() + " - " + this.end);
		for (BlockSTreeNode btn : this.internalBlocks) {
			btn.printTree();
		}
	}
	
	/**
	 * Get the twin block of the current node, this only works for the case when the statement is a if-else block.
	 * @return the twin block
	 */
	public BlockSTreeNode getTwinBlockNode() {
		return this.twinBlock;
	}
	
	/**
	 * Return where did the block node generate from, e.g. IfStatement, WhileStatement...
	 * @return the owner of the block.
	 */
	public Statement getBlockOwner() {
		return this.blockOwner;
	}
	
	public BlockType getBlockType() {
		return this.blockType;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Integer getLevel() {
		return this.level;
	}
	
	public BlockSTreeNode getParent() {
		return this.parentNode;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	
	/***** Part of adaption ******/
	private List<Statement> statementsToBeDeleted = new ArrayList<Statement>();
	private List<Statement> statementsToBeAddedAtBeginning = new ArrayList<Statement>();
	private List<Statement> statementsToBeAddedAtTheEnd = new ArrayList<Statement>();
	private boolean isDoubleCleared = false;
	
	public List<Statement> getStatementsToBeAddedAtBeginning() { return this.statementsToBeAddedAtBeginning; }
	public List<Statement> getStatementsToBeAddedAtTheEnd() { return this.statementsToBeAddedAtTheEnd; }
	public List<Statement> getStatementsToBeDeleted() { return this.statementsToBeDeleted; }
	
	// Calculate which statements are supposed to be add at the block. This is done recursively
	public void collectStatementsToBeShiftedForEachBlock(MatcherSet matchers) {
		matchers.getStatementsToBeAddedToTheBlock(this);
		for (BlockSTreeNode i : this.internalBlocks) {
			i.collectStatementsToBeShiftedForEachBlock(matchers);
		}
	}
	
	public void addToDeleteSet(Statement s) {
		this.statementsToBeDeleted.add(s);
	}
	
	/**
	 * Check whether a statement should be deleted in the adaptation process.
	 * @param s
	 * @return
	 */
	public boolean toBeDeleted(Statement s) {
		for (Statement k : statementsToBeDeleted) {
			if (k.getStartPosition() == s.getStartPosition()) {
				return true;
			}
		}
		return false;
	}
	
	public void addToStmtListBeginning(Statement s) {
		this.statementsToBeAddedAtBeginning.add(s);
		if (this.twinBlock != null) {
			this.twinBlock.statementsToBeAddedAtBeginning.add(s);
		}
	}
	
	public void addToStmtListEnd(Statement s) {
		this.statementsToBeAddedAtTheEnd.add(s);
		if (this.twinBlock != null) {
			this.twinBlock.statementsToBeAddedAtTheEnd.add(s);
		}
	}
	
	/**
	 * During the shifted statements addition process, multiple same statements may be added to this block.
	 */
	public void clearAddedStatements() {
		isDoubleCleared = true;
		this.statementsToBeAddedAtBeginning = this.clearAndSort(statementsToBeAddedAtBeginning);
		this.statementsToBeAddedAtTheEnd = this.clearAndSort(statementsToBeAddedAtTheEnd);
		for (BlockSTreeNode i : this.internalBlocks) {
			i.clearAddedStatements();
		}
		
		boolean PrintShiftCommand = false;
		if (PrintShiftCommand)
		{
			System.out.println("=== These are the statements to be added in the block: ");
			System.out.println("[Block]\n\t" + this.block);
			System.out.println("[Beginning part]");
			for (Statement i : this.statementsToBeAddedAtBeginning) {
				System.out.println(i);
			}
			System.out.println("[End part]");
			for (Statement i : this.statementsToBeAddedAtTheEnd) {
				System.out.println(i);
			}
			System.out.println("[DeletePart]");
			for (Statement i : this.statementsToBeDeleted) {
				System.out.println(i);
			}
			System.out.println("*** End now");
		}
		
	}
	
	private List<Statement> clearAndSort(List<Statement> stmtList) {
		List<Statement> result = new ArrayList<Statement>();
		for (Statement s : stmtList) {
			boolean contains = false;
			boolean deleted = false;
			for (Statement k : result) {
				if (k.getStartPosition() == s.getStartPosition())
					contains = true;
			}
			for (Statement u : this.statementsToBeDeleted) {
				if (u.getStartPosition() == s.getStartPosition())
					deleted = true;
			}
			if (!contains && !deleted)
				result.add(s);
		}
		result.sort(new StmtPositionComparator());
		return result;
	}
	
	/**
	 * Get the children nodes of this node
	 * @return a list of BlockSTreeNode
	 */
	public List<BlockSTreeNode> children() {
		return this.internalBlocks;
	}

	public void collectStatementsToBeDeleted(MatcherSet matchers) {
		matchers.collectStatementsToBeDeletedInBlock(this);
		for (BlockSTreeNode i : this.internalBlocks)
			i.collectStatementsToBeDeleted(matchers);
	}

}

// TODO: check whether this is right later on.
class StmtPositionComparator implements Comparator<Statement> {
    @Override
    public int compare(Statement a, Statement b) {
        return a.getStartPosition() - b.getStartPosition();
    }
}
