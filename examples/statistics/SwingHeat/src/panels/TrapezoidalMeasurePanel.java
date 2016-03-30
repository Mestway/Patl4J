package panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.Utilities;

public class TrapezoidalMeasurePanel extends JPanel {

	private JTextField base1Text;
	private JTextField base2Text;
	private JTextField heightText;
	private JTextField ambientTempText;
	private JTextField baseTempText;

	public TrapezoidalMeasurePanel() {
		super(new BorderLayout());//Y

		JPanel panelLeft = new JPanel(new BorderLayout());//Ymm
		JPanel panelRight = new JPanel(new BorderLayout());//Ymm

		// LEFT PANEL
		panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JLabel base1Label = new JLabel("Base");//Ymm
		base1Text = new JTextField(10);//Ymm
		base1Text.setBorder(Utilities.normalBorder);//Ymm
		base1Text.addFocusListener(Utilities.focusListener);//Y

		JLabel base2Label = new JLabel("Base");//Ymm
		base2Text = new JTextField(10);//Ymm
		base2Text.setBorder(Utilities.normalBorder);//Ymm
		base2Text.addFocusListener(Utilities.focusListener);//Y

		JLabel heightLabel = new JLabel("Height");//Ymm
		heightText = new JTextField(10);//Ymm
		heightText.setBorder(Utilities.normalBorder);//Ymm
		heightText.addFocusListener(Utilities.focusListener);//Y

		JPanel leftLabels = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		leftLabels.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));//Ymm
		JPanel leftFields = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm

		leftLabels.add(base1Label);//Ymm
		leftLabels.add(base2Label);//Ymm
		leftLabels.add(heightLabel);//Ymm

		leftFields.add(base1Text);//Ymm
		leftFields.add(base2Text);//Ymm
		leftFields.add(heightText);//Ymm

		panelLeft.add(leftLabels, BorderLayout.WEST);//Ymm
		panelLeft.add(leftFields, BorderLayout.EAST);//Ymm

		// RIGHT PANEL
		panelRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JPanel rightLabels = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		rightLabels.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JPanel rightFields = new JPanel(new GridLayout(0, 1, 1, 1));//Ymm
		rightFields.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm

		JLabel ambientTempLabel = new JLabel("Ambient Temperature");//Ymm
		ambientTempText = new JTextField(10);//Ymm
		ambientTempText.setBorder(Utilities.normalBorder);//Ymm
		ambientTempText.addFocusListener(Utilities.focusListener);//Y

		JLabel baseTempLabel = new JLabel("Base Temperature");//Ymm
		baseTempText = new JTextField(10);//Ymm
		baseTempText.setBorder(Utilities.normalBorder);//Ymm
		baseTempText.addFocusListener(Utilities.focusListener);//Y

		rightLabels.add(ambientTempLabel);//Ymm
		rightLabels.add(baseTempLabel);//Ymm

		rightFields.add(ambientTempText);//Ymm
		rightFields.add(baseTempText);//Ymm

		panelRight.add(rightLabels, BorderLayout.WEST);//Ymm
		panelRight.add(rightFields, BorderLayout.CENTER);//Ymm

		this.add(panelLeft, BorderLayout.WEST);//Ymm
		this.add(panelRight, BorderLayout.EAST);//Ymm
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
		base1Text.addFocusListener(focusListener);//Y
	}
	
	public void setBase2Focus(FocusListener focusListener){
		base2Text.addFocusListener(focusListener);//Y
	}
	
	public void setHeightFocus(FocusListener focusListener){
		heightText.addFocusListener(focusListener);//Y
	}
}
