package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergLibrary;
import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildQueen extends EcActionBuildUnit implements Serializable
{
	private static final long serialVersionUID = 1780688280823177517L;

	public EcActionBuildQueen()
	{
		super(ZergUnitLibrary.Queen);
	}

	@Override
	protected void postExecute(final EcBuildOrder s, final GameLog e)
	{
            super.postExecute(s, e);
		if (s.larva.size() > s.hasQueen.size())
		{
			spawnLarva(s, e);
		}
		else
			s.addFutureAction(5, new RunnableAction()
			{
				@Override
				public void run(GameLog e)
				{
					if (s.larva.size() > s.hasQueen.size())
						spawnLarva(s, e);
					else
						s.addFutureAction(5, this);
				}
			});
	}

	private void spawnLarva(final EcBuildOrder s, final GameLog e)
	{
		int hatchWithoutQueen = s.hasQueen.size();
		if (s.larva.size() > hatchWithoutQueen)
		{
			s.hasQueen.add(true);

			final int hatchIndex = hatchWithoutQueen;
			s.addFutureAction(40, new RunnableAction()
			{
				@Override
				public void run(GameLog e)
				{
					if (e.isEnabled() && s.getLarva() < s.bases() * 19)
						e.printMessage(s, GameLog.MessageType.Obtained,
								" @"+messages.getString(ZergLibrary.Hatchery.getName()) + " #" + (hatchIndex+1) +" "
								+ messages.getString(ZergLibrary.Larva.getName())
								+ " +"
								+ (Math.min(19, s.getLarva(hatchIndex) + 2) - s
										.getLarva(hatchIndex)));
					s.setLarva(hatchIndex, Math.min(19, s.getLarva(hatchIndex) + 2));
					s.addFutureAction(1, new RunnableAction()
					{
						@Override
						public void run(GameLog e)
						{
							if (e.isEnabled() && s.getLarva() < s.bases() * 19)
								e.printMessage(s, GameLog.MessageType.Obtained,
										" @"+messages.getString(ZergLibrary.Hatchery.getName()) + " #" + (hatchIndex+1) +" "
										+ messages.getString(ZergLibrary.Larva.getName())
										+ " +"
										+ (Math.min(19, s.getLarva(hatchIndex) + 2) - s
												.getLarva(hatchIndex)));
							s.setLarva(hatchIndex, Math.min(19, s.getLarva(hatchIndex) + 2));
						}
					});
					s.addFutureAction(45, this);
					s.larvaProduction.decrement(hatchIndex);
				}
			});
		}
	}

}
