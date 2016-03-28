package com.fray.evo.action;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;

public final class EcActionMineGas extends EcAction implements Serializable
{
	private static final long serialVersionUID = 7000036942224791220L;

	@Override
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		if (s.settings.pullThreeWorkersOnly) 
		{
			s.dronesGoingOnGas += 3;
			s.dronesOnMinerals -= 3;
		}
		else
		{
			s.dronesGoingOnGas += 1;
			s.dronesOnMinerals -= 1;
		}
		s.addFutureAction(2, new RunnableAction()
		{
			@Override
			public void run(GameLog e)
			{
				if (s.settings.pullThreeWorkersOnly) 
				{
					if (e.isEnabled())
						e.printMessage(s, GameLog.MessageType.Mining, 
								" " + messages.getString("3ongas"));
					s.dronesGoingOnGas -= 3;
					s.dronesOnGas += 3;
				}
				else
				{
					if (e.isEnabled())
						e.printMessage(s, GameLog.MessageType.Mining, 
								" " + messages.getString("1ongas"));
					s.dronesGoingOnGas--;
					s.dronesOnGas++;
				}

			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s) {
		return !isPossible(s);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if ((s.dronesOnGas + s.dronesGoingOnGas) >= 3 * s.getMineableGasExtractors())
			return false;
		if (s.dronesOnMinerals == 0 || s.settings.pullThreeWorkersOnly && s.dronesOnMinerals < 3)
			return false;
		return true;
	}

}