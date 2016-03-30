package panels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

//import java.awt.BorderLayout;
//import java.awt.GridLayout;
//import java.awt.event.FocusListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;

import utility.Utilities;

public class TrapezoidalMeasurePanel extends Composite {

	private Text base1Text;
	private Text base2Text;
	private Text heightText;
	private Text ambientTempText;
	private Text baseTempText;

	protected void checkSubclass(){
		
	}
	
	public TrapezoidalMeasurePanel(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout());

		Composite panelLeft = new Composite(this, SWT.BORDER);
		panelLeft.setLayoutData(BorderData.WEST);
		panelLeft.setLayout(new BorderLayout());
		Composite panelRight = new Composite(this, SWT.BORDER);
		panelRight.setLayoutData(BorderData.EAST);
		panelRight.setLayout(new BorderLayout());
		
		Composite leftLabels = new Composite(panelLeft, SWT.BORDER);
		leftLabels.setLayoutData(BorderData.WEST);
		GridLayout gridLayout0 = new GridLayout(1, true);
		gridLayout0.horizontalSpacing = 1;
		gridLayout0.verticalSpacing = 1;
		leftLabels.setLayout(gridLayout0);
		Composite leftFields = new Composite(panelLeft, SWT.NULL);
		leftFields.setLayoutData(BorderData.EAST);
		GridLayout gridLayout1 = new GridLayout(1, true);
		gridLayout1.horizontalSpacing = 1;
		gridLayout1.verticalSpacing = 1;
		leftFields.setLayout(gridLayout1);

		Label base1Label = new Label(leftLabels, SWT.NULL);
		base1Label.setText("Base");
		base1Text = new Text(leftFields, SWT.NULL);
		base1Text.setTextLimit(10);
		base1Text.addPaintListener(Utilities.normalBorder);
		base1Text.addFocusListener(Utilities.focusListener);

		Label base2Label = new Label(leftLabels, SWT.NULL);
		base2Label.setText("Base");
		base2Text = new Text(leftFields, SWT.NULL);
		base2Text.setTextLimit(10);
		base2Text.addPaintListener(Utilities.normalBorder);
		base2Text.addFocusListener(Utilities.focusListener);

		Label heightLabel = new Label(leftLabels, SWT.NULL);
		heightLabel.setText("Height");
		heightText = new Text(leftFields, SWT.NULL);
		heightText.setTextLimit(10);
		heightText.addPaintListener(Utilities.normalBorder);
		heightText.addFocusListener(Utilities.focusListener);

		Composite rightLabels = new Composite(panelRight, SWT.BORDER);
		GridLayout gridLayout2 = new GridLayout(1, true);
		gridLayout2.horizontalSpacing = 1;
		gridLayout2.verticalSpacing = 1;
		rightLabels.setLayout(gridLayout2);
		rightLabels.setLayoutData(BorderData.WEST);
		
		Composite rightFields = new Composite(panelRight, SWT.BORDER);
		GridLayout gridLayout3 = new GridLayout(1, true);
		gridLayout3.horizontalSpacing = 1;
		gridLayout3.verticalSpacing = 1;
		rightFields.setLayout(gridLayout3);
		rightFields.setLayoutData(BorderData.EAST);

		Label ambientTempLabel = new Label(rightLabels, SWT.NULL);
		ambientTempLabel.setText("Ambient Temperature");
		ambientTempText = new Text(rightFields, SWT.NULL);
		ambientTempText.setTextLimit(10);
		ambientTempText.addPaintListener(Utilities.normalBorder);
		ambientTempText.addFocusListener(Utilities.focusListener);
		
		Label baseTempLabel = new Label(rightLabels, SWT.NULL);
		baseTempLabel.setText("Base Temperature");
		baseTempText = new Text(rightFields, SWT.NULL);
		baseTempText.setTextLimit(10);
		baseTempText.addPaintListener(Utilities.normalBorder);
		baseTempText.addFocusListener(Utilities.focusListener);

	}

	public double getBase1() {
		return Double.parseDouble(base1Text.getText());
	}

	public double getBase2() {
		return Double.parseDouble(base2Text.getText());
	}

	public double getHeightF() {
		return Double.parseDouble(heightText.getText());
	}

	public double getAmbientTemp() {
		return Double.parseDouble(ambientTempText.getText());
	}

	public double getBaseTemp() {
		return Double.parseDouble(baseTempText.getText());
	};
	
	public void setBase1Focus(FocusListener focusListener){
		base1Text.addFocusListener(focusListener);
	}
	
	public void setBase2Focus(FocusListener focusListener){
		base2Text.addFocusListener(focusListener);
	}
	
	public void setHeightFocus(FocusListener focusListener){
		heightText.addFocusListener(focusListener);
	}
}
