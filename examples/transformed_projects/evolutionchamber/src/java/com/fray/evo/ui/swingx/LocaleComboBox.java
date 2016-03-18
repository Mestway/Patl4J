package com.fray.evo.ui.swingx;

import java.util.Locale;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class LocaleComboBox {
	private static final long serialVersionUID = 784618264004326168L;
	private Combo combo;
	public LocaleComboBox(Composite parent, int style, Locale[] locales, Locale currentLocale) {
		combo = new Combo(parent, style);
		for (Locale locale : locales) {
			Item item = new Item();
			item.locale = locale;
			item.text = locale.getDisplayLanguage(currentLocale);
			String url = LocaleComboBox.class.getResource(locale.getLanguage() + ".png").getPath();
			if (url != null) {
				item.image = new Image(Display.getCurrent(), url);
			}
			combo.add(item.text);
			combo.setData(item.text, item);
		}

		setSelectedLocale(currentLocale);
	}

	public Locale getSelectedLocale() {
		String itemText = combo.getText();
		Item item = (Item) combo.getData(itemText);
		return item.locale;
	}

	public void setSelectedLocale(Locale locale) {
		String[] items = combo.getItems();
		for (int i = 0; i < items.length; i++) {
			String itemText = items[i];
			Item item = (Item) combo.getData(itemText);;
			if (item.locale.getLanguage().equals(locale.getLanguage())) {
				combo.select(i);
				if (item.locale.getCountry().equals(locale.getCountry())) {
					break;
				}
			}
		}
	}

	private static class Item {
		public String text;
		public Image image;
		public Locale locale;
		
	}
	
	public void setSize(int x, int y){
		combo.setSize(x, y);
	}
	
	public void setLayoutData(Object layoutData){
		combo.setLayoutData(layoutData);
	}
	
	
	public void addSelectionListener(SelectionListener selectionListener){
		combo.addSelectionListener(selectionListener);
	}
	
	public Combo getCombo(){
		return combo;
	}
	
	public void setEnabled(boolean enabled){
		combo.setEnabled(enabled);
	}
}
