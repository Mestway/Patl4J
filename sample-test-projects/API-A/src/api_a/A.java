package api_a;

public class A {
	
	Integer l = 0;
	Integer r = 100;
	
	public A() {}
	
	public void set(Integer l, Integer r) {
		this.l = l;
		this.r = r;
	}
	
	public Integer getL() {
		return l;
	}
	
	public Integer getR() {
		return r;
	}
	
	public void lAdd() {
		l ++;
	}
	
	public void rDec() {
		r --;
	}
	
	public void print() {
		System.out.println(l + " " + r);
	}
	
}
