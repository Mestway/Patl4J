/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;

public final class ProtossLibrary {
	static private ArrayList<Buildable> createList(Buildable... buildables) {
		return new ArrayList<Buildable>(Arrays.asList(buildables));
	}

	//TODO: Final a better way than setting the ids statically
	//		cannot use a static counter since the array will now start from 0
	//		if we switch races at any point.

	public static final Building Nexus				= new Building(0, "Nexus", 400, 0, 100, null, createList());
	public static final Building Pylon				= new Building(1, "Pylon", 100, 0, 25, null, createList());
	public static final Building Assimilator		= new Building(2, "Assimilator", 75, 0, 30, null, createList());
	public static final Building Gateway			= new Building(3, "Gateway", 150, 0, 65, null, createList(Pylon));
	public static final Building Forge				= new Building(4, "Forge", 150, 0, 45, null, createList(Pylon));
	public static final Building PhotonCannon		= new Building(5, "Photon Cannon", 150, 0, 40, null, createList(Forge));
	public static final Building CyberneticsCore	= new Building(6, "Cybernetics Core", 150, 0, 50, null, createList(Gateway));
	public static final Building TwilightCouncil	= new Building(7, "Twilight Council", 150, 100, 50, null, createList(CyberneticsCore));
	public static final Building RoboticsFacility	= new Building(8, "Robotics Facility", 200, 100, 65, null, createList(CyberneticsCore));
	public static final Building Stargate			= new Building(9, "Stargate", 150, 150, 60, null, createList(CyberneticsCore));
	public static final Building TemplarArchives	= new Building(10, "Templar Archives", 150, 200, 50, null, createList(TwilightCouncil));
	public static final Building DarkShrine			= new Building(11, "Dark Shrine", 100, 250, 100, null, createList(TwilightCouncil));
	public static final Building RoboticsBay		= new Building(12, "Robotics Bay", 200, 200, 65, null, createList(RoboticsFacility));
	public static final Building FleetBeacon		= new Building(13, "FleetBeacon", 300, 200, 60, null, createList(Stargate));
	
	public static final Unit Probe					= new Unit(0, "Probe", 50, 0, 1, 17, null, Nexus, createList());
	public static final Unit Zealot					= new Unit(1, "Zealot", 100, 0, 2, 38, null, Gateway, createList());
	public static final Unit Stalker				= new Unit(2, "Stalker", 125, 50, 2, 42, null, Gateway, createList(CyberneticsCore));
	public static final Unit Sentry					= new Unit(3, "Sentry", 50, 100, 2, 42, null, Gateway, createList(CyberneticsCore));
	public static final Unit Observer				= new Unit(4, "Observer", 50, 100, 1, 40, null, RoboticsFacility, createList());
	public static final Unit Immortal				= new Unit(5, "Immortal", 250, 100, 4, 55, null, RoboticsFacility, createList());
	public static final Unit WarpPrism				= new Unit(6, "Warp Prism", 200, 0, 2, 60, null, RoboticsFacility, createList());
	public static final Unit Colossus				= new Unit(7, "Colossus", 300, 200, 6, 75, null, RoboticsFacility, createList(RoboticsBay));
	public static final Unit VoidRay				= new Unit(8, "Void Ray", 250, 150, 3, 60, null, Stargate, createList());
	public static final Unit HighTemplar			= new Unit(9, "High Templar", 50, 150, 2, 55, null, Gateway, createList(TemplarArchives));
	public static final Unit DarkTemplar			= new Unit(10, "Dark Templar", 125, 125, 2, 55, null, Gateway, createList(DarkShrine));
	/* TODO: Special case for Archon morphing from two HighTemplar */
	public static final Unit Archon					= new Unit(11, "Archon", 0, 0, 0, 12, HighTemplar, null, createList());
	public static final Unit Carrier				= new Unit(12, "Carrier", 350, 250, 6, 120, null, Stargate, createList(FleetBeacon));
	public static final Unit Mothership				= new Unit(13, "Mothership", 400, 400, 8, 160, null, Nexus, createList(FleetBeacon));
	/* TODO: Special case for Interceptors */
	public static final Unit Interceptor			= new Unit(14, "Interceptor", 25, 0, 0, 8, null, null, createList());

