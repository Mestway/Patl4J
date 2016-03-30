package utility;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Utilities {
	
	public static Border normalBorder=BorderFactory.createLineBorder(Color.BLACK);
	public static Border highlightBorder=BorderFactory.createLineBorder(Color.BLUE);
	
	
	public static FocusListener focusListener=new FocusListener() {//Ym
		
		@Override
		public void focusLost(FocusEvent focusEvent) {//Ym
			((JTextField)focusEvent.getSource()).setBorder(normalBorder);//Y
		}
		
		@Override
		public void focusGained(FocusEvent focusEvent) {//Ym
			((JTextField)focusEvent.getSource()).setBorder(highlightBorder);//Y
			
		}
	};
	
	

}
