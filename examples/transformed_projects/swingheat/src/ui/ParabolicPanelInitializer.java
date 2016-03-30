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

import models.ParabolicFin;
import panels.ParabolicPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;

public class ParabolicPanelInitializer implements BaseInitializer{
	
	private Shell frame;
	private ResultsReadyListener resultsListener;
	final Image image = new Image(Display.getCurrent(), "resources/para.jpg");
	final Image imageL = new Image(Display.getCurrent(), "resources/paraL.jpg");
	final Image imageW = new Image(Display.getCurrent(), "resources/paraW.jpg");
	final Image imageT = new Image(Display.getCurrent(), "resources/paraTh.jpg");

	public ParabolicPanelInitializer(Shell frame,ResultsReadyListener listener){
		this.frame=frame;
		this.resultsListener=listener;
	}
	
	@Override
	public void initializePanel() {
		System.out.println("in ParadbolicPanel initializePanel");
		frame.setVisible(false);
		System.out.println("in ParadbolicPanel initializePanel");
		frame.setLayout(new FillLayout());
		for(Control c : frame.getChildren()){
			c.dispose();
		}
		
		final ParabolicPanel parabolicPanel = new ParabolicPanel(frame,  SWT.NULL);
		frame.pack();

		parabolicPanel.addActionListenerButton(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ParabolicFin parabolicFin = parabolicPanel.getParabolicFin();
				resultsListener.goToResults(parabolicFin);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		FocusListener focusListenerL = new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				parabolicPanel.setImage(imageL);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				parabolicPanel.setImage(image);
			}
		};
		parabolicPanel.setLengthFocus(focusListenerL);

		FocusListener focusListenerW = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				parabolicPanel.setImage(imageW);
			}

			@Override
			public void focusLost(FocusEvent e) {
				parabolicPanel.setImage(image);
			}

		};
		parabolicPanel.setWidthFocus(focusListenerW);

		FocusListener focusListenerT = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				parabolicPanel.setImage(imageT);
			}

			@Override
			public void focusLost(FocusEvent e) {
				parabolicPanel.setImage(image);
			}

		};
		parabolicPanel.setThicknessFocus(focusListenerT);

		frame.setVisible(true);
	
	}

	
}
