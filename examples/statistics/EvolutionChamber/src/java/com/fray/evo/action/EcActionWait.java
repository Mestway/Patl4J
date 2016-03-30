package com.fray.evo.action;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;

public final class EcActionWait extends EcAction implements Serializable
{
	private static final long serialVersionUID = -2361841555630773088L;
	boolean	go	= false;

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
		s.waits += 1;
	}

	@Override
	public EcAction.CanExecuteResult canExecute(EcBuildOrder s, GameLog e)
	{
		if (isPossible(s))
			return new CanExecuteResult(true, false);
		s.seconds += 1;
		RunnableAction futureAction;
                boolean changed = false;
		while( ( futureAction = s.getFutureAction( s.seconds ) ) != null ) {
			futureAction.run(e);
			go = true;
                        changed = true;
		}
		s.tick(e);
		return new CanExecuteResult(true, changed);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.nothingGoingToHappen())
			return true;
		return super.isInvalid(s);
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return go;
	}

}
