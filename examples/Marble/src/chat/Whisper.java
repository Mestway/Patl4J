package chat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Whisper extends JDialog implements ActionListener {
  JTextField tf;
  JButton b;
  RoomList rl;
  String receivename;
  public Whisper(  RoomList rl,  String receivename){
    super(rl.r);
    chat.Whisper genVar285;
    genVar285=this;
    genVar285.rl=rl;
    chat.Whisper genVar286;
    genVar286=this;
    genVar286.receivename=receivename;
    int genVar287;
    genVar287=25;
    tf=new JTextField(genVar287);
    chat.Whisper genVar288;
    genVar288=this;
    tf.addActionListener(genVar288);
    java.lang.String genVar289;
    genVar289="whisper";
    b=new JButton(genVar289);
    chat.Whisper genVar290;
    genVar290=this;
    b.addActionListener(genVar290);
    Whisper genVar291;
    genVar291=this;
    java.lang.String genVar292;
    genVar292="Center";
    genVar291.add(tf,genVar292);
    Whisper genVar293;
    genVar293=this;
    java.lang.String genVar294;
    genVar294="East";
    genVar293.add(b,genVar294);
  }
  public void actionPerformed(  ActionEvent ae){
    java.lang.String genVar295;
    genVar295="whisper/";
    java.lang.String genVar296;
    genVar296="/";
    java.lang.String genVar297;
    genVar297="/";
    java.lang.String genVar298;
    genVar298="/";
    java.lang.String genVar299;
    genVar299=tf.getText();
    java.lang.String genVar300;
    genVar300=genVar295 + rl.roomtitle + genVar296+ receivename+ genVar297+ rl.id+ genVar298+ genVar299;
    rl.sendMsg(genVar300);
    java.lang.String genVar301;
    genVar301="";
    tf.setText(genVar301);
  }
}
