package com.fray.evo;

import com.fray.evo.util.*;
import com.fray.evo.util.optimization.ArrayListInt;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

public class EcState implements Serializable
{
	private static final long serialVersionUID = 8913147157413079020L;
	private static final Logger logger = Logger.getLogger(EcState.class.getName());
	public EcSettings	settings	= new EcSettings();
	protected HashSet<Upgrade> upgrades;
	protected BuildableCollection<Building> buildings;
	protected BuildableCollection<Unit> units;


	public EcState()
	{
		hatcheryTimes.add(0);
		larva.add(3);
		larvaProduction.add(1);
		units = new BuildableCollection<Unit>(RaceLibraries.getUnitLibrary(settings.race).getList(), settings.race);
		// Building test = ZergLibrary.Lair;
		// for(Unit unit: RaceLibraries.getZergUnitLibrary(settings.race).getList()){
		// units.put(unit, 0);
		// }
		ArrayList<Building> allBuildingsList = RaceLibraries.getBuildingLibrary(settings.race).getList();
		buildings = new BuildableCollection<Building>(allBuildingsList, settings.race);
        for (Building bldg : allBuildingsList)
            buildings.put(bldg, 0);

		upgrades = new HashSet<Upgrade>();

		units.put(ZergUnitLibrary.Drone, 6);
		units.put(ZergUnitLibrary.Overlord, 1);
		buildings.put(ZergBuildingLibrary.Hatchery, 1);
	}

	public double				preTimeScore		= 0.0;
	public double				timeBonus			= 0.0;


	public ArrayListInt			larva				= new ArrayListInt();
	public ArrayList<Boolean>	hasQueen 			= new ArrayList<Boolean>();
	public ArrayListInt			larvaProduction 	= new ArrayListInt();
	public double				minerals			= 50;
	public double				gas					= 0;
	public double				supplyUsed			= 6;
	public int					requiredBases		= 1;


	public int					scoutDrone			= 0;

	public int					seconds				= 0;
	public int					targetSeconds		= 0;
	public int					invalidActions		= 0;
	public double				actionLength		= 0;
	public int					waits;

	public int					maxOverDrones		= 50;
	public int					overDroneEfficiency	= 80;

	public ArrayListInt			hatcheryTimes		= new ArrayListInt();

