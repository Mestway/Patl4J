package chat;
import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
public class Room extends JFrame implements ActionListener {
  JFrame f;
  JPanel pa1, pa2;
  JScrollPane sp1, sp2;
  JTextArea ta;
  JList li;
  JTextField tf;
  JButton b;
  JPopupMenu pm;
  JMenuItem mi;
  RoomList rl;
  BufferedReader br;
  BufferedWriter bw;
  String roomtitle;
  public Room(  RoomList rl,  String roomtitle){
    super(" " + roomtitle + " Room("+ rl.id+ ")");
    chat.Room genVar77;
    genVar77=this;
    genVar77.rl=rl;
    chat.Room genVar78;
    genVar78=this;
    genVar78.roomtitle=roomtitle;
    pa1=new JPanel();
    pa2=new JPanel();
    java.awt.BorderLayout genVar79;
    genVar79=new BorderLayout();
    pa1.setLayout(genVar79);
    java.awt.BorderLayout genVar80;
    genVar80=new BorderLayout();
    pa2.setLayout(genVar80);
    int genVar81;
    genVar81=15;
    int genVar82;
    genVar82=30;
    ta=new JTextArea(genVar81,genVar82);
    li=new JList();
    pm=new JPopupMenu();
    java.lang.String genVar83;
    genVar83="whisper";
    mi=new JMenuItem(genVar83);
    chat.Room genVar84;
    genVar84=this;
    mi.addActionListener(genVar84);
    pm.add(mi);
    java.lang.String genVar85;
    genVar85="user List";
    javax.swing.border.TitledBorder genVar86;
    genVar86=new TitledBorder(genVar85);
    li.setBorder(genVar86);
    li.add(pm);
    MouseAdapter genVar87;
    genVar87=new MouseAdapter(){
      public void mouseClicked(      MouseEvent me){
        if (me.getButton() == 3) {
          pm.show(li,me.getX(),me.getY());
        }
      }
    }
;
    li.addMouseListener(genVar87);
    tf=new JTextField();
    chat.Room genVar88;
    genVar88=this;
    tf.addActionListener(genVar88);
    java.lang.String genVar89;
    genVar89="exit room";
    b=new JButton(genVar89);
    chat.Room genVar90;
    genVar90=this;
    b.addActionListener(genVar90);
    sp1=new JScrollPane(ta);
    sp2=new JScrollPane(li);
    java.lang.String genVar91;
    genVar91="Center";
    pa1.add(sp1,genVar91);
    java.lang.String genVar92;
    genVar92="East";
    pa1.add(sp2,genVar92);
    java.lang.String genVar93;
    genVar93="Center";
    pa2.add(tf,genVar93);
    java.lang.String genVar94;
    genVar94="East";
    pa2.add(b,genVar94);
    Room genVar95;
    genVar95=this;
    Container container;
    container=genVar95.getContentPane();
    java.lang.String genVar96;
    genVar96="Center";
    container.add(pa1,genVar96);
    java.lang.String genVar97;
    genVar97="South";
    container.add(pa2,genVar97);
    Room genVar98;
    genVar98=this;
    WindowAdapter genVar99;
    genVar99=new WindowAdapter(){
      public void windowClosing(      WindowEvent we){
        Room.this.rl.sendMsg("exitroom/" + Room.this.rl.roomtitle + "/"+ Room.this.rl.id);
        setVisible(false);
        Room.this.rl.setVisible(true);
      }
    }
;
    genVar98.addWindowListener(genVar99);
    Room genVar100;
    genVar100=this;
    genVar100.pack();
    Room genVar101;
    genVar101=this;
    boolean genVar102;
    genVar102=true;
    genVar101.setVisible(genVar102);
  }
  public void actionPerformed(  ActionEvent ae){
    java.lang.Object genVar103;
    genVar103=ae.getSource();
    boolean genVar104;
    genVar104=genVar103 == tf;
    if (genVar104) {
      java.lang.String genVar105;
      genVar105="say/";
      java.lang.String genVar106;
      genVar106="/";
      java.lang.String genVar107;
      genVar107="/";
      java.lang.String genVar108;
      genVar108=tf.getText();
      java.lang.String genVar109;
      genVar109=genVar105 + rl.roomtitle + genVar106+ rl.id+ genVar107+ genVar108;
      rl.sendMsg(genVar109);
      java.lang.String genVar110;
      genVar110="";
      tf.setText(genVar110);
    }
 else {
      java.lang.Object genVar111;
      genVar111=ae.getSource();
      boolean genVar112;
      genVar112=genVar111 == b;
      if (genVar112) {
        java.lang.String genVar113;
        genVar113="exitroom/";
        java.lang.String genVar114;
        genVar114="/";
        java.lang.String genVar115;
        genVar115=genVar113 + rl.roomtitle + genVar114+ rl.id;
        rl.sendMsg(genVar115);
        Room genVar116;
        genVar116=this;
        boolean genVar117;
        genVar117=false;
        genVar116.setVisible(genVar117);
        boolean genVar118;
        genVar118=true;
        rl.setVisible(genVar118);
      }
 else {
        java.lang.Object genVar119;
        genVar119=ae.getSource();
        boolean genVar120;
        genVar120=genVar119 == mi;
        if (genVar120) {
          java.lang.Object genVar121;
          genVar121=li.getSelectedValue();
          java.lang.String genVar122;
          genVar122=(String)genVar121;
          Whisper w;
          w=new Whisper(rl,genVar122);
          w.pack();
          boolean genVar123;
          genVar123=true;
          w.setVisible(genVar123);
        }
 else {
          ;
        }
      }
    }
  }
}
