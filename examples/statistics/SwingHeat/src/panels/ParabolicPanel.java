package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.ParabolicFin;

public class ParabolicPanel extends JPanel {
	private ParabolicMeasurePanel measurePanel;
	private ValuesPanel valuesPanel;

	public ParabolicPanel() {
		super(new BorderLayout());//Y
		measurePanel = new ParabolicMeasurePanel(); //W//Ymm
		valuesPanel = new ValuesPanel(); //W//Ymm
		ImageIcon image=new ImageIcon("resources/para.jpg");//Y
		JLabel imageL=new JLabel(image);//Ymm
		this.add(measurePanel, BorderLayout.NORTH);//W//Ymm
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.add(valuesPanel, BorderLayout.SOUTH);//W//Ymm

	}

	public void addActionListenerButton(ActionListener actionListener) {
		valuesPanel.getAnalyzeButton().addActionListener(actionListener);//Y
	}

	public ParabolicFin getParabolicFin() {
		return new ParabolicFin(measurePanel.getLength(),
				measurePanel.getWidthF(), measurePanel.getThickness(),
				measurePanel.getAmbientTemp(), measurePanel.getBaseTemp(),
				valuesPanel.getNumberFins(), valuesPanel.getCondHeat(),
				valuesPanel.getThermalCond());
	}

	public void setImage(ImageIcon image) {
		JLabel imageL = new JLabel(image);//Ymm
		imageL.setPreferredSize(new Dimension(200, 300));//Y
		BorderLayout layout = (BorderLayout) this.getLayout();//Y
		this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		this.revalidate();
		this.add(imageL, BorderLayout.CENTER);//Ymm
		this.repaint();
	}

	public void setLengthFocus(FocusListener focusListener){
		measurePanel.setLengthFocus(focusListener);
	}
	
	public void setWidthFocus(FocusListener focusListener){
		measurePanel.setWidthFocus(focusListener);
	}
	
	public void setThicknessFocus(FocusListener focusListener){
		measurePanel.setThicknessFocus(focusListener);
	}
}