	public ArrayList<EcState>	waypoints			= new ArrayList<EcState>();
	public EcState				mergedWaypoints		= null;
	public double				totalMineralsMined	= 0;

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		EcState s = new EcState();
		assign(s);
		return s;
	}
	
	protected void assign(EcState s)
	{
		for (EcState st : waypoints)
		{
			try
			{
				s.waypoints.add((EcState) st.clone());
			}
			catch (CloneNotSupportedException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		}

		try
		{
			if (mergedWaypoints == null)
			{
				s.mergedWaypoints = null;
			}
			else
			{
				s.mergedWaypoints = (EcState) mergedWaypoints.clone();
			}
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}

		s.settings = settings;
		s.minerals = minerals;
		s.gas = gas;
		s.supplyUsed = supplyUsed;

		s.requiredBases = requiredBases;
		s.scoutDrone = scoutDrone;

		s.buildings = buildings.clone();
		s.upgrades = (HashSet<Upgrade>) upgrades.clone();
		s.units = units.clone();

		s.seconds = seconds;
		s.targetSeconds = targetSeconds;
		s.invalidActions = invalidActions;
		s.actionLength = actionLength;
		s.totalMineralsMined = totalMineralsMined;
		
//		s.larva = (ArrayListInt) larva.clone();
//		s.hasQueen = (ArrayList<Boolean>) hasQueen.clone();
//		s.larvaProduction = (ArrayListInt) larvaProduction.clone();
	}

	public int supply()
	{
		return Math.min((units.get(ZergUnitLibrary.Overlord) + units.get(ZergUnitLibrary.Overseer)) * 8 + 2 * bases(), 200);
	}

	public static EcState defaultDestination()
	{
		EcState d = new EcState();

		d.setUnits(ZergUnitLibrary.Drone, 0);
		d.setUnits(ZergUnitLibrary.Overlord, 0);
		d.setBuilding(ZergBuildingLibrary.Hatchery, 0);
		d.targetSeconds = 60 * 120;

		return d;
	}

	public double score(EcState candidate)
	{
		return settings.getFitnessFunction().score(candidate, this);
	}

	public void union(EcState s)
	{
		if (s.requiredBases > requiredBases)
			requiredBases = s.requiredBases;

		units = unionUnits(units, s.units);
		BuildableCollection<Building> temp = new BuildableCollection<Building>(buildings.getSize(), settings.race);
		for (int i = 0; i < buildings.getSize(); i++)
		{
			temp.putById(i, Math.max(buildings.getById(i), s.buildings.getById(i)));
		}
		buildings = temp;
		upgrades.addAll(s.upgrades);
	}

	private BuildableCollection<Unit> unionUnits(BuildableCollection<Unit> map, BuildableCollection<Unit> s)
	{
		BuildableCollection<Unit> result = new BuildableCollection<Unit>(units.getSize(), settings.race);
		for (int i = 0; i < map.getSize(); i++)
		{
			result.putById(i, Math.max(map.getById(i), s.getById(i)));
		}

		return result;
	}

	public boolean isSatisfied(EcState candidate)
	{
		if (waypoints.size() > 0)
		{
			if (mergedWaypoints == null)
				mergedWaypoints = getMergedState();
			return mergedWaypoints.isSatisfied(candidate);
		}
		else
			mergedWaypoints = null;

		for (int i = 0; i < units.getSize(); i++)
		{
			//Don't make larva a requirement for satisfaction.
			if (i == 0 && units.race.equals(Race.Zerg))
				continue;
			if (candidate.units.getById(i) < units.getById(i))
			{
				return false;
			}
		}

		if (candidate.bases() < requiredBases)
			return false;

		for (int i = 0; i < buildings.getSize(); i++)
		{
			if (candidate.buildings.getById(i) < buildings.getById(i))
			{
				return false;
			}
		}

		if (!candidate.upgrades.containsAll(upgrades))
		{
			return false;
		}

		if (candidate.settings.overDrone || candidate.settings.workerParity)
		{
			int overDrones = getOverDrones(candidate);

			if (candidate.settings.overDrone && candidate.units.get(ZergUnitLibrary.Drone) < overDrones)
			{
				return false;
			}
			if (candidate.settings.workerParity)
			{
				int parityDrones = getParityDrones(candidate);

				if (candidate.units.get(ZergUnitLibrary.Drone) < parityDrones)
				{
					return false;
				}
			}
		}

		return true;
	}

	public int getOverDrones(EcState s)
	{
		int overDrones = ((s.productionTime() / 17) + s.usedDrones()) * overDroneEfficiency / 100;
		return Math.min(overDrones, maxOverDrones);
	}

	public int getParityDrones(EcState s)
	{
		int optimalDrones = Math.min((Math.min(s.bases(), 3) * 16) + (s.getGasExtractors() * 3), maxOverDrones);
        return Math.min(s.getOverDrones(s), optimalDrones);
	}

	public int getParityDronesClean(EcState s)
	{
		int optimalDrones = Math.min((Math.min(s.bases(), 3) * 16) + (s.buildings.get(ZergBuildingLibrary.Extractor) * 3),
				maxOverDrones);
        return Math.min(s.getOverDrones(s), optimalDrones);
	}

	public int bases()
	{
		return buildings.get(ZergBuildingLibrary.Hatchery) + buildings.get(ZergBuildingLibrary.Lair) +
                        buildings.get(ZergBuildingLibrary.Hive) ;
	}

	public int productionTime()
	{
		int productionTime = 0;

		// Calculate raw hatchery production time
		for (int i = 0; i < Math.min(hatcheryTimes.size(), 4); i++)
		{
			productionTime += seconds - hatcheryTimes.get(i); // TODO: Change to
			// constant
		}

		return productionTime;
	}

	public int usedDrones()
	{
		return ((getHatcheries() - 1) + getLairs() + getHives() + getSpawningPools()
				+ getEvolutionChambers() + getRoachWarrens() + getHydraliskDen() + getBanelingNest() + getInfestationPit() + getUltraliskCavern()
				+ getGasExtractors() + getSpire() + getSpineCrawlers() + getSporeCrawlers() + getNydusWorm());

	}

	public int usedDronesClean()
	{
        int total =  -1 ;
        for(Building building: RaceLibraries.getBuildingLibrary(settings.race).getList()){
            if(building.getConsumes() == ZergUnitLibrary.Drone){
                total += buildings.get(building);
            }
        }

		return total;
	}

	public int getEstimatedActions()
	{
		if (waypoints.size() > 0)
		{
			if (mergedWaypoints == null)
				mergedWaypoints = getMergedState();
			return mergedWaypoints.getEstimatedActions();
		}

		int i = requiredBases + getLairs() + getHives() + getSpawningPools() + getEvolutionChambers()
				+ getRoachWarrens() + getHydraliskDen() + getBanelingNest() + getInfestationPit() + getGreaterSpire()
				+ getUltraliskCavern() + getGasExtractors() + getSpire() + getSpineCrawlers() + getSporeCrawlers()
				+ getNydusNetwork() + getNydusWorm()

				+ getDrones() + getOverlords() + getOverseers() + getZerglings() + getBanelings() * 2 + getRoaches()
				+ getMutalisks() * 2 + getInfestors() * 2 + getQueens() + getHydralisks() * 2 + getCorruptors() * 2
				+ getUltralisks() * 2 + getBroodlords() * 4;

		if (isMetabolicBoost())
			i++;
		if (isAdrenalGlands())
			i+=3;
		if (isGlialReconstitution())
			i+=2;
		if (isTunnelingClaws())
			i+=2;
		if (isBurrow())
			i+=2;
		if (isPneumatizedCarapace())
			i+=2;
		if (isVentralSacs())
			i+=2;
		if (isCentrifugalHooks())
			i+=2;
		if (isMelee1())
			i++;
		if (isMelee2())
			i+=2;
		if (isMelee3())
			i+=3;
		if (isMissile1())
			i++;
		if (isMissile2())
			i+=2;
		if (isMissile3())
			i+=3;
		if (isArmor1())
			i++;
		if (isArmor2())
			i+=2;
		if (isArmor3())
			i+=3;
		if (isGroovedSpines())
			i+=2;
		if (isNeuralParasite())
			i+=2;
		if (isPathogenGlands())
			i+=2;
		if (isFlyerAttack1())
			i++;
		if (isFlyerAttack2())
			i+=2;
		if (isFlyerAttack3())
			i+=3;
		if (isFlyerArmor1())
			i++;
		if (isFlyerArmor2())
			i+=2;
		if (isFlyerArmor3())
			i+=3;
		if (isChitinousPlating())
			i+=3;
		for (EcState s : waypoints)
			i += s.getEstimatedActions();
		return i;
	}

	// TODO do we need to change tier 2 and 3 units
	public int getEstimatedActionsClean()
	{
		if (waypoints.size() > 0)
		{
			if (mergedWaypoints == null)
				mergedWaypoints = getMergedState();
			return mergedWaypoints.getEstimatedActions();
		}

		int i = requiredBases;

		i += units.getCount();

		i += buildings.getCount();
		i += upgrades.size();
		for (EcState s : waypoints)
			i += s.getEstimatedActions();
		return i;
	}

	public EcState getMergedState()
	{
		EcState state = defaultDestination();
		for (EcState s : waypoints)
			state.union(s);
		state.union(this);
		return state;
	}

	public String timestamp()
	{
		return seconds / 60 + ":" + (seconds % 60 < 10 ? "0" : "") + seconds % 60;
	}

	public String toCompleteString()
	{
		StringBuilder sb = new StringBuilder();
        sb.append(messages.getString("AtTime")).append(": ").append(timestamp());
        sb.append("\n")
                .append(messages.getString("Minerals")).append(": ").append((int) minerals).append("\t")
                .append(messages.getString("Gas")).append(":      ").append((int) gas).append("\t")
                .append(messages.getString("Supply")).append(":   ").append((int) supplyUsed).append("/").append(supply()).append("\t")
                .append(messages.getString("Larva")).append(": ").append(getLarva());
		appendBuildStuff(sb);
		return sb.toString();
	}

	public String toUnitsOnlyString()
	{
		StringBuilder sb = new StringBuilder();
		appendBuildStuff(sb);
		return sb.toString();
	}

	private void appendBuildStuff(StringBuilder sb)
	{
		append(sb, ZergLibrary.Drone.getName(), getDrones());
		append(sb, ZergLibrary.Overlord.getName(), getOverlords());
		append(sb, ZergLibrary.Overseer.getName(), getOverseers());
		append(sb, ZergLibrary.Queen.getName(), getQueens());
		append(sb, ZergLibrary.Zergling.getName(), getZerglings());
		append(sb, ZergLibrary.Baneling.getName(), getBanelings());
		append(sb, ZergLibrary.Roach.getName(), getRoaches());
		append(sb, ZergLibrary.Hydralisk.getName(), getHydralisks());
		append(sb, ZergLibrary.Infestor.getName(), getInfestors());
		append(sb, ZergLibrary.Mutalisk.getName(), getMutalisks());
		append(sb, ZergLibrary.Corruptor.getName(), getCorruptors());
		append(sb, ZergLibrary.Ultralisk.getName(), getUltralisks());
		append(sb, ZergLibrary.Broodlord.getName(), getBroodlords());
		append(sb, "Total.Minerals.Mined", (int) totalMineralsMined);

		if (bases()>= requiredBases)
			append(sb, "Bases", bases());
		else
			append(sb, "Required.Bases", requiredBases);
		append(sb, ZergLibrary.Lair.getName(), getLairs());
		append(sb, ZergLibrary.Hive.getName(), getHives());
		append(sb, ZergLibrary.Extractor.getName(), getGasExtractors());
		append(sb, ZergLibrary.SpawningPool.getName(), getSpawningPools());
		append(sb, ZergLibrary.BanelingNest.getName(), getBanelingNest());
		append(sb, ZergLibrary.RoachWarren.getName(), getRoachWarrens());
		append(sb, ZergLibrary.HydraliskDen.getName(), getHydraliskDen());
		append(sb, ZergLibrary.InfestationPit.getName(), getInfestationPit());
		append(sb, ZergLibrary.Spire.getName(), getSpire());
		append(sb, ZergLibrary.UltraliskCavern.getName(), getUltraliskCavern());
		append(sb, ZergLibrary.GreaterSpire.getName(), getGreaterSpire());
		append(sb, ZergLibrary.EvolutionChamber.getName(), getEvolutionChambers());
		append(sb, ZergLibrary.SpineCrawler.getName(), getSpineCrawlers());
		append(sb, ZergLibrary.SporeCrawler.getName(), getSporeCrawlers());
		append(sb, ZergLibrary.NydusNetwork.getName(), getNydusNetwork());
		append(sb, ZergLibrary.NydusWorm.getName(), getNydusWorm());

		append(sb, ZergLibrary.Melee1.getName(), isMelee1());
		append(sb, ZergLibrary.Melee2.getName(), isMelee2());
		append(sb, ZergLibrary.Melee3.getName(), isMelee3());
		append(sb, ZergLibrary.Missile1.getName(), isMissile1());
		append(sb, ZergLibrary.Missile2.getName(), isMissile2());
		append(sb, ZergLibrary.Missile3.getName(), isMissile3());
		append(sb, ZergLibrary.Armor1.getName(), isArmor1());
		append(sb, ZergLibrary.Armor2.getName(), isArmor2());
		append(sb, ZergLibrary.Armor3.getName(), isArmor3());
		append(sb, ZergLibrary.FlyerAttacks1.getName(), isFlyerAttack1());
		append(sb, ZergLibrary.FlyerAttacks2.getName(), isFlyerAttack2());
		append(sb, ZergLibrary.FlyerAttacks3.getName(), isFlyerAttack3());
		append(sb, ZergLibrary.FlyerArmor1.getName(), isFlyerArmor1());
		append(sb, ZergLibrary.FlyerArmor2.getName(), isFlyerArmor2());
		append(sb, ZergLibrary.FlyerArmor3.getName(), isFlyerArmor3());
		append(sb, ZergLibrary.MetabolicBoost.getName(), isMetabolicBoost());
		append(sb, ZergLibrary.AdrenalGlands.getName(), isAdrenalGlands());
		append(sb, ZergLibrary.GlialReconstitution.getName(), isGlialReconstitution());
		append(sb, ZergLibrary.TunnelingClaws.getName(), isTunnelingClaws());
		append(sb, ZergLibrary.Burrow.getName(), isBurrow());
		append(sb, ZergLibrary.PneumatizedCarapace.getName(), isPneumatizedCarapace());
		append(sb, ZergLibrary.VentralSacs.getName(), isVentralSacs());
		append(sb, ZergLibrary.CentrifugalHooks.getName(), isCentrifugalHooks());
		append(sb, ZergLibrary.GroovedSpines.getName(), isGroovedSpines());
		append(sb, ZergLibrary.NeuralParasite.getName(), isNeuralParasite());
		append(sb, ZergLibrary.PathogenGlands.getName(), isPathogenGlands());
		append(sb, ZergLibrary.ChitinousPlating.getName(), isChitinousPlating());
	}

	private void appendBuildStuffClean(StringBuilder sb)
	{
		// for(Unit unit:units.keySet()){
		// append(sb, unit.getName(), units.get(unit));
		// }

		append(sb, "Bases", requiredBases);

		// for(Building building: buildings.keySet()){
		// append(sb, building.getName(), buildings.get(building));
		// }
		// TODO clean that up
		for (Upgrade upgrade : upgrades)
		{
			append(sb, upgrade.getName(), true);
		}
	}

	private void append(StringBuilder sb, String name, boolean doit)
	{
		if (doit)
            sb.append("\n").append(messages.getString(name));
	}

	private void append(StringBuilder sb, String name, int count)
	{
		if (count > 0)
            sb.append("\n").append(messages.getString(name)).append(": ").append(count);
	}

	public boolean waypointMissed(EcBuildOrder candidate)
	{
		if (waypoints == null)
			return false;

		for (int i = 0; i < waypoints.size(); ++i) {
			EcState s = waypoints.get(i);
			if (candidate.seconds == s.targetSeconds)
				if (!s.isSatisfied(candidate))
					return true;
		}
		return false;
	}

	public int getCurrWaypointIndex(EcBuildOrder candidate)
	{
		for( int i = 0; i < waypoints.size(); ++i )
			if (waypoints.get(i).targetSeconds == candidate.seconds)
				return i;
		return -1;
	}
	
	public int getWaypointActions(int index) {
		return waypoints.get(index).getEstimatedActions();
	}

	public EcState getMergedWaypoints() {
		if( mergedWaypoints == null )
			mergedWaypoints = getMergedState();
		return mergedWaypoints;
	}
	public HashSet<Upgrade> getUpgrades()
	{
		return upgrades;
	}

	public void addUpgrade(Upgrade upgrade)
	{
		upgrades.add(upgrade);
	}

	public void addUnits(Unit unit, int number)
	{
		units.put(unit, units.get(unit) + number);
	}

	public void removeUnits(Unit unit, int number)
	{
		units.put(unit, units.get(unit) - number);
	}

	public void removeUpgrade(Upgrade upgrade)
	{
		upgrades.remove(upgrade);
	}

	public void setUnits(Unit unit, int number)
	{
		units.put(unit, number);
	}

	public void addBuilding(Building building)
	{
		buildings.put(building, buildings.get(building) + 1);
	}

        public int getBuildingCount(Building building){
            return buildings.getById(building.getId());
        }

        public boolean isBuilding(Building building){
            if(buildings.get(building)== 0){
                if(building == ZergBuildingLibrary.Hatchery){
                    return isBuilding(ZergBuildingLibrary.Lair);
                }else if(building == ZergBuildingLibrary.Lair){
                    return isBuilding(ZergBuildingLibrary.Hive);
                }else if(building == ZergBuildingLibrary.Spire){
                    return isBuilding(ZergBuildingLibrary.GreaterSpire);
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }

        public int getUnitCount(Unit unit){
            return units.getById(unit.getId());
        }

        public boolean isUpgrade(Upgrade upgrade){
            return upgrades.contains(upgrade);
	}

	void RequireUnit(Unit unit)
	{
		if (unit == ZergUnitLibrary.Zergling)
			return; //Extra zerglings should not be required for banelings.
		if (unit == ZergUnitLibrary.Corruptor)
			return; //Extra corruptors should not be required for brood lords.
		if (unit == ZergUnitLibrary.Overlord)
			return; //Extra overlords should not be required for overseers.
		if (units.get(unit) < 1)
		{
			units.put(unit, 1);
		}
	}
	public void removeBuilding(Building building)
	{
		buildings.put(building, buildings.get(building) - 1);
	}

	public void requireBuilding(Building building)
	{
		if (building == ZergBuildingLibrary.Spire)
			return; //Exemption due to greater spire satisfying spire.
		if (building == ZergBuildingLibrary.Lair)
			return; //Exemption due to hive satisfying lair.
		if (building == ZergBuildingLibrary.Hatchery)
			return; //Exemption due to lair satisfying hatchery.
		if (buildings.get(building) < 1)
		{
			buildings.put(building, 1);
		}
	}


	public HashMap<Building, Integer> getBuildings()
	{
		Library<Building> allBuildings = RaceLibraries.getBuildingLibrary(settings.race);
		return buildings.toHashMap(allBuildings);
	}

	public HashMap<Unit, Integer> getUnits()
	{
		Library<Unit> allUnits = RaceLibraries.getUnitLibrary(settings.race);
		return units.toHashMap(allUnits);
	}

	public void setBuilding(Building building, int number)
	{
		buildings.put(building, number);
	}

	/**
	 * @return the metabolicBoost
	 */
	public boolean isMetabolicBoost()
	{
		return upgrades.contains(ZergUpgradeLibrary.MetabolicBoost);
	}

	/**
	 * @return the adrenalGlands
	 */
	public boolean isAdrenalGlands()
	{
		return upgrades.contains(ZergUpgradeLibrary.AdrenalGlands);
	}

	/**
	 * @return the glialReconstitution
	 */
	public boolean isGlialReconstitution()
	{
		return upgrades.contains(ZergUpgradeLibrary.GlialReconstitution);
	}

	/**
	 * @return the tunnelingClaws
	 */
	public boolean isTunnelingClaws()
	{
		return upgrades.contains(ZergUpgradeLibrary.TunnelingClaws);
	}

	/**
	 * @return the burrow
	 */
	public boolean isBurrow()
	{
		return upgrades.contains(ZergUpgradeLibrary.Burrow);
	}

	/**
	 * @return the pneumatizedCarapace
	 */
	public boolean isPneumatizedCarapace()
	{
		return upgrades.contains(ZergUpgradeLibrary.PneumatizedCarapace);
	}

	/**
	 * @return the ventralSacs
	 */
	public boolean isVentralSacs()
	{
		return upgrades.contains(ZergUpgradeLibrary.VentralSacs);
	}

	/**
	 * @return the centrifugalHooks
	 */
	public boolean isCentrifugalHooks()
	{
		return upgrades.contains(ZergUpgradeLibrary.CentrifugalHooks);
	}

	/**
	 * @return the melee1
	 */
	public boolean isMelee1()
	{
		return upgrades.contains(ZergUpgradeLibrary.Melee1);
	}

	/**
	 * @return the melee2
	 */
	public boolean isMelee2()
	{
		return upgrades.contains(ZergUpgradeLibrary.Melee2);
	}

	/**
	 * @return the melee3
	 */
	public boolean isMelee3()
	{
		return upgrades.contains(ZergUpgradeLibrary.Melee3);
	}

	/**
	 * @return the missile1
	 */
	public boolean isMissile1()
	{
		return upgrades.contains(ZergUpgradeLibrary.Missile1);
	}

	/**
	 * @return the missile2
	 */
	public boolean isMissile2()
	{
		return upgrades.contains(ZergUpgradeLibrary.Missile2);
	}

	/**
	 * @return the missile3
	 */
	public boolean isMissile3()
	{
		return upgrades.contains(ZergUpgradeLibrary.Missile3);
	}

	/**
	 * @return the armor1
	 */
	public boolean isArmor1()
	{
		return upgrades.contains(ZergUpgradeLibrary.Armor1);
	}

	/**
	 * @return the armor2
	 */
	public boolean isArmor2()
	{
		return upgrades.contains(ZergUpgradeLibrary.Armor2);
	}

	/**
	 * @return the armor3
	 */
	public boolean isArmor3()
	{
		return upgrades.contains(ZergUpgradeLibrary.Armor3);
	}

	/**
	 * @return the groovedSpines
	 */
	public boolean isGroovedSpines()
	{
		return upgrades.contains(ZergUpgradeLibrary.GroovedSpines);
	}

	/**
	 * @return the neuralParasite
	 */
	public boolean isNeuralParasite()
	{
		return upgrades.contains(ZergUpgradeLibrary.NeuralParasite);
	}

	/**
	 * @return the pathogenGlands
	 */
	public boolean isPathogenGlands()
	{
		return upgrades.contains(ZergUpgradeLibrary.PathogenGlands);
	}

	/**
	 * @return the flyerAttack1
	 */
	public boolean isFlyerAttack1()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerAttacks1);
	}

	/**
	 * @return the flyerAttack2
	 */
	public boolean isFlyerAttack2()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerAttacks2);
	}

	/**
	 * @return the flyerAttack3
	 */
	public boolean isFlyerAttack3()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerAttacks3);
	}

	/**
	 * @return the flyerArmor1
	 */
	public boolean isFlyerArmor1()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerArmor1);
	}

	/**
	 * @return the flyerArmor2
	 */
	public boolean isFlyerArmor2()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerArmor2);
	}

	/**
	 * @return the flyerArmor3
	 */
	public boolean isFlyerArmor3()
	{
		return upgrades.contains(ZergUpgradeLibrary.FlyerArmor3);
	}

	/**
	 * @return the chitinousPlating
	 */
	public boolean isChitinousPlating()
	{
		return upgrades.contains(ZergUpgradeLibrary.ChitinousPlating);
	}

	/**
	 * @return the larva
	 */
	public int getLarva()
	{
		return larva.total();
	}
	public int getLarva(int base)
	{
		return this.larva.get(base);
	}

	/**
     * @param larva
	 *            the larva to set
	 */
	public void setLarva(int base, int larva)
	{
		while (this.larva.size() <= base)
			this.larva.add(0);
		this.larva.set(base, larva);
	}

	public void incrementLarva(int base)
	{
		while (this.larva.size() <= base)
			this.larva.add(0);
		this.larva.increment(base);
	}

	public void decrementLarva(int base)
	{
		while (this.larva.size() <= base)
			this.larva.add(0);
		this.larva.decrement(base);
	}

	/**
	 * @return the drones
	 */
	public int getDrones()
	{
		return units.get(ZergUnitLibrary.Drone);
	}

	/**
	 * @return the overlords
	 */
	public int getOverlords()
	{
		return units.get(ZergUnitLibrary.Overlord);
	}

	/**
	 * @return the overseers
	 */
	public int getOverseers()
	{
		return units.get(ZergUnitLibrary.Overseer);
	}

	/**
	 * @return the zerglings
	 */
	public int getZerglings()
	{
		return units.get(ZergUnitLibrary.Zergling);
	}

	/**
	 * @return the banelings
	 */
	public int getBanelings()
	{
		return units.get(ZergUnitLibrary.Baneling);
	}

	/**
	 * @return the roaches
	 */
	public int getRoaches()
	{
		return units.get(ZergUnitLibrary.Roach);
	}

	/**
	 * @return the mutalisks
	 */
	public int getMutalisks()
	{
		return units.get(ZergUnitLibrary.Mutalisk);
	}

	/**
	 * @return the infestors
	 */
	public int getInfestors()
	{
		return units.get(ZergUnitLibrary.Infestor);
	}

	/**
	 * @return the queens
	 */
	public int getQueens()
	{
		return units.get(ZergUnitLibrary.Queen);
	}

	/**
	 * @return the hydralisks
	 */
	public int getHydralisks()
	{
		return units.get(ZergUnitLibrary.Hydralisk);
	}

	/**
	 * @return the corruptors
	 */
	public int getCorruptors()
	{
		return units.get(ZergUnitLibrary.Corruptor);
	}

	/**
	 * @return the ultralisks
	 */
	public int getUltralisks()
	{
		return units.get(ZergUnitLibrary.Ultralisk);
	}

	/**
	 * @return the broodlords
	 */
	public int getBroodlords()
	{
		return units.get(ZergUnitLibrary.Broodlord);
	}

	/**
	 * @return the hatcheries
	 */
	public int getHatcheries()
	{
		return buildings.get(ZergBuildingLibrary.Hatchery);
	}

	/**
	 * @return the lairs
	 */
	public int getLairs()
	{
		return buildings.get(ZergBuildingLibrary.Lair);
	}

	/**
	 * @return the hives
	 */
	public int getHives()
	{
		return buildings.get(ZergBuildingLibrary.Hive);
	}

	/**
	 * @return the spawningPools
	 */
	public int getSpawningPools()
	{
		return buildings.get(ZergBuildingLibrary.SpawningPool);
	}

	/**
	 * @return the evolutionChambers
	 */
	public int getEvolutionChambers()
	{
		return buildings.get(ZergBuildingLibrary.EvolutionChamber);
	}

	/**
	 * @return the roachWarrens
	 */
	public int getRoachWarrens()
	{
		return buildings.get(ZergBuildingLibrary.RoachWarren);
	}

	/**
	 * @return the hydraliskDen
	 */
	public int getHydraliskDen()
	{
		return buildings.get(ZergBuildingLibrary.HydraliskDen);
	}

	/**
	 * @return the banelingNest
	 */
	public int getBanelingNest()
	{
		return buildings.get(ZergBuildingLibrary.BanelingNest);
	}

	/**
	 * @return the infestationPit
	 */
	public int getInfestationPit()
	{
		return buildings.get(ZergBuildingLibrary.InfestationPit);
	}

	/**
	 * @return the greaterSpire
	 */
	public int getGreaterSpire()
	{
		return buildings.get(ZergBuildingLibrary.GreaterSpire);
	}

	/**
	 * @return the ultraliskCavern
	 */
	public int getUltraliskCavern()
	{
		return buildings.get(ZergBuildingLibrary.UltraliskCavern);
	}

	/**
	 * @return the gasExtractors
	 */
	public int getGasExtractors()
	{
		return buildings.get(ZergBuildingLibrary.Extractor);
	}

	/**
	 * @return the spire
	 */
	public int getSpire()
	{
		return buildings.get(ZergBuildingLibrary.Spire);
	}

	/**
	 * @return the spineCrawlers
	 */
	public int getSpineCrawlers()
	{
		return buildings.get(ZergBuildingLibrary.SpineCrawler);
	}

	/**
	 * @return the sporeCrawlers
	 */
	public int getSporeCrawlers()
	{
		return buildings.get(ZergBuildingLibrary.SporeCrawler);
	}

	/**
	 * @return the nydusNetwork
	 */
	public int getNydusNetwork()
	{
		return buildings.get(ZergBuildingLibrary.NydusNetwork);
	}

	/**
	 * @return the nydusWorm
	 */
	public int getNydusWorm()
	{
		return buildings.get(ZergBuildingLibrary.NydusWorm);
	}

}
