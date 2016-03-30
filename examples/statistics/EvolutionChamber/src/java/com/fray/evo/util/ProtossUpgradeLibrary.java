/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 * The singleton instance contains all Protoss Upgrades
 * 
 */
public final class ProtossUpgradeLibrary extends Library<Upgrade>  {
    public static final Upgrade Blink = ProtossLibrary.Blink;
    public static final Upgrade Charge = ProtossLibrary.Charge;
    public static final Upgrade ExtendedThermalLance = ProtossLibrary.ExtendedThermalLance;
    public static final Upgrade FluxVanes = ProtossLibrary.FluxVanes;
    public static final Upgrade GraviticBoosters = ProtossLibrary.GraviticBoosters;
    public static final Upgrade GraviticDrive = ProtossLibrary.GraviticDrive;
    public static final Upgrade GravitonCatapult = ProtossLibrary.GravitonCatapult;
    public static final Upgrade Hallucination = ProtossLibrary.Hallucination;
    public static final Upgrade KhaydarinAmulet = ProtossLibrary.KhaydarinAmulet;
    public static final Upgrade PsionicStorm = ProtossLibrary.PsionicStorm;
    public static final Upgrade WarpGateUpgrade = ProtossLibrary.WarpGateUpgrade;
    public static final Upgrade AirArmor1 = ProtossLibrary.AirArmor1;
    public static final Upgrade AirArmor2 = ProtossLibrary.AirArmor2;
    public static final Upgrade AirArmor3 = ProtossLibrary.AirArmor3;
    public static final Upgrade AirWeapons1 = ProtossLibrary.AirWeapons1;
    public static final Upgrade AirWeapons2 = ProtossLibrary.AirWeapons2;
    public static final Upgrade AirWeapons3 = ProtossLibrary.AirWeapons3;
    public static final Upgrade GroundArmor1 = ProtossLibrary.GroundArmor1;
    public static final Upgrade GroundArmor2 = ProtossLibrary.GroundArmor2;
    public static final Upgrade GroundArmor3 = ProtossLibrary.GroundArmor3;
    public static final Upgrade GroundWeapons1 = ProtossLibrary.GroundWeapons1;
    public static final Upgrade GroundWeapons2 = ProtossLibrary.GroundWeapons2;
    public static final Upgrade GroundWeapons3 = ProtossLibrary.GroundWeapons3;
    public static final Upgrade Shields1 = ProtossLibrary.Shields1;
    public static final Upgrade Shields2 = ProtossLibrary.Shields2;
    public static final Upgrade Shields3 = ProtossLibrary.Shields3;

    private ProtossUpgradeLibrary() {
        // call parent constructor to init the lists first
        super();
		libraryList.add(Blink);
		libraryList.add(Charge);
		libraryList.add(ExtendedThermalLance);
		libraryList.add(FluxVanes);
		libraryList.add(GraviticBoosters);
		libraryList.add(GraviticDrive);
		libraryList.add(GravitonCatapult);
		libraryList.add(Hallucination);
		libraryList.add(KhaydarinAmulet);
		libraryList.add(PsionicStorm);
		libraryList.add(WarpGateUpgrade);
		libraryList.add(AirArmor1);
		libraryList.add(AirArmor2);
		libraryList.add(AirArmor3);
		libraryList.add(AirWeapons1);
		libraryList.add(AirWeapons2);
		libraryList.add(AirWeapons3);
		libraryList.add(GroundArmor1);
		libraryList.add(GroundArmor2);
		libraryList.add(GroundArmor3);
		libraryList.add(GroundWeapons1);
		libraryList.add(GroundWeapons2);
		libraryList.add(GroundWeapons3);
		libraryList.add(Shields1);
		libraryList.add(Shields2);
		libraryList.add(Shields3);
        
        for (Upgrade building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
    // has to be at the end of the class to keep the class initialization in order
    private static final ProtossUpgradeLibrary instance = new ProtossUpgradeLibrary();
    public static final ProtossUpgradeLibrary getInstance() {
        return instance;
    }
}
