package patl4j.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import patl4j.patl.ast.ModInstruction;
import patl4j.patl.ast.Rule;
import patl4j.patl.ast.VarDecl;
import patl4j.util.ErrorManager;
import patl4j.util.Pair;

public class Matcher {
	
	// Map a variable to its type in the old context.
	private Map<String, String> var2type = new HashMap<String, String>();
	// Map a variable to its type in the new context.
	private Map<String, String> var2newtype = new HashMap<String, String>();
	// Map a variable to its binded type.
	private Map<String, Optional<WrappedName>> varMap = new HashMap<String, Optional<WrappedName>>();

	// Some assignment statements are mapped to variable declaration statements, 
	// and this set keeps which variable are in such assignment.
	private Set<String> matchedToDeclaration = new HashSet<String>();
	
	// The binding between mod instruction patterns and java statements, including both minus, m and addition
	private List<Pair<ModInstruction, Optional<Statement>>> 
		instrBindings = new ArrayList<Pair<ModInstruction, Optional<Statement>>>();
	
	// If matchpoint == -1, then there exists no matchpoint
	private Integer matchpoint = 0;

	private Matcher() {};
	
	public Matcher(Rule r) {
		for (VarDecl d : r.getDecls()) {
			var2type.put(d.getName(), d.getOldType());
			varMap.put(d.getName(), Optional.ofNullable(null));
			var2newtype.put(d.getName(), d.getNewType());
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
	
	public List<Matcher> accept(Statement s) {
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
				instr.tryMatch(s, var2type);
		
		// Check if variable bindings can match the existed result
		// TODO: the binding check is based on the name of the variables.
		if (result.getSecond()) {
			boolean varmatched = true;
			for (Pair<String, Name> p : result.getFirst()) {
				if (varMap.get(p.getFirst()).isPresent() && 
						!varMap.get(p.getFirst()).get().getStr().equals(p.getSecond().getFullyQualifiedName())) {
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
					if (!varMap.get(p.getFirst()).isPresent()) {
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
	
	// Check whether a statement is the last source statement pattern.
	public boolean matchedToTheLastSrcStmtPattern(Statement s) {
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
	
	// Check whether a statement is matched to some pattern in the model
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
	
}
