package org.openfuxml.client.gui.util;

import org.eclipse.swt.widgets.Combo;

public class GuiSettingsValidator
{
	public static synchronized void checkSet(Combo cbo) throws IllegalArgumentException
	{
		if(cbo.getText().equals(""))
		{
			throw new IllegalArgumentException("You have to chose a "+cbo.getData());
		}
	}
}
