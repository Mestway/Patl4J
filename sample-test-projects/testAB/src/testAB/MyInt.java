package testAB;

public class MyInt {
	public int x;
	public YourInt z = new YourInt();
	
	public MyInt(int v) {
		x = v;
		z.y = v;
	}
	
	public YourInt getZ() {
		return z;
	}
}

class YourInt {
	
	public int y;
}