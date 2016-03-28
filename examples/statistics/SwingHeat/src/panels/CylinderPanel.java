package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.CylinderFin;

public class CylinderPanel extends JPanel {

	private ValuesPanel valuesPanel;
	private CylinderMeasurePanel measurePanel;
	
	public CylinderPanel() {
		super(new BorderLayout());
		measurePanel = new CylinderMeasurePanel();  //W//Ymm
		valuesPanel = new ValuesPanel(); //W//Ymm
		ImageIcon image=new ImageIcon("resources/para.jpg");//Y
		JLabel imageL=new JLabel(image);//Ymm
		this.add(measurePanel, BorderLayout.NORTH);//W//Ymm
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.add(valuesPanel, BorderLayout.SOUTH);//W//Ymm

	}
	
	public void addActionListenerButton(ActionListener actionListener){
		valuesPanel.getAnalyzeButton().addActionListener(actionListener);//Y
	}
	
	public CylinderFin getCylinderFin(){
		return new CylinderFin(measurePanel.getDiameter(),measurePanel.getLength(),measurePanel.getAmbientTemp(),measurePanel.getBaseTemp(),valuesPanel.getNumberFins(),valuesPanel.getCondHeat(),valuesPanel.getThermalCond());
	}
	
	public void setImage(ImageIcon image) {
		JLabel imageL = new JLabel(image);//Ymm
		imageL.setPreferredSize(new Dimension(200, 300));//Y
		BorderLayout layout = (BorderLayout) this.getLayout();//Y
		this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		this.revalidate();
		this.add(imageL, BorderLayout.CENTER); //Ymm
		this.repaint();
	}
	
	public void setDiameterFocus(FocusListener focusListener){
		measurePanel.setDiameterFocus(focusListener);
	}
	
	public void setLengthFocus(FocusListener focusListener){
		measurePanel.setLengthFocus(focusListener);
	}

}
