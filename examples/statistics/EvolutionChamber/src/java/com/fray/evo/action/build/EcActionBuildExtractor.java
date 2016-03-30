package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildExtractor extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = -4936056567243927906L;

	public EcActionBuildExtractor()
	{
		super(ZergBuildingLibrary.Extractor);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.extractorsBuilding++;
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		if (s.extractorsBuilding == 0)
			throw new RuntimeException("wtf?");
		s.addBuilding((Building) buildable);
		if (s.settings.pullWorkersFromGas == false)
		{
			s.dronesOnMinerals -= 3;
			s.dronesOnGas += 3;
		}
		s.extractorsBuilding--;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getGasExtractors() + s.extractorsBuilding >= s.extractors())
			return true;
		if (s.supplyUsed < s.settings.minimumExtractorSupply)
			return true;
		return super.isPossible(s);
	}

}
