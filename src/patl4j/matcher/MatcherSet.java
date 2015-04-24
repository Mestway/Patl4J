package patl4j.matcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Statement;

import patl4j.patl.ast.Rule;

public class MatcherSet {
	
	List<Matcher> matchers = new ArrayList<Matcher>();
	
	public MatcherSet(List<Rule> rules) {
		for (Rule r : rules) {
			matchers.add(new Matcher(r));
		}
	}
	
	// Update the matcher collection set with a Java Statement,
	// which will then shift the responsibility to matchers
	public MatcherSet accept(Statement s) {
		List<Matcher> collection = new ArrayList<Matcher>();
		for (Matcher i : matchers) {
			// After update, both the updated and the un-updated matcher will be genearted
			List<Matcher> updated = i.accept(s);
			for (Matcher j : updated) {
				collection.add(j);
			}
		}
		matchers = collection;
		return this;
	}
	
	// Clear all unfinished matcher
	public MatcherSet clear() {
		List<Matcher> cleared = new ArrayList<Matcher>();
		for (Matcher m : matchers) {
			if (m.getMatchPoint().equals(-1)) {
				cleared.add(m);
			}
		}
		this.matchers = cleared;
		return this;
	}
	
	// Warning: might cause some order problem
	// TODO: we might be able to improve its performance using an alternative implementation
	public static MatcherSet merge(MatcherSet u, MatcherSet v) {
		for (Matcher i : v.matchers) {
			boolean flag = false;
			for (Matcher j : u.matchers) {
				if (j.equals(i)) { 
					flag = true;
					break;
				}
			}
			if (flag == false) {
				u.matchers.add(i);
			}
		}
		return u;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Matcher i : matchers) {
			str += i.toString() + "\n\n";
		}
		return str;
	}
	
}
