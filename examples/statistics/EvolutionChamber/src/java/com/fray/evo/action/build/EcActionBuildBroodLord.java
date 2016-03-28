package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildBroodLord extends EcActionBuildUnit implements Serializable
{
	private static final long serialVersionUID = 1L;

	public EcActionBuildBroodLord()
	{
		super(ZergUnitLibrary.Broodlord);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHives() == 0 )
			return true;
		if (s.getGreaterSpire() == 0)
			return true;
		if (!s.hasSupply(2))
			return true;
		return super.isInvalid(s);
	}
}
