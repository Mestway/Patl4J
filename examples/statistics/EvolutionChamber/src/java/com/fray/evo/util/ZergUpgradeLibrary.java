/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 * The singleton instance contains all Zerg Upgrades
 * @author Cyrik
 * 
 */
public final class ZergUpgradeLibrary extends Library<Upgrade> {
    
    public static final Upgrade MetabolicBoost = ZergLibrary.MetabolicBoost;
    public static final Upgrade AdrenalGlands = ZergLibrary.AdrenalGlands;
    public static final Upgrade GlialReconstitution = ZergLibrary.GlialReconstitution;
    public static final Upgrade TunnelingClaws = ZergLibrary.TunnelingClaws;
    public static final Upgrade Burrow = ZergLibrary.Burrow;
    public static final Upgrade PneumatizedCarapace = ZergLibrary.PneumatizedCarapace;
    public static final Upgrade VentralSacs = ZergLibrary.VentralSacs;
    public static final Upgrade CentrifugalHooks = ZergLibrary.CentrifugalHooks;
    public static final Upgrade Melee1 = ZergLibrary.Melee1;
    public static final Upgrade Melee2 = ZergLibrary.Melee2;
    public static final Upgrade Melee3 = ZergLibrary.Melee3;
    public static final Upgrade Missile1 = ZergLibrary.Missile1;
    public static final Upgrade Missile2 = ZergLibrary.Missile2;
    public static final Upgrade Missile3 = ZergLibrary.Missile3;
    public static final Upgrade Armor1 = ZergLibrary.Armor1;
    public static final Upgrade Armor2 = ZergLibrary.Armor2;
    public static final Upgrade Armor3 = ZergLibrary.Armor3;
    public static final Upgrade FlyerAttacks1 = ZergLibrary.FlyerAttacks1;
    public static final Upgrade FlyerAttacks2 = ZergLibrary.FlyerAttacks2;
    public static final Upgrade FlyerAttacks3 = ZergLibrary.FlyerAttacks3;
    public static final Upgrade FlyerArmor1 = ZergLibrary.FlyerArmor1;
    public static final Upgrade FlyerArmor2 = ZergLibrary.FlyerArmor2;
    public static final Upgrade FlyerArmor3 = ZergLibrary.FlyerArmor3;
    public static final Upgrade GroovedSpines = ZergLibrary.GroovedSpines;
    public static final Upgrade NeuralParasite = ZergLibrary.NeuralParasite;
    public static final Upgrade PathogenGlands = ZergLibrary.PathogenGlands;
    public static final Upgrade ChitinousPlating = ZergLibrary.ChitinousPlating;

    private ZergUpgradeLibrary() {
        // call parent constructor to init the lists first
        super();
        libraryList.add(MetabolicBoost);
        libraryList.add(AdrenalGlands);
        libraryList.add(GlialReconstitution);
        libraryList.add(TunnelingClaws);
        libraryList.add(Burrow);
        libraryList.add(PneumatizedCarapace);
        libraryList.add(VentralSacs);
        libraryList.add(CentrifugalHooks);
        libraryList.add(Melee1);
        libraryList.add(Melee2);
        libraryList.add(Melee3);
        libraryList.add(Armor1);
        libraryList.add(Armor2);
        libraryList.add(Armor3);
        libraryList.add(FlyerAttacks1);
        libraryList.add(FlyerAttacks2);
        libraryList.add(FlyerAttacks3);
        libraryList.add(FlyerArmor1);
        libraryList.add(FlyerArmor2);
        libraryList.add(FlyerArmor3);
        libraryList.add(GroovedSpines);
        libraryList.add(NeuralParasite);
        libraryList.add(PathogenGlands);
        libraryList.add(ChitinousPlating);
        
        for (Upgrade building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
    // has to be at the end of the class to keep the class initialization in order
    private static final ZergUpgradeLibrary instance = new ZergUpgradeLibrary();
    public static final ZergUpgradeLibrary getInstance() {
        return instance;
    }
}
