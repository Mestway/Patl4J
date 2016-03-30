/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

/**
 * The singleton instance contains all Zerg Buildings
 * @author Cyrik
 * 
 */
public final class ZergBuildingLibrary extends Library<Building> {

    public static final Building Hatchery = ZergLibrary.Hatchery;
    public static final Building Extractor = ZergLibrary.Extractor;
    public static final Building SpawningPool = ZergLibrary.SpawningPool;
    public static final Building Lair = ZergLibrary.Lair;
    public static final Building InfestationPit = ZergLibrary.InfestationPit;
    public static final Building Hive = ZergLibrary.Hive;
    public static final Building RoachWarren = ZergLibrary.RoachWarren;
    public static final Building HydraliskDen = ZergLibrary.HydraliskDen;
    public static final Building BanelingNest = ZergLibrary.BanelingNest;
    public static final Building Spire = ZergLibrary.Spire;
    public static final Building GreaterSpire = ZergLibrary.GreaterSpire;
    public static final Building UltraliskCavern = ZergLibrary.UltraliskCavern;
    public static final Building EvolutionChamber = ZergLibrary.EvolutionChamber;
    public static final Building NydusNetwork = ZergLibrary.NydusNetwork;
    public static final Building NydusWorm = ZergLibrary.NydusWorm;
    public static final Building SpineCrawler = ZergLibrary.SpineCrawler;
    public static final Building SporeCrawler = ZergLibrary.SporeCrawler;

    private ZergBuildingLibrary() {
        libraryList.add(Hatchery);
        libraryList.add(Extractor);
        libraryList.add(Hive);
        libraryList.add(ZergLibrary.Lair);
        libraryList.add(SpawningPool);
        libraryList.add(RoachWarren);
        libraryList.add(HydraliskDen);
        libraryList.add(BanelingNest);
        libraryList.add(GreaterSpire);
        libraryList.add(UltraliskCavern);
        libraryList.add(Spire);
        libraryList.add(InfestationPit);
        libraryList.add(EvolutionChamber);
        libraryList.add(NydusNetwork);
        libraryList.add(NydusWorm);
        libraryList.add(SpineCrawler);
        libraryList.add(SporeCrawler);
        
        for (Building building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
 // has to be at the end of the class to keep the class initialization in order
    private static final ZergBuildingLibrary instance = new ZergBuildingLibrary();
    public static final ZergBuildingLibrary getInstance() {
        return instance;
    }
}
