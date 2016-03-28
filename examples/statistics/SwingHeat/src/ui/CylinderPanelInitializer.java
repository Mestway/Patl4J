package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.CylinderFin;
import panels.CylinderPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class CylinderPanelInitializer implements BaseInitializer {

	private JFrame frame;
	private ResultsReadyListener resultsListener;
	private final ImageIcon image = new ImageIcon("resources/trap.jpg");//Y
	private final ImageIcon imageD = new ImageIcon("resources/trapB1.jpg");//Y
	private final ImageIcon imageL = new ImageIcon("resources/trapB2.jpg");//Y

	public CylinderPanelInitializer(JFrame frame, ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	@Override
	public void initializePanel() {
		frame.setVisible(false);//Y
		final CylinderPanel cylinderPanel = new CylinderPanel();//W//Ymm
		frame.getContentPane().removeAll();//Y
		frame.getContentPane().add(cylinderPanel);//W//Ymm
		frame.pack();//Y

		cylinderPanel.addActionListenerButton(new ActionListener() {//Ym

			@Override
			public void actionPerformed(ActionEvent arg0) {//Ym
				CylinderFin cylinderFin = cylinderPanel.getCylinderFin();
				resultsListener.goToResults(cylinderFin);
			}
		});

		FocusListener focusListenerD = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				cylinderPanel.setImage(imageD);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				cylinderPanel.setImage(image);//Y
			}
		};
		cylinderPanel.setDiameterFocus(focusListenerD);

		FocusListener focusListenerL = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				cylinderPanel.setImage(imageL);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				cylinderPanel.setImage(image);//Y
			}

		};
		cylinderPanel.setLengthFocus(focusListenerL);
		frame.setVisible(true);
	}

}
