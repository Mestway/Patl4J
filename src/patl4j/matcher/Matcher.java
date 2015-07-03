package patl4j.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import patl4j.debug.DebugSwitcher;
import patl4j.java.analyzer.Analyzer;
import patl4j.patl.ast.ModInstruction;
import patl4j.patl.ast.Rule;
import patl4j.patl.ast.VarDecl;
import patl4j.shifter.datastructure.BlockSTreeNode;
import patl4j.util.ErrorManager;
import patl4j.util.Pair;
import patl4j.util.VariableContext;
import patl4j.util.VariableGenerator;

public class Matcher {
	
	// Map a variable to its type in the old context.
	private Map<String, String> var2type = new HashMap<String, String>();
	// Map a variable to its type in the new context.
	private Map<String, String> var2newtype = new HashMap<String, String>();
	// Map a variable to its binded type.
	private Map<String, Optional<WrappedName>> varMap = new HashMap<String, Optional<WrappedName>>();
	
	// Type transformation information is stored in this map
	private Map<String, String> typeMap = new HashMap<String, String>();
	
	// Some assignment statements are mapped to variable declaration statements, 
	// and this set keeps which variable are in such assignment.
	private Set<String> matchedToDeclaration = new HashSet<String>();
	
	// The binding between mod instruction patterns and java statements, including both minus, m and addition
	private List<Pair<ModInstruction, Optional<Statement>>> 
		instrBindings = new ArrayList<Pair<ModInstruction, Optional<Statement>>>();
	private List<Statement> matchedStatements = new ArrayList<Statement>();
	
	// If matchpoint == -1, then there exists no matchpoint
	private Integer matchpoint = 0;
	
	// The method name of the matcher working in
	private String methodName = "";
	public void setMethodName(String methodNm) {this.methodName = methodNm;}
	
	private Matcher() {};
	
	public Matcher(Rule r) {
		for (VarDecl d : r.getDecls()) {
			var2type.put(d.getName(), d.getOldType());
			varMap.put(d.getName(), Optional.ofNullable(null));
			var2newtype.put(d.getName(), d.getNewType());
			if (!d.getOldType().equals(d.getNewType())) {
				typeMap.put(d.getOldType(), d.getNewType());
			}
		}
		for (ModInstruction i : r.getInstrs()) {
			instrBindings.add(
					new Pair<ModInstruction, Optional<Statement>>(
							i, Optional.ofNullable(null)));
		}
	}
	
	/**
	 * Query the binding information of the variable var in the matcher, including its binded value, its old type and new type.
	 * @param var: the variable to be queried.
	 * @return Pais in the form: <BindedValue, <OldType, NewType>>
	 */
	public Pair<Optional<WrappedName>, Pair<String, String>> getMetaVariableImage(String var) {
		return new Pair<Optional<WrappedName>, Pair<String, String>>(
				varMap.get(var), 
				new Pair<String, String>(var2type.get(var), 
						var2newtype.get(var)));
	}
	
	public List<Matcher> accept(Statement s, VariableContext context) {
		List<Matcher> mlist = new ArrayList<Matcher>();
		// Copy the one w/o update
		mlist.add(Matcher.deepCopy(this));
		
		// If the match point is already -1, just ignore the current matcher
		if (this.matchpoint == -1) {
			return mlist;
		}
	
		// Else we will try to use the new match point to match the next statement
		ModInstruction instr = instrBindings.get(matchpoint).getFirst();
	
		Pair<List<Pair<String,Name>>, Boolean> result = 
				instr.tryMatch(s, var2type, context);
		
		// Check if variable bindings can match the existed result
		// TODO: the binding check is based on the name of the variables.
		if (result.getSecond()) {
			boolean varmatched = true;
			for (Pair<String, Name> p : result.getFirst()) {
				System.out.println("Matcher109==[] " + p.getFirst());
				//if (varMap.get(p.getFirst()).isPresent() && 
						//!varMap.get(p.getFirst()).get().getStr().equals(p.getSecond().getFullyQualifiedName())) {
				if (context.checkAliasRelation(p, varMap) == false) {
					varmatched = false;
				}
			}
			// The statement successfully matched the pattern 
			if (varmatched == true) {
				
				if (instr.isAssignmentPattern() || (s instanceof VariableDeclarationStatement)) {
					this.matchedToDeclaration.add(instr.assignmentLHSVariableName());
				}
				
				// Add the variable bindings into varMap
				for (Pair<String, Name> p : result.getFirst()) {
					if (varMap.containsKey(p.getFirst()) && !varMap.get(p.getFirst()).isPresent()) {
						varMap.put(p.getFirst(), Optional.of(new WrappedName(p.getSecond())));
					}
				}
				// This is the only position to update 
				this.instrBindings.get(this.matchpoint).setSecond(Optional.of(s));
				this.matchpoint = this.getNextMatchpoint();
				mlist.add(Matcher.deepCopy(this));
			}
		}
		return mlist;
	}
	
