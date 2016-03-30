package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildCorruptor extends EcActionBuildUnit implements Serializable
{	
	private static final long serialVersionUID = 1L;

	public EcActionBuildCorruptor()
	{
		super(ZergUnitLibrary.Corruptor);
	}

}
