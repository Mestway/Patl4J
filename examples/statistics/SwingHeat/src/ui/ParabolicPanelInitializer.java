package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.ParabolicFin;
import panels.ParabolicPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class ParabolicPanelInitializer implements BaseInitializer{
	
	private JFrame frame;
	private ResultsReadyListener resultsListener;
	final ImageIcon image = new ImageIcon("resources/para.jpg");//Y
	final ImageIcon imageL = new ImageIcon("resources/paraL.jpg");//Y
	final ImageIcon imageW = new ImageIcon("resources/paraW.jpg");//Y
	final ImageIcon imageT = new ImageIcon("resources/paraTh.jpg");//Y

	public ParabolicPanelInitializer(JFrame frame,ResultsReadyListener listener){
		this.frame=frame;
		this.resultsListener=listener;
	}
	
	@Override
	public void initializePanel() {
		frame.setVisible(false);//Y
		frame.getContentPane().removeAll();//Y
		final ParabolicPanel parabolicPanel = new ParabolicPanel();//W//Ym
		frame.getContentPane().add(parabolicPanel);//W//Ym
		frame.pack();//Y

		parabolicPanel.addActionListenerButton(new ActionListener() {//Ym

			@Override
			public void actionPerformed(ActionEvent arg0) {//Ym
				ParabolicFin parabolicFin = parabolicPanel.getParabolicFin();
				resultsListener.goToResults(parabolicFin);
			}
		});


		FocusListener focusListenerL = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				parabolicPanel.setImage(imageL);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				parabolicPanel.setImage(image);//Y
			}
		};
		parabolicPanel.setLengthFocus(focusListenerL);

		FocusListener focusListenerW = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				parabolicPanel.setImage(imageW);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				parabolicPanel.setImage(image);//Y
			}

		};
		parabolicPanel.setWidthFocus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				parabolicPanel.setImage(imageT);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				parabolicPanel.setImage(image);//Y
			}

		};
		parabolicPanel.setThicknessFocus(focusListenerT);

		frame.setVisible(true);//Y
	
	}

	
}
