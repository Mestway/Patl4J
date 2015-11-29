package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import models.RectangularFin;
import panels.RectanglePanel;
import ui.BaseInterface.BaseInitializer;
import ui.BaseInterface.ResultsReadyListener;
public class RectanglePanelInitializer implements BaseInitializer {
  private JFrame frame;
  private ResultsReadyListener resultsListener;
  private final ImageIcon image=new ImageIcon("resources/rect.jpg");
  private final ImageIcon imageL=new ImageIcon("resources/rectL.jpg");
  private final ImageIcon imageW=new ImageIcon("resources/rectW.jpg");
  private final ImageIcon imageT=new ImageIcon("resources/rectTh.jpg");
  public RectanglePanelInitializer(  JFrame frame,  ResultsReadyListener listener){
    ui.RectanglePanelInitializer genVar1112;
    genVar1112=this;
    genVar1112.frame=frame;
    ui.RectanglePanelInitializer genVar1113;
    genVar1113=this;
    genVar1113.resultsListener=listener;
  }
  public void initializePanel(){
{
      boolean genVar1114;
      genVar1114=false;
      frame.setVisible(genVar1114);
      java.awt.Container genVar1115;
      genVar1115=frame.getContentPane();
      genVar1115.removeAll();
      final RectanglePanel rectanglePanel;
      rectanglePanel=new RectanglePanel();
      java.awt.Container genVar1116;
      genVar1116=frame.getContentPane();
      genVar1116.add(rectanglePanel);
      frame.pack();
      ActionListener genVar1117;
      genVar1117=new ActionListener(){
        @Override public void actionPerformed(        ActionEvent arg0){
          RectangularFin rectangularFin=rectanglePanel.getRectangleFin();
          resultsListener.goToResults(rectangularFin);
        }
      }
;
      rectanglePanel.addActionListenerButton(genVar1117);
      FocusListener focusListenerL;
      focusListenerL=new FocusListener(){
        @Override public void focusGained(        FocusEvent e){
          rectanglePanel.setImage(imageL);
        }
        @Override public void focusLost(        FocusEvent e){
          rectanglePanel.setImage(image);
        }
      }
;
      rectanglePanel.setLengthFocus(focusListenerL);
      FocusListener focusListenerW;
      focusListenerW=new FocusListener(){
        @Override public void focusGained(        FocusEvent e){
          rectanglePanel.setImage(imageW);
        }
        @Override public void focusLost(        FocusEvent e){
          rectanglePanel.setImage(image);
        }
      }
;
      rectanglePanel.setWidthFocus(focusListenerW);
      FocusListener focusListenerT;
      focusListenerT=new FocusListener(){
        @Override public void focusGained(        FocusEvent e){
          rectanglePanel.setImage(imageT);
        }
        @Override public void focusLost(        FocusEvent e){
          rectanglePanel.setImage(image);
        }
      }
;
      rectanglePanel.setThicknessFocus(focusListenerT);
      boolean genVar1118;
      genVar1118=true;
      frame.setVisible(genVar1118);
    }
  }
}
