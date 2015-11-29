package com.fray.evo.ui.swingx;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.JXTextField;
/** 
 * A text field that only allows numeric characters to be entered. Negative values are not allowed.
 * @author mike.angstadt
 */
@SuppressWarnings("serial") public class NumberTextField extends JXTextField {
  @Override protected Document createDefaultModel(){
    com.fray.evo.ui.swingx.NumberTextField.NumberDocument genVar4148;
    genVar4148=new NumberDocument();
    return genVar4148;
  }
  /** 
 * Allows only numbers to be entered into the text box.
 * @author mike.angstadt
 */
private static class NumberDocument extends PlainDocument {
    private static final Pattern pattern=Pattern.compile("\\d*");
    @Override public void insertString(    int offs,    String str,    AttributeSet a) throws BadLocationException {
      boolean genVar4149;
      genVar4149=str == null;
      if (genVar4149) {
        return;
      }
 else {
        ;
      }
      NumberDocument genVar4150;
      genVar4150=this;
      int genVar4151;
      genVar4151=0;
      NumberDocument genVar4152;
      genVar4152=this;
      int genVar4153;
      genVar4153=genVar4152.getLength();
      String oldString;
      oldString=genVar4150.getText(genVar4151,genVar4153);
      boolean genVar4154;
      genVar4154=pattern.matcher(oldString).matches() && pattern.matcher(str).matches();
      if (genVar4154) {
        super.insertString(offs,str,a);
      }
 else {
        ;
      }
    }
  }
  /** 
 * Gets the numeric value of the text box.
 * @return
 */
  public int getValue(){
    NumberTextField genVar4155;
    genVar4155=this;
    java.lang.String genVar4156;
    genVar4156=genVar4155.getText();
    boolean genVar4157;
    genVar4157=genVar4156.isEmpty();
    if (genVar4157) {
      int genVar4158;
      genVar4158=0;
      return genVar4158;
    }
 else {
      ;
    }
    NumberTextField genVar4159;
    genVar4159=this;
    java.lang.String genVar4160;
    genVar4160=genVar4159.getText();
    int genVar4161;
    genVar4161=Integer.parseInt(genVar4160);
    return genVar4161;
  }
  /** 
 * Sets the numeric value of the text box.
 * @param value
 */
  public void setValue(  int value){
    int genVar4162;
    genVar4162=0;
    boolean genVar4163;
    genVar4163=value < genVar4162;
    if (genVar4163) {
      value=0;
    }
 else {
      ;
    }
    NumberTextField genVar4164;
    genVar4164=this;
    java.lang.String genVar4165;
    genVar4165=Integer.toString(value);
    genVar4164.setText(genVar4165);
  }
}
