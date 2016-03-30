package com.fray.evo.ui.swingx;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public abstract class CustomActionListener implements SelectionListener
{
	abstract void reverse(Object o); 

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}
