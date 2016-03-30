/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Cyrik
 */
public final class ZergLibrary {
	static private ArrayList<Buildable> createList(Buildable... buildables) {
		return new ArrayList<Buildable>(Arrays.asList(buildables));
	}

	//TODO: Final a better way than setting the ids statically
	//		cannot use a static counter since the array will now start from 0
	//		if we switch races at any point.

	public final static Unit Larva					= new Unit(0, "zerg.unit.larva", 0, 0, 0, 0, null, null, createList());
	public final static Unit Drone					= new Unit(1, "zerg.unit.drone", 50, 0, 1, 17, Larva, null, createList());

	public static final Building Hatchery			= new Building(0, "zerg.building.hatchery", 300, 0, 100, Drone, createList());
	public static final Building Extractor			= new Building(1, "zerg.building.extractor", 25, 0, 30, Drone, createList());
	public static final Building SpawningPool		= new Building(2, "zerg.building.spawningPool", 200, 0, 65, Drone, createList(Hatchery));
	public static final Building Lair				= new Building(3, "zerg.building.lair", 150, 100, 80, Hatchery, createList(Hatchery, SpawningPool));
	public static final Building InfestationPit		= new Building(4, "zerg.building.infestationPit", 100, 100, 50, Drone, createList(Lair));
	public static final Building Hive				= new Building(5, "zerg.building.hive", 200, 150, 100, Lair, createList(Lair, InfestationPit));
	public static final Building RoachWarren		= new Building(6, "zerg.building.roachWarren", 150, 0, 55, Drone, createList(SpawningPool));
	public static final Building HydraliskDen		= new Building(7, "zerg.building.hydraliskDen", 100, 100, 40, Drone, createList(Lair));
	public static final Building BanelingNest		= new Building(8, "zerg.building.banelingNest", 100, 50, 60, Drone, createList(SpawningPool));
	public static final Building Spire				= new Building(9, "zerg.building.spire", 200, 200, 100, Drone, createList(Lair));
	public static final Building GreaterSpire		= new Building(10, "zerg.building.greaterSpire", 100, 150, 100, Spire, createList(Spire, Hive));
	public static final Building UltraliskCavern	= new Building(11, "zerg.building.ultraliskCavern", 150, 200, 65, Drone, createList(Hive));
	public static final Building EvolutionChamber	= new Building(12, "zerg.building.evolutionChamber", 75, 0, 35, Drone, createList(Hatchery));
	public static final Building NydusNetwork		= new Building(13, "zerg.building.nydusNetwork", 150, 200, 50, Drone, createList(Lair));
	public static final Building NydusWorm			= new Building(14, "zerg.building.nydusWorm", 100, 100, 20, null, createList(NydusNetwork));
	public static final Building SpineCrawler		= new Building(15, "zerg.building.spineCrawler", 100, 0, 50, Drone, createList(SpawningPool));
	public static final Building SporeCrawler		= new Building(16, "zerg.building.sporeCrawler", 75, 0, 30, Drone, createList(EvolutionChamber));

	public static final Unit Zergling				= new Unit(2,"zerg.unit.zergling", 50, 0, 1, 24.0, Larva, null, createList(SpawningPool));
	public static final Unit Roach					= new Unit(3,"zerg.unit.roach", 75, 25, 2, 27, Larva, null, createList(RoachWarren));
	public static final Unit Queen					= new Unit(4,"zerg.unit.queen", 150, 0, 2, 50, null, Hatchery, createList(SpawningPool));
	public static final Unit Baneling				= new Unit(5,"zerg.unit.baneling", 25, 25, 0.5, 20, Zergling, null, createList(BanelingNest));
	public static final Unit Mutalisk				= new Unit(6,"zerg.unit.mutalisk", 100, 100, 2, 33, Larva, null, createList(Spire));
	public static final Unit Hydralisk				= new Unit(7,"zerg.unit.hydralisk", 100, 50, 2, 33, Larva, null, createList(HydraliskDen));
	public static final Unit Infestor				= new Unit(8,"zerg.unit.infestor", 100, 150, 2, 50, Larva, null, createList(InfestationPit));
	public static final Unit Corruptor				= new Unit(9,"zerg.unit.corruptor", 150, 100, 2, 40, Larva, null, createList(Spire));
	public static final Unit Ultralisk				= new Unit(10,"zerg.unit.ultralisk", 300, 200, 6, 70, Larva, null, createList(UltraliskCavern));
	public static final Unit Broodlord				= new Unit(11,"zerg.unit.broodLord", 150, 150, 4, 34, Corruptor, null, createList(GreaterSpire));
	public static final Unit Overlord				= new Unit(12,"zerg.unit.overlord", 100, 0, 0, 25, Larva, null, createList());
	public static final Unit Overseer				= new Unit(13,"zerg.unit.overseer", 50, 100, 0, 17, Overlord, null, createList(Lair));
	
