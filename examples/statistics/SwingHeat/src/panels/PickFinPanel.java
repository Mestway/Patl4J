package panels;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.MainWindow.PickFinInterface;

public class PickFinPanel extends JPanel {
	
	private String[] choices={"Rectangular Fin", "Parabolic Fin", "Trapezoidal Fin", "Triangular Fin", "Cylindrical Fin"};
	private JComboBox comboBox;
	
	public PickFinPanel(final PickFinInterface pickFinInterface){
		super(new BorderLayout());//Y
		this.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));//Ymm
		JLabel label=new JLabel("Pick Fin Type",JLabel.CENTER);//Ymm
		comboBox=new JComboBox(choices);//Ymm
		comboBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//Ymm
		comboBox.setSelectedItem(null);//Y
		comboBox.addItemListener(new ItemListener() {//Ym
			
			@Override
			public void itemStateChanged(ItemEvent event) {//Ym
				String item=(String)((JComboBox)event.getSource()).getSelectedItem();//Y
				pickFinInterface.finPiked(item);
			}
		});
		
		this.add(label,BorderLayout.NORTH);//Ymm
		this.add(comboBox,BorderLayout.CENTER);//Ymm
	}
	
	public String getSelectedItem(){
		return (String)comboBox.getSelectedItem();//Y
	}
	

	
}
