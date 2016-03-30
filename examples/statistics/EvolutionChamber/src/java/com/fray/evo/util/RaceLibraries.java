/**
 * 
 */
package com.fray.evo.util;

/**
 * @author "Beat Durrer"
 *
 * Contains all libraries
 */
public final class RaceLibraries {
	
	public static final ZergBuildingLibrary zergBuildingLibrary = ZergBuildingLibrary.getInstance();
	public static final ZergUpgradeLibrary zergUpgradeLibrary = ZergUpgradeLibrary.getInstance();
	public static final ZergUnitLibrary zergUnitLibrary = ZergUnitLibrary.getInstance();
	public static final TerranBuildingLibrary terranBuildingLibrary = TerranBuildingLibrary.getInstance();
	public static final TerranUpgradeLibrary terranUpgradeLibrary = TerranUpgradeLibrary.getInstance();
	public static final TerranUnitLibrary terranUnitLibrary = TerranUnitLibrary.getInstance();
	public static final ProtossBuildingLibrary protossBuildingLibrary = ProtossBuildingLibrary.getInstance();
	public static final ProtossUpgradeLibrary protossUpgradeLibrary = ProtossUpgradeLibrary.getInstance();
	public static final ProtossUnitLibrary protossUnitLibrary = ProtossUnitLibrary.getInstance();

	public static Library<Building> getBuildingLibrary(Race race){
		switch(race){
			case Zerg:
				return zergBuildingLibrary;
			case Terran:
				return terranBuildingLibrary;
			case Protoss:
				return protossBuildingLibrary;
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
	
	public static Library<Unit> getUnitLibrary(Race race){
		switch(race){
			case Zerg:
				return zergUnitLibrary;
			case Terran:
				return terranUnitLibrary;
			case Protoss:
				return protossUnitLibrary;
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
	
	public static Library<Upgrade> getUpgradeLibrary(Race race){
		switch(race){
			case Zerg:
				return zergUpgradeLibrary;
			case Terran:
				return terranUpgradeLibrary;
			case Protoss:
				return protossUpgradeLibrary;
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
}
