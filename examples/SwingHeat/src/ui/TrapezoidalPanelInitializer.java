package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.TrapezoidalFin;
import panels.TrapezoidalPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;
public class TrapezoidalPanelInitializer implements BaseInitializer {
  private JFrame frame;
  private ResultsReadyListener resultsListener;
  final ImageIcon image=new ImageIcon("resources/trap.jpg");
  final ImageIcon imageB1=new ImageIcon("resources/trapB1.jpg");
  final ImageIcon imageB2=new ImageIcon("resources/trapB2.jpg");
  final ImageIcon imageH=new ImageIcon("resources/trapH.jpg");
  public TrapezoidalPanelInitializer(  JFrame frame,  ResultsReadyListener listener){
    ui.TrapezoidalPanelInitializer genVar1119;
    genVar1119=this;
    genVar1119.frame=frame;
    ui.TrapezoidalPanelInitializer genVar1120;
    genVar1120=this;
    genVar1120.resultsListener=listener;
  }
  @Override public void initializePanel(){
    boolean genVar1121;
    genVar1121=false;
    frame.setVisible(genVar1121);
    java.awt.Container genVar1122;
    genVar1122=frame.getContentPane();
    genVar1122.removeAll();
    final TrapezoidalPanel trapezoidalPanel;
    trapezoidalPanel=new TrapezoidalPanel();
    java.awt.Container genVar1123;
    genVar1123=frame.getContentPane();
    genVar1123.add(trapezoidalPanel);
    frame.pack();
    ActionListener genVar1124;
    genVar1124=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        TrapezoidalFin trapezoidalFin=trapezoidalPanel.getTrapezoidalFin();
        resultsListener.goToResults(trapezoidalFin);
      }
    }
;
    trapezoidalPanel.addActionListenerButton(genVar1124);
    FocusListener focusListenerL;
    focusListenerL=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        trapezoidalPanel.setImage(imageB1);
      }
      @Override public void focusLost(      FocusEvent e){
        trapezoidalPanel.setImage(image);
      }
    }
;
    trapezoidalPanel.setBase1Focus(focusListenerL);
    FocusListener focusListenerW;
    focusListenerW=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        trapezoidalPanel.setImage(imageB2);
      }
      @Override public void focusLost(      FocusEvent e){
        trapezoidalPanel.setImage(image);
      }
    }
;
    trapezoidalPanel.setBase2Focus(focusListenerW);
    FocusListener focusListenerT;
    focusListenerT=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        trapezoidalPanel.setImage(imageH);
      }
      @Override public void focusLost(      FocusEvent e){
        trapezoidalPanel.setImage(image);
      }
    }
;
    trapezoidalPanel.setHeightFocus(focusListenerT);
    boolean genVar1125;
    genVar1125=true;
    frame.setVisible(genVar1125);
  }
}
