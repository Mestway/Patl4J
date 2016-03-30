package com.fray.evo.action;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;

public final class EcActionMineMineral extends EcAction implements Serializable
{
	private static final long serialVersionUID = 5849476088588414509L;

	@Override
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		if (s.settings.pullThreeWorkersOnly) 
		{
			s.dronesGoingOnMinerals += 3;
			s.dronesOnGas -= 3;
		}
		else
		{
			s.dronesGoingOnMinerals += 1;
			s.dronesOnGas -= 1;
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
								" " + messages.getString("3onminerals"));
					s.dronesGoingOnMinerals -= 3;
					s.dronesOnMinerals += 3;
				}
				else
				{
					e.printMessage(s, GameLog.MessageType.Mining,
							" " + messages.getString("1onminerals"));
					s.dronesGoingOnMinerals--;
					s.dronesOnMinerals++;
				}

			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		return (s.dronesOnGas == 0 || s.settings.pullThreeWorkersOnly && s.dronesOnGas < 3);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return !isInvalid(s);
	}

}