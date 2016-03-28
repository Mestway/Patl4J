package com.fray.evo.action;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionExtractorTrick extends EcAction implements Serializable
{
	private static final long serialVersionUID = 8713629742335543863L;

	@Override
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		s.minerals -= 25;
		s.removeUnits(ZergUnitLibrary.Drone, 1);
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.extractorsBuilding++;
		s.addFutureAction(2, new RunnableAction()
		{
			@Override
			public void run(GameLog e)
			{
				if (e.isEnabled())
					e.printMessage(s, GameLog.MessageType.Obtained,
							" " + messages.getString("finished.extractortrick"));
				s.minerals += 19;
				s.addUnits(ZergUnitLibrary.Drone, 1);
				s.dronesOnMinerals += 1;
				s.supplyUsed += 1;
				s.extractorsBuilding--;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.supplyUsed > s.settings.maximumExtractorTrickSupply)
			return true;
		if (s.getGasExtractors() + s.extractorsBuilding >= s.extractors())
			return true;
		if (s.supplyUsed < s.supply() - 1)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 75)
			return false;
		if (s.getDrones() < 1)
			return false;
		return true;
	}

}