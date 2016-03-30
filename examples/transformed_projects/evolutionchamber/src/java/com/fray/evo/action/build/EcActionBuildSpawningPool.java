package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildSpawningPool extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = 1L;

	public EcActionBuildSpawningPool()
	{
		super(ZergBuildingLibrary.SpawningPool);
	}

}
