package panels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.FlowLayout;
//
//import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;

import models.FinInterface;

public class ResultsPanel extends Composite {

	protected void checkSubclass(){
		
	}
	
	public ResultsPanel(FinInterface object, Composite parent, int style){
		super(parent, style);
		this.setLayout(new BorderLayout());
		
		Composite biotNumberPanel = new Composite(this, SWT.BORDER);
		biotNumberPanel.setLayout(new BorderLayout());
		biotNumberPanel.setLayoutData(BorderData.NORTH);
		
		Label bioNumberLabel = new Label(biotNumberPanel, SWT.NULL);
		bioNumberLabel.setText("Biot Number");
		bioNumberLabel.setLayoutData(BorderData.WEST);
		Text biotNumberText = new Text(biotNumberPanel,SWT.BORDER);
		biotNumberText.setText(object.getBiotNumber()+"");
		biotNumberText.setLayoutData(BorderData.CENTER);
		
		
		Composite finTransferPanel = new Composite(this, SWT.BORDER);
		GridLayout gridLayout = new GridLayout(1, true);
		finTransferPanel.setLayout(gridLayout);
		finTransferPanel.setLayoutData(BorderData.WEST);
		Label finTransferLabel = new Label(finTransferPanel, SWT.BORDER);
		finTransferLabel.setText("Fine Heat Transfer Rate");
		
		Composite convHeatPanel = new Composite(finTransferPanel, SWT.BORDER);
		convHeatPanel.setLayout(new RowLayout());
		Label convHeatLabel = new Label(convHeatPanel, SWT.BORDER);
		convHeatLabel.setText("Convection Heat Transfer");
		Text convHeatText = new Text(convHeatPanel, SWT.BORDER);
		convHeatText.setText(object.getConvHeatT()+"");
		Label waHS1 = new Label(convHeatPanel, SWT.NULL);
		waHS1.setText("WaHS");
		
		Composite adiabaticPanel = new Composite(finTransferPanel, SWT.BORDER);
		adiabaticPanel.setLayout(new RowLayout());
		Label adiabaticLabel = new Label(adiabaticPanel, SWT.BORDER);
		adiabaticLabel.setText("Adiabatic");
		Text adiabaticText = new Text(adiabaticPanel, SWT.BORDER);
		adiabaticText.setText(object.getAdiabatic()+"");
		Label waHS2 = new Label(adiabaticPanel, SWT.NULL);
		waHS2.setText("WaHS");
		
		Composite infinitePanel = new Composite(finTransferPanel, SWT.BORDER);
		infinitePanel.setLayout(new RowLayout());
		Label infiniteLabel = new Label(infinitePanel, SWT.NULL);
		infiniteLabel.setText("Infinite Length");
		Text infiniteText = new Text(infinitePanel, SWT.BORDER);
		infiniteText.setText(object.getInfiniteLength()+"");
		Label waHS3 = new Label(infinitePanel, SWT.NULL);
		waHS3.setText("WaHS");
		
		Composite finOuterPanel = new Composite(this, SWT.NULL);
		finOuterPanel.setLayoutData(BorderData.SOUTH);
		finOuterPanel.setLayout(new BorderLayout());
		Composite finEffiPanel = new Composite(finOuterPanel, SWT.BORDER);
		finEffiPanel.setLayoutData(BorderData.NORTH);
		finEffiPanel.setLayout(new BorderLayout());
		Composite finPerfPanel = new Composite(finOuterPanel, SWT.BORDER);
		finPerfPanel.setLayoutData(BorderData.SOUTH);
		finPerfPanel.setLayout(new BorderLayout());

		Label finEffiLabel = new Label(finEffiPanel, SWT.BORDER);
		finEffiLabel.setText("Fin Efficency");
		finEffiLabel.setLayoutData(BorderData.NORTH);
		
		Composite longFinPanel = new Composite(finEffiPanel, SWT.NULL);
		longFinPanel.setLayoutData(BorderData.WEST);
		longFinPanel.setLayout(new BorderLayout());
		Composite insulatedFinPanel = new Composite(finEffiPanel, SWT.NULL);
		insulatedFinPanel.setLayoutData(BorderData.CENTER);
		insulatedFinPanel.setLayout(new BorderLayout());
		
		Label longFinLabel = new Label(longFinPanel, SWT.BORDER);
		longFinLabel.setLayoutData(BorderData.WEST);
		longFinLabel.setText("Long Fin");
		Text longFinText = new Text(longFinPanel, SWT.NULL);
		longFinText.setLayoutData(BorderData.CENTER);
		longFinText.setText(object.getLongFin()+"");
		
		Label insulatedFinLabel = new Label(insulatedFinPanel, SWT.BORDER);
		insulatedFinLabel.setLayoutData(BorderData.WEST);
		insulatedFinLabel.setText("Insulated Fin");
		Text insulatedFinText = new Text(insulatedFinPanel, SWT.NULL);
		insulatedFinText.setLayoutData(BorderData.CENTER);
		insulatedFinText.setText(object.getInsulatedFin()+"");
		Label finPerfLabel = new Label(finPerfPanel, SWT.BORDER);
		finPerfLabel.setLayoutData(BorderData.NORTH);
		finPerfLabel.setText("Fin Performance");
		
		Composite convHeatPerfPanel=new Composite(finPerfPanel, SWT.NULL);
		convHeatPerfPanel.setLayoutData(BorderData.WEST);
		convHeatPerfPanel.setLayout(new BorderLayout());
		Composite adiabaticPerfPanel=new Composite(finPerfPanel, SWT.NULL);
		adiabaticPerfPanel.setLayoutData(BorderData.CENTER);
		adiabaticPerfPanel.setLayout(new BorderLayout());
		Composite infinitePerfPanel=new Composite(finPerfPanel, SWT.NULL);
		infinitePerfPanel.setLayoutData(BorderData.EAST);
		infinitePerfPanel.setLayout(new BorderLayout());
		
		Label convHeatPerfLabel = new Label(convHeatPerfPanel, SWT.BORDER);
		convHeatPerfLabel.setLayoutData(BorderData.WEST);
		convHeatPerfLabel.setText("Convection Heat Transfer");
		Text convHeatPerfText = new Text(convHeatPerfPanel, SWT.NULL);
		convHeatPerfText.setLayoutData(BorderData.CENTER);
		convHeatPerfText.setTextLimit(10);
		
		Label adiabaticPerfLabel = new Label(adiabaticPerfPanel, SWT.BORDER);
		adiabaticPerfLabel.setLayoutData(BorderData.WEST);
		adiabaticPerfLabel.setText("Adiabatic");
		Text adiabaticPerfText = new Text(adiabaticPerfPanel, SWT.NULL);
		adiabaticPerfText.setLayoutData(BorderData.CENTER);
		adiabaticPerfText.setTextLimit(10);
		
		Label infinitePerfLabel = new Label(infinitePerfPanel, SWT.BORDER);
		infinitePerfLabel.setLayoutData(BorderData.WEST);
		infinitePerfLabel.setText("Infinite Length");
		Text infinitePerfText = new Text(infinitePerfPanel, SWT.NULL);
		infinitePerfText.setLayoutData(BorderData.CENTER);
		infinitePerfText.setTextLimit(10);
		
	}
}
