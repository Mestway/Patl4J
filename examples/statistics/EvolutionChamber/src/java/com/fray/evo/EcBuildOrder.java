package com.fray.evo;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.ActionList;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RaceLibraries;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergLibrary;

public final class EcBuildOrder extends EcState implements Serializable
{
	static final long		serialVersionUID	= 1L;
	public int				dronesGoingOnMinerals	= 6;	
	public int				dronesGoingOnGas	= 0;
	public int				dronesOnMinerals	= 0;
	public int				dronesOnGas			= 0;
	
	public boolean 			droneIsScouting		= false;
	
	public final HashMap<EcAction,Building> actionBusyIn = new HashMap<EcAction, Building>();
	public ArrayList<ArrayList<EcAction>> 	madeBusyBy;
	
	private transient ActionList	futureAction;
	public ArrayList<EcAction>		actions				 = new ArrayList<EcAction>();

	
	public EcBuildOrder()
	{
	    super();
	    futureAction=new ActionList();
	    com.fray.evo.util.Library<com.fray.evo.util.Building> genVar0;
	    genVar0=RaceLibraries.getBuildingLibrary(settings.race);
	    ArrayList<Building> buildingList;
	    buildingList=genVar0.getList();
	    int genVar1;
	    genVar1=buildingList.size();
	    madeBusyBy=new ArrayList<ArrayList<EcAction>>(genVar1);
	    int i=0;
	    for (; i < buildingList.size(); ++i) {
	      java.util.ArrayList<com.fray.evo.action.EcAction> genVar2;
	      genVar2=new ArrayList<EcAction>();
	      madeBusyBy.add(genVar2);
	    }
	    EcBuildOrder genVar3;
	    genVar3=this;
	    int genVar4;
	    genVar4=5;
	    RunnableAction genVar5;
	    genVar5=new RunnableAction(){
	      @Override public void run(      GameLog e){
	        dronesOnMinerals+=6;
	        dronesGoingOnMinerals-=6;
	      }
	    }
	;
	    genVar3.addFutureAction(genVar4,genVar5);
	}
	
	public EcBuildOrder(EcState importDestination)
	{
		futureAction = new ActionList();
		
		//Fixed: Need to assign this to the variable, not the other way around.
		//-Lomilar
		importDestination.assign(this);
	}

	public void tick(GameLog e)
	{
		//Larva Production
		for (int hatchIndex = 0; hatchIndex < larva.size(); hatchIndex++) {
			if (getLarva(hatchIndex) < 3)
			{
				if (larvaProduction.get(hatchIndex) == 15)
				{
					if (e.isEnabled())
						e.printMessage(this, GameLog.MessageType.Obtained,
								" @" + messages.getString(ZergLibrary.Hatchery.getName()) + " #" + (hatchIndex+1) + " " + messages.getString(ZergLibrary.Larva.getName()) + " +1" );
					incrementLarva(hatchIndex);
					larvaProduction.set(hatchIndex, 0);
				}
				larvaProduction.increment(hatchIndex);
			}
		}

		//Accumulate Materials
		double mins = mineMinerals();
		minerals += mins;
		totalMineralsMined += mins;
		gas += mineGas();

		//Check Scouting Drone
		if(!droneIsScouting && scoutDrone != 0 && seconds >= scoutDrone ) {
			if (dronesGoingOnMinerals > 0) {
				droneIsScouting = true;
				dronesGoingOnMinerals--;
				if (e.isEnabled())
					e.printMessage(this, GameLog.MessageType.Scout, " +1 Scouting Drone");
			} else if (dronesOnMinerals > 0) {
				droneIsScouting = true;
				dronesOnMinerals--;
				if (e.isEnabled())
					e.printMessage(this, GameLog.MessageType.Scout, " +1 Scouting Drone");
			}
		}
	}
	
	@Override
	public EcBuildOrder clone() throws CloneNotSupportedException
	{
		final EcBuildOrder s = new EcBuildOrder();
		assign(s);
		return s;
	}

	private void assign(final EcBuildOrder s)
	{
		s.dronesGoingOnMinerals = dronesGoingOnMinerals;
		s.dronesGoingOnGas = dronesGoingOnGas;
		s.dronesOnMinerals = dronesOnMinerals;
		s.dronesOnGas = dronesOnGas;
		super.assign(s);
	}

