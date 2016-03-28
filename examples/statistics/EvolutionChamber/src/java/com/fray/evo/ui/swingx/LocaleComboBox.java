package com.fray.evo.ui.swingx;

import java.awt.Component;
import java.net.URL;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A combobox that displays a list of languages.
 * 
 * @author mike.angstadt
 * 
 */
public class LocaleComboBox extends JComboBox {
	private static final long serialVersionUID = 784618264004326168L;

	/**
	 * Constructor.
	 * 
	 * @param locales the Locales to be shown in the combobox
	 * @param currentLocale the current Locale of the application
	 */
	public LocaleComboBox(Locale[] locales, Locale currentLocale) {
		//add the items to the combobox
		for (Locale locale : locales) {
			Item item = new Item();
			item.locale = locale;
			item.text = locale.getDisplayLanguage(currentLocale);
			URL url = LocaleComboBox.class.getResource(locale.getLanguage() + ".png");
			if (url != null) {
				item.image = new ImageIcon(url);
			}
			addItem(item);//Y
		}

		setRenderer(new ComboBoxRenderer());
		setSelectedLocale(currentLocale);
	}

	/**
	 * Gets the selected Locale.
	 * 
	 * @return
	 */
	public Locale getSelectedLocale() {
		Item item = (Item) getSelectedItem();
		return item.locale;
	}

	/**
	 * Sets the selected Locale
	 * 
	 * @param locale
	 */
	public void setSelectedLocale(Locale locale) {
		for (int i = 0; i < getItemCount(); i++) {
			Item item = (Item) getItemAt(i);
			if (item.locale.getLanguage().equals(locale.getLanguage())) {
				setSelectedIndex(i);
				if (item.locale.getCountry().equals(locale.getCountry())) {
					break;
				}
			}
		}
	}

	/**
	 * Represents an item in the combobox
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class Item {
		public String text;
		public ImageIcon image;
		public Locale locale;
	}

	/**
	 * Allows for a flag icon to be shown alongside each language.
	 * 
	 * @see http://www.java2s.com/Code/Java/Swing-JFC/CustomComboBoxwithImage.htm
	 * @author mike.angstadt
	 * 
	 */
	private static class ComboBoxRenderer extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = -2560362531812495409L;

		public ComboBoxRenderer() {
			setOpaque(true);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Item item = (Item) value;

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			setIcon(item.image);
			setText(item.text);
			setFont(list.getFont());

			return this;
		}
	}
}
