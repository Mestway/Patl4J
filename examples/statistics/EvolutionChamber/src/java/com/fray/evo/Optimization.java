package com.fray.evo;

public final class Optimization {
	private static Integer[] integers = new Integer[10000];
	
	public static Integer inte(int i) {
		if(i < 0 || i > integers.length)
			return Integer.valueOf(i);
		if(integers[i] == null)
			integers[i] = Integer.valueOf(i);
		return integers[i];
	}
}
