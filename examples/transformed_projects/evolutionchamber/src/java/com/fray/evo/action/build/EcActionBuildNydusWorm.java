package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildNydusWorm extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = -983279526149686118L;

	public EcActionBuildNydusWorm()
	{
		super(ZergBuildingLibrary.NydusWorm);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.nydusNetworkInUse += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		s.addBuilding((Building)buildable);
		s.nydusNetworkInUse -= 1;
	}
}
