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

import models.TrapezoidalFin;

public class TrapezoidalPanel extends Composite {

	private TrapezoidalMeasurePanel measurePanel;
	private ValuesPanel valuesPanel;

	protected void checkSubclass(){
		
	}
	
	public TrapezoidalPanel(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout());
		measurePanel = new TrapezoidalMeasurePanel(this, SWT.NULL);
		measurePanel.setLayoutData(BorderData.NORTH);
		valuesPanel = new ValuesPanel(this, SWT.NULL);
		valuesPanel.setLayoutData(BorderData.SOUTH);
		
		Image image = new Image(Display.getCurrent(), "resources/trap.jpg");
		Label imageL = new Label(this, SWT.NULL);
		imageL.setLayoutData(BorderData.CENTER);
		imageL.setImage(image);

	}

	public void addActionListenerButton(SelectionListener actionListener) {
		valuesPanel.getAnalyzeButton().addSelectionListener(actionListener);
	}

	public TrapezoidalFin getTrapezoidalFin() {
		return new TrapezoidalFin(measurePanel.getBase1(),
				measurePanel.getBase2(), measurePanel.getHeightF(),
				measurePanel.getAmbientTemp(), measurePanel.getBaseTemp(),
				valuesPanel.getNumberFins(), valuesPanel.getCondHeat(),
				valuesPanel.getThermalCond());
	}

	public void setImage(Image image) {

		BorderLayout layout = (BorderLayout) this.getLayout();
		layout.getControl(BorderData.CENTER).dispose();
		
		Label imageL = new Label(this, SWT.NULL);
		imageL.setLayoutData(BorderData.CENTER);
		imageL.setImage(image);
		imageL.setSize(new Point(200, 300));
		
		this.pack();
	}

	public void setBase1Focus(FocusListener focusListener) {

		measurePanel.setBase1Focus(focusListener);
	}
	
	public void setBase2Focus(FocusListener focusListener) {

		measurePanel.setBase2Focus(focusListener);
	}
	
	public void setHeightFocus(FocusListener focusListener) {

		measurePanel.setHeightFocus(focusListener);
	}
}
