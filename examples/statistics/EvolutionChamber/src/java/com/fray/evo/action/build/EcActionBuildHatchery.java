package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildHatchery extends EcActionBuildBuilding implements Serializable
{
	private static final long serialVersionUID = 265935963741996160L;

	public EcActionBuildHatchery()
	{
		super(ZergBuildingLibrary.Hatchery);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.hatcheriesBuilding += 1;
		s.addFutureAction((int)(getTime() - ZergBuildingLibrary.Extractor.getTime()), new RunnableAction()
		{
			@Override
			public void run(GameLog e)
			{
				// This is a futureaction purely made for wait timing so that
				// you can build a extractor to line up with this hatch.
			}
		});
		s.addFutureAction((int)(getTime() - ZergUnitLibrary.Queen.getTime()), new RunnableAction()
		{
			@Override
			public void run(GameLog e)
			{
				// This is a futureaction purely made for wait timing so that
				// you can build a queen to line up with this hatch.
			}
		});
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		s.addBuilding(ZergBuildingLibrary.Hatchery);
		s.hatcheriesBuilding -= 1;
		s.hatcheryTimes.add(s.seconds);
		s.larva.add(1);
		s.larvaProduction.add(1);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.supplyUsed < s.settings.minimumHatcherySupply)
			return true;
		return super.isPossible(s);
	}

}