	public static final Upgrade MetabolicBoost		= new Upgrade(0, "zerg.upgrade.metabolicBoost", 100, 100, 110, SpawningPool, createList());
	public static final Upgrade AdrenalGlands		= new Upgrade(1, "zerg.upgrade.adrenalGlands", 200, 200, 130, SpawningPool, createList(Hive));
	public static final Upgrade GlialReconstitution	= new Upgrade(2, "zerg.upgrade.glialReconstitution", 100, 100, 110, RoachWarren, createList(Lair));
	public static final Upgrade TunnelingClaws		= new Upgrade(3, "zerg.upgrade.tunnelingClaws", 150, 150, 110, RoachWarren, createList(Lair));
	public static final Upgrade Burrow				= new Upgrade(4, "zerg.upgrade.burrow", 100, 100, 100, Hatchery, createList(Lair));
	public static final Upgrade PneumatizedCarapace	= new Upgrade(5, "zerg.upgrade.pneumatizedCarapace", 100, 100, 60, Hatchery, createList(Lair));
	public static final Upgrade VentralSacs			= new Upgrade(6, "zerg.upgrade.ventralSacs", 200, 200, 130,Hatchery, createList(Lair));
	public static final Upgrade CentrifugalHooks	= new Upgrade(7, "zerg.upgrade.centrifugalHooks", 150, 150, 110, BanelingNest, createList(Lair));
	public static final Upgrade Melee1				= new Upgrade(8, "zerg.upgrade.melee1", 100, 100, 160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Melee2				= new Upgrade(9, "zerg.upgrade.melee2", 150, 150, 190,EvolutionChamber, createList(Lair,Melee1));
	public static final Upgrade Melee3				= new Upgrade(10, "zerg.upgrade.melee3", 200, 200, 220,EvolutionChamber, createList(Hive,Melee2));
	public static final Upgrade Missile1			= new Upgrade(11, "zerg.upgrade.missile1", 100, 100, 160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Missile2			= new Upgrade(12, "zerg.upgrade.missile2", 150, 150, 190,EvolutionChamber, createList(Lair,Missile1));
	public static final Upgrade Missile3			= new Upgrade(13, "zerg.upgrade.missile3", 200, 200, 220,EvolutionChamber, createList(Hive,Missile2));
	public static final Upgrade Armor1				= new Upgrade(14, "zerg.upgrade.carapace1",150,150,160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Armor2				= new Upgrade(15, "zerg.upgrade.carapace2", 225, 225, 190,EvolutionChamber, createList(Lair,Armor1));
	public static final Upgrade Armor3				= new Upgrade(16, "zerg.upgrade.carapace3", 300, 300, 220,EvolutionChamber, createList(Lair,Armor2));
	public static final Upgrade FlyerAttacks1		= new Upgrade(17, "zerg.upgrade.flyerAttacks1", 100, 100, 160,Spire, createList(Spire));
	public static final Upgrade FlyerAttacks2		= new Upgrade(18, "zerg.upgrade.flyerAttacks2", 175, 175, 190,Spire, createList(Lair,FlyerAttacks1));
	public static final Upgrade FlyerAttacks3		= new Upgrade(19, "zerg.upgrade.flyerAttacks3", 250, 250, 220,Spire, createList(Hive,FlyerAttacks2));
	public static final Upgrade FlyerArmor1			= new Upgrade(20, "zerg.upgrade.flyerArmor1", 150, 150, 160,Spire, createList(Spire));
	public static final Upgrade FlyerArmor2			= new Upgrade(21, "zerg.upgrade.flyerArmor2", 225, 225, 190,Spire, createList(Lair,FlyerArmor1));
	public static final Upgrade FlyerArmor3			= new Upgrade(22, "zerg.upgrade.flyerArmor3", 300, 300, 220,Spire, createList(Hive,FlyerArmor2));
	public static final Upgrade GroovedSpines		= new Upgrade(23, "zerg.upgrade.groovedSpines", 150, 150, 80,HydraliskDen, createList());
	public static final Upgrade NeuralParasite		= new Upgrade(24, "zerg.upgrade.neuralParasite", 150, 150, 110,InfestationPit, createList());
	public static final Upgrade PathogenGlands		= new Upgrade(25, "zerg.upgrade.pathogenGlands", 150, 150, 80,InfestationPit, createList());
	public static final Upgrade ChitinousPlating	= new Upgrade(26, "zerg.upgrade.chitinousPlating", 150, 150, 110,UltraliskCavern, createList());
}
