package main;
import javax.swing.SwingConstants;
import ui.MainWindow;
public class Main {
  public static void main(  String[] args){
    Runnable genVar0;
    genVar0=new Runnable(){
      @Override public void run(){
        MainWindow mainWindow=new MainWindow();
      }
    }
;
    javax.swing.SwingUtilities.invokeLater(genVar0);
  }
}
