package com.fray.evo;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import com.fray.evo.action.ActionManager;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.build.EcActionBuild;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.upgrade.EcActionUpgrade;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergUnitLibrary;
/** 
 * Utility to populate the list of require actions for the evolver
 */
public final class EcRequirementTree {
  private static final Logger logger=Logger.getLogger(EcRequirementTree.class.getName());
  /** 
 * fills a List with all required actions for a EcState
 * @param destination the destination to build the action list for
 * @return a unmodifiable list of all required actions
 */
  public static List<Class<? extends EcAction>> createActionList(  EcState destination){
    List<Class<? extends EcAction>> actions;
    actions=new ArrayList<Class<? extends EcAction>>();
    actions.clear();
    java.lang.Class<com.fray.evo.action.EcActionWait> genVar1437;
    genVar1437=EcActionWait.class;
    actions.add(genVar1437);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildQueen> genVar1438;
    genVar1438=EcActionBuildQueen.class;
    actions.add(genVar1438);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildDrone> genVar1439;
    genVar1439=EcActionBuildDrone.class;
    actions.add(genVar1439);
    if (destination.settings.useExtractorTrick) {
      java.lang.Class<com.fray.evo.action.EcActionExtractorTrick> genVar1440;
      genVar1440=EcActionExtractorTrick.class;
      actions.add(genVar1440);
    }
 else {
      ;
    }
    java.lang.Class<com.fray.evo.action.build.EcActionBuildHatchery> genVar1441;
    genVar1441=EcActionBuildHatchery.class;
    actions.add(genVar1441);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildOverlord> genVar1442;
    genVar1442=EcActionBuildOverlord.class;
    actions.add(genVar1442);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildSpawningPool> genVar1443;
    genVar1443=EcActionBuildSpawningPool.class;
    actions.add(genVar1443);
    populateActionList(destination,actions);
    boolean genVar1445;
    genVar1445=destination.settings.avoidMiningGas && doesBuildRequireGas(actions);
    if (genVar1445) {
      java.lang.Class<com.fray.evo.action.EcActionMineGas> genVar1446;
      genVar1446=EcActionMineGas.class;
      actions.add(genVar1446);
      java.lang.Class<com.fray.evo.action.build.EcActionBuildExtractor> genVar1447;
      genVar1447=EcActionBuildExtractor.class;
      actions.add(genVar1447);
    }
 else {
      ;
    }
    return Collections.unmodifiableList(actions);
  }
  /** 
 * loops a list of actions and checks if any of those consumes gas
 * @param actions
 * @return true if any action requires gas, false if not
 */
  private static boolean doesBuildRequireGas(  List<Class<? extends EcAction>> actions){
    int actionListSize;
    actionListSize=actions.size();
    int i=0;
    for (; i < actionListSize; i++) {
      Class<? extends EcAction> actionClass;
      actionClass=actions.get(i);
      try {
        EcAction actionObj;
        actionObj=actionClass.newInstance();
        boolean genVar1449;
        genVar1449=actionObj instanceof EcActionBuild;
        if (genVar1449) {
          com.fray.evo.action.build.EcActionBuild genVar1450;
          genVar1450=(EcActionBuild)actionObj;
          EcActionBuild genVar1451;
          genVar1451=(genVar1450);
          int genVar1452;
          genVar1452=genVar1451.getGas();
          int genVar1453;
          genVar1453=0;
          boolean genVar1454;
          genVar1454=genVar1452 > genVar1453;
          if (genVar1454) {
            boolean genVar1455;
            genVar1455=true;
            return genVar1455;
          }
 else {
            ;
          }
        }
 else {
          boolean genVar1456;
          genVar1456=actionObj instanceof EcActionUpgrade;
          if (genVar1456) {
            com.fray.evo.action.upgrade.EcActionUpgrade genVar1457;
            genVar1457=(EcActionUpgrade)actionObj;
            EcActionUpgrade genVar1458;
            genVar1458=(genVar1457);
            int genVar1459;
            genVar1459=genVar1458.getGas();
            int genVar1460;
            genVar1460=0;
            boolean genVar1461;
            genVar1461=genVar1459 > genVar1460;
            if (genVar1461) {
              boolean genVar1462;
              genVar1462=true;
              return genVar1462;
            }
 else {
              ;
            }
          }
 else {
            ;
          }
        }
      }
 catch (      InstantiationException e) {
        java.lang.String genVar1463;
        genVar1463="The class ";
        java.lang.String genVar1464;
        genVar1464=actionClass.getName();
        java.lang.String genVar1465;
        genVar1465=" does not specifiy a public non-arg constructor or is an interface/abstract.";
        java.lang.String genVar1466;
        genVar1466=genVar1463 + genVar1464 + genVar1465;
        logger.severe(genVar1466);
        java.lang.RuntimeException genVar1467;
        genVar1467=new RuntimeException(e);
        throw genVar1467;
      }
catch (      IllegalAccessException e) {
        java.lang.RuntimeException genVar1468;
        genVar1468=new RuntimeException(e);
        throw genVar1468;
      }
    }
    boolean genVar1469;
    genVar1469=false;
    return genVar1469;
  }
  /** 
 * populates the action list with the actions of a state
 * @param destination state to take actions from
 * @param actions actionlist
 */
  private static void populateActionList(  EcState destination,  List<Class<? extends EcAction>> actions){
    java.util.HashSet<com.fray.evo.util.Upgrade> genVar1470;
    genVar1470=destination.getUpgrades();
    java.lang.Object genVar1471;
    genVar1471=genVar1470.clone();
    java.util.HashSet<com.fray.evo.util.Upgrade> genVar1472;
    genVar1472=(HashSet<Upgrade>)genVar1471;
    for (    Upgrade upgrade : genVar1472) {
      require(upgrade,destination,actions);
    }
    java.util.HashMap<com.fray.evo.util.Building,java.lang.Integer> genVar1474;
    genVar1474=destination.getBuildings();
    java.util.Set<java.util.Map.Entry<com.fray.evo.util.Building,java.lang.Integer>> genVar1475;
    genVar1475=genVar1474.entrySet();
    for (    Entry<Building,Integer> entry : genVar1475) {
      java.lang.Integer genVar1476;
      genVar1476=entry.getValue();
      int genVar1477;
      genVar1477=0;
      boolean genVar1478;
      genVar1478=genVar1476 > genVar1477;
      if (genVar1478) {
        com.fray.evo.util.Building genVar1480;
        genVar1480=entry.getKey();
        require(genVar1480,destination,actions);
      }
 else {
        ;
      }
    }
    java.util.HashMap<com.fray.evo.util.Unit,java.lang.Integer> genVar1481;
    genVar1481=destination.getUnits();
    java.util.Set<java.util.Map.Entry<com.fray.evo.util.Unit,java.lang.Integer>> genVar1482;
    genVar1482=genVar1481.entrySet();
    for (    Entry<Unit,Integer> entry : genVar1482) {
      java.lang.Integer genVar1483;
      genVar1483=entry.getValue();
      int genVar1484;
      genVar1484=0;
      boolean genVar1485;
      genVar1485=genVar1483 > genVar1484;
      if (genVar1485) {
        com.fray.evo.util.Unit genVar1487;
        genVar1487=entry.getKey();
        require(genVar1487,destination,actions);
      }
 else {
        ;
      }
    }
    for (    EcState s : destination.waypoints) {
      populateActionList(s,actions);
    }
  }
  /** 
 * adds an action to the list of required actions
 * @param actions list of required actions
 * @param action an action to add
 */
  private static void addActionToList(  List<Class<? extends EcAction>> actions,  EcAction action){
    boolean genVar1489;
    genVar1489=action == null;
    if (genVar1489) {
      java.security.InvalidParameterException genVar1490;
      genVar1490=new InvalidParameterException();
      throw genVar1490;
    }
 else {
      ;
    }
    java.lang.Class genVar1491;
    genVar1491=action.getClass();
    boolean genVar1492;
    genVar1492=actions.contains(genVar1491);
    boolean genVar1493;
    genVar1493=!genVar1492;
    if (genVar1493) {
      java.lang.Class genVar1494;
      genVar1494=action.getClass();
      actions.add(genVar1494);
    }
 else {
      ;
    }
  }
  private static void require(  Buildable requirement,  EcState destination,  List<Class<? extends EcAction>> actions){
    boolean genVar1495;
    genVar1495=requirement == ZergUnitLibrary.Larva;
    if (genVar1495) {
      return;
    }
 else {
      ;
    }
    boolean genVar1496;
    genVar1496=requirement instanceof Upgrade;
    if (genVar1496) {
      com.fray.evo.util.Upgrade genVar1497;
      genVar1497=(Upgrade)requirement;
      destination.addUpgrade(genVar1497);
      com.fray.evo.util.Upgrade genVar1499;
      genVar1499=(Upgrade)requirement;
      Upgrade genVar1500;
      genVar1500=(genVar1499);
      com.fray.evo.util.Building genVar1501;
      genVar1501=genVar1500.getBuiltIn();
      require(genVar1501,destination,actions);
    }
 else {
      boolean genVar1502;
      genVar1502=requirement instanceof Building;
      if (genVar1502) {
        com.fray.evo.util.Building genVar1503;
        genVar1503=(Building)requirement;
        destination.requireBuilding(genVar1503);
      }
 else {
        boolean genVar1504;
        genVar1504=requirement instanceof Unit;
        if (genVar1504) {
          com.fray.evo.util.Unit genVar1505;
          genVar1505=(Unit)requirement;
          destination.RequireUnit(genVar1505);
        }
 else {
          ;
        }
      }
    }
    int i=0;
    for (; i < requirement.getRequirement().size(); i++) {
      java.util.ArrayList<com.fray.evo.util.Buildable> genVar1507;
      genVar1507=requirement.getRequirement();
      com.fray.evo.util.Buildable genVar1508;
      genVar1508=genVar1507.get(i);
      require(genVar1508,destination,actions);
    }
    com.fray.evo.util.Buildable genVar1509;
    genVar1509=requirement.getConsumes();
    boolean genVar1510;
    genVar1510=genVar1509 != null;
    if (genVar1510) {
      com.fray.evo.util.Buildable genVar1512;
      genVar1512=requirement.getConsumes();
      require(genVar1512,destination,actions);
    }
 else {
      ;
    }
    boolean genVar1513;
    genVar1513=requirement.equals(ZergUnitLibrary.Larva);
    boolean genVar1514;
    genVar1514=!genVar1513;
    if (genVar1514) {
      com.fray.evo.action.EcAction genVar1516;
      genVar1516=ActionManager.getActionFor(requirement);
      addActionToList(actions,genVar1516);
    }
 else {
      ;
    }
    java.util.ArrayList<com.fray.evo.util.Buildable> genVar1517;
    genVar1517=requirement.getRequirement();
    for (    Buildable buildable : genVar1517) {
      require(buildable,destination,actions);
    }
  }
}