	private int getNextMatchpoint() {
		if (matchpoint == -1) 
			return -1;
		Integer i = matchpoint + 1;
		while (i < instrBindings.size()) {
			if (instrBindings.get(i).getFirst().isSrcPattern()) {
				return i;
			}
			i ++;
		}
		//no matchpoint can be found in the followed context
		return -1;
	}
	
	public Integer getMatchPoint() {
		return matchpoint;
	}
	
	/*
	 *  Deep copy a Matcher from an existing one
	 *  Note that the ModInstruction and the statement will not be copied!
	 */
	public static Matcher deepCopy(Matcher m) {
		Matcher matcher = new Matcher();
		matcher.matchpoint = m.matchpoint;
		// type bindings
		for (Entry<String, String> i : m.var2type.entrySet())
			matcher.var2type.put(i.getKey(), i.getValue());
		// binded metavariables
		for (Entry<String, Optional<WrappedName>> i : m.varMap.entrySet())
			matcher.varMap.put(i.getKey(), Optional.ofNullable(i.getValue().orElse(null)));
		// binded instructions
		for (Pair<ModInstruction, Optional<Statement>> p : m.instrBindings)
			matcher.instrBindings.add(
					new Pair<ModInstruction, Optional<Statement>>(
							p.getFirst(), 
							Optional.ofNullable(p.getSecond().orElse(null))));
		for (Entry<String, String> k : m.typeMap.entrySet()) {
			matcher.typeMap.put(k.getKey(), k.getValue());
		}
		// copy the methodName
		matcher.methodName = m.methodName;
		return matcher;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Matcher)) 
			return false;
		Matcher m = (Matcher) obj;
		if (!this.matchpoint.equals(m.matchpoint)) 
			return false;
		if (this.varMap.size() != m.varMap.size())
			return false;
		for (Entry<String, Optional<WrappedName>> i : m.varMap.entrySet()) {
			if (this.varMap.get(i.getKey()) == null 
					|| !this.varMap.get(i.getKey()).equals(i.getValue())) 
				return false;			
		}
		if (this.instrBindings.size() != m.instrBindings.size())
			return false;
		for (int i = 0; i < this.instrBindings.size(); i ++) {
			if (!this.instrBindings.get(i).equals(m.instrBindings.get(i)))
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String str = "(";
		boolean flag = false;
		for (Entry<String, Optional<WrappedName>> i : varMap.entrySet()) {
			if (flag) str += ", ";
			flag = true;
			str += i.getKey() + ":" + i.getValue().orElse(new WrappedName()).toDetailedString();
		}
		str += ") {\n";
		for (Pair<ModInstruction, Optional<Statement>> i : instrBindings) {
			str += "	" + i.getFirst() + ( i.getSecond().isPresent() ? (" <-> " + "[" + i.getSecond().get().getStartPosition() + "]" + i.getSecond().get()) : "" ) + "\n";
		}
		str += "\n}";
		return str;
	}
	
	// Check whether a variable is matched to a statement pattern
	public boolean markedAsDecl(String variableName) {
		if (this.matchedToDeclaration.contains(variableName))
			return true;
		else return false;
	}
	
	private boolean theFirstGenPointTaken = false;
	
	// Check whether a statement is the last source statement pattern.
	private boolean matchedToTheLastSrcStmtPattern(Statement s) {
		// TODO: I want to use the statement.equal() to check the equivalence, 
		//   ...but not sure if it is correct. (Probably not...as copySubtree is used everywhere...)
		// Thus probably I should use the start position to achieve the goal (what if it does't work? I don't know...)
		
		Pair<ModInstruction, Optional<Statement>> lastSrcPatternBinding = this.getTheLastSrcPatternBinding();
		if (!lastSrcPatternBinding.getSecond().isPresent()) {
			ErrorManager.error("Matcher@212", "The last pattern is not binded.");
		}
		Statement lastStmt = lastSrcPatternBinding.getSecond().get();
		
		if (s.getStartPosition() == -1 || lastStmt.getStartPosition() == -1) {
			ErrorManager.error("Matcher@196", "The statement was not in the source file.");
			return false;
		}
		
		if (!s.getClass().equals(lastStmt.getClass()))
			return false;

		if (s.getStartPosition() == lastStmt.getStartPosition())
			return true;
		else return false;
	}
	
	// Whether the statement is a genpoint statement
	public boolean isGenPointForTheMatcher(Statement s) {
		if (theFirstGenPointTaken) return false;
		if (matchedToTheLastSrcStmtPattern(s)) {
			return true;
		}
		return false;
	}
	
	public void genPointUsed() {
		this.theFirstGenPointTaken = true;
	} 
	
	// Check whether a statement is matched to some pattern in the model
	public boolean matchedToSrcStmtPattern(Statement s) {
		for (Pair<ModInstruction, Optional<Statement>> i : instrBindings) {
			if (i.getSecond().isPresent() == false)
				continue;
			if (!i.getSecond().get().getClass().equals(s.getClass()) || !i.getFirst().isSrcPattern())
				continue;
			if (i.getSecond().get().getStartPosition() == s.getStartPosition())
				return true;
		}
		return false;
	}
	
	// Check whether a statement is matched to some minus pattern in the model
	public boolean matchedToMinusStmtPattern(Statement s) {
		for (Pair<ModInstruction, Optional<Statement>> i : instrBindings) {
			if (i.getSecond().isPresent() == false)
				continue;
			if (!i.getSecond().get().getClass().equals(s.getClass()) || !i.getFirst().isMinus())
				continue;
			if (i.getSecond().get().getStartPosition() == s.getStartPosition())
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return null: if the last src pattern does not exist
	 * 		   pair: the last binding of the src pattern 
	 */
	private Pair<ModInstruction, Optional<Statement>> getTheLastSrcPatternBinding() {
		for (int i = instrBindings.size() - 1; i >= 0; i --) {
			Pair<ModInstruction, Optional<Statement>> ib = instrBindings.get(i);
			if (ib.getFirst().isSrcPattern()) {
				return ib;
			}
		}
		ErrorManager.error("Matcher@252", "This matcher contains no src pattern");
		return null;
	}
	
	/**
	 * Calculate the statements to be generated from the pattern
	 */
	public List<Statement> generatedNewStatements() {
		List<Statement> stmtList = new ArrayList<Statement>();
		for (Pair<ModInstruction, Optional<Statement>> mi : this.instrBindings) {
			if (mi.getFirst().isTgtPattern()) {
				stmtList.add(mi.getFirst().patternToStatement(this));
			}
		}
		return stmtList;
	}
	
	public void removeFromDecl(String variable) {
		this.matchedToDeclaration.remove(variable);
	}
	
	public void bindMetaVariableToNewName(String variable) {
		varMap.put(variable, Optional.of(new WrappedName(VariableGenerator.genVar())));
	}
	
	/**
	 * Get the reversed image of a metavariable map
	 * @param name the name of the image
	 * @return the name of the meta-variable
	 */
	public String getReversedVarMap(String name) {
		for (Entry<String, Optional<WrappedName>> i : this.varMap.entrySet()) {
			if (i.getValue().isPresent()) {
				if (i.getValue().get().getStr().equals(name)) {
					return i.getKey();
				}
			}
		}
		return null;
	}
	
	/**
	 * Given a string representing the name of the type, return the type it mapped to.
	 * @param oldType the name of the old type
	 * @return a ASTNode type representing the new type
	 */
	public Type getMappedType(String oldType) {
		if (!typeMap.containsKey(oldType))
			return null;
		String typeName = typeMap.get(oldType);
		AST tAST = AST.newAST(AST.JLS8);
		return tAST.newSimpleType(tAST.newSimpleName(typeName));
	}
	
	/*********** The following part is used for shifting operation ******************/
	
	// The high level block node and the low level block node
	private BlockSTreeNode highLevelBlockNode = null;
	private BlockSTreeNode lowLevelBlockNode = null;
	// The statements before the highLevelBlockNode
	private List<Statement> firstHalfStatementsToBeShifted = new ArrayList<Statement>();
	// The statements after the highLevelBlockNode
	private List<Statement> secondHalfStatementsToBeShifted = new ArrayList<Statement>();
	
	// An analyzer to query the statement dependency relation 
	private Analyzer analyzer;
	
	public List<Statement> getFirstHalfStatementsToBeShifted() {return this.firstHalfStatementsToBeShifted;}
	public List<Statement> getSecondHalfStatementsToBeShifted() {return this.secondHalfStatementsToBeShifted;}
	
	public boolean blockTreeLevelCheck(BlockSTreeNode root) {
		
		// We will first add all matched statements to the matchedStatements list.
		for (Pair<ModInstruction, Optional<Statement>> i : instrBindings) {
			if (i.getFirst().isSrcPattern()) {
				if (i.getSecond().isPresent()) {
					this.matchedStatements.add(i.getSecond().get());
				} else {
					ErrorManager.error("Matcher@356", "The instructions are not correctly binded.");
				}
			}
		}
		
		List<BlockSTreeNode> theirblocks = new ArrayList<BlockSTreeNode>();
		// First collect the block node info from the tree
		for (Pair<ModInstruction, Optional<Statement>> i : instrBindings) {
			if (i.getFirst().isSrcPattern()) {
				if (i.getSecond().isPresent()) {
					Optional<BlockSTreeNode> thisblk = root.retrieveDirectContainedBlock(i.getSecond().get());
					if (thisblk.isPresent())
						theirblocks.add(thisblk.get());
					else {
						ErrorManager.error("Matcher@332", "The matcher is no valid\n\t" + this.toString());
						return false;
					}
				}
			}
		}
		int lowLevel=-1, highLevel=-1;

		for (BlockSTreeNode bst : theirblocks) {
			// If there exists statements in three levels
			if ((lowLevel != -1 && highLevel != -1 && lowLevel != highLevel) 
					&& (bst.getLevel() != lowLevel && bst.getLevel() != highLevel)) {
				ErrorManager.error("Matcher@344", "The matcher is no valid\n\t" + this.toString());
				return false;
			}
			if (lowLevel == -1) {
				lowLevel = bst.getLevel();
				lowLevelBlockNode = bst;
			}
			else if (lowLevel > bst.getLevel()) {
				lowLevel = bst.getLevel();
				lowLevelBlockNode = bst;
			}
			if (highLevel == -1) {
				highLevel = bst.getLevel();
				highLevelBlockNode = bst;
			}
			else if (highLevel < bst.getLevel()) {
				highLevel = bst.getLevel();
				highLevelBlockNode = bst;
			}

			// If there exists two levels, there should be only two nodes holding all of the info.
			if ((lowLevel == bst.getLevel() && !lowLevelBlockNode.getId().equals(bst.getId()))
				|| (highLevel == bst.getLevel() && !highLevelBlockNode.getId().equals(bst.getId()))) {
				ErrorManager.error("Matcher@363", "The matcher is no valid\n\t" + this.toString());
				return false;
			}
		}
		// They are the same block
		if (highLevelBlockNode.getId() == lowLevelBlockNode.getId()) {
			return true;
		}
		if (highLevelBlockNode.getParent().getId() == lowLevelBlockNode.getId()) {
			// [Warning Checker] This banished the cross loop error
			if (highLevelBlockNode.getBlockType().equals(BlockSTreeNode.BlockType.IFELSEBLOCK))
				return true;
		}
		ErrorManager.error("Matcher@374", "The matcher is no valid\n\t" + this.toString());
		return false;
	}

	@SuppressWarnings("unchecked")
	public void collectStatementsToBeShifted() {
		System.out.println("DBG:  " + this.highLevelBlockNode + " ~ " + this.lowLevelBlockNode);
		if (this.highLevelBlockNode.getId().equals(this.lowLevelBlockNode.getId())) {
			// In this case, all statements are in the same block
			return;
		}
		
		//Else, 1) collect matched statements in the lowLevelBlockNode
		// Also collect statements to be deleted at lowBlock.
		for (Statement s : (List<Statement>)lowLevelBlockNode.getBlock().statements()) {
			if (this.matchedToSrcStmtPattern(s)) {
				if (s.getStartPosition() <= highLevelBlockNode.getBlock().getStartPosition())
					firstHalfStatementsToBeShifted.add(s);
				else if (s.getStartPosition() > highLevelBlockNode.getBlock().getStartPosition())
					secondHalfStatementsToBeShifted.add(s);
			}
		}
		
		// Then, 2) collect dependent statements in both sides
		boolean updated = true;
		while(updated) {
			updated = false;
			for (Statement s : (List<Statement>)lowLevelBlockNode.getBlock().statements()) {
				if (s.getStartPosition() <= highLevelBlockNode.getBlock().getStartPosition()) {
					if (inStmtSet(s, firstHalfStatementsToBeShifted))
						continue;
					if (dependentToTheFirstHalf(s)) {
						this.firstHalfStatementsToBeShifted.add(s);
						updated = true;
					}
				}
			}
		}
		updated = true;
		while(updated) {
			updated = false;
			for (int i = lowLevelBlockNode.getBlock().statements().size() - 1; i >= 0; i --) {
				Statement s = (Statement) lowLevelBlockNode.getBlock().statements().get(i);
				if (s.getStartPosition() >= highLevelBlockNode.getBlock().getStartPosition()) {
					if (inStmtSet(s, secondHalfStatementsToBeShifted)) //isMatchedStatement(s)
						continue;
					if (dependentToTheSecondHalf(s)) {
						this.secondHalfStatementsToBeShifted.add(s);
						updated = true;
					}
				}
			}
		}
	}
	
	private boolean inStmtSet(Statement s, List<Statement> dset) {
		for (Statement i : dset) {
			if (i.getStartPosition() == s.getStartPosition())
				return true;
		}
		return false;
	}
	
	// Check whether the given statement (in the first half) depends on the given statements
	private boolean dependentToTheFirstHalf(Statement s) {
		for (Statement i : this.matchedStatements) {
			if (dependentTo(s, i) && s.getStartPosition() >= i.getStartPosition())
				return true;
		}
		for (Statement i : this.firstHalfStatementsToBeShifted) {
			if (dependentTo(s, i) && s.getStartPosition() >= i.getStartPosition())
				return true;
		}
		return false;
	}
	
	// Check whether the given statement (in the second half) depends on the given statements
	// Should traverse the statements in the list in reverse order in order to collect all matched statements
	private boolean dependentToTheSecondHalf(Statement s) {
		for (Statement i : this.matchedStatements) {
			if (dependentTo(i, s) && s.getStartPosition() <= i.getStartPosition())
				return true;
		}
		for (Statement i : this.secondHalfStatementsToBeShifted) {
			if (dependentTo(i, s) && s.getStartPosition() <= i.getStartPosition())
				return true;
		}
		return false;
	}
	
	// Check whether a statement is matched by some pattern
	private boolean isMatchedStatement(Statement s) {
		for (Statement i : this.matchedStatements) {
			if (i.getStartPosition() == s.getStartPosition())
				return true;
		}
		return false;
	}
	
	// Check whether the statement s depends on the statement t
	private boolean dependentTo(Statement s, Statement t) {
		// TODO: test if it is right
		boolean result = analyzer.analyze(this.methodName, s, t);
		
		if (DebugSwitcher.dependencyDebug) {
			if ((s instanceof ExpressionStatement || s instanceof VariableDeclarationStatement) &&
					(t instanceof ExpressionStatement || t instanceof VariableDeclarationStatement)) {
				if (result) {
					System.out.println(s + " <===> " + t);
				} else {
					System.out.println(s + " <=/=> " + t);
				}
			}
		}
		return result;
	}
	
	public BlockSTreeNode getLowLevelBlock() {
		return this.lowLevelBlockNode;
	}
	
	public BlockSTreeNode getHighLevelBlock() {
		return this.highLevelBlockNode;
	}

	public void collectStatementsToBeDeletedInBlock(BlockSTreeNode blk) {
		if (this.lowLevelBlockNode.getId() == blk.getId()) {
			for (Statement s : this.firstHalfStatementsToBeShifted) {
				blk.addToDeleteSet(s);
			}
			for (Statement s: this.secondHalfStatementsToBeShifted) {
				blk.addToDeleteSet(s);
			}
		}
		if (this.highLevelBlockNode.getId() == blk.getId()) {
			for (Statement s : this.matchedStatements) {
				if (this.matchedToMinusStmtPattern(s)) {
					blk.addToDeleteSet(s);
				}
			}
		}
	}

	public void addAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
	
}
