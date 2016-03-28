package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildHive extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = 527262025964328976L;

	public EcActionBuildHive()
	{
		super(ZergBuildingLibrary.Hive);
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
            s.removeBuilding(ZergBuildingLibrary.Lair);
		s.addBuilding((Building) buildable);
	}
}
