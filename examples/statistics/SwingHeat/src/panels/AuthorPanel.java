package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AuthorPanel extends JPanel {
	
	private String CREATED_BY="Created by : Momoh Ozavoshon Jamiu";
	private String TITLE="Analysis of Heat Transfer \n Through Fins";
	
	
	public AuthorPanel(){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		 JTextField titleTextArea=new JTextField(TITLE);//Ymm
		 titleTextArea.setHorizontalAlignment(JTextField.CENTER);//Y
		titleTextArea.setEditable(false);//Y
		titleTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		
		
		 JTextField createdByTextArea=new JTextField(CREATED_BY);//Ymm
		 createdByTextArea.setHorizontalAlignment(JTextField.CENTER);//Y
		createdByTextArea.setEditable(false);//Y
		createdByTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		
		
		JButton analyzeButton=new JButton("Analyze");//Ymm
		analyzeButton.setAlignmentX(CENTER_ALIGNMENT);//Y
		analyzeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		
		this.add(titleTextArea,BorderLayout.NORTH);//Ymm
		this.add(createdByTextArea,BorderLayout.CENTER);//Ymm
		this.add(analyzeButton,BorderLayout.SOUTH);//Ymm
		
		
	}
	
	public JButton getButton(){
		return (JButton) this.getComponent(2);
	}

}
