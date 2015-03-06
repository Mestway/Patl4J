package patl4j.patl.ast;

public class BinaryOperation implements RHSPattern {
	
	String first;
	String second;
	// For simplicity consideration, just make it one with 
	String operator;
	
	public BinaryOperation(String op1, String op2, String operator) {
		this.first = op1;
		this.second = op2;
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return this.first + " " + operator + " " + this.second;
	} 
	
}
