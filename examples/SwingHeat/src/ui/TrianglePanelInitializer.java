package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.TriangleFin;
import panels.TrianglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;
public class TrianglePanelInitializer implements BaseInitializer {
  private JFrame frame;
  private ResultsReadyListener resultsListener;
  final ImageIcon image=new ImageIcon("resources/tri.jpg");
  final ImageIcon imageL=new ImageIcon("resources/triL.jpg");
  final ImageIcon imageW=new ImageIcon("resources/triW.jpg");
  final ImageIcon imageT=new ImageIcon("resources/triTh.jpg");
  public TrianglePanelInitializer(  JFrame frame,  ResultsReadyListener listener){
    ui.TrianglePanelInitializer genVar1133;
    genVar1133=this;
    genVar1133.frame=frame;
    ui.TrianglePanelInitializer genVar1134;
    genVar1134=this;
    genVar1134.resultsListener=listener;
  }
  @Override public void initializePanel(){
    boolean genVar1135;
    genVar1135=false;
    frame.setVisible(genVar1135);
    java.awt.Container genVar1136;
    genVar1136=frame.getContentPane();
    genVar1136.removeAll();
    final TrianglePanel trianglePanel;
    trianglePanel=new TrianglePanel();
    java.awt.Container genVar1137;
    genVar1137=frame.getContentPane();
    genVar1137.add(trianglePanel);
    frame.pack();
    ActionListener genVar1138;
    genVar1138=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        TriangleFin triangleFin=trianglePanel.getTriangleFin();
        resultsListener.goToResults(triangleFin);
      }
    }
;
    trianglePanel.addActionListenerButton(genVar1138);
    FocusListener focusLength;
    focusLength=new FocusListener(){
      @Override public void focusGained(      FocusEvent arg0){
        trianglePanel.setImage(imageL);
      }
      @Override public void focusLost(      FocusEvent arg0){
        trianglePanel.setImage(image);
      }
    }
;
    trianglePanel.setLengthFocus(focusLength);
    FocusListener focusListenerW;
    focusListenerW=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        trianglePanel.setImage(imageW);
      }
      @Override public void focusLost(      FocusEvent e){
        trianglePanel.setImage(image);
      }
    }
;
    trianglePanel.setWidthFocus(focusListenerW);
    FocusListener focusListenerT;
    focusListenerT=new FocusListener(){
      @Override public void focusGained(      FocusEvent e){
        trianglePanel.setImage(imageT);
      }
      @Override public void focusLost(      FocusEvent e){
        trianglePanel.setImage(image);
      }
    }
;
    trianglePanel.setThicknessFocus(focusListenerT);
    boolean genVar1139;
    genVar1139=true;
    frame.setVisible(genVar1139);
  }
}