    @Override
	public String toString()
	{
		return toUnitsOnlyString().replaceAll("\n"," ");
	}
	
	public String toShortString()
	{
		return (messages.getString("short.time") + timestamp() + "\t"+messages.getString("short.minerals")+":" + (int) minerals + "\t"+messages.getString("short.gas")+":" + (int) gas + "\t"+messages.getString("short.larva")+":" + getLarva() + "\t"+messages.getString("short.supply")+":"
				+ ((int) supplyUsed) + "/" + supply());
	}

	public ArrayList<EcAction> getActions()
	{
		return actions;
	}

	public void addAction(EcAction ecActionBuildDrone)
	{
		actions.add(ecActionBuildDrone);
	}

	public void addFutureAction(int time, RunnableAction runnable)
	{
		time = seconds + time;
		futureAction.put(time, runnable);
		actionLength++;
	}

	public RunnableAction getFutureAction(int time)
	{
		RunnableAction result = futureAction.get(time);
		return result;
	}

	public boolean nothingGoingToHappen()
	{
		return futureAction.hasFutureActions();
	}

	public void consumeLarva(final GameLog e)
	{
		int highestLarvaHatch = 0;
		int highestLarva = 0;
		
		for (int i = 0;i < larva.size();i++)
			if (larva.get(i) > highestLarva)
			{
				highestLarvaHatch = i;
				highestLarva = larva.get(i); 
			}
		final int finalHighestLarvaHatch = highestLarvaHatch;
				
		decrementLarva(finalHighestLarvaHatch);
	}

	public boolean hasSupply(double i)
	{
		if (supplyUsed + i <= supply())
			return true;
		return false;
	}

	public int mineralPatches()
	{
		return bases() * 8;
	}

	int[]		patches				= new int[24];
	public int	extractorsBuilding	= 0;
	public int	hatcheriesBuilding	= 0;
	public int	spawningPoolsInUse	= 0;
	public int	roachWarrensInUse	= 0;
	public int	infestationPitInUse	= 0;
	public int	nydusNetworkInUse = 0;

    private static double[][] cachedMineralsMined = new double[200][200];

    public double mineMinerals() {
        int mineralPatches = mineralPatches();
        if(dronesOnMinerals <= 0 || mineralPatches <= 0)
            return 0;

        if(dronesOnMinerals >= 200 || mineralPatches >= 200)
            return mineMineralsImpl();

        if(cachedMineralsMined[mineralPatches][dronesOnMinerals] == 0)
            cachedMineralsMined[mineralPatches][dronesOnMinerals] = mineMineralsImpl();

        return cachedMineralsMined[mineralPatches][dronesOnMinerals];
    }


    /**
     * Mines minerals on all bases perfectly per one second.
     * 
     * okay, we got x patches, and y drones. so the amount of drones per patch should be (y - (y%x))/x
	 * the drones%patches represent the amount of patches that have one worker more than the average.
	 * also account for the far and near patches here, assuming half of the workes go to far patches and other half onto the near ones. 
     */
	private double mineMineralsImpl()
	{
		final int drones = dronesOnMinerals;
		final int mineralPatches = mineralPatches();
	    
	    if(drones == 0 || mineralPatches == 0){
	    	return 0;
	    }
	    
	    
	    // an amount of workers do not fill a complete collection of patches
	    int droneOverflow = drones % mineralPatches ;
	    
	    int avgPatchLoad = (drones - droneOverflow) / mineralPatches;
	    
	    // above 3 per patch is overmining and does not provide any improvement 
	    if( avgPatchLoad >= 3 ){
	    	avgPatchLoad = 3;
	    	droneOverflow = 0;
	    }
	    
	    // since the overflow is also mining, those patches have a higher worker load!
	    int lowerMiningWorkload = (mineralPatches > drones) ? 0 : (mineralPatches-droneOverflow);
	    int higherMiningWorload = droneOverflow;
	    
	    // those are the patches with average load. We assume the half is on far patches, if the amount is odd, one more is on near patches
	    int farPatchesLowLoad = Math.round(lowerMiningWorkload / 2.0f);
	    int nearPatchesLowLoad = lowerMiningWorkload - farPatchesLowLoad;
	    
	    // those patches have average+1 load
	    int nearPatchesHighLoad =  Math.round(higherMiningWorload / 2.0f);
	    int farPatchesHighLoad = higherMiningWorload - nearPatchesHighLoad;
	    
	    
	    
	    double mineralsMined = 0.0;
	    switch(avgPatchLoad){
	    	case 0:
	    		// only the overflow is mining
	    		mineralsMined += nearPatchesHighLoad * 45.0 / 60.0;
	    		mineralsMined += farPatchesHighLoad * 35.0 / 60.0;
	    		break;
	    	case 1:
	    		// with one workers on it
	    		mineralsMined += nearPatchesLowLoad * 45.0 / 60.0;
	    		mineralsMined += farPatchesLowLoad * 35.0 / 60.0;
	    		
	    		// with two worker on it
	    		mineralsMined += nearPatchesHighLoad * 90.0 / 60.0;
	    		mineralsMined += farPatchesHighLoad * 75.0 / 60.0;
	    		break;
	    	case 2:
	    		// with two workers on it
	    		mineralsMined += nearPatchesLowLoad * 90.0 / 60.0;
	    		mineralsMined += farPatchesLowLoad * 75.0 / 60.0;
	    		
	    		// with three worker on it
	    		mineralsMined += nearPatchesHighLoad * 102.0 / 60.0;
	    		mineralsMined += farPatchesHighLoad * 100.0 / 60.0;
	    		break;
	    	case 3:
	    		// all patches have three workers on it
	    		mineralsMined += nearPatchesLowLoad * 102.0 / 60.0;
	    		mineralsMined += farPatchesLowLoad * 100.0 / 60.0;
	    }
	    return mineralsMined;
	}

