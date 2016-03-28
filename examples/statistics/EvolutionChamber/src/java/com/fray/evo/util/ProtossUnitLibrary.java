/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 * The singleton instance contains all Protoss Units
 * 
 */
public final class ProtossUnitLibrary extends Library<Unit> {
    public static final Unit Probe = ProtossLibrary.Probe;
    public static final Unit Zealot = ProtossLibrary.Zealot;
    public static final Unit Stalker = ProtossLibrary.Stalker;
    public static final Unit Sentry = ProtossLibrary.Sentry;
    public static final Unit Observer = ProtossLibrary.Observer;
    public static final Unit Immortal = ProtossLibrary.Immortal;
    public static final Unit WarpPrism = ProtossLibrary.WarpPrism;
    public static final Unit Colossus = ProtossLibrary.Colossus;
    public static final Unit VoidRay = ProtossLibrary.VoidRay;
    public static final Unit HighTemplar = ProtossLibrary.HighTemplar;
    public static final Unit DarkTemplar = ProtossLibrary.DarkTemplar;
    public static final Unit Archon = ProtossLibrary.Archon;
    public static final Unit Carrier = ProtossLibrary.Carrier;
    public static final Unit Mothership = ProtossLibrary.Mothership;
    public static final Unit Interceptor = ProtossLibrary.Interceptor;

    /**
     * initializes the lists
     */
    private ProtossUnitLibrary() {
        // execute parent constructor to init the lists first
        super();
		libraryList.add(Probe);
		libraryList.add(Zealot);
		libraryList.add(Stalker);
		libraryList.add(Sentry);
		libraryList.add(Observer);
		libraryList.add(Immortal);
		libraryList.add(WarpPrism);
		libraryList.add(Colossus);
		libraryList.add(VoidRay);
		libraryList.add(HighTemplar);
		libraryList.add(DarkTemplar);
		libraryList.add(Archon);
		libraryList.add(Carrier);
		libraryList.add(Mothership);
		libraryList.add(Interceptor);

        for (Unit unit : libraryList) {
            idToItemMap.put(unit.getId(), unit);
        }
    }

    // has to be at the end of the class to keep the class initialization in order
    private static final ProtossUnitLibrary instance = new ProtossUnitLibrary();
    public static final ProtossUnitLibrary getInstance() {
        return instance;
    }
}
