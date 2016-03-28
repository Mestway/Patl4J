package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.Upgrade;
import java.util.ArrayList;

public abstract class EcActionBuildBuilding extends EcActionBuild implements Serializable
{
	private static final long serialVersionUID = 6761289110026332078L;

	public EcActionBuildBuilding(Building building)
	{
		super(building);
	}

	@Override
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		if (getConsumes() == ZergUnitLibrary.Drone)
		{
			s.removeUnits(ZergUnitLibrary.Drone, 1);
			s.dronesOnMinerals -= 1;
			s.supplyUsed -= 1;
		}
		preExecute(s);
		s.addFutureAction(getTime(), new RunnableAction()
		{
			@Override
			public void run(GameLog e)
			{
				obtainOne(s, e);
				postExecute(s, e);
			}

		});
	}

	protected void preExecute(EcBuildOrder s)
	{

	}

    @Override
    public boolean isPossible(EcBuildOrder s) {
        if (getConsumes() == ZergUnitLibrary.Drone) {
            if (s.getDrones() < 1) {
                return false;
            }
        } else if (getConsumes() instanceof Building) {
            if(!s.doesNonBusyReallyExist((Building)buildable.getConsumes())){
                return false;
            }
        }
        return isPossibleResources(s);
    }

	protected void postExecute(EcBuildOrder s, GameLog e){
            s.addBuilding((Building) buildable);
        };

    @Override
    public boolean isInvalid(EcBuildOrder s) {
        ArrayList<Buildable> requirements = buildable.getRequirement();
        for (int i = 0; i < requirements.size(); i++) {
            Buildable requirement = requirements.get(i);
            if (requirement instanceof Building && !s.isBuilding((Building)requirement)) {
                return true;
            }else if(requirement instanceof Unit && s.getUnitCount((Unit)requirement) == 0){
                return true;
            }else if(requirement instanceof Upgrade && !s.isUpgrade((Upgrade)requirement)){
                return true;
            }
        }
        Buildable consumes = buildable.getConsumes();
        if(consumes instanceof Building && s.getBuildingCount((Building)consumes) == 0){
            return true;
        }else if(consumes instanceof Unit && s.getUnitCount((Unit)consumes) ==0){
            return true;
        }
        return false;
    }

}
