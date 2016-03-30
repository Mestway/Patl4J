package panels;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

//import java.awt.BorderLayout;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

import ui.MainWindow.PickFinInterface;

public class PickFinPanel extends Composite {
	
	private String[] choices={"Rectangular Fin", "Parabolic Fin", "Trapezoidal Fin", "Triangular Fin", "Cylindrical Fin"};
	private Combo comboBox;
	
	protected void checkSubclass(){
		
	}
	
	public PickFinPanel(final PickFinInterface pickFinInterface, Composite parent, int style){
		super(parent, style | SWT.BORDER);
		this.setLayout(new BorderLayout());
		Label label = new Label(this, SWT.NULL);
		label.setAlignment(SWT.CENTER);
		label.setText("Pick Fin Type");
		label.setLayoutData(BorderData.NORTH);
		
		comboBox = new Combo(this, SWT.BORDER | SWT.DROP_DOWN);
		comboBox.setItems(choices);
		
		comboBox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String item=(String)((Combo)arg0.getSource()).getItem(((Combo)arg0.getSource()).getSelectionIndex());
				pickFinInterface.finPiked(item);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		comboBox.setLayoutData(BorderData.CENTER);
		
	}
	
	public String getSelectedItem(){
		return (String)comboBox.getItem(comboBox.getSelectionIndex());
	}
	
}
