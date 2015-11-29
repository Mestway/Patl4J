package chat;
import jFrame.UI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class WindowDestroyer extends WindowAdapter {
  UI r;
  RoomList rl;
  public WindowDestroyer(  UI r,  RoomList rl){
    chat.WindowDestroyer genVar278;
    genVar278=this;
    genVar278.rl=rl;
    chat.WindowDestroyer genVar279;
    genVar279=this;
    genVar279.r=r;
  }
  public void windowClosing(  WindowEvent e){
    boolean genVar280;
    genVar280=false;
    r.setVisible(genVar280);
    java.lang.String genVar281;
    genVar281="exitroom/";
    java.lang.String genVar282;
    genVar282="/";
    java.lang.String genVar283;
    genVar283=genVar281 + rl.roomtitle + genVar282+ rl.id;
    rl.sendMsg(genVar283);
    boolean genVar284;
    genVar284=true;
    rl.setVisible(genVar284);
  }
}
