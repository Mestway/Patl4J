package com.fray.evo.action;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcState;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;

public abstract class EcAction implements Serializable
{
	private static final long serialVersionUID = -2128362561021916042L;

	public static class CanExecuteResult{
        public boolean can;
        public boolean somethingChanged;
        public CanExecuteResult(boolean can, boolean somethingChanged){
            this.can = can;
            this.somethingChanged = somethingChanged;
        }
    }
	public abstract void execute(EcBuildOrder s, GameLog e);

	@Override
	public String toString()
	{
		return messages.getString(getClass().getSimpleName().replace("EcAction", ""));
	}
	
	public String toBuildOrderString(EcState state)
	{
		//remove all the prefixes
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "");
		if (state.settings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", "+3 Drones on gas").replace("MineMineral", "+3 Drones on minerals");
		}
		else
		{
			result = result.replace("MineGas", "+1 Drone on gas").replace("MineMineral", "+1 Drone on minerals");
		}
		return result;
	}

	public CanExecuteResult canExecute(EcBuildOrder s, GameLog e)
	{
		if (isPossible(s))
			return new CanExecuteResult(true, false);
		s.seconds += 1;
		RunnableAction futureAction;
                boolean changed = false;
		while( (futureAction = s.getFutureAction(s.seconds)) != null ){
			futureAction.run(e);
                        changed = true;
            }
		s.tick(e);

		return new CanExecuteResult(false, changed);
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		return false;
	}

	public abstract boolean isPossible(EcBuildOrder s);

	public static Integer findAllele(List<Class<? extends EcAction>> actionList, EcAction a)
	{
		int actionIndex = actionList.indexOf(a);
		if( actionIndex < 0){
			return null;
		}
		else{
			return Integer.valueOf(actionIndex);
		}
		
		
//		Integer allele = null;
//		for (Integer i : actions.keySet())
//		{
//			Class a2 = actions.get(i);
//			if (!actions.containsValue(a.getClass()))
//				break;
//			if (a2.getName().equals(a.getClass().getName()))
//			{
//				allele = i;
//				break;
//			}
//		}
//		return allele;
	}

}
