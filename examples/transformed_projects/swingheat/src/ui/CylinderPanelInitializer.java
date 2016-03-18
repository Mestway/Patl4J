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

import models.CylinderFin;
import panels.CylinderPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class CylinderPanelInitializer implements BaseInitializer {

	private Shell frame;
	private ResultsReadyListener resultsListener;
	private final Image image = new Image(Display.getCurrent(), "resources/trap.jpg");
	private final Image imageD = new Image(Display.getCurrent(), "resources/trapB1.jpg");
	private final Image imageL = new Image(Display.getCurrent(), "resources/trapB2.jpg");

	public CylinderPanelInitializer(Shell frame, ResultsReadyListener listener) {
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
		final CylinderPanel cylinderPanel = new CylinderPanel(frame, SWT.NULL);
		frame.pack();

		cylinderPanel.addActionListenerButton(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				CylinderFin cylinderFin = cylinderPanel.getCylinderFin();
				resultsListener.goToResults(cylinderFin);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		FocusListener focusListenerD = new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				cylinderPanel.setImage(imageD);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				cylinderPanel.setImage(image);
			}
		};
		cylinderPanel.setDiameterFocus(focusListenerD);

		FocusListener focusListenerL = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				cylinderPanel.setImage(imageL);
			}

			@Override
			public void focusLost(FocusEvent e) {
				cylinderPanel.setImage(image);
			}

		};
		cylinderPanel.setLengthFocus(focusListenerL);
		frame.setVisible(true);
		
	}

}
