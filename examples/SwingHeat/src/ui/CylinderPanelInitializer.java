package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.CylinderFin;
import panels.CylinderPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;
public class CylinderPanelInitializer implements BaseInitializer {
  private JFrame frame;
  private ResultsReadyListener resultsListener;
  private final ImageIcon image=new ImageIcon("resources/trap.jpg");
  private final ImageIcon imageD=new ImageIcon("resources/trapB1.jpg");
  private final ImageIcon imageL=new ImageIcon("resources/trapB2.jpg");
  public CylinderPanelInitializer(  JFrame frame,  ResultsReadyListener listener){
    ui.CylinderPanelInitializer genVar1126;
    genVar1126=this;
    genVar1126.frame=frame;
    ui.CylinderPanelInitializer genVar1127;
    genVar1127=this;
    genVar1127.resultsListener=listener;
  }
  @Override public void initializePanel(){
    boolean genVar1128;
    genVar1128=false;
    frame.setVisible(genVar1128);
    final CylinderPanel cylinderPanel;
    cylinderPanel=new CylinderPanel();
    java.awt.Container genVar1129;
    genVar1129=frame.getContentPane();
    genVar1129.removeAll();
    java.awt.Container genVar1130;
    genVar1130=frame.getContentPane();
    genVar1130.add(cylinderPanel);
    frame.pack();
    ActionListener genVar1131;
    genVar1131=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        CylinderFin cylinderFin=cylinderPanel.getCylinderFin();
        resultsListener.goToResults(cylinderFin);
      }
    }
;
    cylinderPanel.addActionListenerButton(genVar1131);
    FocusListener focusListenerD;
    focusListenerD=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        cylinderPanel.setImage(imageD);
      }
      @Override public void focusLost(      FocusEvent e){
        cylinderPanel.setImage(image);
      }
    }
;
    cylinderPanel.setDiameterFocus(focusListenerD);
    FocusListener focusListenerL;
    focusListenerL=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        cylinderPanel.setImage(imageL);
      }
      @Override public void focusLost(      FocusEvent e){
        cylinderPanel.setImage(image);
      }
    }
;
    cylinderPanel.setLengthFocus(focusListenerL);
    boolean genVar1132;
    genVar1132=true;
    frame.setVisible(genVar1132);
  }
}
