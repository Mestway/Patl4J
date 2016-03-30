package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.TriangleFin;
import panels.TrianglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class TrianglePanelInitializer implements BaseInitializer {

	private JFrame frame;
	private ResultsReadyListener resultsListener;
	final ImageIcon image = new ImageIcon("resources/tri.jpg");//Y
	final ImageIcon imageL = new ImageIcon("resources/triL.jpg");//Y
	final ImageIcon imageW = new ImageIcon("resources/triW.jpg");//Y
	final ImageIcon imageT = new ImageIcon("resources/triTh.jpg");//Y

	public TrianglePanelInitializer(JFrame frame, ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	@Override
	public void initializePanel() {
		frame.setVisible(false);//Y
		frame.getContentPane().removeAll();//Y
		final TrianglePanel trianglePanel = new TrianglePanel();//W//Ym
		frame.getContentPane().add(trianglePanel);//W//Ym
		frame.pack();//Y

		trianglePanel.addActionListenerButton(new ActionListener() {//Ym
			@Override
			public void actionPerformed(ActionEvent arg0) {//Ym
				TriangleFin triangleFin = trianglePanel.getTriangleFin();
				resultsListener.goToResults(triangleFin);
			}
		});

		FocusListener focusLength = new FocusListener() {//Ym

			@Override
			public void focusGained(FocusEvent arg0) {//Ym
				trianglePanel.setImage(imageL);//Y
			}

			@Override
			public void focusLost(FocusEvent arg0) {//Ym
				trianglePanel.setImage(image);//Y
			}

		};
		trianglePanel.setLengthFocus(focusLength);

		FocusListener focusListenerW = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				trianglePanel.setImage(imageW);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				trianglePanel.setImage(image);//Y
			}

		};
		trianglePanel.setWidthFocus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {//Ym
			@Override
			public void focusGained(FocusEvent e) {//Ym
				trianglePanel.setImage(imageT);//Y
			}

			@Override
			public void focusLost(FocusEvent e) {//Ym
				trianglePanel.setImage(image);//Y
			}

		};
		trianglePanel.setThicknessFocus(focusListenerT);

		frame.setVisible(true);//Y
	}

}
