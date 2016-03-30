package com.fray.evo.util;

import java.util.ArrayList;

public final class EcUtil
{
	public static String toString(ArrayList<String> strings)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.size(); ++i) {
			sb.append(strings.get(i));
			sb.append('\n');
		}
		return sb.toString();
	}
}
