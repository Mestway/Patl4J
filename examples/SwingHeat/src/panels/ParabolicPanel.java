package panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.ParabolicFin;
public class ParabolicPanel extends JPanel {
  private ParabolicMeasurePanel measurePanel;
  private ValuesPanel valuesPanel;
  public ParabolicPanel(){
    super(new BorderLayout());
    measurePanel=new ParabolicMeasurePanel();
    valuesPanel=new ValuesPanel();
    java.lang.String genVar933;
    genVar933="resources/para.jpg";
    ImageIcon image;
    image=new ImageIcon(genVar933);
    JLabel imageL;
    imageL=new JLabel(image);
    panels.ParabolicPanel genVar934;
    genVar934=this;
    genVar934.add(measurePanel,BorderLayout.NORTH);
    panels.ParabolicPanel genVar935;
    genVar935=this;
    genVar935.add(imageL,BorderLayout.CENTER);
    panels.ParabolicPanel genVar936;
    genVar936=this;
    genVar936.add(valuesPanel,BorderLayout.SOUTH);
  }
  public void addActionListenerButton(  ActionListener actionListener){
    javax.swing.JButton genVar937;
    genVar937=valuesPanel.getAnalyzeButton();
    genVar937.addActionListener(actionListener);
  }
  public ParabolicFin getParabolicFin(){
    double genVar938;
    genVar938=measurePanel.getLength();
    double genVar939;
    genVar939=measurePanel.getWidthF();
    double genVar940;
    genVar940=measurePanel.getThickness();
    double genVar941;
    genVar941=measurePanel.getAmbientTemp();
    double genVar942;
    genVar942=measurePanel.getBaseTemp();
    int genVar943;
    genVar943=valuesPanel.getNumberFins();
    double genVar944;
    genVar944=valuesPanel.getCondHeat();
    double genVar945;
    genVar945=valuesPanel.getThermalCond();
    models.ParabolicFin genVar946;
    genVar946=new ParabolicFin(genVar938,genVar939,genVar940,genVar941,genVar942,genVar943,genVar944,genVar945);
    return genVar946;
  }
  public void setImage(  ImageIcon image){
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar947;
    genVar947=200;
    int genVar948;
    genVar948=300;
    java.awt.Dimension genVar949;
    genVar949=new Dimension(genVar947,genVar948);
    imageL.setPreferredSize(genVar949);
    panels.ParabolicPanel genVar950;
    genVar950=this;
    java.awt.LayoutManager genVar951;
    genVar951=genVar950.getLayout();
    BorderLayout layout;
    layout=(BorderLayout)genVar951;
    panels.ParabolicPanel genVar952;
    genVar952=this;
    java.awt.Component genVar953;
    genVar953=layout.getLayoutComponent(BorderLayout.CENTER);
    genVar952.remove(genVar953);
    panels.ParabolicPanel genVar954;
    genVar954=this;
    genVar954.revalidate();
    panels.ParabolicPanel genVar955;
    genVar955=this;
    genVar955.add(imageL,BorderLayout.CENTER);
    panels.ParabolicPanel genVar956;
    genVar956=this;
    genVar956.repaint();
  }
  public void setLengthFocus(  FocusListener focusListener){
    measurePanel.setLengthFocus(focusListener);
  }
  public void setWidthFocus(  FocusListener focusListener){
    measurePanel.setWidthFocus(focusListener);
  }
  public void setThicknessFocus(  FocusListener focusListener){
    measurePanel.setThicknessFocus(focusListener);
  }
}
