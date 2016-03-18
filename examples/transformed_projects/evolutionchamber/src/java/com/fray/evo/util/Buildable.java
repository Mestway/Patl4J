package com.fray.evo.util;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Buildable implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final String name;
	protected final int minerals;
	protected final int gas;
	protected final double time;
	protected final Buildable consumes;
	protected final ArrayList<Buildable> requirements;
	protected final int id;
    
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
    public int hashCode(){
        return name.hashCode();
    }

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
