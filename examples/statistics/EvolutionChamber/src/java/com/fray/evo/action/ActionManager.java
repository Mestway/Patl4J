/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.action;

import com.fray.evo.action.build.EcActionBuildBaneling;
import com.fray.evo.action.build.EcActionBuildBanelingNest;
import com.fray.evo.action.build.EcActionBuildBroodLord;
import com.fray.evo.action.build.EcActionBuildCorruptor;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildGreaterSpire;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildHydralisk;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.action.build.EcActionBuildInfestationPit;
import com.fray.evo.action.build.EcActionBuildInfestor;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildMutalisk;
import com.fray.evo.action.build.EcActionBuildNydusNetwork;
import com.fray.evo.action.build.EcActionBuildNydusWorm;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildOverseer;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildRoach;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildSpineCrawler;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.action.build.EcActionBuildSporeCrawler;
import com.fray.evo.action.build.EcActionBuildUltralisk;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.action.upgrade.*;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public final class ActionManager {
    private static HashMap<Buildable,EcAction> buildableToActionMap;

    synchronized static public EcAction getActionFor(Buildable buildable){
        if(buildableToActionMap== null){
            init();
        }
        return buildableToActionMap.get(buildable);
    }

    private static void init() {
        buildableToActionMap = new HashMap<Buildable, EcAction>();
        buildableToActionMap.put(ZergUpgradeLibrary.AdrenalGlands, new EcActionUpgradeAdrenalGlands());
        buildableToActionMap.put(ZergUpgradeLibrary.Armor1, new EcActionUpgradeCarapace1());
        buildableToActionMap.put(ZergUpgradeLibrary.Armor2, new EcActionUpgradeCarapace2());
        buildableToActionMap.put(ZergUpgradeLibrary.Armor3, new EcActionUpgradeCarapace3());
        buildableToActionMap.put(ZergUpgradeLibrary.Burrow, new EcActionUpgradeBurrow());
        buildableToActionMap.put(ZergUpgradeLibrary.CentrifugalHooks, new EcActionUpgradeCentrifugalHooks());
        buildableToActionMap.put(ZergUpgradeLibrary.ChitinousPlating, new EcActionUpgradeChitinousPlating());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerArmor1, new EcActionUpgradeFlyerArmor1());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerArmor2, new EcActionUpgradeFlyerArmor2());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerArmor3, new EcActionUpgradeFlyerArmor3());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerAttacks1, new EcActionUpgradeFlyerAttacks1());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerAttacks2, new EcActionUpgradeFlyerAttacks2());
        buildableToActionMap.put(ZergUpgradeLibrary.FlyerAttacks3, new EcActionUpgradeFlyerAttacks3());
        buildableToActionMap.put(ZergUpgradeLibrary.GlialReconstitution, new EcActionUpgradeGlialReconstitution());
        buildableToActionMap.put(ZergUpgradeLibrary.GroovedSpines, new EcActionUpgradeGroovedSpines());
        buildableToActionMap.put(ZergUpgradeLibrary.Melee1, new EcActionUpgradeMelee1());
        buildableToActionMap.put(ZergUpgradeLibrary.Melee2, new EcActionUpgradeMelee2());
        buildableToActionMap.put(ZergUpgradeLibrary.Melee3, new EcActionUpgradeMelee3());
        buildableToActionMap.put(ZergUpgradeLibrary.MetabolicBoost, new EcActionUpgradeMetabolicBoost());
        buildableToActionMap.put(ZergUpgradeLibrary.Missile1, new EcActionUpgradeMissile1());
        buildableToActionMap.put(ZergUpgradeLibrary.Missile2, new EcActionUpgradeMissile2());
        buildableToActionMap.put(ZergUpgradeLibrary.Missile3, new EcActionUpgradeMissile3());
        buildableToActionMap.put(ZergUpgradeLibrary.NeuralParasite, new EcActionUpgradeNeuralParasite());
        buildableToActionMap.put(ZergUpgradeLibrary.PathogenGlands, new EcActionUpgradePathogenGlands());
        buildableToActionMap.put(ZergUpgradeLibrary.PneumatizedCarapace, new EcActionUpgradePneumatizedCarapace());
        buildableToActionMap.put(ZergUpgradeLibrary.TunnelingClaws, new EcActionUpgradeTunnelingClaws());
        buildableToActionMap.put(ZergUpgradeLibrary.VentralSacs, new EcActionUpgradeVentralSacs());

        buildableToActionMap.put(ZergBuildingLibrary.BanelingNest, new EcActionBuildBanelingNest());
        buildableToActionMap.put(ZergBuildingLibrary.EvolutionChamber, new EcActionBuildEvolutionChamber());
        buildableToActionMap.put(ZergBuildingLibrary.Extractor, new EcActionBuildExtractor());
        buildableToActionMap.put(ZergBuildingLibrary.GreaterSpire, new EcActionBuildGreaterSpire());
        buildableToActionMap.put(ZergBuildingLibrary.Hatchery, new EcActionBuildHatchery());
        buildableToActionMap.put(ZergBuildingLibrary.Hive, new EcActionBuildHive());
        buildableToActionMap.put(ZergBuildingLibrary.HydraliskDen, new EcActionBuildHydraliskDen());
        buildableToActionMap.put(ZergBuildingLibrary.InfestationPit, new EcActionBuildInfestationPit());
        buildableToActionMap.put(ZergBuildingLibrary.Lair, new EcActionBuildLair());
        buildableToActionMap.put(ZergBuildingLibrary.NydusNetwork, new EcActionBuildNydusNetwork());
        buildableToActionMap.put(ZergBuildingLibrary.NydusWorm, new EcActionBuildNydusWorm());
        buildableToActionMap.put(ZergBuildingLibrary.RoachWarren, new EcActionBuildRoachWarren());
        buildableToActionMap.put(ZergBuildingLibrary.SpawningPool, new EcActionBuildSpawningPool());
        buildableToActionMap.put(ZergBuildingLibrary.SpineCrawler, new EcActionBuildSpineCrawler());
        buildableToActionMap.put(ZergBuildingLibrary.Spire, new EcActionBuildSpire());
        buildableToActionMap.put(ZergBuildingLibrary.SporeCrawler, new EcActionBuildSporeCrawler());
        buildableToActionMap.put(ZergBuildingLibrary.UltraliskCavern, new EcActionBuildUltraliskCavern());

        buildableToActionMap.put(ZergUnitLibrary.Baneling, new EcActionBuildBaneling());
        buildableToActionMap.put(ZergUnitLibrary.Broodlord, new EcActionBuildBroodLord());
        buildableToActionMap.put(ZergUnitLibrary.Corruptor, new EcActionBuildCorruptor());
        buildableToActionMap.put(ZergUnitLibrary.Drone, new EcActionBuildDrone());
        buildableToActionMap.put(ZergUnitLibrary.Hydralisk, new EcActionBuildHydralisk());
        buildableToActionMap.put(ZergUnitLibrary.Infestor, new EcActionBuildInfestor());
        buildableToActionMap.put(ZergUnitLibrary.Mutalisk, new EcActionBuildMutalisk());
        buildableToActionMap.put(ZergUnitLibrary.Overlord, new EcActionBuildOverlord());
        buildableToActionMap.put(ZergUnitLibrary.Overseer, new EcActionBuildOverseer());
        buildableToActionMap.put(ZergUnitLibrary.Queen, new EcActionBuildQueen());
        buildableToActionMap.put(ZergUnitLibrary.Roach, new EcActionBuildRoach());
        buildableToActionMap.put(ZergUnitLibrary.Ultralisk, new EcActionBuildUltralisk());
        buildableToActionMap.put(ZergUnitLibrary.Zergling, new EcActionBuildZergling());

    }

}