    private static double[][] cachedGasMined = new double[200][200];

    public double mineGas()
    {
    	int gasExtra = getGasExtractors();
        if (gasExtra == 0 || dronesOnGas == 0)
			return 0;

        if(gasExtra >= 200 || dronesOnGas >= 200)
            return mineGasImpl();

        if(cachedGasMined[gasExtra][dronesOnGas] == 0)
            cachedGasMined[gasExtra][dronesOnGas] = mineGasImpl();

        return cachedGasMined[gasExtra][dronesOnGas];
    }

	// Mines gas on all bases perfectly per one second.
	private double mineGasImpl()
	{
		int drones = dronesOnGas;
		int[] extractors = new int[Math.min(getGasExtractors(),bases()*2)]; // Assign drones/patch
		for (int i = 0; i < extractors.length; i++)
			extractors[i] = 0;
		for (int i = 0; i < extractors.length; i++)
			// Assign first drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign second drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign third drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		double gasMined = 0.0;
		for (int i = 0; i < extractors.length; i++)
			if (extractors[i] == 0)
				;
			else if (extractors[i] == 1)
				gasMined += 38.0 / 60.0; // Per TeamLiquid
			else if (extractors[i] == 2)
				gasMined += 82.0 / 60.0; // Per TeamLiquid
			else
				gasMined += 114.0 / 60.0; // Per TeamLiquid

		return gasMined;
	}

	public String timestampIncremented(int increment)
	{
		int incrementedSeconds = seconds + increment;
		return incrementedSeconds / 60 + ":" + (incrementedSeconds%60 < 10 ? "0" : "") + incrementedSeconds % 60;
	}

	public int extractors()
	{
		return (bases() + hatcheriesBuilding) * 2;
	}
	
	public int getMineableGasExtractors()
	{
		return Math.min(bases() * 2, getGasExtractors());
	}

	public void makeBuildingBusy(Building consumes,EcActionMakeBuildable action)
	{
        action.makeBusy(madeBusyBy, actionBusyIn, consumes, buildings);
	}

    public void makeBuildingNotBusy(EcActionMakeBuildable action) {
        action.makeNotBusy(madeBusyBy, actionBusyIn);
    }

    public boolean doesNonBusyExist(Building building){
        if(!doesNonBusyReallyExist(building)){
            if(building == ZergBuildingLibrary.Hatchery){
                return doesNonBusyExist(ZergBuildingLibrary.Lair);
            }else if(building == ZergBuildingLibrary.Lair){
                return doesNonBusyExist(ZergBuildingLibrary.Hive);
            }else if(building == ZergBuildingLibrary.Spire){
                return doesNonBusyExist(ZergBuildingLibrary.GreaterSpire);
            }
            return false;
        }else{
            return true;
        }
    }

    public boolean doesNonBusyReallyExist(Building building){
    	return madeBusyBy.get(building.getId()).size() < buildings.get(building);
    }
}
