package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.Utilities;

public class ValuesPanel extends JPanel {

	private JButton analyzeButton;
	private JTextField numberFinsText;
	private JTextField thermalCondText;
	private JTextField condHeatText;

	public ValuesPanel() {
		super(new BorderLayout());

		JPanel panelLeft = new JPanel(new BorderLayout());//Ymm
		JPanel panelRight = new JPanel(new BorderLayout());//Ymm

		// LEFT PANEL
		panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Y
		JPanel leftLabels = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		leftLabels.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));//Ymm
		JPanel leftFields = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm

		JLabel numberFinsLabel = new JLabel("Number of Fins");//Ymm
		numberFinsText = new JTextField(10);//Ymm
		numberFinsText.setBorder(Utilities.normalBorder);//Ymm
		numberFinsText.addFocusListener(Utilities.focusListener);//Y

		JLabel thermalCondLabel = new JLabel("Thermal Conductivity");//Ymm
		thermalCondText = new JTextField(10);//Ymm
		thermalCondText.setBorder(Utilities.normalBorder);//Ymm
		thermalCondText.addFocusListener(Utilities.focusListener);//Y

		leftLabels.add(numberFinsLabel);//Ymm
		leftLabels.add(thermalCondLabel);//Ymm

		leftFields.add(numberFinsText);//Ymm
		leftFields.add(thermalCondText);//Ymm
		panelLeft.add(leftLabels, BorderLayout.WEST);//Ymm
		panelLeft.add(leftFields, BorderLayout.EAST);//Ymm

		// RIGHT PANEL
		panelRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JPanel rightLabels = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		rightLabels.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JPanel rightFields = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		rightFields.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm

		JLabel condHeatLabel = new JLabel("Conductive Heat Transfer");//Ymm
		condHeatText = new JTextField(10);//Ymm
		condHeatText.setBorder(Utilities.normalBorder);//Ymm
		condHeatText.addFocusListener(Utilities.focusListener);//Y

		rightLabels.add(condHeatLabel);//Ymm
		rightFields.add(condHeatText);//Ymm

		analyzeButton = new JButton("Analyze");//Ymm
		analyzeButton.setAlignmentX(CENTER_ALIGNMENT);//Y
		analyzeButton.setPreferredSize(new Dimension(100, 20));//Y
		analyzeButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));//Ymm
		

		panelRight.add(rightLabels, BorderLayout.WEST);//Ymm
		panelRight.add(rightFields, BorderLayout.CENTER);//Ymm
		panelRight.add(analyzeButton, BorderLayout.SOUTH);//Ymm

		this.add(panelLeft, BorderLayout.WEST);//Ymm
		this.add(panelRight, BorderLayout.EAST);//Ymm
		
	}

	public JButton getAnalyzeButton() {
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
