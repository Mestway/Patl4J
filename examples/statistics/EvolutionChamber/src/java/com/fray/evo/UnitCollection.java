/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.fray.evo.util.Race;
import com.fray.evo.util.RaceLibraries;
import com.fray.evo.util.Unit;

/**
 * Deperecated, use BuildableCollection instead
 * 
 * @author Cyrik
 */
@Deprecated
public final class UnitCollection extends BuildableCollection<Unit> implements Serializable {

	private static final long serialVersionUID = 4544549226705001596L;

	public UnitCollection(Collection<Unit> units, Race race) {
		this(units.size(), race);
	}

	public UnitCollection(int size, Race race) {
		super(size, race);
	}

	public HashMap<Unit, Integer> toHashMap() {
		return super.toHashMap(RaceLibraries.getUnitLibrary(race));
	}

	@Override
	public UnitCollection clone() {
		UnitCollection result = new UnitCollection(arr.length, race);
		for (int i = 0; i < arr.length; i++) {
			result.putById(i, arr[i]);
		}
		return result;
	}
}
