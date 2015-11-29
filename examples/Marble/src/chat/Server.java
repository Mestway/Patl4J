package chat;
import java.net.*;
import java.io.*;
import java.util.*;
public class Server {
  ServerSocket ss;
  Vector<Guest> v=new Vector<Guest>();
  HashMap<String,Vector> map=new HashMap<String,Vector>();
  public Server(){
    try {
      int genVar302;
      genVar302=4313;
      ss=new ServerSocket(genVar302);
      while (true) {
        Socket s;
        s=ss.accept();
        chat.Server genVar303;
        genVar303=this;
        Guest g;
        g=new Guest(s,genVar303);
        Server genVar304;
        genVar304=this;
        genVar304.addGuest(g);
        boolean genVar305;
        genVar305=true;
        g.setDaemon(genVar305);
        g.start();
      }
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public void addGuest(  Guest g){
    v.add(g);
  }
  public void removeGuest(  Guest g){
    v.remove(g);
  }
  public void addRoom(  String roomtitle){
    Vector<Guest> rv;
    rv=new Vector<Guest>();
    map.put(roomtitle,rv);
  }
  public void removeRoom(  String roomtitle){
    map.remove(roomtitle);
  }
  public void addRoomGuest(  Guest g){
    Vector<Guest> rv;
    rv=map.get(g.roomtitle);
    rv.add(g);
  }
  public void removeRoomGuest(  Guest g){
    Vector<Guest> rv;
    rv=map.get(g.roomtitle);
    rv.remove(g);
    int genVar306;
    genVar306=rv.size();
    int genVar307;
    genVar307=0;
    boolean genVar308;
    genVar308=genVar306 == genVar307;
    if (genVar308) {
      Server genVar309;
      genVar309=this;
      genVar309.removeRoom(g.roomtitle);
      Server genVar310;
      genVar310=this;
      java.lang.String genVar311;
      genVar311="removeroom/";
      genVar310.broadcast(genVar311);
    }
 else {
      ;
    }
  }
  public void broadcast(  String msg){
    for (    Guest g : v) {
      g.sendMsg(msg);
    }
  }
  public void broadcastRoom(  String roomtitle,  String msg){
    java.util.Vector genVar312;
    genVar312=map.get(roomtitle);
    boolean genVar313;
    genVar313=genVar312 != null;
    if (genVar313) {
      Vector<Guest> rv;
      rv=map.get(roomtitle);
      for (      Guest g : rv) {
        g.sendMsg(msg);
      }
    }
 else {
      ;
    }
  }
  public void broadcastRoomWithID(  String roomtitle,  String msg,  String id){
    java.util.Vector genVar314;
    genVar314=map.get(roomtitle);
    boolean genVar315;
    genVar315=genVar314 != null;
    if (genVar315) {
      Vector<Guest> rv;
      rv=map.get(roomtitle);
      int i=0;
      for (; i < rv.size(); i++) {
        chat.Guest genVar316;
        genVar316=rv.get(i);
        java.lang.String genVar317;
        genVar317=genVar316.id;
        boolean genVar318;
        genVar318=genVar317.equals(id);
        if (genVar318) {
          java.lang.String genVar319;
          genVar319="/";
          msg=msg + genVar319 + i;
        }
 else {
          ;
        }
      }
      for (      Guest g : rv) {
        g.sendMsg(msg);
      }
    }
 else {
      ;
    }
  }
  public void returnIndex(  String roomtitle,  String id){
    java.util.Vector genVar320;
    genVar320=map.get(roomtitle);
    boolean genVar321;
    genVar321=genVar320 != null;
    if (genVar321) {
      java.lang.String genVar322;
      genVar322="id:";
      java.lang.String genVar323;
      genVar323=genVar322 + id;
      System.out.println(genVar323);
      Vector<Guest> rv;
      rv=map.get(roomtitle);
      int i=0;
      for (; i < rv.size(); i++) {
        chat.Guest genVar324;
        genVar324=rv.get(i);
        java.lang.String genVar325;
        genVar325=genVar324.id;
        boolean genVar326;
        genVar326=genVar325.equals(id);
        if (genVar326) {
          chat.Guest genVar327;
          genVar327=rv.get(i);
          java.lang.String genVar328;
          genVar328="myIndex/";
          java.lang.String genVar329;
          genVar329=genVar328 + i;
          genVar327.sendMsg(genVar329);
        }
 else {
          ;
        }
      }
    }
 else {
      java.lang.String genVar330;
      genVar330="Server.returnIndex() error";
      System.err.println(genVar330);
    }
  }
  void whisper(  String roomtitle,  String receiveName,  String sendName,  String msg){
    Vector<Guest> rv;
    rv=map.get(roomtitle);
    for (    Guest g : rv) {
      boolean genVar331;
      genVar331=g.id.equals(receiveName);
      if (genVar331) {
        java.lang.String genVar332;
        genVar332="whisper/";
        java.lang.String genVar333;
        genVar333="/";
        java.lang.String genVar334;
        genVar334=genVar332 + sendName + genVar333+ msg;
        g.sendMsg(genVar334);
      }
 else {
        ;
      }
    }
  }
  public void broadcastRoomList(){
    String roomlist;
    roomlist="roomlist/";
    Set<String> set;
    set=map.keySet();
    for (    String roomtitle : set) {
      String name;
      name=roomtitle;
      java.lang.String genVar335;
      genVar335="/";
      roomlist+=roomtitle + genVar335;
      int i=0;
      for (; i < map.size(); i++) {
        Vector<Guest> rv;
        rv=map.get(name);
        int genVar336;
        genVar336=rv.size();
        java.lang.String genVar337;
        genVar337="/";
        roomlist+=genVar336 + genVar337;
      }
    }
    Server genVar338;
    genVar338=this;
    genVar338.broadcast(roomlist);
  }
  public void broadcastGuestList(){
    String guestlist;
    guestlist="guestlist/";
    for (    Guest g : v) {
      java.lang.String genVar339;
      genVar339="/";
      guestlist+=g.id + genVar339;
    }
    Server genVar340;
    genVar340=this;
    genVar340.broadcast(guestlist);
  }
  public void broadcastRoomGuestList(  String roomtitle){
    String roomguestlist;
    roomguestlist="roomguestlist/";
    java.util.Vector genVar341;
    genVar341=map.get(roomtitle);
    boolean genVar342;
    genVar342=genVar341 != null;
    if (genVar342) {
      Vector<Guest> rv;
      rv=map.get(roomtitle);
      int genVar343;
      genVar343=rv.size();
      int genVar344;
      genVar344=0;
      boolean genVar345;
      genVar345=genVar343 > genVar344;
      if (genVar345) {
        for (        Guest g : rv) {
          java.lang.String genVar346;
          genVar346="/";
          roomguestlist+=g.id + genVar346;
        }
        Server genVar347;
        genVar347=this;
        genVar347.broadcastRoom(roomtitle,roomguestlist);
      }
 else {
        ;
      }
    }
 else {
      ;
    }
  }
  public static void main(  String args[]){
    Server server;
    server=new Server();
  }
}
