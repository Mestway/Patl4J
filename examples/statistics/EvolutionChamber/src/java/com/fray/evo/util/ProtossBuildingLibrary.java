/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

/**
 * The singleton instance contains all Protoss Buildings
 * 
 */
public final class ProtossBuildingLibrary extends Library<Building> {

	public static final Building Nexus = ProtossLibrary.Nexus;
	public static final Building Pylon = ProtossLibrary.Pylon;
	public static final Building Assimilator = ProtossLibrary.Assimilator;
	public static final Building Gateway = ProtossLibrary.Gateway;
	public static final Building Forge = ProtossLibrary.Forge;
	public static final Building PhotonCannon = ProtossLibrary.PhotonCannon;
	public static final Building CyberneticsCore = ProtossLibrary.CyberneticsCore;
	public static final Building TwilightCouncil = ProtossLibrary.TwilightCouncil;
	public static final Building RoboticsFacility = ProtossLibrary.RoboticsFacility;
	public static final Building Stargate = ProtossLibrary.Stargate;
	public static final Building TemplarArchives = ProtossLibrary.TemplarArchives;
	public static final Building DarkShrine = ProtossLibrary.DarkShrine;
	public static final Building RoboticsBay = ProtossLibrary.RoboticsBay;
	public static final Building FleetBeacon = ProtossLibrary.FleetBeacon;
	public static final Building WarpGate = ProtossLibrary.WarpGate;

    private ProtossBuildingLibrary() {
		libraryList.add(Nexus);
		libraryList.add(Pylon);
		libraryList.add(Assimilator);
		libraryList.add(Gateway);
		libraryList.add(Forge);
		libraryList.add(PhotonCannon);
		libraryList.add(CyberneticsCore);
		libraryList.add(TwilightCouncil);
		libraryList.add(RoboticsFacility);
		libraryList.add(Stargate);
		libraryList.add(TemplarArchives);
		libraryList.add(DarkShrine);
		libraryList.add(RoboticsBay);
		libraryList.add(FleetBeacon);
		libraryList.add(WarpGate);
        
        for (Building building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
 // has to be at the end of the class to keep the class initialization in order
    private static final ProtossBuildingLibrary instance = new ProtossBuildingLibrary();
    public static final ProtossBuildingLibrary getInstance() {
        return instance;
    }
}
