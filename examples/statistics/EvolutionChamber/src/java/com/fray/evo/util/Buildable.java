/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Provides the basic information a item like a Unit,Upgrade or Building requires.
 * Items that do not consume anything, the consumes may be set to null. 
 * If the consumable is not null, the consumed costs and time are considered for the full-values (FullCost,FullGas,FullTime)
 * 
 * @author Cyrik, bdurrer
 */
public abstract class Buildable implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final String name;
	protected final int minerals;
	protected final int gas;
	protected final double time;
	protected final Buildable consumes;
	protected final ArrayList<Buildable> requirements;
	protected final int id;
    
	/**
	 * @param name
	 * @param minerals
	 * @param gas
	 * @param time
	 * @param consumes
	 * @param requierments
	 * @param id the ID. Is currently only unique for its sort (Unit/Upgrade/Building)
	 */
    public Buildable(String name, int minerals, int gas, double time, Buildable consumes, ArrayList<Buildable> requirements,int id) {
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
        this.consumes = consumes;
        this.requirements = requirements;
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
	
    public int getMinerals() {
        return minerals;
    }

    public int getGas() {
        return gas;
    }

    public double getTime() {
        return time;
    }

    /**
     * The i18n property name of the unit/upgrade/building.
     * @return
     */
    public String getName() {
        return name;
    }

    public int getFullMinerals() {
        if (consumes == null) {
            return minerals;
        } else {

            return consumes.getFullMinerals() + minerals;
        }
    }

    public int getFullGas() {
        if (consumes == null) {
            return gas;
        } else {
            return consumes.getFullGas() + gas;
        }

    }

    
    public double getFullTime() {
        if (consumes == null) {
            return time;
        } else {
            return consumes.getFullTime() + time;
        }
    }

    public Buildable getConsumes() {
        return consumes;
    }

    public ArrayList<Buildable> getRequirement() {
        return requirements;
    }

    /**
     * @return the hashCode built out of the item's name
     */
    public int hashCode(){
        return name.hashCode();
    }


    /**
     * A Buildable A is the same as B if 				<br/>
     * a) it is of the same subclass (runtime class)	<br/>
     * b) it has the same name
     * 
     * @param obj object to compare
     * @return true if both are considered as equals
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Buildable other = (Buildable) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
