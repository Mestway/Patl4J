package patl4j.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.patl.ast.ModInstruction;
import patl4j.patl.ast.Rule;
import patl4j.patl.ast.VarDecl;
import patl4j.util.Pair;

public class Matcher {
	
	// Map a variable to its type in the old context.
	private Map<String, String> var2type = new HashMap<String, String>();
	// Map a variable to its binded type.
	private Map<String, Optional<WrappedName>> varMap = new HashMap<String, Optional<WrappedName>>();
	
	private List<Pair<ModInstruction, Optional<Statement>>> 
		instrBindings = new ArrayList<Pair<ModInstruction, Optional<Statement>>>();
	
	// If matchpoint == -1, then there exists no matchpoint
	private Integer matchpoint = 0;

	private Matcher() {};
	
	public Matcher(Rule r) {
		for (VarDecl d : r.getDecls()) {
			var2type.put(d.getName(), d.getOldType());
			varMap.put(d.getName(), Optional.ofNullable(null));
		}
		for (ModInstruction i : r.getInstrs()) {
			instrBindings.add(
					new Pair<ModInstruction, Optional<Statement>>(
							i, Optional.ofNullable(null)));
		}
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
	
}
