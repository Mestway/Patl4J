package patl4j.java.normalizer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.ErrorManager;

public class WrappedStatement {
	
	private Statement statement;
	boolean generatedBlock = false;
	
	public WrappedStatement(Statement _stmt) {
		this.statement = _stmt;
	}
	
	public void setGenBlock() {
		generatedBlock = true;
	}
	
	public boolean isGenBlock() {
		return generatedBlock;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	@SuppressWarnings("unchecked")
	public List<Statement> decomposeBlock() {
		List<Statement> ls = new ArrayList<Statement>();
		if (generatedBlock == false) {
			ls.add(statement);
			return ls;
		} else {
			if (!(statement instanceof Block)) {
				ErrorManager.error("This is definately a big disaster");
				return ls;
			}
			for (Statement i : (List<Statement>)(((Block) statement).statements())) {
				ls.add(i);
			}
			return ls;
		}
	}
}
