package com.fray.evo.util;

import java.util.ArrayList;

public final class Building extends Buildable {
    
	private static final long serialVersionUID = 1L;

	public Building(int id, String name, int minerals, int gas, double time, Buildable consumes, ArrayList<Buildable> requirements) {
        super(name,minerals,gas,time,consumes,requirements,id);
    }
    
}
