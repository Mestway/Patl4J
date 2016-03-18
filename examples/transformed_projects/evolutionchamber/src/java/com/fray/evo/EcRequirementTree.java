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


public final class EcRequirementTree {
	
	private static final Logger logger = Logger.getLogger(EcRequirementTree.class.getName());

    public static List<Class<? extends EcAction>> createActionList(EcState destination) {
    	 List<Class<? extends EcAction>> actions;
    	    actions=new ArrayList<Class<? extends EcAction>>();
    	    actions.clear();
    	    java.lang.Class<com.fray.evo.action.EcActionWait> genVar1461;
    	    genVar1461=EcActionWait.class;
    	    actions.add(genVar1461);
    	    java.lang.Class<com.fray.evo.action.build.EcActionBuildQueen> genVar1462;
    	    genVar1462=EcActionBuildQueen.class;
    	    actions.add(genVar1462);
    	    java.lang.Class<com.fray.evo.action.build.EcActionBuildDrone> genVar1463;
    	    genVar1463=EcActionBuildDrone.class;
    	    actions.add(genVar1463);
    	    if (destination.settings.useExtractorTrick) {
    	      java.lang.Class<com.fray.evo.action.EcActionExtractorTrick> genVar1464;
    	      genVar1464=EcActionExtractorTrick.class;
    	      actions.add(genVar1464);
    	    }
    	 else {
    	      ;
    	    }
    	    java.lang.Class<com.fray.evo.action.build.EcActionBuildHatchery> genVar1465;
    	    genVar1465=EcActionBuildHatchery.class;
    	    actions.add(genVar1465);
    	    java.lang.Class<com.fray.evo.action.build.EcActionBuildOverlord> genVar1466;
    	    genVar1466=EcActionBuildOverlord.class;
    	    actions.add(genVar1466);
    	    java.lang.Class<com.fray.evo.action.build.EcActionBuildSpawningPool> genVar1467;
    	    genVar1467=EcActionBuildSpawningPool.class;
    	    actions.add(genVar1467);
    	    EcRequirementTree genVar1468;
    	    populateActionList(destination,actions);
    	    boolean genVar1469;
    	    genVar1469=destination.settings.avoidMiningGas && doesBuildRequireGas(actions);
    	    if (genVar1469) {
    	      java.lang.Class<com.fray.evo.action.EcActionMineGas> genVar1470;
    	      genVar1470=EcActionMineGas.class;
    	      actions.add(genVar1470);
    	      java.lang.Class<com.fray.evo.action.build.EcActionBuildExtractor> genVar1471;
    	      genVar1471=EcActionBuildExtractor.class;
    	      actions.add(genVar1471);
    	    }
    	 else {
    	      ;
    	    }
    	    return Collections.unmodifiableList(actions);
    }

    private static boolean doesBuildRequireGas(List<Class<? extends EcAction>> actions){
    	
    	int actionListSize = actions.size();
        for (int i = 0; i < actionListSize; i++) {
        	Class<? extends EcAction> actionClass = actions.get(i);
        	try {
        		EcAction actionObj = actionClass.newInstance();
				
				if( actionObj instanceof EcActionBuild){
					if ( ((EcActionBuild)actionObj).getGas() > 0){
						return true;
					}
				}else if ( actionObj instanceof EcActionUpgrade){
					if (((EcActionUpgrade)actionObj).getGas() > 0){
						return true;
					}
				}
			} catch (InstantiationException e) {
				logger.severe("The class " + actionClass.getName() +  " does not specifiy a public non-arg constructor or is an interface/abstract.");
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
        
        return false;
    }

    private static void populateActionList(EcState destination,List<Class<? extends EcAction>> actions) {
        for (Upgrade upgrade : (HashSet<Upgrade>)destination.getUpgrades().clone()) {
            require(upgrade, destination, actions);
        }
        for (Entry<Building, Integer> entry : destination.getBuildings().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination,actions);
            }
        }
        for (Entry<Unit, Integer> entry : destination.getUnits().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination, actions);
            }
        }
        for (EcState s : destination.waypoints) {
            populateActionList(s,actions);
        }
    }

    private static void addActionToList(List<Class<? extends EcAction>> actions, EcAction action) {
    	if (action == null) throw new InvalidParameterException();
        if (!actions.contains(action.getClass())) {
            actions.add(action.getClass());
        }
    }


    private static void require(Buildable requirement, EcState destination, List<Class<? extends EcAction>> actions) {
        if(requirement == ZergUnitLibrary.Larva){
            return;
        }
        if (requirement instanceof  Upgrade) {
            destination.addUpgrade((Upgrade) requirement);
            require(((Upgrade)requirement).getBuiltIn(), destination, actions);
        } else if (requirement instanceof  Building) {
            destination.requireBuilding((Building) requirement);
        } else if (requirement instanceof  Unit) {
            destination.RequireUnit((Unit) requirement);
        }
        for (int i = 0; i < requirement.getRequirement().size(); i++) {
            require(requirement.getRequirement().get(i), destination, actions);
        }
        if(requirement.getConsumes()!=null){
            require(requirement.getConsumes(), destination, actions);
        }
        if (!requirement.equals(ZergUnitLibrary.Larva))
        	addActionToList(actions, ActionManager.getActionFor(requirement));
        for (Buildable buildable : requirement.getRequirement()) {
            require(buildable, destination, actions);
        }

    }
}
