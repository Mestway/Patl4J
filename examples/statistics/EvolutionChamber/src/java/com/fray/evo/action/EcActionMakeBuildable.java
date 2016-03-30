/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.action;

import com.fray.evo.BuildableCollection;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public abstract class EcActionMakeBuildable extends EcAction
{
	private static final long serialVersionUID = -6108769310392430929L;
	public void makeBusy(ArrayList<ArrayList<EcAction>> madeBusyBy,HashMap<EcAction,Building> actionBusyIn, Building consumes,BuildableCollection<Building> buildings )
            	{
            //ArrayList<EcAction> acc = madeBusyBy.get(consumes);
            if(madeBusyBy.get(consumes.getId()).size() >= buildings.get(consumes)){
                if(consumes == ZergBuildingLibrary.Hatchery){
                    makeBusy(madeBusyBy, actionBusyIn, ZergBuildingLibrary.Lair, buildings);
                }else if(consumes == ZergBuildingLibrary.Lair){
                    makeBusy(madeBusyBy, actionBusyIn, ZergBuildingLibrary.Hive, buildings);
                }else if(consumes == ZergBuildingLibrary.Spire){
                    makeBusy(madeBusyBy, actionBusyIn, ZergBuildingLibrary.GreaterSpire, buildings);
                }else{
                    throw new RuntimeException("should not have been called with too few not busy main buildings");
                }
            }else{
                madeBusyBy.get(consumes.getId()).add(this);
                actionBusyIn.put(this, consumes);
            }
	}
    public void makeNotBusy(ArrayList<ArrayList<EcAction>> madeBusyBy,HashMap<EcAction,Building> actionBusyIn )
    {        Building busyBuilding = actionBusyIn.get(this);
        madeBusyBy.get(busyBuilding.getId()).remove(this);
        actionBusyIn.remove(this);
    }
}
