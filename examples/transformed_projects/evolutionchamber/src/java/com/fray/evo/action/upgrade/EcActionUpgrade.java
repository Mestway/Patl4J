package com.fray.evo.action.upgrade;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.*;
import java.util.ArrayList;

public abstract class EcActionUpgrade extends EcActionMakeBuildable implements Serializable 
{
	private static final long serialVersionUID = -7775300504786760312L;
	protected  Upgrade upgrade;

    public EcActionUpgrade() {
        init();
    }

    @Override
    public void execute(final EcBuildOrder s, final GameLog e) {
        s.minerals -= getMinerals();
        s.gas -= getGas();
        s.makeBuildingBusy(upgrade.getBuiltIn(),this);
        s.addFutureAction(getTime(), new RunnableAction() {

            @Override
            public void run(GameLog e) {
            	if (e.isEnabled())
	            	e.printMessage(s, GameLog.MessageType.Evolved
	            			, messages.getString(getName()));
                afterTime(s, e);
            }
        });
    }

    @Override
    public boolean isPossible(EcBuildOrder s) {
        if (s.minerals < getMinerals()) {
            return false;
        }
        if (s.gas < getGas()) {
            return false;
        }
        return s.doesNonBusyExist(upgrade.getBuiltIn());
    }

    public abstract void init();

    protected void init(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public void afterTime(EcBuildOrder s, GameLog e) {
        s.makeBuildingNotBusy(this);
        superAfterTime(s, e);
    }

    protected void superAfterTime(EcBuildOrder s, GameLog e) {
        s.addUpgrade(upgrade);
    }

    public int getMinerals() {
        return upgrade.getMinerals();
    }

    public int getGas() {
        return upgrade.getGas();
    }

    public int getTime() {
        return (int) upgrade.getTime();
    }

    public String getName() {
        return upgrade.getName();
    }

    @Override
    public boolean isInvalid(EcBuildOrder s) {
        ArrayList<Buildable> requirements = upgrade.getRequirement();
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
        if(!s.isBuilding(upgrade.getBuiltIn())){
            return true;
        }
        if(s.isUpgrade(upgrade)){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
		return messages.getString(upgrade.getName() + ".upgrade");
    }
}
