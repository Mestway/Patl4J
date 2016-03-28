package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.RectangularFin;
import panels.RectanglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class RectanglePanelInitializer implements BaseInitializer {

	private JFrame frame;
	private ResultsReadyListener resultsListener;
	private final ImageIcon image = new ImageIcon("resources/rect.jpg");//Y
	private final ImageIcon imageL = new ImageIcon("resources/rectL.jpg");//Y
	private final ImageIcon imageW = new ImageIcon("resources/rectW.jpg");//Y
	private final ImageIcon imageT = new ImageIcon("resources/rectTh.jpg");//Y

	public RectanglePanelInitializer(JFrame frame, ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	public void initializePanel() {
		{
			frame.setVisible(false);//Y
			frame.getContentPane().removeAll();//Y
			final RectanglePanel rectanglePanel = new RectanglePanel();//W//Ym
			frame.getContentPane().add(rectanglePanel);//W//Ym
			frame.pack();//Y

			rectanglePanel.addActionListenerButton(new ActionListener() {//Ym

				@Override
				public void actionPerformed(ActionEvent arg0) {//Ym
					RectangularFin rectangularFin = rectanglePanel
							.getRectangleFin();
					resultsListener.goToResults(rectangularFin);
				}
			});

			FocusListener focusListenerL = new FocusListener() {//Ym
				@Override
				public void focusGained(FocusEvent e) {//Ym
					rectanglePanel.setImage(imageL);//Y
				}

				@Override
				public void focusLost(FocusEvent e) {//Ym
					rectanglePanel.setImage(image);//Y
				}
			};
			rectanglePanel.setLengthFocus(focusListenerL);

			FocusListener focusListenerW = new FocusListener() {//Ym
				@Override
				public void focusGained(FocusEvent e) {//Ym
					rectanglePanel.setImage(imageW);//Y
				}

				@Override
				public void focusLost(FocusEvent e) {//Ym
					rectanglePanel.setImage(image);//Y
				}

			};
			rectanglePanel.setWidthFocus(focusListenerW);

			FocusListener focusListenerT = new FocusListener() {//Ym
				@Override
				public void focusGained(FocusEvent e) {//Ym
					rectanglePanel.setImage(imageT);//Y
				}

				@Override
				public void focusLost(FocusEvent e) {//Ym
					rectanglePanel.setImage(image);//Y
				}

			};
			rectanglePanel.setThicknessFocus(focusListenerT);
			frame.setVisible(true);//Y
		}
	}
}
