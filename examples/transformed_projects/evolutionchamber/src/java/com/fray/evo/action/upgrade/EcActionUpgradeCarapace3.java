package com.fray.evo.action.upgrade;

import com.fray.evo.util.ZergUpgradeLibrary;

public final class EcActionUpgradeCarapace3 extends EcActionUpgrade
{
	private static final long serialVersionUID = 1L;

	@Override
	public void init()
	{
		init(ZergUpgradeLibrary.Armor3);
	}

}