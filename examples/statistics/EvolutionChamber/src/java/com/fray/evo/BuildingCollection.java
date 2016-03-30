/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.fray.evo.util.Building;
import com.fray.evo.util.Race;
import com.fray.evo.util.RaceLibraries;

/**
 * Deperecated, use BuildableCollection instead
 * 
 * @author Cyrik
 */
@Deprecated
public final class BuildingCollection extends BuildableCollection<Building> implements Serializable {

	private static final long serialVersionUID = 7576950287259854976L;

	public BuildingCollection(Collection<Building> buildings, Race race) {
		this(buildings.size(), race);
	}

	public BuildingCollection(int size, Race race) {
		super(size, race);
	}

	public HashMap<Building, Integer> toHashMap() {
		return super.toHashMap(RaceLibraries.getBuildingLibrary(race));
	}

	@Override
	public BuildingCollection clone() {
		BuildingCollection result = new BuildingCollection(arr.length, race);
		for (int i = 0; i < arr.length; i++) {
			result.putById(i, arr[i]);
		}
		return result;
	}
}
