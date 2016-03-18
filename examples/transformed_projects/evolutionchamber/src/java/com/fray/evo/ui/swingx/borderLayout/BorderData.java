package com.fray.evo.ui.swingx.borderLayout;
public class BorderData {

	/** North */
	public static final BorderData NORTH = new BorderData("North");

	/** South */
	public static final BorderData SOUTH = new BorderData("South");

	/** East */
	public static final BorderData EAST = new BorderData("East");

	/** West */
	public static final BorderData WEST = new BorderData("West");

	/** Center */
	public static final BorderData CENTER = new BorderData("Center");

	private String name;

	private BorderData(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
