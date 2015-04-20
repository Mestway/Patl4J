package patl4j.patl.ast.full;

import java.util.ArrayList;
import java.util.List;

public class FullAddExpression implements FullExpression {
	List<String> operands = new ArrayList<String>();
	List<Boolean> operators = new ArrayList<Boolean>(); 
	
	public FullAddExpression(List<String> operands, List<Boolean> operators) {
		this.operands = operands;
		this.operators = operators;
	}
	
	public List<String> getOperands() {
		return operands;
	}
	
	public List<Boolean> getOperators() {
		return operators;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < operands.size(); i ++) {
			if (i > 0)
				str += (operators.get(i-1)?"+":"-") + operands.get(i);
			else 
				str += operands.get(i);
		}
		return str;
	}
	
}
