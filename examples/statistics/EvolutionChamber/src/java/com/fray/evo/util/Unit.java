/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;

/**
 *
 * @author Cyrik
 */
public final class Unit extends Buildable {

	private static final long serialVersionUID = 1L;
	private final double supply;
    private final Building builtFrom;
    
    public Unit(int id, String name, int minerals, int gas, double supply, double time, Buildable consumes, Building builtFrom, ArrayList<Buildable> requirements) {
        super(name, minerals, gas, time, consumes, requirements, id);

        this.supply = supply;
        this.builtFrom = builtFrom;
    }

    public double getSupply() {
        return supply;
    }
    public Building getBuiltFrom(){
        return builtFrom;
    }
}
