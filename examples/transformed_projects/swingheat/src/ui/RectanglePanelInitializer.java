package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;

import models.RectangularFin;
import panels.RectanglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class RectanglePanelInitializer implements BaseInitializer {

	private Shell frame;
	private ResultsReadyListener resultsListener;
	private final Image image = new Image(Display.getCurrent(), "resources/rect.jpg");
	private final Image imageL = new Image(Display.getCurrent(), "resources/rectL.jpg");
	private final Image imageW = new Image(Display.getCurrent(), "resources/rectW.jpg");
	private final Image imageT = new Image(Display.getCurrent(), "resources/rectTh.jpg");

	public RectanglePanelInitializer(Shell frame, ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	public void initializePanel() {
		{
			frame.setVisible(false);
			frame.setLayout(new FillLayout());
			for(Control c : frame.getChildren()){
				c.dispose();
			}
			
			final RectanglePanel rectanglePanel = new RectanglePanel(frame, SWT.NULL);
			frame.pack();

			rectanglePanel.addActionListenerButton(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					RectangularFin rectangularFin = rectanglePanel
							.getRectangleFin();
					resultsListener.goToResults(rectangularFin);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			FocusListener focusListenerL = new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					rectanglePanel.setImage(imageL);
				}

				@Override
				public void focusLost(FocusEvent e) {
					rectanglePanel.setImage(image);
				}
			};
			rectanglePanel.setLengthFocus(focusListenerL);

			FocusListener focusListenerW = new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					rectanglePanel.setImage(imageW);
				}

				@Override
				public void focusLost(FocusEvent e) {
					rectanglePanel.setImage(image);
				}

			};
			rectanglePanel.setWidthFocus(focusListenerW);

			FocusListener focusListenerT = new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					rectanglePanel.setImage(imageT);
				}

				@Override
				public void focusLost(FocusEvent e) {
					rectanglePanel.setImage(image);
				}

			};
			rectanglePanel.setThicknessFocus(focusListenerT);
			frame.setVisible(true);
		}
	}
}
