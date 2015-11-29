package chat;
import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
public class Guest extends Thread {
  Socket s;
  Server server;
  BufferedReader br;
  BufferedWriter bw;
  String id, roomtitle, say, receivename, sendname, whisper;
  public Guest(  Socket s,  Server server){
    chat.Guest genVar0;
    genVar0=this;
    genVar0.s=s;
    chat.Guest genVar1;
    genVar1=this;
    genVar1.server=server;
    try {
      java.io.InputStream genVar2;
      genVar2=s.getInputStream();
      java.io.InputStreamReader genVar3;
      genVar3=new InputStreamReader(genVar2);
      br=new BufferedReader(genVar3);
      java.io.OutputStream genVar4;
      genVar4=s.getOutputStream();
      java.io.OutputStreamWriter genVar5;
      genVar5=new OutputStreamWriter(genVar4);
      bw=new BufferedWriter(genVar5);
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public void run(){
    String line;
    line="";
    try {
      while ((line=br.readLine()) != null) {
        System.out.println(line);
        Guest genVar6;
        genVar6=this;
        java.lang.String genVar7;
        genVar7="/";
        int genVar8;
        genVar8=line.indexOf(genVar7);
        int genVar9;
        genVar9=1;
        int genVar10;
        genVar10=genVar8 + genVar9;
        java.lang.String genVar11;
        genVar11=line.substring(genVar10);
        String[] arr;
        arr=genVar6.parseMsg(genVar11);
        java.lang.String genVar12;
        genVar12="in/";
        boolean genVar13;
        genVar13=line.startsWith(genVar12);
        if (genVar13) {
          int genVar14;
          genVar14=0;
          id=arr[genVar14];
          server.broadcastRoomList();
          server.broadcastGuestList();
        }
 else {
          java.lang.String genVar15;
          genVar15="out/";
          boolean genVar16;
          genVar16=line.startsWith(genVar15);
          if (genVar16) {
            int genVar17;
            genVar17=0;
            id=arr[genVar17];
            chat.Guest genVar18;
            genVar18=this;
            server.removeGuest(genVar18);
            server.broadcastRoomList();
            server.broadcastGuestList();
            Guest genVar19;
            genVar19=this;
            genVar19.terminate();
          }
 else {
            java.lang.String genVar20;
            genVar20="makeroom/";
            boolean genVar21;
            genVar21=line.startsWith(genVar20);
            if (genVar21) {
              int genVar22;
              genVar22=0;
              roomtitle=arr[genVar22];
              int genVar23;
              genVar23=1;
              java.lang.String genVar24;
              genVar24=arr[genVar23];
              java.lang.String genVar25;
              genVar25="(king)";
              id=genVar24 + genVar25;
              server.addRoom(roomtitle);
              chat.Guest genVar26;
              genVar26=this;
              server.addRoomGuest(genVar26);
              chat.Guest genVar27;
              genVar27=this;
              server.removeGuest(genVar27);
              server.broadcastRoomList();
              server.broadcastGuestList();
              server.broadcastRoomGuestList(roomtitle);
            }
 else {
              java.lang.String genVar28;
              genVar28="removeroom/";
              boolean genVar29;
              genVar29=line.startsWith(genVar28);
              if (genVar29) {
                server.broadcastRoomList();
                server.broadcastGuestList();
              }
 else {
                java.lang.String genVar30;
                genVar30="enterroom/";
                boolean genVar31;
                genVar31=line.startsWith(genVar30);
                if (genVar31) {
                  int genVar32;
                  genVar32=0;
                  roomtitle=arr[genVar32];
                  int genVar33;
                  genVar33=1;
                  id=arr[genVar33];
                  chat.Guest genVar34;
                  genVar34=this;
                  server.addRoomGuest(genVar34);
                  chat.Guest genVar35;
                  genVar35=this;
                  server.removeGuest(genVar35);
                  server.broadcastRoomList();
                  server.broadcastGuestList();
                  server.broadcastRoomGuestList(roomtitle);
                  server.broadcastRoom(roomtitle,line);
                }
 else {
                  java.lang.String genVar36;
                  genVar36="exitroom/";
                  boolean genVar37;
                  genVar37=line.startsWith(genVar36);
                  if (genVar37) {
                    int genVar38;
                    genVar38=0;
                    roomtitle=arr[genVar38];
                    int genVar39;
                    genVar39=1;
                    id=arr[genVar39];
                    chat.Guest genVar40;
                    genVar40=this;
                    server.addGuest(genVar40);
                    chat.Guest genVar41;
                    genVar41=this;
                    server.removeRoomGuest(genVar41);
                    server.broadcastRoomList();
                    server.broadcastGuestList();
                    server.broadcastRoomGuestList(roomtitle);
                    server.broadcastRoom(roomtitle,line);
                  }
 else {
                    java.lang.String genVar42;
                    genVar42="say/";
                    boolean genVar43;
                    genVar43=line.startsWith(genVar42);
                    if (genVar43) {
                      int genVar44;
                      genVar44=0;
                      roomtitle=arr[genVar44];
                      int genVar45;
                      genVar45=1;
                      id=arr[genVar45];
                      int genVar46;
                      genVar46=2;
                      say=arr[genVar46];
                      server.broadcastRoom(roomtitle,line);
                    }
 else {
                      java.lang.String genVar47;
                      genVar47="whisper/";
                      boolean genVar48;
                      genVar48=line.startsWith(genVar47);
                      if (genVar48) {
                        int genVar49;
                        genVar49=0;
                        roomtitle=arr[genVar49];
                        int genVar50;
                        genVar50=1;
                        receivename=arr[genVar50];
                        int genVar51;
                        genVar51=2;
                        sendname=arr[genVar51];
                        int genVar52;
                        genVar52=3;
                        whisper=arr[genVar52];
                        server.whisper(roomtitle,receivename,sendname,whisper);
                      }
 else {
                        java.lang.String genVar53;
                        genVar53="dice/";
                        boolean genVar54;
                        genVar54=line.startsWith(genVar53);
                        if (genVar54) {
                          int genVar55;
                          genVar55=0;
                          roomtitle=arr[genVar55];
                          int genVar56;
                          genVar56=1;
                          id=arr[genVar56];
                          int genVar57;
                          genVar57=2;
                          say=arr[genVar57];
                          server.broadcastRoomWithID(roomtitle,line,id);
                        }
 else {
                          java.lang.String genVar58;
                          genVar58="getIndex/";
                          boolean genVar59;
                          genVar59=line.startsWith(genVar58);
                          if (genVar59) {
                            int genVar60;
                            genVar60=0;
                            roomtitle=arr[genVar60];
                            int genVar61;
                            genVar61=1;
                            id=arr[genVar61];
                            server.returnIndex(roomtitle,id);
                          }
 else {
                            java.lang.String genVar62;
                            genVar62="money/";
                            boolean genVar63;
                            genVar63=line.startsWith(genVar62);
                            if (genVar63) {
                              int genVar64;
                              genVar64=0;
                              roomtitle=arr[genVar64];
                              server.broadcastRoom(roomtitle,line);
                            }
 else {
                              java.lang.String genVar65;
                              genVar65="move/";
                              boolean genVar66;
                              genVar66=line.startsWith(genVar65);
                              if (genVar66) {
                                int genVar67;
                                genVar67=0;
                                roomtitle=arr[genVar67];
                                int genVar68;
                                genVar68=1;
                                id=arr[genVar68];
                                int genVar69;
                                genVar69=2;
                                say=arr[genVar69];
                                server.broadcastRoomWithID(roomtitle,line,id);
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
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public void sendMsg(  String msg){
    try {
      java.lang.String genVar70;
      genVar70="\n";
      java.lang.String genVar71;
      genVar71=msg + genVar70;
      bw.write(genVar71);
      bw.flush();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public String[] parseMsg(  String msg){
    java.lang.String genVar72;
    genVar72="/";
    StringTokenizer st;
    st=new StringTokenizer(msg,genVar72);
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
}
