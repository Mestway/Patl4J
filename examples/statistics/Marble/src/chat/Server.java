package chat;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	ServerSocket ss;
	Vector<Guest> v = new Vector<Guest>();
	HashMap<String, Vector> map = new HashMap<String, Vector>();
	public Server(){
		try {
			ss = new ServerSocket(4313);
			while(true){
				Socket s = ss.accept();
				Guest g = new Guest(s, this);
				addGuest(g);
				g.setDaemon(true);
				g.start();   
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 대기실 사용자 추가
	public void addGuest(Guest g){
		v.add(g);
	}
	// 대기실 사용자 제거
	public void removeGuest(Guest g){
		v.remove(g);
	}
	// 방 추가
	public void addRoom(String roomtitle){
		Vector<Guest> rv = new Vector<Guest>();
		map.put(roomtitle, rv);
	}
	// 방 제거
	public void removeRoom(String roomtitle){
		map.remove(roomtitle);
	}
	// 방 사용자 추가
	public void addRoomGuest(Guest g){
		Vector<Guest> rv = map.get(g.roomtitle);
		rv.add(g);
	}
	// 방 사용자 제거
	public void removeRoomGuest(Guest g){
		Vector<Guest> rv = map.get(g.roomtitle);
		rv.remove(g);
		if(rv.size()==0){
			removeRoom(g.roomtitle);
			broadcast("removeroom/");
		}
	}
	// 대기실 모두에게
	public void broadcast(String msg){
		for(Guest g : v){
			g.sendMsg(msg);
		}
	}
	// 방 사용자 모두에게
	public void broadcastRoom(String roomtitle, String msg){
		if(map.get(roomtitle)!=null){
			Vector<Guest> rv = map.get(roomtitle);
			for(Guest g : rv){
				g.sendMsg(msg);
			}
		}
	}

	// 방 사용자 모두에게 아이디를 인덱스로 변환하여 보냄
	public void broadcastRoomWithID(String roomtitle, String msg, String id){
		if(map.get(roomtitle)!=null){
			Vector<Guest> rv = map.get(roomtitle);
			for(int i=0; i<rv.size(); i++){
				if(rv.get(i).id.equals(id))
					msg = msg+"/"+i;
			}
			for(Guest g : rv){
				g.sendMsg(msg);
			}
		}
	}
	
	public void returnIndex(String roomtitle, String id){
		if(map.get(roomtitle)!=null){
			System.out.println("id:"+id);
			Vector<Guest> rv = map.get(roomtitle);
			for(int i=0; i<rv.size(); i++){
				if(rv.get(i).id.equals(id)){
					rv.get(i).sendMsg("myIndex/"+i);
				}
			}
		} else
			System.err.println("Server.returnIndex() error");
	}

	// 귓속말
	void whisper(String roomtitle, String receiveName, String sendName, String msg){
		Vector<Guest> rv = map.get(roomtitle);
		for( Guest g : rv){
			if(g.id.equals(receiveName))
				g.sendMsg("whisper/"+sendName+"/"+msg);
		}
	}
	// 방 목록
	public void broadcastRoomList(){
		String roomlist="roomlist/";
		Set<String> set = map.keySet();
		for(String roomtitle : set){
			String name=roomtitle;
			roomlist+=roomtitle+"/";
			for(int i=0; i<map.size(); i++){
				Vector<Guest> rv = map.get(name);
				roomlist+=rv.size()+"/";    
			}
		}
		broadcast(roomlist);
	}
	// 대기실 사용자 목록
	public void broadcastGuestList(){
		String guestlist="guestlist/";
		for(Guest g : v){
			guestlist+=g.id+"/";
		}
		broadcast(guestlist);
	}
	// 방 사용자 목록
	public void broadcastRoomGuestList(String roomtitle){
		String roomguestlist="roomguestlist/";
		if(map.get(roomtitle)!=null){
			Vector<Guest> rv = map.get(roomtitle);
			if(rv.size()>0){
				for(Guest g : rv){
					roomguestlist+=g.id+"/";
				}
				broadcastRoom(roomtitle, roomguestlist);
			}
		}
	}
	public static void main(String args[]){
		Server server = new Server();

	}
}