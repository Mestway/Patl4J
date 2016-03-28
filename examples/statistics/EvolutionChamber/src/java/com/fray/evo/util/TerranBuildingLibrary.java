/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

/**
 * The singleton instance contains all Terran Buildings
 * 
 */
public final class TerranBuildingLibrary extends Library<Building> {

	public static final Building CommandCenter = TerranLibrary.CommandCenter;
	public static final Building SupplyDepot = TerranLibrary.SupplyDepot;
	public static final Building Refinery = TerranLibrary.Refinery;
	public static final Building Barracks = TerranLibrary.Barracks;
	public static final Building OrbitalCommand = TerranLibrary.OrbitalCommand;
	public static final Building EngineeringBay = TerranLibrary.EngineeringBay;
	public static final Building PlanetaryFortress = TerranLibrary.PlanetaryFortress;
	public static final Building Bunker = TerranLibrary.Bunker;
	public static final Building MissleTurret = TerranLibrary.MissleTurret;
	public static final Building SensorTower = TerranLibrary.SensorTower;
	public static final Building Factory = TerranLibrary.Factory;
	public static final Building GhostAcademy = TerranLibrary.GhostAcademy;
	public static final Building Armory = TerranLibrary.Armory;
	public static final Building Starport = TerranLibrary.Starport;
	public static final Building FusionCore = TerranLibrary.FusionCore;
	public static final Building BarracksTechLab = TerranLibrary.BarracksTechLab;
	public static final Building BarracksReactor = TerranLibrary.BarracksReactor;
	public static final Building FactoryTechLab = TerranLibrary.FactoryTechLab;
	public static final Building FactoryReactor = TerranLibrary.FactoryReactor;
	public static final Building StarportTechLab = TerranLibrary.StarportTechLab;
	public static final Building StarportReactor = TerranLibrary.StarportReactor;

    private TerranBuildingLibrary() {
		libraryList.add(CommandCenter);
		libraryList.add(SupplyDepot);
		libraryList.add(Refinery);
		libraryList.add(Barracks);
		libraryList.add(OrbitalCommand);
		libraryList.add(EngineeringBay);
		libraryList.add(PlanetaryFortress);
		libraryList.add(Bunker);
		libraryList.add(MissleTurret);
		libraryList.add(SensorTower);
		libraryList.add(Factory);
		libraryList.add(GhostAcademy);
		libraryList.add(Armory);
		libraryList.add(Starport);
		libraryList.add(FusionCore);
		libraryList.add(BarracksTechLab);
		libraryList.add(BarracksReactor);
		libraryList.add(FactoryTechLab);
		libraryList.add(FactoryReactor);
		libraryList.add(StarportTechLab);
		libraryList.add(StarportReactor);
        
        for (Building building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
 // has to be at the end of the class to keep the class initialization in order
    private static final TerranBuildingLibrary instance = new TerranBuildingLibrary();
    public static final TerranBuildingLibrary getInstance() {
        return instance;
    }
}
