package patl4j.core.transformer.phases;

import org.eclipse.jdt.core.dom.Block;

import patl4j.matcher.MatcherSet;

public class Shifter {
	
	private Block body;
	private MatcherSet matchers;
	
	public Shifter(Block body, MatcherSet matchers) {
		this.body = body;
		this.matchers = matchers;
	}

	public Block shiftCode() {
		// TODO Auto-generated method stub
		// Simply return the body without shifting operation
		return body;
	}
}
