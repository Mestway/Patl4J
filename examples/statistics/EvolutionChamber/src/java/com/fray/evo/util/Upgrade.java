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
public final class Upgrade extends Buildable {
	private static final long serialVersionUID = 1L;
	private final Building builtFrom;

    public Upgrade(int id, String name,int minerals, int gas, double time, Building builtIn, ArrayList<Buildable> requirements){
        super(name,minerals,gas,time,null,requirements, id);

        this.builtFrom = builtIn;
    }


    public Building getBuiltIn(){
        return builtFrom;
    }
}
