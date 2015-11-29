package panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.TriangleFin;
public class TrianglePanel extends JPanel {
  private TriangleMeasurePanel measurePanel;
  private ValuesPanel valuesPanel;
  public TrianglePanel(){
    super(new BorderLayout());
    measurePanel=new TriangleMeasurePanel();
    valuesPanel=new ValuesPanel();
    java.lang.String genVar1028;
    genVar1028="resources/triL.jpg";
    ImageIcon image;
    image=new ImageIcon(genVar1028);
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar1029;
    genVar1029=200;
    int genVar1030;
    genVar1030=300;
    java.awt.Dimension genVar1031;
    genVar1031=new Dimension(genVar1029,genVar1030);
    imageL.setPreferredSize(genVar1031);
    panels.TrianglePanel genVar1032;
    genVar1032=this;
    genVar1032.add(measurePanel,BorderLayout.NORTH);
    panels.TrianglePanel genVar1033;
    genVar1033=this;
    genVar1033.add(imageL,BorderLayout.CENTER);
    panels.TrianglePanel genVar1034;
    genVar1034=this;
    genVar1034.add(valuesPanel,BorderLayout.SOUTH);
  }
  public void addActionListenerButton(  ActionListener actionListener){
    javax.swing.JButton genVar1035;
    genVar1035=valuesPanel.getAnalyzeButton();
    genVar1035.addActionListener(actionListener);
  }
  public TriangleFin getTriangleFin(){
    double genVar1036;
    genVar1036=measurePanel.getLength();
    double genVar1037;
    genVar1037=measurePanel.getWidthF();
    double genVar1038;
    genVar1038=measurePanel.getThickness();
    double genVar1039;
    genVar1039=measurePanel.getAmbientTemp();
    double genVar1040;
    genVar1040=measurePanel.getBaseTemp();
    int genVar1041;
    genVar1041=valuesPanel.getNumberFins();
    double genVar1042;
    genVar1042=valuesPanel.getCondHeat();
    double genVar1043;
    genVar1043=valuesPanel.getThermalCond();
    models.TriangleFin genVar1044;
    genVar1044=new TriangleFin(genVar1036,genVar1037,genVar1038,genVar1039,genVar1040,genVar1041,genVar1042,genVar1043);
    return genVar1044;
  }
  public void setImage(  ImageIcon image){
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar1045;
    genVar1045=200;
    int genVar1046;
    genVar1046=300;
    java.awt.Dimension genVar1047;
    genVar1047=new Dimension(genVar1045,genVar1046);
    imageL.setPreferredSize(genVar1047);
    panels.TrianglePanel genVar1048;
    genVar1048=this;
    java.awt.LayoutManager genVar1049;
    genVar1049=genVar1048.getLayout();
    BorderLayout layout;
    layout=(BorderLayout)genVar1049;
    panels.TrianglePanel genVar1050;
    genVar1050=this;
    java.awt.Component genVar1051;
    genVar1051=layout.getLayoutComponent(BorderLayout.CENTER);
    genVar1050.remove(genVar1051);
    panels.TrianglePanel genVar1052;
    genVar1052=this;
    genVar1052.revalidate();
    panels.TrianglePanel genVar1053;
    genVar1053=this;
    genVar1053.add(imageL,BorderLayout.CENTER);
    panels.TrianglePanel genVar1054;
    genVar1054=this;
    genVar1054.repaint();
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
