package com.fray.evo.ui.swingx;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXTextField;

/**
 * A text field that only allows numeric characters to be entered. Negative
 * values are not allowed.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class NumberTextField extends JXTextField {
	@Override
	protected Document createDefaultModel() {
		return new NumberDocument();
	}

	/**
	 * Allows only numbers to be entered into the text box.
	 * @author mike.angstadt
	 *
	 */
	private static class NumberDocument extends PlainDocument {
		private static final Pattern pattern = Pattern.compile("\\d*");

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
				return;
			}

			String oldString = getText(0, getLength());//Y
			if (pattern.matcher(oldString).matches() && pattern.matcher(str).matches()) {
				//only allow numeric characters to be entered
				super.insertString(offs, str, a);
			}
		}
	}

	/**
	 * Gets the numeric value of the text box.
	 * @return
	 */
	public int getValue() {
		if (getText().isEmpty()) {//Y
			return 0;
		}
		return Integer.parseInt(getText());
	}

	/**
	 * Sets the numeric value of the text box.
	 * @param value
	 */
	public void setValue(int value) {
		if (value < 0) {
			value = 0;
		}
		setText(Integer.toString(value));//Y
	}
}
