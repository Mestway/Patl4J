package panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.RectangularFin;
public class RectanglePanel extends JPanel {
  private RectangleMeasurePanel measurePanel;
  private ValuesPanel valuesPanel;
  public RectanglePanel(){
    super(new BorderLayout());
    panels.RectanglePanel genVar811;
    genVar811=this;
    int genVar812;
    genVar812=20;
    int genVar813;
    genVar813=20;
    int genVar814;
    genVar814=20;
    int genVar815;
    genVar815=20;
    javax.swing.border.Border genVar816;
    genVar816=BorderFactory.createEmptyBorder(genVar812,genVar813,genVar814,genVar815);
    genVar811.setBorder(genVar816);
    java.lang.String genVar817;
    genVar817="resources/rect.jpg";
    ImageIcon image;
    image=new ImageIcon(genVar817);
    JLabel imageL;
    imageL=new JLabel(image);
    measurePanel=new RectangleMeasurePanel();
    valuesPanel=new ValuesPanel();
    panels.RectanglePanel genVar818;
    genVar818=this;
    genVar818.add(measurePanel,BorderLayout.NORTH);
    panels.RectanglePanel genVar819;
    genVar819=this;
    genVar819.add(imageL,BorderLayout.CENTER);
    panels.RectanglePanel genVar820;
    genVar820=this;
    genVar820.add(valuesPanel,BorderLayout.SOUTH);
  }
  public void addActionListenerButton(  ActionListener actionListener){
    javax.swing.JButton genVar821;
    genVar821=valuesPanel.getAnalyzeButton();
    genVar821.addActionListener(actionListener);
  }
  public RectangularFin getRectangleFin(){
    double genVar822;
    genVar822=measurePanel.getLength();
    double genVar823;
    genVar823=measurePanel.getWidthF();
    double genVar824;
    genVar824=measurePanel.getThickness();
    double genVar825;
    genVar825=measurePanel.getAmbientTemp();
    double genVar826;
    genVar826=measurePanel.getBaseTemp();
    int genVar827;
    genVar827=valuesPanel.getNumberFins();
    double genVar828;
    genVar828=valuesPanel.getCondHeat();
    double genVar829;
    genVar829=valuesPanel.getThermalCond();
    models.RectangularFin genVar830;
    genVar830=new RectangularFin(genVar822,genVar823,genVar824,genVar825,genVar826,genVar827,genVar828,genVar829);
    return genVar830;
  }
  public void setImage(  ImageIcon image){
    JLabel imageL;
    imageL=new JLabel(image);
    int genVar831;
    genVar831=200;
    int genVar832;
    genVar832=300;
    java.awt.Dimension genVar833;
    genVar833=new Dimension(genVar831,genVar832);
    imageL.setPreferredSize(genVar833);
    panels.RectanglePanel genVar834;
    genVar834=this;
    java.awt.LayoutManager genVar835;
    genVar835=genVar834.getLayout();
    BorderLayout layout;
    layout=(BorderLayout)genVar835;
    panels.RectanglePanel genVar836;
    genVar836=this;
    java.awt.Component genVar837;
    genVar837=layout.getLayoutComponent(BorderLayout.CENTER);
    genVar836.remove(genVar837);
    panels.RectanglePanel genVar838;
    genVar838=this;
    genVar838.revalidate();
    panels.RectanglePanel genVar839;
    genVar839=this;
    genVar839.add(imageL,BorderLayout.CENTER);
    panels.RectanglePanel genVar840;
    genVar840=this;
    genVar840.repaint();
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
