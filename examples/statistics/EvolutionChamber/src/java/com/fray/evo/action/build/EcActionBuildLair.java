package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildLair extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = 2953922728901731654L;

	public EcActionBuildLair()
	{
		super(ZergBuildingLibrary.Lair);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.makeBuildingBusy((Building)buildable.getConsumes(),this);
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
            s.makeBuildingNotBusy(this);
            s.removeBuilding(ZergBuildingLibrary.Hatchery);
		s.addBuilding((Building) buildable);
	}
}
