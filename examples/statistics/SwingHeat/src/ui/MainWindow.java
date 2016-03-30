package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import models.FinInterface;
import panels.AuthorPanel;
import panels.PickFinPanel;
import panels.ResultsPanel;
import ui.BaseInterface.ResultsReadyListener;

public class MainWindow extends JFrame implements ResultsReadyListener {

	private String CREATED_BY = "Created by : Momoh Ozavoshon Jamiu";
	private String TITLE = "Analysis of Heat Transfer \n Through Fins";
	private PickFinInterface pickFinInterface;
	private CylinderPanelInitializer cylinderInitializer;
	private ParabolicPanelInitializer parabolicInitializer;
	private RectanglePanelInitializer rectangleInitializer;
	private TrapezoidalPanelInitializer trapezoidalInitializer;
	private TrianglePanelInitializer triangleInitializer;

	public MainWindow() {
		super("Heat Analysis");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMainFrame();
		this.cylinderInitializer = new CylinderPanelInitializer(this, this);
		this.parabolicInitializer = new ParabolicPanelInitializer(this, this);
		this.rectangleInitializer = new RectanglePanelInitializer(this, this);
		this.trapezoidalInitializer = new TrapezoidalPanelInitializer(this,
				this);
		this.triangleInitializer = new TrianglePanelInitializer(this, this);

	}

	public void setMainFrame() {
		AuthorPanel authorPanel = new AuthorPanel(); //W//Ymm
		authorPanel.getButton().addActionListener(new ActionListener() {//Ym

			@Override
			public void actionPerformed(ActionEvent arg0) {//Ym
				goToSelectPage();
			}
		});
		this.setVisible(false);//Y
		this.getContentPane().add(authorPanel);//W//Ymm
		this.pack();//Y
		this.setVisible(true);//Y

	}

	public void goToSelectPage() {
		PickFinInterface pickFinInterface = new PickFinInterface() {
			@Override
			public void finPiked(String item) {
				if (item == "Cylindrical Fin")
					cylinderInitializer.initializePanel();
				if (item == "Parabolic Fin")
					parabolicInitializer.initializePanel();
				if (item == "Rectangular Fin")
					rectangleInitializer.initializePanel();
				if (item == "Trapezoidal Fin")
					trapezoidalInitializer.initializePanel();
				if (item == "Triangular Fin")
					triangleInitializer.initializePanel();

			}

		};

		this.setVisible(false);
		PickFinPanel pickFin = new PickFinPanel(pickFinInterface);//W//Ymm
		this.getContentPane().removeAll();//Y
		this.getContentPane().add(pickFin);//W//Ymm
		this.pack();//Y
		this.setVisible(true);//Y

	}

	@Override
	public void goToResults(FinInterface object) {
		this.setVisible(false);//Y
		this.getContentPane().removeAll();//Y
		this.getContentPane().add(new ResultsPanel(object));//W//Ymm
		this.revalidate();
		this.repaint();//Y
		this.pack();//Y
		this.setVisible(true);//Y
	}

	public void refresh() {
		this.revalidate();
		this.repaint();//Y
	}

	public interface PickFinInterface {
		public void finPiked(String item);
	}

}
