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

import models.TriangleFin;
import panels.TrianglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class TrianglePanelInitializer implements BaseInitializer {

	private Shell frame;
	private ResultsReadyListener resultsListener;
	
	final Image image = new Image(Display.getCurrent(), "resources/tri.jpg");
	final Image imageL = new Image(Display.getCurrent(), "resources/triL.jpg");
	final Image imageW = new Image(Display.getCurrent(), "resources/triW.jpg");
	final Image imageT = new Image(Display.getCurrent(), "resources/triTh.jpg");
	
	public TrianglePanelInitializer(Shell frame, ResultsReadyListener listener) {
		this.frame = frame;
		this.resultsListener = listener;
	}

	@Override
	public void initializePanel() {
		frame.setVisible(false);
		frame.setLayout(new FillLayout());
		for(Control c : frame.getChildren()){
			c.dispose();
		}
		final TrianglePanel trianglePanel = new TrianglePanel(frame, SWT.NULL);
		frame.pack();

		trianglePanel.addActionListenerButton(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TriangleFin triangleFin = trianglePanel.getTriangleFin();
				resultsListener.goToResults(triangleFin);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		FocusListener focusLength = new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				trianglePanel.setImage(imageL);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				trianglePanel.setImage(image);
			}

		};
		trianglePanel.setLengthFocus(focusLength);

		FocusListener focusListenerW = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				trianglePanel.setImage(imageW);
			}

			@Override
			public void focusLost(FocusEvent e) {
				trianglePanel.setImage(image);
			}

		};
		trianglePanel.setWidthFocus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				trianglePanel.setImage(imageT);
			}

			@Override
			public void focusLost(FocusEvent e) {
				trianglePanel.setImage(image);
			}

		};
		trianglePanel.setThicknessFocus(focusListenerT);

		frame.setVisible(true);
	}

}
