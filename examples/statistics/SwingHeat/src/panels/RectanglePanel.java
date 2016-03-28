package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.RectangularFin;

public class RectanglePanel extends JPanel {
	private RectangleMeasurePanel measurePanel;
	private ValuesPanel valuesPanel;
	public RectanglePanel(){
		super(new BorderLayout());//Y
		this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));//Ymm
		ImageIcon image=new ImageIcon("resources/rect.jpg");//Y
		JLabel imageL=new JLabel(image);//Ymm
		 measurePanel=new RectangleMeasurePanel();//W//Ymm
		 valuesPanel=new ValuesPanel();//W//Ymm
		this.add(measurePanel,BorderLayout.NORTH);//W//Ymm
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.add(valuesPanel,BorderLayout.SOUTH);//W//Ymm
	}
	
	public void addActionListenerButton(ActionListener actionListener){
		valuesPanel.getAnalyzeButton().addActionListener(actionListener);//Y
	}

	public RectangularFin getRectangleFin(){
		return new RectangularFin(measurePanel.getLength(),measurePanel.getWidthF(),measurePanel.getThickness(),measurePanel.getAmbientTemp(),measurePanel.getBaseTemp(),valuesPanel.getNumberFins(),valuesPanel.getCondHeat(),valuesPanel.getThermalCond());
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
