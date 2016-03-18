package panels;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;

//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;
import utility.Utilities;

public class ValuesPanel extends Composite {

	private Button analyzeButton;
	private Text numberFinsText;
	private Text thermalCondText;
	private Text condHeatText;

	public ValuesPanel(Composite parent, int style) {
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
		
		Label numberFinsLabel = new Label(leftLabels, SWT.NULL);
		numberFinsLabel.setText("Number of Fins");
		numberFinsText = new Text(leftFields, SWT.NULL);
		numberFinsText.setTextLimit(10);
		numberFinsText.addPaintListener(Utilities.normalBorder);
		numberFinsText.addFocusListener(Utilities.focusListener);
		
		Label thermalCondLabel = new Label(leftLabels, SWT.NULL);
		thermalCondLabel.setText("Thermal Conductivity");
		thermalCondText = new Text(leftFields, SWT.NULL);
		thermalCondText.setTextLimit(10);
		thermalCondText.addPaintListener(Utilities.normalBorder);
		thermalCondText.addFocusListener(Utilities.focusListener);
		
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
		rightFields.setLayoutData(BorderData.CENTER);
		
		Label condHeatLabel = new Label(rightLabels, SWT.NULL);
		condHeatLabel.setText("Conductive Heat Transfer");
		condHeatText = new Text(rightFields, SWT.NULL);
		condHeatText.setTextLimit(10);
		condHeatText.addPaintListener(Utilities.normalBorder);
		condHeatText.addFocusListener(Utilities.focusListener);
		
		analyzeButton = new Button(panelRight, SWT.PUSH | SWT.BORDER | SWT.CENTER);
		analyzeButton.setLayoutData(BorderData.SOUTH);
		analyzeButton.setText("Analyze");
		analyzeButton.setSize(new Point(100, 200));
		
	}

	public Button getAnalyzeButton() {
		return analyzeButton;
	}

	public int getNumberFins(){
		return Integer.parseInt(numberFinsText.getText());
	}
	
	public double getThermalCond(){
		return Double.parseDouble(thermalCondText.getText());
	}
	
	public double getCondHeat(){
		return Double.parseDouble(condHeatText.getText());
	}
}
