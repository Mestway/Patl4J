package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.TriangleFin;

public class TrianglePanel extends JPanel {

	private TriangleMeasurePanel measurePanel;
	private ValuesPanel valuesPanel;

	public TrianglePanel() {
		super(new BorderLayout());//Y
		measurePanel = new TriangleMeasurePanel(); //W//Ymm
		valuesPanel = new ValuesPanel(); //W//Ymm
		ImageIcon image=new ImageIcon("resources/triL.jpg");//Y
		JLabel imageL=new JLabel(image);//Ymm
		imageL.setPreferredSize(new Dimension(200,300));//Y
		this.add(measurePanel, BorderLayout.NORTH);//W//Ymm
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.add(valuesPanel, BorderLayout.SOUTH);//W//Ymm

		
	}

	public void addActionListenerButton(ActionListener actionListener) {
		valuesPanel.getAnalyzeButton().addActionListener(actionListener);//Y
	}
	
	public TriangleFin getTriangleFin(){
		return new TriangleFin(measurePanel.getLength(),measurePanel.getWidthF(),measurePanel.getThickness(),measurePanel.getAmbientTemp(),measurePanel.getBaseTemp(),valuesPanel.getNumberFins(),valuesPanel.getCondHeat(),valuesPanel.getThermalCond());
	}

	public void setImage(ImageIcon image){
		JLabel imageL=new JLabel(image);//Ymm
		imageL.setPreferredSize(new Dimension(200,300));//Y
		BorderLayout layout = (BorderLayout) this.getLayout();//Y
		this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		this.revalidate();
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.repaint();
	}
	
	public void setLengthFocus(FocusListener focusListener){
		measurePanel.setLengthFocus(focusListener);
	}
	
	public void setWidthFocus(FocusListener focusListener){
		measurePanel.setWidthFocus(focusListener);
	}
	
	public void setThicknessFocus(FocusListener focusListener){
		measurePanel.setThicknessFocus	(focusListener);
	}
}