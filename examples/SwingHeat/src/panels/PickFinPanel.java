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
  private String[] choices={"Rectangular Fin","Parabolic Fin","Trapezoidal Fin","Triangular Fin","Cylindrical Fin"};
  private JComboBox comboBox;
  public PickFinPanel(  final PickFinInterface pickFinInterface){
    super(new BorderLayout());
    panels.PickFinPanel genVar524;
    genVar524=this;
    int genVar525;
    genVar525=100;
    int genVar526;
    genVar526=100;
    int genVar527;
    genVar527=100;
    int genVar528;
    genVar528=100;
    javax.swing.border.Border genVar529;
    genVar529=BorderFactory.createEmptyBorder(genVar525,genVar526,genVar527,genVar528);
    genVar524.setBorder(genVar529);
    java.lang.String genVar530;
    genVar530="Pick Fin Type";
    JLabel label;
    label=new JLabel(genVar530,JLabel.CENTER);
    comboBox=new JComboBox(choices);
    int genVar531;
    genVar531=10;
    int genVar532;
    genVar532=10;
    int genVar533;
    genVar533=10;
    int genVar534;
    genVar534=10;
    javax.swing.border.Border genVar535;
    genVar535=BorderFactory.createEmptyBorder(genVar531,genVar532,genVar533,genVar534);
    comboBox.setBorder(genVar535);
    comboBox.setSelectedItem(null);
    ItemListener genVar536;
    genVar536=new ItemListener(){
      @Override public void itemStateChanged(      ItemEvent event){
        String item=(String)((JComboBox)event.getSource()).getSelectedItem();
        pickFinInterface.finPiked(item);
      }
    }
;
    comboBox.addItemListener(genVar536);
    panels.PickFinPanel genVar537;
    genVar537=this;
    genVar537.add(label,BorderLayout.NORTH);
    panels.PickFinPanel genVar538;
    genVar538=this;
    genVar538.add(comboBox,BorderLayout.CENTER);
  }
  public String getSelectedItem(){
    java.lang.Object genVar539;
    genVar539=comboBox.getSelectedItem();
    java.lang.String genVar540;
    genVar540=(String)genVar539;
    return genVar540;
  }
}
