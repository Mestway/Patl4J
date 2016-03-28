package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.FinInterface;

public class ResultsPanel extends JPanel {

	public ResultsPanel(FinInterface object){
		super(new BorderLayout());//Y
		
		JLabel waHS1=new JLabel("WaHS");//Ymm
		JLabel waHS2=new JLabel("WaHS");//Ymm
		JLabel waHS3=new JLabel("WaHS");//Ymm
		
		JPanel biotNumberPanel=new JPanel(new BorderLayout());//Ymm
		JLabel biotNumberLabel=new JLabel("Biot Number");//Ymm
		JTextField biotNumberText=new JTextField(object.getBiotNumber()+"");//Ymm
		biotNumberLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		
		biotNumberPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		biotNumberPanel.add(biotNumberLabel,BorderLayout.WEST);//Ymm
		biotNumberPanel.add(biotNumberText,BorderLayout.CENTER);//Ymm
		
		//Add biot number panel
		this.add(biotNumberPanel,BorderLayout.NORTH);//Ymm
		
		JPanel finTransferPanel=new JPanel();//Ymm
	
		finTransferPanel.setLayout(new BoxLayout(finTransferPanel,BoxLayout.Y_AXIS));//Y
		JLabel finTransferLabel=new JLabel("Fin Heat Transfer Rate");//Ymm
		finTransferLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		finTransferPanel.setAlignmentX(Component.LEFT_ALIGNMENT);//Y
		
		JPanel convHeatPanel=new JPanel(new FlowLayout());//Ymm
		convHeatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		JLabel convHeatLabel=new JLabel("Convection Heat Transfer");//Ymm
		JTextField convHeatText=new JTextField(object.getConvHeatT()+"");//Ymm
		convHeatLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));//Ymm
		convHeatPanel.add(convHeatLabel);//Ymm
		convHeatPanel.add(convHeatText);//Ymm
		convHeatPanel.add(waHS1);//Ymm
		
		JPanel adiabaticPanel=new JPanel(new FlowLayout());//Ymm
		adiabaticPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		JLabel adiabaticLabel=new JLabel("Adiabatic");//Ymm
		JTextField adiabaticText=new JTextField(object.getAdiabatic()+"");//Ymm
		adiabaticLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));//Ymm
		adiabaticPanel.add(adiabaticLabel);//Ymm
		adiabaticPanel.add(adiabaticText);//Ymm
		adiabaticPanel.add(waHS2);//Ymm
		
		JPanel infinitePanel=new JPanel(new FlowLayout());//Ymm
		infinitePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		JLabel infiniteLabel=new JLabel("Infinite Length");//Ymm
		JTextField infiniteText=new JTextField(object.getInfiniteLength()+"");//Ymm
		infiniteLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));//Ymm
		infinitePanel.add(infiniteLabel);//Ymm
		infinitePanel.add(infiniteText);//Ymm
		infinitePanel.add(waHS3);//Ymm
		
		finTransferPanel.add(finTransferLabel);//Ymm
		finTransferPanel.add(convHeatPanel);//Ymm
		finTransferPanel.add(adiabaticPanel);//Ymm
		finTransferPanel.add(infinitePanel);//Ymm
		
		this.add(finTransferPanel,BorderLayout.WEST);//Ymm
		//
		JPanel finOuterPanel=new JPanel(new BorderLayout());//Ymm
		JPanel finEffiPanel=new JPanel(new BorderLayout());//Ymm
		JPanel finPerfPanel=new JPanel(new BorderLayout());//Ymm
		
		//fin efficiency panel setup
		finEffiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		JLabel finEffiLabel=new JLabel("Fin Efficency");//Ymm
		finEffiLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		
		JPanel longFinPanel=new JPanel(new BorderLayout());//Ymm
		JPanel insulatedFinPanel=new JPanel(new BorderLayout());//Ymm
		
		JLabel longFinLabel=new JLabel("Long Fin");//Ymm
		longFinLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JTextField longFinText=new JTextField(object.getLongFin()+"");//Ymm
		longFinPanel.add(longFinLabel,BorderLayout.WEST);//Ymm
		longFinPanel.add(longFinText,BorderLayout.CENTER);//Ymm
		
		JLabel insulatedFinLabel=new JLabel("Insulated Fin");//Ymm
		insulatedFinLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JTextField insulatedFinText=new JTextField(object.getInsulatedFin()+"");//Ymm
		insulatedFinPanel.add(insulatedFinLabel,BorderLayout.WEST);//Ymm
		insulatedFinPanel.add(insulatedFinText,BorderLayout.CENTER);//Ymm
		
		finEffiPanel.add(finEffiLabel,BorderLayout.NORTH);//Ymm
		finEffiPanel.add(longFinPanel,BorderLayout.WEST);//Ymm
		finEffiPanel.add(insulatedFinPanel,BorderLayout.CENTER);//Ymm
		
		finPerfPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		JLabel finPerfLabel=new JLabel("Fin Performance");//Ymm
		finPerfLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		
		JPanel convHeatPerfPanel=new JPanel(new BorderLayout());//Ymm
		JPanel adiabaticPerfPanel=new JPanel(new BorderLayout());//Ymm
		JPanel infinitePerfPanel=new JPanel(new BorderLayout());//Ymm
		
		JLabel convHeatPerfLabel=new JLabel("Convection Heat Transfer");//Ymm
		convHeatPerfLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JTextField convHeatPerfText=new JTextField(10);//Ymm
		convHeatPerfPanel.add(convHeatPerfLabel,BorderLayout.WEST);//Ymm
		convHeatPerfPanel.add(convHeatPerfText,BorderLayout.CENTER);//Ymm
		
		JLabel adiabaticPerfLabel=new JLabel("Adiabatic");//Ymm
		adiabaticPerfLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JTextField adiabaticPerfText=new JTextField(10);//Ymm
		adiabaticPerfPanel.add(adiabaticPerfLabel,BorderLayout.WEST);//Ymm
		adiabaticPerfPanel.add(adiabaticPerfText,BorderLayout.CENTER);//Ymm
		
		JLabel infinitePerfLabel=new JLabel("Infinite Length");//Ymm
		infinitePerfLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Ymm
		JTextField infinitePerfText=new JTextField(10);//Ymm
		infinitePerfPanel.add(infinitePerfLabel,BorderLayout.WEST);//Ymm
		infinitePerfPanel.add(infinitePerfText,BorderLayout.CENTER);//Ymm
		
		finPerfPanel.add(finPerfLabel,BorderLayout.NORTH);//Ymm
		finPerfPanel.add(convHeatPerfPanel,BorderLayout.WEST);//Ymm
		finPerfPanel.add(adiabaticPerfPanel,BorderLayout.CENTER);//Ymm
		finPerfPanel.add(infinitePerfPanel,BorderLayout.EAST);//Ymm
		
		
		finOuterPanel.add(finEffiPanel,BorderLayout.NORTH);//Ymm
		finOuterPanel.add(finPerfPanel,BorderLayout.SOUTH);//Ymm
		
		this.add(finOuterPanel,BorderLayout.SOUTH);//Ymm
	
		
	}
}
