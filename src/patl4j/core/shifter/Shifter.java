package patl4j.core.shifter;

import java.util.Optional;

import org.eclipse.jdt.core.dom.Block;

import patl4j.common.java.analyzer.Analyzer;
import patl4j.core.matcher.MatcherSet;
import patl4j.core.shifter.datastructure.BlockSTreeNode;

public class Shifter {
	
	private Block body;
	private MatcherSet matchers;
	
	private BlockSTreeNode blockTree;
	
	public Shifter(Block body, MatcherSet matchers, Analyzer analyzer) {
		this.body = body;
		this.matchers = matchers;
		blockTree = new BlockSTreeNode(body, body);
		
		// Debug code for block nodes
		/*
		System.out.println("[[Shifter Initialization]]");
		System.out.println("[Block Tree Informatino]");
		blockTree.printTree();*/
		
		this.matchers.addAnalyzer(analyzer);
		this.matchers.matcherBlockLevelCheck(blockTree);
		this.matchers.collectStatementsToBeShifted(blockTree);
		
		
		blockTree.collectStatementsToBeShiftedForEachBlock(matchers);
		blockTree.collectStatementsToBeDeleted(matchers);
		blockTree.clearAddedStatements();
	}
	
	/**
	 * Retrieve the blocknode of the corresponding block. 
	 * (In fact this should be part of BlockSTreeNode class, but we put here to make it looks better...)
	 * @param block The block to be queried
	 * @param blkNode The blocknode currently being searched
	 * @return the corresponding block node of the block, it may be optional.ofnullable().
	 */
	public Optional<BlockSTreeNode> retrieveBlockNode(Block block, BlockSTreeNode blkNode) {
		if (blkNode.getBlock().getStartPosition() == block.getStartPosition())
			return Optional.of(blkNode);
		for (BlockSTreeNode i : blkNode.children()) {
			Optional<BlockSTreeNode> rst = retrieveBlockNode(block, i);
			if (rst.isPresent())
				return rst;
		}
		return Optional.<BlockSTreeNode>empty();
	}
	
	public Block getBody() {
		return this.body;
	}
	
	public BlockSTreeNode getTreeRoot() {
		return this.blockTree;
	}
}
