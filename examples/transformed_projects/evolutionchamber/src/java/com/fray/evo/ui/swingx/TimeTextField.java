package com.fray.evo.ui.swingx;

import java.util.regex.Pattern;

import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Text;


@SuppressWarnings("serial")
public class TimeTextField extends MText{
	
	private static final Pattern pattern = Pattern.compile("[\\d:]*");
	
	public TimeTextField(Composite parent, int style) {
		super(parent, style);
		
		text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = pattern.matcher(e.text).matches();
			}
		});
	}
	
	public Text Text(){
		return text;
	}
	
	public void setText(String string){
		text.setText(string);
	}
	
	public String getText(){
		return text.getText();
	}
	
	public void addSelectionListner(SelectionListener selectionListener){
		text.addSelectionListener(selectionListener);
	}
	
	public void addFocusListener(FocusListener focusListener){
		text.addFocusListener(focusListener);
	}

	@Override
	public void setLayoutData(Object layoutData) {
		text.setLayoutData(layoutData);
	}

}
