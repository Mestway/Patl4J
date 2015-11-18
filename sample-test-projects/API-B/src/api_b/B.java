package api_b;

public class B {
	
	IntPair p;
	
	public B(Integer l, Integer r) {
		p = new IntPair(l, r);
	}
	
	public B() {
		p = new IntPair(0, 100);
	}
	
	public IntPair getP() {
		return p;
	}
}