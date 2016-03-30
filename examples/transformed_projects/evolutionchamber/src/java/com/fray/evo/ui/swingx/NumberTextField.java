package com.fray.evo.ui.swingx;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("serial")
public class NumberTextField extends MText {
	
	private static final Pattern pattern = Pattern.compile("\\d*");
	
	public NumberTextField(Composite parent, int style) {
		super(parent, style);
		text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = pattern.matcher(e.text).matches();
			}
		});
		
	}

	public int getValue() {
		if (text.getText().isEmpty()) {
			return 0;
		}
		return Integer.parseInt(text.getText());
	}

	public void setValue(int value) {
		if (value < 0) {
			value = 0;
		}
		text.setText(Integer.toString(value));
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
