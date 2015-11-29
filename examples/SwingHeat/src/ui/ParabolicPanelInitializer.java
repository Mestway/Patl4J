package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.ParabolicFin;
import panels.ParabolicPanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;
public class ParabolicPanelInitializer implements BaseInitializer {
  private JFrame frame;
  private ResultsReadyListener resultsListener;
  final ImageIcon image=new ImageIcon("resources/para.jpg");
  final ImageIcon imageL=new ImageIcon("resources/paraL.jpg");
  final ImageIcon imageW=new ImageIcon("resources/paraW.jpg");
  final ImageIcon imageT=new ImageIcon("resources/paraTh.jpg");
  public ParabolicPanelInitializer(  JFrame frame,  ResultsReadyListener listener){
    ui.ParabolicPanelInitializer genVar1105;
    genVar1105=this;
    genVar1105.frame=frame;
    ui.ParabolicPanelInitializer genVar1106;
    genVar1106=this;
    genVar1106.resultsListener=listener;
  }
  @Override public void initializePanel(){
    boolean genVar1107;
    genVar1107=false;
    frame.setVisible(genVar1107);
    java.awt.Container genVar1108;
    genVar1108=frame.getContentPane();
    genVar1108.removeAll();
    final ParabolicPanel parabolicPanel;
    parabolicPanel=new ParabolicPanel();
    java.awt.Container genVar1109;
    genVar1109=frame.getContentPane();
    genVar1109.add(parabolicPanel);
    frame.pack();
    ActionListener genVar1110;
    genVar1110=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        ParabolicFin parabolicFin=parabolicPanel.getParabolicFin();
        resultsListener.goToResults(parabolicFin);
      }
    }
;
    parabolicPanel.addActionListenerButton(genVar1110);
    FocusListener focusListenerL;
    focusListenerL=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        parabolicPanel.setImage(imageL);
      }
      @Override public void focusLost(      FocusEvent e){
        parabolicPanel.setImage(image);
      }
    }
;
    parabolicPanel.setLengthFocus(focusListenerL);
    FocusListener focusListenerW;
    focusListenerW=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        parabolicPanel.setImage(imageW);
      }
      @Override public void focusLost(      FocusEvent e){
        parabolicPanel.setImage(image);
      }
    }
;
    parabolicPanel.setWidthFocus(focusListenerW);
    FocusListener focusListenerT;
    focusListenerT=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        parabolicPanel.setImage(imageT);
      }
      @Override public void focusLost(      FocusEvent e){
        parabolicPanel.setImage(image);
      }
    }
;
    parabolicPanel.setThicknessFocus(focusListenerT);
    boolean genVar1111;
    genVar1111=true;
    frame.setVisible(genVar1111);
  }
}
