package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.GameLog;

public abstract class EcActionBuild extends EcActionMakeBuildable implements Serializable {

	private static final long serialVersionUID = 3929615537949650115L;
	protected Buildable buildable;

    public EcActionBuild(Buildable buildable) {
        this.buildable = buildable;
    }

    protected boolean isPossibleResources(EcBuildOrder s) {
        if (s.minerals < getMinerals()) {
            return false;
        }
        if (s.gas < getGas()) {
            return false;
        }
        return true;
    }

    protected void obtainOne(EcBuildOrder s, GameLog e) {
    	if (e.isEnabled())
			e.printMessage(s, GameLog.MessageType.Obtained,
					" " + messages.getString(getName()) + "+1");
    }

    public int getMinerals() {
        return buildable.getMinerals();
    }

    public int getGas() {
        return buildable.getGas();
    }

    public int getTime() {
        return (int) buildable.getTime();
    }

    public String getName() {
        return buildable.getName();
    }
    public Buildable getConsumes(){
        return buildable.getConsumes();
    }
    
    @Override
    public String toString(){
		return messages.getString(buildable.getName() + ".build");
    }

}
