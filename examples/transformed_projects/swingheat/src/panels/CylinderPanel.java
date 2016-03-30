package panels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusListener;
//
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

import models.CylinderFin;

public class CylinderPanel extends Composite {

	private ValuesPanel valuesPanel;
	private CylinderMeasurePanel measurePanel;
	
	protected void checkSubclass(){
		
	}
	
	public CylinderPanel(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout());
		measurePanel = new CylinderMeasurePanel(this, SWT.NULL);
		measurePanel.setLayoutData(BorderData.NORTH);
		valuesPanel = new ValuesPanel(this, SWT.NULL);
		valuesPanel.setLayoutData(BorderData.SOUTH);
		Image image = new Image(Display.getCurrent(), "resources/para.jpg");
		Label imageL = new Label(this, SWT.NULL);
		imageL.setImage(image);
		imageL.setLayoutData(BorderData.CENTER);
	}
	
	public void addActionListenerButton(SelectionListener actionListener){
		valuesPanel.getAnalyzeButton().addSelectionListener(actionListener);
	}
	
	public CylinderFin getCylinderFin(){
		return new CylinderFin(measurePanel.getDiameter(),measurePanel.getLength(),measurePanel.getAmbientTemp(),measurePanel.getBaseTemp(),valuesPanel.getNumberFins(),valuesPanel.getCondHeat(),valuesPanel.getThermalCond());
	}
	
	public void setImage(Image image) {

		BorderLayout layout = (BorderLayout) this.getLayout();
		layout.getControl(BorderData.CENTER).dispose();
		
		Label imageL = new Label(this, SWT.NULL);
		imageL.setImage(image);
		this.setLayoutData(BorderData.CENTER);
		imageL.setSize(new Point(200, 300));
		this.pack();
	}
	
	public void setDiameterFocus(FocusListener focusListener){
		measurePanel.setDiameterFocus(focusListener);
	}
	
	public void setLengthFocus(FocusListener focusListener){
		measurePanel.setLengthFocus(focusListener);
	}

}
