package com.fray.evo.ui.swingx;

import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public abstract class MText {
	protected Text text;
	
	public MText(Composite parent, int style){
		text = new Text(parent, style);
	}
	
	abstract public Text Text();
	
	abstract public void setText(String string);
	
	abstract public String getText();
	
	abstract public void addSelectionListner(SelectionListener selectionListener);
	
	abstract public void addFocusListener(FocusListener focusListener);
	
	abstract public void setLayoutData(Object layoutData);
}
