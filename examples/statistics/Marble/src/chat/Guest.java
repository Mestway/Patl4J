package chat;
import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
public class Guest extends Thread{
	Socket s;
	Server server;
	BufferedReader br;
	BufferedWriter bw;
	String id, roomtitle, say, receivename, sendname, whisper;
	
	public Guest(Socket s, Server server){
		this.s = s;
		this.server = server;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run(){
		String line="";
		try {
			while((line=br.readLine())!=null){
				System.out.println(line);
				String[] arr = parseMsg(line.substring(line.indexOf("/")+1));
				if(line.startsWith("in/")){       // 대기실 입장
					id = arr[0];
					server.broadcastRoomList();
					server.broadcastGuestList();
				}else if(line.startsWith("out/")){     // 대기실 퇴장
					id = arr[0];
					server.removeGuest(this);
					server.broadcastRoomList();
					server.broadcastGuestList();
					terminate();     
				}else if(line.startsWith("makeroom/")){  // 대화방 만들기
					roomtitle = arr[0];
					id = arr[1]+"(king)";
					server.addRoom(roomtitle);
					server.addRoomGuest(this);
					server.removeGuest(this);
					server.broadcastRoomList();
					server.broadcastGuestList();
					server.broadcastRoomGuestList(roomtitle);
				}else if(line.startsWith("removeroom/")){  // 대화방 제거
					server.broadcastRoomList();
					server.broadcastGuestList();    
				}else if(line.startsWith("enterroom/")){   // 대화방 입장
					roomtitle = arr[0];
					id = arr[1];
					server.addRoomGuest(this);
					server.removeGuest(this);
					server.broadcastRoomList();
					server.broadcastGuestList();
					server.broadcastRoomGuestList(roomtitle);
					server.broadcastRoom(roomtitle, line);
				}else if(line.startsWith("exitroom/")){   // 대화방 퇴장
					roomtitle=arr[0];
					id = arr[1];
					server.addGuest(this);
					server.removeRoomGuest(this);
					server.broadcastRoomList();
					server.broadcastGuestList();
					server.broadcastRoomGuestList(roomtitle);
					server.broadcastRoom(roomtitle, line);
				}else if(line.startsWith("say/")){     // 대화방 대화
					roomtitle = arr[0];
					id = arr[1];
					say = arr[2];
					server.broadcastRoom(roomtitle, line);
				}else if(line.startsWith("whisper/")){   // 대화방 귓속말
					roomtitle = arr[0];
					receivename = arr[1];
					sendname = arr[2];
					whisper = arr[3];
					server.whisper(roomtitle, receivename, sendname, whisper);
				}else if(line.startsWith("dice/")){ //주사위
					roomtitle = arr[0];
					id = arr[1];
					say = arr[2];
					server.broadcastRoomWithID(roomtitle, line, id);
				}
				else if(line.startsWith("getIndex/")){ //인덱스 리턴
					roomtitle = arr[0];
					id = arr[1];
					server.returnIndex(roomtitle, id);
				}
				else if(line.startsWith("money/")){ //돈정보
					roomtitle = arr[0];
					server.broadcastRoom(roomtitle, line);
				}else if(line.startsWith("move/")){ //움직이기
					roomtitle = arr[0];
					id = arr[1];
					say = arr[2];
					server.broadcastRoomWithID(roomtitle, line, id);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 메시지 보내기
	public void sendMsg(String msg){
		try {
			bw.write(msg+"\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 메시지 파싱
	public String[] parseMsg(String msg){
		StringTokenizer st = new StringTokenizer(msg, "/");
		String[] arr = new String[st.countTokens()];
		for(int i=0; st.hasMoreTokens(); i++){
			arr[i] = st.nextToken();
		}
		return arr;
	}
	// 자원 정리
	public void terminate(){
		try {
			br.close();
			bw.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}