package utility;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
public class Utilities {
  public static Border normalBorder=BorderFactory.createLineBorder(Color.BLACK);
  public static Border highlightBorder=BorderFactory.createLineBorder(Color.BLUE);
  public static FocusListener focusListener=new FocusListener(){
    @Override public void focusLost(    FocusEvent focusEvent){
      java.lang.Object genVar1140;
      genVar1140=focusEvent.getSource();
      javax.swing.JTextField genVar1141;
      genVar1141=(JTextField)genVar1140;
      JTextField genVar1142;
      genVar1142=(genVar1141);
      genVar1142.setBorder(normalBorder);
    }
    @Override public void focusGained(    FocusEvent focusEvent){
      java.lang.Object genVar1143;
      genVar1143=focusEvent.getSource();
      javax.swing.JTextField genVar1144;
      genVar1144=(JTextField)genVar1143;
      JTextField genVar1145;
      genVar1145=(genVar1144);
      genVar1145.setBorder(highlightBorder);
    }
  }
;
}
