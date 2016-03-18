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

import models.TrapezoidalFin;
import panels.TrapezoidalPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class TrapezoidalPanelInitializer implements BaseInitializer {

	private Shell frame;
	private ResultsReadyListener resultsListener;
	final Image image = new Image(Display.getCurrent(), "resources/trap.jpg");
	final Image imageB1 = new Image(Display.getCurrent(), "resources/trapB1.jpg");
	final Image imageB2 = new Image(Display.getCurrent(), "resources/trapB2.jpg");
	final Image imageH = new Image(Display.getCurrent(), "resources/trapH.jpg");

	public TrapezoidalPanelInitializer(Shell frame,
			ResultsReadyListener listener) {
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
		final TrapezoidalPanel trapezoidalPanel = new TrapezoidalPanel(frame, SWT.NULL);
		frame.pack();
		trapezoidalPanel.addActionListenerButton(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TrapezoidalFin trapezoidalFin = trapezoidalPanel
						.getTrapezoidalFin();
				resultsListener.goToResults(trapezoidalFin);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		FocusListener focusListenerL = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				trapezoidalPanel.setImage(imageB1);
			}

			@Override
			public void focusLost(FocusEvent e) {
				trapezoidalPanel.setImage(image);
			}
		};
		trapezoidalPanel.setBase1Focus(focusListenerL);

		FocusListener focusListenerW = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				trapezoidalPanel.setImage(imageB2);
			}

			@Override
			public void focusLost(FocusEvent e) {
				trapezoidalPanel.setImage(image);
			}

		};
		trapezoidalPanel.setBase2Focus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				trapezoidalPanel.setImage(imageH);
			}

			@Override
			public void focusLost(FocusEvent e) {
				trapezoidalPanel.setImage(image);
			}

		};
		trapezoidalPanel.setHeightFocus(focusListenerT);
		frame.setVisible(true);

	}

}
