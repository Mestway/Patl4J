package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JFrame;

import models.FinInterface;
import panels.AuthorPanel;
import panels.PickFinPanel;
import panels.ResultsPanel;
import ui.BaseInterface.ResultsReadyListener;

public class MainWindow extends Shell implements ResultsReadyListener {

	private String CREATED_BY = "Created by : Momoh Ozavoshon Jamiu";
	private String TITLE = "Analysis of Heat Transfer \n Through Fins";
	private PickFinInterface pickFinInterface;
	private CylinderPanelInitializer cylinderInitializer;
	private ParabolicPanelInitializer parabolicInitializer;
	private RectanglePanelInitializer rectangleInitializer;
	private TrapezoidalPanelInitializer trapezoidalInitializer;
	private TrianglePanelInitializer triangleInitializer;
	private static Display display = new Display();
	protected void checkSubclass(){
		
	}
	
	public MainWindow() {
		super(display);
		this.setText("Heat Analysis");
		this.setLayout(new FillLayout());
		
		this.cylinderInitializer = new CylinderPanelInitializer(this, this);
		this.parabolicInitializer = new ParabolicPanelInitializer(this, this);
		this.rectangleInitializer = new RectanglePanelInitializer(this, this);
		this.trapezoidalInitializer = new TrapezoidalPanelInitializer(this, this);
		this.triangleInitializer = new TrianglePanelInitializer(this, this);
		
		setMainFrame();

	}

	public void setMainFrame() {
		AuthorPanel authorPanel = new AuthorPanel(this, SWT.NULL);
		authorPanel.getButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				goToSelectPage();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		this.setVisible(false);
		this.pack();
		this.open();
		this.setVisible(true);
		while(!this.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();

	}

	public void goToSelectPage() {
		PickFinInterface pickFinInterface = new PickFinInterface() {
			@Override
			public void finPiked(String item) {
				if (item.equals("Cylindrical Fin"))
					cylinderInitializer.initializePanel();
				if (item.equals("Parabolic Fin"))
					parabolicInitializer.initializePanel();
				if (item.equals("Rectangular Fin"))
					rectangleInitializer.initializePanel();
				if (item.equals("Trapezoidal Fin"))
					trapezoidalInitializer.initializePanel();
				if (item.equals("Triangular Fin"))
					triangleInitializer.initializePanel();

			}

		};

		this.setVisible(false);
		
		for(Control c : this.getChildren()){
			c.dispose();
		}
		
		PickFinPanel pickFin = new PickFinPanel(pickFinInterface, this, SWT.NULL);
		
		this.pack();
		this.setVisible(true);

	}

	@Override
	public void goToResults(FinInterface object) {
		this.setVisible(false);
		for(Control c : this.getChildren()){
			c.dispose();
		}
		ResultsPanel resultsPanel = new ResultsPanel(object, this, SWT.NULL);
		this.pack();
		this.setVisible(true);
	}

	public void refresh() {
		this.pack();
	}

	public interface PickFinInterface {
		public void finPiked(String item);
	}

}
