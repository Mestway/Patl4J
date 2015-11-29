package com.fray.evo.action;
import com.fray.evo.BuildableCollection;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import java.util.ArrayList;
import java.util.HashMap;
/** 
 * @author Cyrik
 */
public abstract class EcActionMakeBuildable extends EcAction {
  private static final long serialVersionUID=-6108769310392430929L;
  public void makeBusy(  ArrayList<ArrayList<EcAction>> madeBusyBy,  HashMap<EcAction,Building> actionBusyIn,  Building consumes,  BuildableCollection<Building> buildings){
    int genVar1591;
    genVar1591=consumes.getId();
    java.util.ArrayList<com.fray.evo.action.EcAction> genVar1592;
    genVar1592=madeBusyBy.get(genVar1591);
    int genVar1593;
    genVar1593=genVar1592.size();
    int genVar1594;
    genVar1594=buildings.get(consumes);
    boolean genVar1595;
    genVar1595=genVar1593 >= genVar1594;
    if (genVar1595) {
      boolean genVar1596;
      genVar1596=consumes == ZergBuildingLibrary.Hatchery;
      if (genVar1596) {
        EcActionMakeBuildable genVar1597;
        genVar1597=this;
        genVar1597.makeBusy(madeBusyBy,actionBusyIn,ZergBuildingLibrary.Lair,buildings);
      }
 else {
        boolean genVar1598;
        genVar1598=consumes == ZergBuildingLibrary.Lair;
        if (genVar1598) {
          EcActionMakeBuildable genVar1599;
          genVar1599=this;
          genVar1599.makeBusy(madeBusyBy,actionBusyIn,ZergBuildingLibrary.Hive,buildings);
        }
 else {
          boolean genVar1600;
          genVar1600=consumes == ZergBuildingLibrary.Spire;
          if (genVar1600) {
            EcActionMakeBuildable genVar1601;
            genVar1601=this;
            genVar1601.makeBusy(madeBusyBy,actionBusyIn,ZergBuildingLibrary.GreaterSpire,buildings);
          }
 else {
            java.lang.String genVar1602;
            genVar1602="should not have been called with too few not busy main buildings";
            java.lang.RuntimeException genVar1603;
            genVar1603=new RuntimeException(genVar1602);
            throw genVar1603;
          }
        }
      }
    }
 else {
      int genVar1604;
      genVar1604=consumes.getId();
      java.util.ArrayList<com.fray.evo.action.EcAction> genVar1605;
      genVar1605=madeBusyBy.get(genVar1604);
      com.fray.evo.action.EcActionMakeBuildable genVar1606;
      genVar1606=this;
      genVar1605.add(genVar1606);
      com.fray.evo.action.EcActionMakeBuildable genVar1607;
      genVar1607=this;
      actionBusyIn.put(genVar1607,consumes);
    }
  }
  public void makeNotBusy(  ArrayList<ArrayList<EcAction>> madeBusyBy,  HashMap<EcAction,Building> actionBusyIn){
    com.fray.evo.action.EcActionMakeBuildable genVar1608;
    genVar1608=this;
    Building busyBuilding;
    busyBuilding=actionBusyIn.get(genVar1608);
    int genVar1609;
    genVar1609=busyBuilding.getId();
    java.util.ArrayList<com.fray.evo.action.EcAction> genVar1610;
    genVar1610=madeBusyBy.get(genVar1609);
    com.fray.evo.action.EcActionMakeBuildable genVar1611;
    genVar1611=this;
    genVar1610.remove(genVar1611);
    com.fray.evo.action.EcActionMakeBuildable genVar1612;
    genVar1612=this;
    actionBusyIn.remove(genVar1612);
  }
}
