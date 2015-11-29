package com.fray.evo.ui.swingx;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.JXTextField;
/** 
 * A text field that only allows time-related characters to be entered.
 * @author mike.angstadt
 */
@SuppressWarnings("serial") public class TimeTextField extends JXTextField {
  @Override protected Document createDefaultModel(){
    com.fray.evo.ui.swingx.TimeTextField.TimeDocument genVar4056;
    genVar4056=new TimeDocument();
    return genVar4056;
  }
private static class TimeDocument extends PlainDocument {
    private static final Pattern pattern=Pattern.compile("[\\d:]*");
    @Override public void insertString(    int offs,    String str,    AttributeSet a) throws BadLocationException {
      boolean genVar4057;
      genVar4057=str == null;
      if (genVar4057) {
        return;
      }
 else {
        ;
      }
      TimeDocument genVar4058;
      genVar4058=this;
      int genVar4059;
      genVar4059=0;
      TimeDocument genVar4060;
      genVar4060=this;
      int genVar4061;
      genVar4061=genVar4060.getLength();
      String oldString;
      oldString=genVar4058.getText(genVar4059,genVar4061);
      boolean genVar4062;
      genVar4062=pattern.matcher(oldString).matches() && pattern.matcher(str).matches();
      if (genVar4062) {
        super.insertString(offs,str,a);
      }
 else {
        ;
      }
    }
  }
}
