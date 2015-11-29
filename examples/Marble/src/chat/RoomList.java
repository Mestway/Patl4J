package chat;
import jFrame.UI;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class RoomList extends JFrame implements ActionListener {
  JList li1, li2;
  JTextField tf;
  JButton b;
  JScrollPane sp1, sp2;
  Socket s;
  BufferedReader br;
  BufferedWriter bw;
  public String id, roomtitle;
  UI r;
  static String username="jaehyun";
  public RoomList(  String id){
    super(" " + id + " 's chat");
    chat.RoomList genVar127;
    genVar127=this;
    genVar127.id=id;
    JPanel pa1;
    pa1=new JPanel();
    JPanel pa2;
    pa2=new JPanel();
    java.awt.BorderLayout genVar128;
    genVar128=new BorderLayout();
    pa1.setLayout(genVar128);
    java.awt.BorderLayout genVar129;
    genVar129=new BorderLayout();
    pa2.setLayout(genVar129);
    li1=new JList();
    MouseAdapter genVar130;
    genVar130=new MouseAdapter(){
      public void mouseClicked(      MouseEvent me){
        if (me.getButton() == 1) {
          if ((String)li1.getSelectedValue() != null) {
            String selectroom=(String)li1.getSelectedValue();
            selectroom=selectroom.substring(0,selectroom.indexOf("/"));
            RoomList.this.selectRoom(selectroom);
          }
        }
      }
    }
;
    li1.addMouseListener(genVar130);
    li2=new JList();
    int genVar131;
    genVar131=30;
    tf=new JTextField(genVar131);
    chat.RoomList genVar132;
    genVar132=this;
    tf.addActionListener(genVar132);
    java.lang.String genVar133;
    genVar133="make room";
    b=new JButton(genVar133);
    chat.RoomList genVar134;
    genVar134=this;
    b.addActionListener(genVar134);
    java.lang.String genVar135;
    genVar135="Center";
    pa1.add(li1,genVar135);
    java.lang.String genVar136;
    genVar136="East";
    pa1.add(li2,genVar136);
    java.lang.String genVar137;
    genVar137="Center";
    pa2.add(tf,genVar137);
    java.lang.String genVar138;
    genVar138="East";
    pa2.add(b,genVar138);
    RoomList genVar139;
    genVar139=this;
    Container container;
    container=genVar139.getContentPane();
    java.lang.String genVar140;
    genVar140="Center";
    container.add(pa1,genVar140);
    java.lang.String genVar141;
    genVar141="South";
    container.add(pa2,genVar141);
    try {
      java.lang.String genVar142;
      genVar142="localhost";
      int genVar143;
      genVar143=4313;
      s=new Socket(genVar142,genVar143);
      java.io.InputStream genVar144;
      genVar144=s.getInputStream();
      java.io.InputStreamReader genVar145;
      genVar145=new InputStreamReader(genVar144);
      br=new BufferedReader(genVar145);
      java.io.OutputStream genVar146;
      genVar146=s.getOutputStream();
      java.io.OutputStreamWriter genVar147;
      genVar147=new OutputStreamWriter(genVar146);
      bw=new BufferedWriter(genVar147);
    }
 catch (    UnknownHostException e) {
      e.printStackTrace();
    }
catch (    IOException e) {
      e.printStackTrace();
    }
    RoomList genVar148;
    genVar148=this;
    java.lang.String genVar149;
    genVar149="in/";
    java.lang.String genVar150;
    genVar150=genVar149 + id;
    genVar148.sendMsg(genVar150);
    RoomList genVar151;
    genVar151=this;
    WindowAdapter genVar152;
    genVar152=new WindowAdapter(){
      public void windowClosing(      WindowEvent e){
        sendMsg("out/" + RoomList.this.id);
        terminate();
        System.exit(1);
      }
    }
;
    genVar151.addWindowListener(genVar152);
  }
  public void actionPerformed(  ActionEvent ae){
    chat.RoomList genVar153;
    genVar153=this;
    java.lang.String genVar154;
    genVar154=tf.getText();
    chat.RoomList genVar155;
    genVar155=this;
    java.lang.String genVar156;
    genVar156=genVar155.id;
    r=new UI(genVar153,genVar154,genVar156);
    roomtitle=tf.getText();
    RoomList genVar157;
    genVar157=this;
    java.lang.String genVar158;
    genVar158="makeroom/";
    java.lang.String genVar159;
    genVar159=tf.getText();
    java.lang.String genVar160;
    genVar160="/";
    java.lang.String genVar161;
    genVar161=genVar158 + genVar159 + genVar160+ id;
    genVar157.sendMsg(genVar161);
    RoomList genVar162;
    genVar162=this;
    java.lang.String genVar163;
    genVar163="getIndex/";
    java.lang.String genVar164;
    genVar164="/";
    java.lang.String genVar165;
    genVar165=genVar163 + roomtitle + genVar164+ id;
    genVar162.sendMsg(genVar165);
    RoomList genVar166;
    genVar166=this;
    boolean genVar167;
    genVar167=false;
    genVar166.setVisible(genVar167);
    java.lang.String genVar168;
    genVar168="";
    tf.setText(genVar168);
    boolean genVar169;
    genVar169=true;
    r.setVisible(genVar169);
  }
  public void selectRoom(  String selectroom){
    chat.RoomList genVar170;
    genVar170=this;
    chat.RoomList genVar171;
    genVar171=this;
    java.lang.String genVar172;
    genVar172=genVar171.id;
    r=new UI(genVar170,selectroom,genVar172);
    roomtitle=selectroom;
    RoomList genVar173;
    genVar173=this;
    java.lang.String genVar174;
    genVar174="enterroom/";
    java.lang.String genVar175;
    genVar175="/";
    java.lang.String genVar176;
    genVar176=genVar174 + roomtitle + genVar175+ id;
    genVar173.sendMsg(genVar176);
    RoomList genVar177;
    genVar177=this;
    java.lang.String genVar178;
    genVar178="getIndex/";
    java.lang.String genVar179;
    genVar179="/";
    java.lang.String genVar180;
    genVar180=genVar178 + roomtitle + genVar179+ id;
    genVar177.sendMsg(genVar180);
    RoomList genVar181;
    genVar181=this;
    boolean genVar182;
    genVar182=false;
    genVar181.setVisible(genVar182);
    boolean genVar183;
    genVar183=true;
    r.setVisible(genVar183);
  }
  public void sendMsg(  String msg){
    try {
      java.lang.String genVar184;
      genVar184="\n";
      java.lang.String genVar185;
      genVar185=msg + genVar184;
      bw.write(genVar185);
      bw.flush();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public void readMsg(){
    String line;
    line="";
    try {
      while ((line=br.readLine()) != null) {
        RoomList genVar186;
        genVar186=this;
        java.lang.String genVar187;
        genVar187="/";
        int genVar188;
        genVar188=line.indexOf(genVar187);
        int genVar189;
        genVar189=1;
        int genVar190;
        genVar190=genVar188 + genVar189;
        java.lang.String genVar191;
        genVar191=line.substring(genVar190);
        String[] arr;
        arr=genVar186.parseMsg(genVar191);
        java.lang.String genVar192;
        genVar192="roomlist/";
        boolean genVar193;
        genVar193=line.startsWith(genVar192);
        if (genVar193) {
          li1.removeAll();
          String[] room;
          room=new String[arr.length];
          int i=0, j=1;
          for (; i < arr.length; i+=2, j+=2) {
            java.lang.String genVar194;
            genVar194=arr[i];
            java.lang.String genVar195;
            genVar195="/";
            java.lang.String genVar196;
            genVar196=arr[j];
            room[i]=genVar194 + genVar195 + genVar196;
          }
          li1.setListData(room);
        }
 else {
          java.lang.String genVar197;
          genVar197="guestlist/";
          boolean genVar198;
          genVar198=line.startsWith(genVar197);
          if (genVar198) {
            li2.removeAll();
            li2.setListData(arr);
          }
 else {
            java.lang.String genVar199;
            genVar199="roomguestlist/";
            boolean genVar200;
            genVar200=line.startsWith(genVar199);
            if (genVar200) {
              r.li.removeAll();
              r.li.setListData(arr);
            }
 else {
              java.lang.String genVar201;
              genVar201="enterroom/";
              boolean genVar202;
              genVar202=line.startsWith(genVar201);
              if (genVar202) {
                java.lang.String genVar203;
                genVar203="[";
                int genVar204;
                genVar204=1;
                java.lang.String genVar205;
                genVar205=arr[genVar204];
                java.lang.String genVar206;
                genVar206="] IN...";
                java.lang.String genVar207;
                genVar207="\n";
                java.lang.String genVar208;
                genVar208=genVar203 + genVar205 + genVar206+ genVar207;
                r.ta.append(genVar208);
              }
 else {
                java.lang.String genVar209;
                genVar209="exitroom/";
                boolean genVar210;
                genVar210=line.startsWith(genVar209);
                if (genVar210) {
                  java.lang.String genVar211;
                  genVar211="[";
                  int genVar212;
                  genVar212=1;
                  java.lang.String genVar213;
                  genVar213=arr[genVar212];
                  java.lang.String genVar214;
                  genVar214="] OUT...";
                  java.lang.String genVar215;
                  genVar215="\n";
                  java.lang.String genVar216;
                  genVar216=genVar211 + genVar213 + genVar214+ genVar215;
                  r.ta.append(genVar216);
                }
 else {
                  java.lang.String genVar217;
                  genVar217="say/";
                  boolean genVar218;
                  genVar218=line.startsWith(genVar217);
                  if (genVar218) {
                    java.lang.String genVar219;
                    genVar219="[";
                    int genVar220;
                    genVar220=1;
                    java.lang.String genVar221;
                    genVar221=arr[genVar220];
                    java.lang.String genVar222;
                    genVar222="] : ";
                    int genVar223;
                    genVar223=2;
                    java.lang.String genVar224;
                    genVar224=arr[genVar223];
                    java.lang.String genVar225;
                    genVar225="\n";
                    java.lang.String genVar226;
                    genVar226=genVar219 + genVar221 + genVar222+ genVar224+ genVar225;
                    r.ta.append(genVar226);
                  }
 else {
                    java.lang.String genVar227;
                    genVar227="whisper/";
                    boolean genVar228;
                    genVar228=line.startsWith(genVar227);
                    if (genVar228) {
                      java.lang.String genVar229;
                      genVar229="[[[";
                      int genVar230;
                      genVar230=0;
                      java.lang.String genVar231;
                      genVar231=arr[genVar230];
                      java.lang.String genVar232;
                      genVar232="]]]";
                      int genVar233;
                      genVar233=1;
                      java.lang.String genVar234;
                      genVar234=arr[genVar233];
                      java.lang.String genVar235;
                      genVar235="\n";
                      java.lang.String genVar236;
                      genVar236=genVar229 + genVar231 + genVar232+ genVar234+ genVar235;
                      r.ta.append(genVar236);
                    }
 else {
                      java.lang.String genVar237;
                      genVar237="dice/";
                      boolean genVar238;
                      genVar238=line.startsWith(genVar237);
                      if (genVar238) {
                        int genVar239;
                        genVar239=2;
                        java.lang.String genVar240;
                        genVar240=arr[genVar239];
                        int genVar241;
                        genVar241=Integer.parseInt(genVar240);
                        int genVar242;
                        genVar242=3;
                        java.lang.String genVar243;
                        genVar243=arr[genVar242];
                        int genVar244;
                        genVar244=Integer.parseInt(genVar243);
                        int genVar245;
                        genVar245=4;
                        java.lang.String genVar246;
                        genVar246=arr[genVar245];
                        int genVar247;
                        genVar247=Integer.parseInt(genVar246);
                        r.Move(genVar241,genVar244,genVar247);
                      }
 else {
                        java.lang.String genVar248;
                        genVar248="myIndex/";
                        boolean genVar249;
                        genVar249=line.startsWith(genVar248);
                        if (genVar249) {
                          int genVar250;
                          genVar250=0;
                          java.lang.String genVar251;
                          genVar251=arr[genVar250];
                          r.myIndex=Integer.parseInt(genVar251);
                          java.lang.String genVar252;
                          genVar252="myIndex : ";
                          java.lang.String genVar253;
                          genVar253=genVar252 + r.myIndex;
                          System.out.println(genVar253);
                        }
 else {
                          java.lang.String genVar254;
                          genVar254="money/";
                          boolean genVar255;
                          genVar255=line.startsWith(genVar254);
                          if (genVar255) {
                            int genVar256;
                            genVar256=1;
                            java.lang.String genVar257;
                            genVar257=arr[genVar256];
                            int genVar258;
                            genVar258=Integer.parseInt(genVar257);
                            javax.swing.JTextField genVar259;
                            genVar259=r.moneyTextField[genVar258];
                            int genVar260;
                            genVar260=2;
                            java.lang.String genVar261;
                            genVar261=arr[genVar260];
                            java.lang.String genVar262;
                            genVar262="��";
                            java.lang.String genVar263;
                            genVar263=genVar261 + genVar262;
                            genVar259.setText(genVar263);
                          }
 else {
                            java.lang.String genVar264;
                            genVar264="move/";
                            boolean genVar265;
                            genVar265=line.startsWith(genVar264);
                            if (genVar265) {
                              int genVar266;
                              genVar266=2;
                              java.lang.String genVar267;
                              genVar267=arr[genVar266];
                              int genVar268;
                              genVar268=Integer.parseInt(genVar267);
                              int genVar269;
                              genVar269=3;
                              java.lang.String genVar270;
                              genVar270=arr[genVar269];
                              int genVar271;
                              genVar271=Integer.parseInt(genVar270);
                              r.Move(genVar268,genVar271);
                            }
 else {
                              ;
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public String[] parseMsg(  String msg){
    java.lang.String genVar272;
    genVar272="/";
    StringTokenizer st;
    st=new StringTokenizer(msg,genVar272);
    String[] arr;
    arr=new String[st.countTokens()];
    int i=0;
    for (; st.hasMoreTokens(); i++) {
      arr[i]=st.nextToken();
    }
    return arr;
  }
  public void terminate(){
    try {
      br.close();
      bw.close();
      s.close();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public static void main(  String args[]){
    RoomList rl;
    rl=new RoomList(username);
    int genVar273;
    genVar273=200;
    int genVar274;
    genVar274=200;
    int genVar275;
    genVar275=400;
    int genVar276;
    genVar276=300;
    rl.setBounds(genVar273,genVar274,genVar275,genVar276);
    boolean genVar277;
    genVar277=true;
    rl.setVisible(genVar277);
    rl.readMsg();
  }
}
