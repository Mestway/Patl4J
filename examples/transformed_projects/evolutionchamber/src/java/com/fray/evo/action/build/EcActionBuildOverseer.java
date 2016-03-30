package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildOverseer extends EcActionBuildUnit implements Serializable
{
	private static final long serialVersionUID = -5927839271180382939L;


	public EcActionBuildOverseer()
	{
		super(ZergUnitLibrary.Overseer);
	}


	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		return false;
	}
}
