package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.TrapezoidalFin;
import panels.TrapezoidalPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class TrapezoidalPanelInitializer implements BaseInitializer {

	private JFrame frame;
	private ResultsReadyListener resultsListener;
	final ImageIcon image = new ImageIcon("resources/trap.jpg");//Y
	final ImageIcon imageB1 = new ImageIcon("resources/trapB1.jpg");//Y
	final ImageIcon imageB2 = new ImageIcon("resources/trapB2.jpg");//Y
	final ImageIcon imageH = new ImageIcon("resources/trapH.jpg");//Y

	public TrapezoidalPanelInitializer(JFrame frame,
			ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	@Override
	public void initializePanel() {
		frame.setVisible(false);//Y
		frame.getContentPane().removeAll();//Y
		final TrapezoidalPanel trapezoidalPanel = new TrapezoidalPanel();//W//Ym
		frame.getContentPane().add(trapezoidalPanel);//W//Ym
		frame.pack();//Y
		trapezoidalPanel.addActionListenerButton(new ActionListener() {//Ym

			@Override
			public void actionPerformed(ActionEvent arg0) {//Ym
				TrapezoidalFin trapezoidalFin = trapezoidalPanel
						.getTrapezoidalFin();
				resultsListener.goToResults(trapezoidalFin);
			}
		});

		FocusListener focusListenerL = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(imageB1);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(image);//Y
			}
		};
		trapezoidalPanel.setBase1Focus(focusListenerL);

		FocusListener focusListenerW = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(imageB2);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(image);//Y
			}

		};
		trapezoidalPanel.setBase2Focus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(imageH);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				trapezoidalPanel.setImage(image);//Y
			}

		};
		trapezoidalPanel.setHeightFocus(focusListenerT);
		frame.setVisible(true);//Y

	}

}
