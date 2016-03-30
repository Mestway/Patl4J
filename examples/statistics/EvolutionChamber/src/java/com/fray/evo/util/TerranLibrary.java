/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;

public final class TerranLibrary {
	static private ArrayList<Buildable> createList(Buildable... buildables) {
		return new ArrayList<Buildable>(Arrays.asList(buildables));
	}

	//TODO: Final a better way than setting the ids statically
	//		cannot use a static counter since the array will now start from 0
	//		if we switch races at any point.

	public static final Building CommandCenter		= new Building(0, "Command Center", 400, 0, 100, null, createList());
	public static final Building SupplyDepot		= new Building(1, "Supply Depot", 100, 0, 30, null, createList());
	public static final Building Refinery			= new Building(2, "Refinery", 75, 0, 30, null, createList());
	public static final Building Barracks			= new Building(3, "Barracks", 150, 0, 60, null, createList(CommandCenter));
	public static final Building OrbitalCommand		= new Building(4, "Orbital Command", 150, 0, 35, CommandCenter, createList(Barracks));
	public static final Building EngineeringBay		= new Building(5, "Engineering Bay", 125, 0, 35, null, createList());
	public static final Building PlanetaryFortress	= new Building(6, "Planetary Fortress", 150, 150, 50, CommandCenter, createList(EngineeringBay));
	public static final Building Bunker				= new Building(7, "Bunker", 100, 0, 35, null, createList());
	public static final Building MissleTurret		= new Building(8, "Missle Turret", 100, 0, 25, null, createList(EngineeringBay));
	public static final Building SensorTower		= new Building(9, "Sensor Tower", 125, 100, 25, null, createList(EngineeringBay));
	public static final Building Factory			= new Building(10, "Factory", 150, 100, 60, null, createList(Barracks));
	public static final Building GhostAcademy		= new Building(11, "Ghost Academy", 150, 50, 40, null, createList(Barracks));
	public static final Building Armory				= new Building(12, "Armory", 150, 100, 65, null, createList(Factory));
	public static final Building Starport			= new Building(13, "Starport", 150, 100, 50, null, createList(Factory));
	public static final Building FusionCore			= new Building(14, "Fusion Core", 150, 150, 65, null, createList(Factory));
	
	/* TODO:
	 * The Tech Labs will not work for both building units and upgrades at the same time in this form
	 * This implementation was used to properly add all the data
	 */
//	public static final Building TechLab			= new Building(, "TechLab", 50, 25, 25, null, createList(Barracks));
//	public static final Building Reactor			= new Building(, "Reactor", 50, 50, 50, null, createList(Barracks));
	public static final Building BarracksTechLab	= new Building(15, "Barracks Tech Lab", 50, 25, 25, Barracks, createList());
	public static final Building BarracksReactor	= new Building(16, "Barracks Reactor", 50, 50, 50, Barracks, createList());
	public static final Building FactoryTechLab		= new Building(17, "Factory Tech Lab", 50, 25, 25, Factory, createList());
	public static final Building FactoryReactor		= new Building(18, "Factory Reactor", 50, 50, 50, Factory, createList());
	public static final Building StarportTechLab	= new Building(19, "Starport Tech Lab", 50, 25, 25, Starport, createList());
	public static final Building StarportReactor	= new Building(20, "Starport Reactor", 50, 50, 50, Starport, createList());

	public static final Unit SCV					= new Unit(0, "SCV", 50, 0, 1, 17, null, CommandCenter, createList());
	// TODO: MULES will likely have to be a special case like larva, especially if we want the build to consider Extra Supplies as well
	public static final Unit MULE					= new Unit(1, "MULE", 0, 0, 0, 0, null, null, createList());
	public static final Unit Marine					= new Unit(2, "Marine", 50, 0, 1, 25, null, Barracks, createList());
	public static final Unit Marauder				= new Unit(3, "Marauder", 100, 25, 2, 30, null, BarracksTechLab, createList());
	public static final Unit Reaper					= new Unit(4, "Reaper", 50, 50, 1, 45, null, BarracksTechLab, createList());
	public static final Unit Ghost					= new Unit(5, "Ghost", 150, 150, 2, 40, null, BarracksTechLab, createList(GhostAcademy));
	public static final Unit Hellion				= new Unit(6, "Hellion", 100, 0, 2, 30, null, Factory, createList());
	public static final Unit SiegeTank				= new Unit(7, "Siege Tank", 150, 125, 3, 45, null, FactoryTechLab, createList());
	public static final Unit Thor					= new Unit(8, "Thor", 300, 200, 6, 60, null, FactoryTechLab, createList(Armory));
	public static final Unit Viking					= new Unit(9, "Viking", 150, 75, 2, 42, null, Starport, createList());
	public static final Unit Medivac				= new Unit(10, "Medivac", 100, 100, 2, 42, null, Starport, createList());
	public static final Unit Raven					= new Unit(11, "Raven", 100, 200, 2, 60, null, StarportTechLab, createList());
	public static final Unit Banshee				= new Unit(12, "Banshee", 150, 100, 3, 60, null, StarportTechLab, createList());
	public static final Unit Battlecruiser			= new Unit(13, "Battlecruiser", 400, 300, 6, 90, null, StarportTechLab, createList(FusionCore));

