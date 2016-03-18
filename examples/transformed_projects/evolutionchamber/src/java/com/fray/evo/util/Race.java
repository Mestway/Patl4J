package com.fray.evo.util;

import java.io.Serializable;

public enum Race implements Serializable {
	Zerg("Zerg"), Terran("Terran"), Protoss("Protoss");
	
	private String name;
	
    private Race(String aName) {
	    name = aName;
    }

    public String getName() {
	    return name;
    }
}
