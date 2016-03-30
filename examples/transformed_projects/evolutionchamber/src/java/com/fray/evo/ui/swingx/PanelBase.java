package com.fray.evo.ui.swingx;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TabFolder;


public abstract class PanelBase extends Composite
{
	private static final long serialVersionUID = 4799832117789414417L;
	private final EcSwingX swingX;

	protected PanelBase(TabFolder parent, EcSwingX swingX, int style, Layout layout)
	{
        super(parent, style);
        this.swingX = swingX;
        this.setLayout(layout);
    }

    protected Composite addRadioButtonBox(String title, String[] captions, int defaultSelected, final CustomActionListener a)
    {	
        
        Group group = new Group(this, SWT.NONE);
        group.setText(title);
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, swingX.marginleft);
        formData.right = new FormAttachment(97, swingX.marginright);
        formData.top = new FormAttachment(0, swingX.margintop);
        group.setLayoutData(formData);
        
        GridLayout gridLayout = new GridLayout(captions.length, true);
        group.setLayout(gridLayout);
        
        for(int i = 0; i < captions.length; i++){
        	Button button = new Button(group, SWT.RADIO);
        	button.setText(captions[i]);
        	button.addSelectionListener(a);
        	swingX.inputControls.add(button);
        	if(i == defaultSelected){
        		button.setSelection(true);
        	}
        }
        
        return group;
    }

    protected Button addCheck(String name, final CustomActionListener a)
    {
    	
    	final Button checkBox = new Button(this, SWT.CHECK);
    	checkBox.setText(name);
    	
    	
    	int increament = 49;
    	if(name.length() == 2){
    		increament = 24;
    	}
    	
		FormData formData = new FormData();
		formData.left = new FormAttachment(swingX.gridx, swingX.marginleft);
		formData.right = new FormAttachment(swingX.gridx+increament, swingX.marginright);
		formData.top = new FormAttachment(0, swingX.margintop);
		swingX.gridx += increament+1;
    	
    	checkBox.setLayoutData(formData);
    	checkBox.addSelectionListener(a);
    	
        swingX.inputControls.add(checkBox);
        return checkBox;
    }
}
