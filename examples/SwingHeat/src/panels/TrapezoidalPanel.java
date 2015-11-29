package panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.TrapezoidalFin;
import models.TriangleFin;
public class TrapezoidalPanel extends JPanel {
  private TrapezoidalMeasurePanel measurePanel;
  private ValuesPanel valuesPanel;
  public TrapezoidalPanel(){
    super(new BorderLayout());
    measurePanel=new TrapezoidalMeasurePanel();
    valuesPanel=new ValuesPanel();
    java.lang.String genVar842;
    genVar842="resources/trap.jpg";
    ImageIcon image;
    image=new ImageIcon(genVar842);
    JLabel imageL;
    imageL=new JLabel(image);
    panels.TrapezoidalPanel genVar843;
    genVar843=this;
    genVar843.add(measurePanel,BorderLayout.NORTH);
    panels.TrapezoidalPanel genVar844;
    genVar844=this;
    genVar844.add(imageL,BorderLayout.CENTER);
    panels.TrapezoidalPanel genVar845;
    genVar845=this;
    genVar845.add(valuesPanel,BorderLayout.SOUTH);
  }
  public void addActionListenerButton(  ActionListener actionListener){
    javax.swing.JButton genVar846;
    genVar846=valuesPanel.getAnalyzeButton();
    genVar846.addActionListener(actionListener);
  }
  public TrapezoidalFin getTrapezoidalFin(){
    double genVar847;
    genVar847=measurePanel.getBase1();
    double genVar848;
    genVar848=measurePanel.getBase2();
    double genVar849;
    genVar849=measurePanel.getHeightF();
    double genVar850;
    genVar850=measurePanel.getAmbientTemp();
    double genVar851;
    genVar851=measurePanel.getBaseTemp();
    int genVar852;
    genVar852=valuesPanel.getNumberFins();
    double genVar853;
    genVar853=valuesPanel.getCondHeat();
    double genVar854;
    genVar854=valuesPanel.getThermalCond();
    models.TrapezoidalFin genVar855;
    genVar855=new TrapezoidalFin(genVar847,genVar848,genVar849,genVar850,genVar851,genVar852,genVar853,genVar854);
    return genVar855;
  }
  public void setImage(  ImageIcon image){
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar856;
    genVar856=200;
    int genVar857;
    genVar857=300;
    java.awt.Dimension genVar858;
    genVar858=new Dimension(genVar856,genVar857);
    imageL.setPreferredSize(genVar858);
    panels.TrapezoidalPanel genVar859;
    genVar859=this;
    java.awt.LayoutManager genVar860;
    genVar860=genVar859.getLayout();
    BorderLayout layout;
    layout=(BorderLayout)genVar860;
    panels.TrapezoidalPanel genVar861;
    genVar861=this;
    java.awt.Component genVar862;
    genVar862=layout.getLayoutComponent(BorderLayout.CENTER);
    genVar861.remove(genVar862);
    panels.TrapezoidalPanel genVar863;
    genVar863=this;
    genVar863.revalidate();
    panels.TrapezoidalPanel genVar864;
    genVar864=this;
    genVar864.add(imageL,BorderLayout.CENTER);
    panels.TrapezoidalPanel genVar865;
    genVar865=this;
    genVar865.repaint();
  }
  public void setBase1Focus(  FocusListener focusListener){
    measurePanel.setBase1Focus(focusListener);
  }
  public void setBase2Focus(  FocusListener focusListener){
    measurePanel.setBase2Focus(focusListener);
  }
  public void setHeightFocus(  FocusListener focusListener){
    measurePanel.setHeightFocus(focusListener);
  }
}
