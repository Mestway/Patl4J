/**
 * 
 */
package com.fray.evo.util;

import java.io.Serializable;

/**
 * @author "Beat Durrer"
 *
 */
public enum Race implements Serializable {
	Zerg("Zerg"), Terran("Terran"), Protoss("Protoss");
	
	private String name;
	
	/**
     * @param aName
     */
    private Race(String aName) {
	    name = aName;
    }

    public String getName() {
	    return name;
    }
}
