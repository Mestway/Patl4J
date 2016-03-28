package com.fray.evo.ui.swingx;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXTextField;

/**
 * A text field that only allows time-related characters to be entered.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class TimeTextField extends JXTextField {
	@Override
	protected Document createDefaultModel() {
		return new TimeDocument();
	}

	private static class TimeDocument extends PlainDocument {
		private static final Pattern pattern = Pattern.compile("[\\d:]*");

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
				return;
			}

			String oldString = getText(0, getLength());//Y
			if (pattern.matcher(oldString).matches() && pattern.matcher(str).matches()) {
				//only allow time-related characters to be entered
				super.insertString(offs, str, a);
			}
		}
	}
}