	public static final Upgrade Blink				= new Upgrade(0, "Blink", 150, 150, 110, TwilightCouncil, createList());
	public static final Upgrade Charge				= new Upgrade(1, "Charge", 200, 200, 140, TwilightCouncil, createList());
	public static final Upgrade ExtendedThermalLance	= new Upgrade(2, "Extended Termal Lance", 200, 200, 140, RoboticsBay, createList());
	public static final Upgrade FluxVanes			= new Upgrade(3, "Flux Vanes", 150, 150, 80, FleetBeacon, createList());
	public static final Upgrade GraviticBoosters	= new Upgrade(4, "Gravitic Booksters", 100, 100, 80, RoboticsBay, createList());
	public static final Upgrade GraviticDrive		= new Upgrade(5, "Gravitic Drive", 100, 100, 80, RoboticsBay, createList());
	public static final Upgrade GravitonCatapult	= new Upgrade(6, "Graviton Catapult", 150, 150, 80, FleetBeacon, createList());
	public static final Upgrade Hallucination		= new Upgrade(7, "Hallucination", 100, 100, 110, CyberneticsCore, createList());
	public static final Upgrade KhaydarinAmulet		= new Upgrade(8, "Khaydarin Amulet", 150, 150, 110, TemplarArchives, createList());
	public static final Upgrade PsionicStorm		= new Upgrade(9, "Psionic Storm", 200, 200, 110, TemplarArchives, createList());
	public static final Upgrade WarpGateUpgrade		= new Upgrade(10, "Warp Gate", 50, 50, 140, CyberneticsCore, createList());
	public static final Upgrade AirArmor1			= new Upgrade(11, "Air Armor +1", 150, 150, 160, CyberneticsCore, createList());
	public static final Upgrade AirArmor2			= new Upgrade(12, "Air Armor +2", 225, 225, 190, CyberneticsCore, createList(FleetBeacon, AirArmor1));
	public static final Upgrade AirArmor3			= new Upgrade(13, "Air Armor +3", 300, 300, 200, CyberneticsCore, createList(FleetBeacon, AirArmor2));
	public static final Upgrade AirWeapons1			= new Upgrade(14, "Air Weapons +1", 100, 100, 160, CyberneticsCore, createList());
	public static final Upgrade AirWeapons2			= new Upgrade(15, "Air Weapons +2", 175, 175, 190, CyberneticsCore, createList(FleetBeacon, AirWeapons1));
	public static final Upgrade AirWeapons3			= new Upgrade(16, "Air Weapons +3", 250, 250, 220, CyberneticsCore, createList(FleetBeacon, AirWeapons2));
	public static final Upgrade GroundArmor1		= new Upgrade(17, "Ground Armor +1", 100, 100, 160, Forge, createList());
	public static final Upgrade GroundArmor2		= new Upgrade(18, "Ground Armor +2", 175, 175, 190, Forge, createList(TwilightCouncil, GroundArmor1));
	public static final Upgrade GroundArmor3		= new Upgrade(19, "Ground Armor +3", 250, 250, 220, Forge, createList(TwilightCouncil, GroundArmor2));
	public static final Upgrade GroundWeapons1		= new Upgrade(20, "Ground Weapons +1", 100, 100, 160, Forge, createList());
	public static final Upgrade GroundWeapons2		= new Upgrade(21, "Ground Weapons +2", 175, 175, 190, Forge, createList(TwilightCouncil, GroundWeapons1));
	public static final Upgrade GroundWeapons3		= new Upgrade(22, "Ground Weapons +3", 250, 250, 220, Forge, createList(TwilightCouncil, GroundWeapons2));
	public static final Upgrade Shields1			= new Upgrade(23, "Shields +1", 200, 200, 160, Forge, createList());
	public static final Upgrade Shields2			= new Upgrade(24, "Shields +2", 300, 300, 190, Forge, createList(TwilightCouncil, Shields1));
	public static final Upgrade Shields3			= new Upgrade(25, "Shields +3", 400, 400, 220, Forge, createList(TwilightCouncil, Shields2));

	public static final Building WarpGate			= new Building(14, "Warp Gate", 0, 0, 10, Gateway, createList(WarpGateUpgrade));
}