	public static final Upgrade T250mmStrikeCannons	= new Upgrade(0, "250mm Strike Cannons", 150, 150, 110, FactoryTechLab, createList(Armory));
	/* TODO: Special case should nukes be units? */
	public static final Upgrade ArmSilowithNuke		= new Upgrade(1, "Arm Silo with Nuke", 100, 100, 60, GhostAcademy, createList(Armory));
	public static final Upgrade BehemothReactor		= new Upgrade(2, "Behemoth Reactor", 150, 150, 80, FusionCore, createList());
	public static final Upgrade CaduceusReactor		= new Upgrade(3, "Caduceus Reactor", 100, 100, 80, StarportTechLab, createList());
	public static final Upgrade CloakingField		= new Upgrade(4, "Cloaking Field", 200, 200, 110, StarportTechLab, createList());
	public static final Upgrade CombatShield		= new Upgrade(5, "Combat Shield", 100, 100, 110, BarracksTechLab, createList());
	public static final Upgrade ConcussiveShells	= new Upgrade(6, "Concussive Shells", 50, 50, 60, BarracksTechLab, createList());
	public static final Upgrade CorvidReactor		= new Upgrade(7, "Corvid Reactor", 150, 150, 110, StarportTechLab, createList());
	public static final Upgrade DurableMaterials	= new Upgrade(8, "Durable Materials", 150, 150, 110, StarportTechLab, createList());
	public static final Upgrade HiSecAutoTracking	= new Upgrade(9, "Hi-Sec Auto Tracking", 100, 100, 80, EngineeringBay, createList());
	public static final Upgrade InfernalPreigniter	= new Upgrade(10, "Infernal Pre-igniter", 150, 150, 110, FactoryTechLab, createList());
	public static final Upgrade MoebiusReactor		= new Upgrade(11, "Moebius Reactor", 100, 100, 80, GhostAcademy, createList());
	public static final Upgrade NeosteelFrame		= new Upgrade(12, "Neosteel Frame", 100, 100, 110, EngineeringBay, createList());
	public static final Upgrade NitroPacks			= new Upgrade(13, "Nitro Packs", 50, 50, 100, BarracksTechLab, createList(Armory));
	public static final Upgrade PersonalCloaking	= new Upgrade(14, "Personal Cloaking", 150, 150, 120, GhostAcademy, createList());
	public static final Upgrade SeekerMissile		= new Upgrade(15, "Seeker Missile", 150, 150, 110, StarportTechLab, createList());
	public static final Upgrade SiegeTech			= new Upgrade(16, "Siege Tech", 100, 100, 80, FactoryTechLab, createList());
	public static final Upgrade Stimpack			= new Upgrade(17, "Stimpack", 100, 100, 140, BarracksTechLab, createList());
	public static final Upgrade BuildingArmor		= new Upgrade(18, "Building Armor", 150, 150, 140, EngineeringBay, createList());
	public static final Upgrade WeaponRefit			= new Upgrade(19, "Weapon Refit", 150, 150, 60, FusionCore, createList());
	public static final Upgrade InfantryArmor1		= new Upgrade(20, "Infantry Armor + 1", 100, 100, 160, EngineeringBay, createList());
	public static final Upgrade InfantryArmor2		= new Upgrade(21, "Infantry Armor + 2", 175, 175, 190, EngineeringBay, createList(InfantryArmor1, Armory));
	public static final Upgrade InfantryArmor3		= new Upgrade(22, "Infantry Armor + 3", 250, 250, 220, EngineeringBay, createList(InfantryArmor1, Armory));
	public static final Upgrade InfantryWeapons1	= new Upgrade(23, "Infantry Weapons + 1", 100, 100, 160, EngineeringBay, createList());
	public static final Upgrade InfantryWeapons2	= new Upgrade(24, "Infantry Weapons + 2", 175, 175, 190, EngineeringBay, createList(InfantryWeapons1, Armory));
	public static final Upgrade InfantryWeapons3	= new Upgrade(25, "Infantry Weapons + 3", 250, 250, 220, EngineeringBay, createList(InfantryWeapons2, Armory));
	public static final Upgrade ShipPlating1		= new Upgrade(26, "Ship Plating + 1", 150, 150, 160, Armory, createList());
	public static final Upgrade ShipPlating2		= new Upgrade(27, "Ship Plating + 2", 225, 225, 190, Armory, createList(ShipPlating1));
	public static final Upgrade ShipPlating3		= new Upgrade(28, "Ship Plating + 3", 300, 300, 220, Armory, createList(ShipPlating2));
	public static final Upgrade ShipWeapons1		= new Upgrade(29, "Ship Weapons + 1", 100, 100, 160, Armory, createList());
	public static final Upgrade ShipWeapons2		= new Upgrade(30, "Ship Weapons + 2", 175, 175, 190, Armory, createList(ShipWeapons1));
	public static final Upgrade ShipWeapons3		= new Upgrade(31, "Ship Weapons + 3", 250, 250, 220, Armory, createList(ShipWeapons2));
	public static final Upgrade VehiclePlating1		= new Upgrade(32, "Vehicle Plating + 1", 100, 100, 160, Armory, createList());
	public static final Upgrade VehiclePlating2		= new Upgrade(33, "Vehicle Plating + 2", 175, 175, 190, Armory, createList(VehiclePlating1));
	public static final Upgrade VehiclePlating3		= new Upgrade(34, "Vehicle Plating + 3", 250, 250, 220, Armory, createList(VehiclePlating2));
	public static final Upgrade VehicleWeapons1		= new Upgrade(35, "Vehicle Weapons + 1", 100, 100, 160, Armory, createList());
	public static final Upgrade VehicleWeapons2		= new Upgrade(36, "Vehicle Weapons + 2", 175, 175, 190, Armory, createList(VehicleWeapons1));
	public static final Upgrade VehicleWeapons3		= new Upgrade(37, "Vehicle Weapons + 3", 250, 250, 220, Armory, createList(VehicleWeapons2));
}
