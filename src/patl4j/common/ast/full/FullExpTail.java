package patl4j.common.ast.full;

import java.util.List;
import java.util.Optional;

public class FullExpTail {
	private HeadofTail first; // this should be something like ".f" ".m(a,b)"
	private Optional<FullExpTail> moreTail;
	
	public FullExpTail() {}
	
	public FullExpTail(HeadofTail hd, FullExpTail tail) {
		this.first = hd;
		this.moreTail = Optional.ofNullable(tail);
	}
	
	public void set(HeadofTail hd, FullExpTail tail) {
		this.first = hd;
		this.moreTail = Optional.ofNullable(tail);
	}

	
	// Need to be changed!
	public FullExpression toExpression(FullExpression headExp) {
		FullExpression newHeadExp = null;
		if (this.first instanceof MethodBody) {
			MethodBody mtd = (MethodBody) this.first;
			newHeadExp = new FullMethodInvk(headExp, mtd.getMethod(), mtd.getArgs());
		} else if (this.first instanceof FieldBody) {
			FieldBody fb = (FieldBody) this.first;
			newHeadExp = new FullFieldAccess(headExp, fb.getField());
		}
		if (moreTail.isPresent()) {
			return moreTail.get().toExpression(newHeadExp);
		} else {
			return newHeadExp;
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "." + first.toString();
		if (moreTail.isPresent()) 
			str += moreTail.get().toString();
		return str;
	}
	
	public interface HeadofTail {

	}
	
	public class MethodBody implements HeadofTail {
		private String method;

		private List<FullExpression> args;
		public MethodBody(String method, List<FullExpression> args) {
			this.method = method;
			this.args = args;
		}
		
		public String getMethod() {
			return method;
		}
		
		public List<FullExpression> getArgs() {
			return args;
		}
		
		@Override
		public String toString() {
			String str = "";
			str += this.method + "(";
			boolean flag = false;
			for (FullExpression i : args) {
				if (flag)
					str += ", ";
				str += i.toString();
				flag = true;
			}
			str += ")";
			return str;
		}
	}

	public class FieldBody implements HeadofTail {
		private String field;
		
		public FieldBody(String name) {
			this.field = name;
		}

		public String getField() {
			return field;
		}
		
		@Override
		public String toString() {
			return this.field;
		}
		
	}
	
}
