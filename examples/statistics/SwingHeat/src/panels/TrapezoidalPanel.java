package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.TrapezoidalFin;
import models.TriangleFin;

public class TrapezoidalPanel extends JPanel {

	private TrapezoidalMeasurePanel measurePanel;
	private ValuesPanel valuesPanel;

	public TrapezoidalPanel() {
		super(new BorderLayout());//Y
		measurePanel = new TrapezoidalMeasurePanel(); //W//Ymm
		valuesPanel = new ValuesPanel(); //W//Ymm
		ImageIcon image=new ImageIcon("resources/trap.jpg");//Y
		JLabel imageL=new JLabel(image);//Ymm
		this.add(measurePanel, BorderLayout.NORTH);//W//Ymm
		this.add(imageL,BorderLayout.CENTER);//Ymm
		this.add(valuesPanel, BorderLayout.SOUTH);//W//Ymm

	}

	public void addActionListenerButton(ActionListener actionListener) {
		valuesPanel.getAnalyzeButton().addActionListener(actionListener);//Y
	}

	public TrapezoidalFin getTrapezoidalFin() {
		return new TrapezoidalFin(measurePanel.getBase1(),
				measurePanel.getBase2(), measurePanel.getHeightF(),
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
