/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 * The singleton instance contains all Terran Upgrades
 * 
 */
public final class TerranUpgradeLibrary extends Library<Upgrade>  {
    public static final Upgrade T250mmStrikeCannons = TerranLibrary.T250mmStrikeCannons;
	public static final Upgrade ArmSilowithNuke = TerranLibrary.ArmSilowithNuke;
	public static final Upgrade BehemothReactor = TerranLibrary.BehemothReactor;
	public static final Upgrade CaduceusReactor = TerranLibrary.CaduceusReactor;
	public static final Upgrade CloakingField = TerranLibrary.CloakingField;
	public static final Upgrade CombatShield = TerranLibrary.CombatShield;
	public static final Upgrade ConcussiveShells = TerranLibrary.ConcussiveShells;
	public static final Upgrade CorvidReactor = TerranLibrary.CorvidReactor;
	public static final Upgrade DurableMaterials = TerranLibrary.DurableMaterials;
	public static final Upgrade HiSecAutoTracking = TerranLibrary.HiSecAutoTracking;
	public static final Upgrade InfernalPreigniter = TerranLibrary.InfernalPreigniter;
	public static final Upgrade MoebiusReactor = TerranLibrary.MoebiusReactor;
	public static final Upgrade NeosteelFrame = TerranLibrary.NeosteelFrame;
	public static final Upgrade NitroPacks = TerranLibrary.NitroPacks;
	public static final Upgrade PersonalCloaking = TerranLibrary.PersonalCloaking;
	public static final Upgrade SeekerMissile = TerranLibrary.SeekerMissile;
	public static final Upgrade SiegeTech = TerranLibrary.SiegeTech;
	public static final Upgrade Stimpack = TerranLibrary.Stimpack;
	public static final Upgrade BuildingArmor = TerranLibrary.BuildingArmor;
	public static final Upgrade WeaponRefit = TerranLibrary.WeaponRefit;
	public static final Upgrade InfantryArmor1 = TerranLibrary.InfantryArmor1;
	public static final Upgrade InfantryArmor2 = TerranLibrary.InfantryArmor2;
	public static final Upgrade InfantryArmor3 = TerranLibrary.InfantryArmor3;
	public static final Upgrade InfantryWeapons1 = TerranLibrary.InfantryWeapons1;
	public static final Upgrade InfantryWeapons2 = TerranLibrary.InfantryWeapons2;
	public static final Upgrade InfantryWeapons3 = TerranLibrary.InfantryWeapons3;
	public static final Upgrade ShipPlating1 = TerranLibrary.ShipPlating1;
	public static final Upgrade ShipPlating2 = TerranLibrary.ShipPlating2;
	public static final Upgrade ShipPlating3 = TerranLibrary.ShipPlating3;
	public static final Upgrade ShipWeapons1 = TerranLibrary.ShipWeapons1;
	public static final Upgrade ShipWeapons2 = TerranLibrary.ShipWeapons2;
	public static final Upgrade ShipWeapons3 = TerranLibrary.ShipWeapons3;
	public static final Upgrade VehiclePlating1 = TerranLibrary.VehiclePlating1;
	public static final Upgrade VehiclePlating2 = TerranLibrary.VehiclePlating2;
	public static final Upgrade VehiclePlating3 = TerranLibrary.VehiclePlating3;
	public static final Upgrade VehicleWeapons1 = TerranLibrary.VehicleWeapons1;
	public static final Upgrade VehicleWeapons2 = TerranLibrary.VehicleWeapons2;
	public static final Upgrade VehicleWeapons3 = TerranLibrary.VehicleWeapons3;

    private TerranUpgradeLibrary() {
        // call parent constructor to init the lists first
        super();
		libraryList.add(T250mmStrikeCannons);
		libraryList.add(ArmSilowithNuke);
		libraryList.add(BehemothReactor);
		libraryList.add(CaduceusReactor);
		libraryList.add(CloakingField);
		libraryList.add(CombatShield);
		libraryList.add(ConcussiveShells);
		libraryList.add(CorvidReactor);
		libraryList.add(DurableMaterials);
		libraryList.add(HiSecAutoTracking);
		libraryList.add(InfernalPreigniter);
		libraryList.add(MoebiusReactor);
		libraryList.add(NeosteelFrame);
		libraryList.add(NitroPacks);
		libraryList.add(PersonalCloaking);
		libraryList.add(SeekerMissile);
		libraryList.add(SiegeTech);
		libraryList.add(Stimpack);
		libraryList.add(BuildingArmor);
		libraryList.add(WeaponRefit);
		libraryList.add(InfantryArmor1);
		libraryList.add(InfantryArmor2);
		libraryList.add(InfantryArmor3);
		libraryList.add(InfantryWeapons1);
		libraryList.add(InfantryWeapons2);
		libraryList.add(InfantryWeapons3);
		libraryList.add(ShipPlating1);
		libraryList.add(ShipPlating2);
		libraryList.add(ShipPlating3);
		libraryList.add(ShipWeapons1);
		libraryList.add(ShipWeapons2);
		libraryList.add(ShipWeapons3);
		libraryList.add(VehiclePlating1);
		libraryList.add(VehiclePlating2);
		libraryList.add(VehiclePlating3);
		libraryList.add(VehicleWeapons1);
		libraryList.add(VehicleWeapons2);
		libraryList.add(VehicleWeapons3);
        
        for (Upgrade building : libraryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
    // has to be at the end of the class to keep the class initialization in order
    private static final TerranUpgradeLibrary instance = new TerranUpgradeLibrary();
    public static final TerranUpgradeLibrary getInstance() {
        return instance;
    }
}
