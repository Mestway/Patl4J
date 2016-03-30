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
	
	private static final Logger logger = Logger.getLogger(EcRequirementTree.class.getName());

    /**
     * fills a List with all required actions for a EcState
     * @param destination the destination to build the action list for
     * @return a unmodifiable list of all required actions
     */
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
    
    /**
     * loops a list of actions and checks if any of those consumes gas
     * @param actions
     * @return true if any action requires gas, false if not
     */
    private static boolean doesBuildRequireGas(List<Class<? extends EcAction>> actions){
    	/* TODO find a better way to check for the gas requirement, maybe encode it int the basic action
    	 * this method may be lead to unreachable goals if new actions are added but forgotten to be considered here 
    	 */
    	
    	int actionListSize = actions.size();
        for (int i = 0; i < actionListSize; i++) {
        	Class<? extends EcAction> actionClass = actions.get(i);
        	try {
        		// create an instance of this action to get it's gas value
				EcAction actionObj = actionClass.newInstance();
				
				// check the action type
				if( actionObj instanceof EcActionBuild){
					if ( ((EcActionBuild)actionObj).getGas() > 0){
						// this building requires gas
						return true;
					}
				}else if ( actionObj instanceof EcActionUpgrade){
					if (((EcActionUpgrade)actionObj).getGas() > 0){
						// this upgrade requires gas
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

    /**
     * populates the action list with the actions of a state
     * @param destination state to take actions from
     * @param actions actionlist
     */
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

    /**
     * adds an action to the list of required actions
     * @param actions list of required actions
     * @param action an action to add
     */
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
        //Lomilar says: Larva causes action to be null, which seems like an OK thing.    	
        if (!requirement.equals(ZergUnitLibrary.Larva))
        	addActionToList(actions, ActionManager.getActionFor(requirement));
        for (Buildable buildable : requirement.getRequirement()) {
            require(buildable, destination, actions);
        }

    }
}
