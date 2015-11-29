package panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.CylinderFin;
public class CylinderPanel extends JPanel {
  private ValuesPanel valuesPanel;
  private CylinderMeasurePanel measurePanel;
  public CylinderPanel(){
    super(new BorderLayout());
    measurePanel=new CylinderMeasurePanel();
    valuesPanel=new ValuesPanel();
    java.lang.String genVar644;
    genVar644="resources/para.jpg";
    ImageIcon image;
    image=new ImageIcon(genVar644);
    JLabel imageL;
    imageL=new JLabel(image);
    panels.CylinderPanel genVar645;
    genVar645=this;
    genVar645.add(measurePanel,BorderLayout.NORTH);
    panels.CylinderPanel genVar646;
    genVar646=this;
    genVar646.add(imageL,BorderLayout.CENTER);
    panels.CylinderPanel genVar647;
    genVar647=this;
    genVar647.add(valuesPanel,BorderLayout.SOUTH);
  }
  public void addActionListenerButton(  ActionListener actionListener){
    javax.swing.JButton genVar648;
    genVar648=valuesPanel.getAnalyzeButton();
    genVar648.addActionListener(actionListener);
  }
  public CylinderFin getCylinderFin(){
    double genVar649;
    genVar649=measurePanel.getDiameter();
    double genVar650;
    genVar650=measurePanel.getLength();
    double genVar651;
    genVar651=measurePanel.getAmbientTemp();
    double genVar652;
    genVar652=measurePanel.getBaseTemp();
    int genVar653;
    genVar653=valuesPanel.getNumberFins();
    double genVar654;
    genVar654=valuesPanel.getCondHeat();
    double genVar655;
    genVar655=valuesPanel.getThermalCond();
    models.CylinderFin genVar656;
    genVar656=new CylinderFin(genVar649,genVar650,genVar651,genVar652,genVar653,genVar654,genVar655);
    return genVar656;
  }
  public void setImage(  ImageIcon image){
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar657;
    genVar657=200;
    int genVar658;
    genVar658=300;
    java.awt.Dimension genVar659;
    genVar659=new Dimension(genVar657,genVar658);
    imageL.setPreferredSize(genVar659);
    panels.CylinderPanel genVar660;
    genVar660=this;
    java.awt.LayoutManager genVar661;
    genVar661=genVar660.getLayout();
    BorderLayout layout;
    layout=(BorderLayout)genVar661;
    panels.CylinderPanel genVar662;
    genVar662=this;
    java.awt.Component genVar663;
    genVar663=layout.getLayoutComponent(BorderLayout.CENTER);
    genVar662.remove(genVar663);
    panels.CylinderPanel genVar664;
    genVar664=this;
    genVar664.revalidate();
    panels.CylinderPanel genVar665;
    genVar665=this;
    genVar665.add(imageL,BorderLayout.CENTER);
    panels.CylinderPanel genVar666;
    genVar666=this;
    genVar666.repaint();
  }
  public void setDiameterFocus(  FocusListener focusListener){
    measurePanel.setDiameterFocus(focusListener);
  }
  public void setLengthFocus(  FocusListener focusListener){
    measurePanel.setLengthFocus(focusListener);
  }
}
