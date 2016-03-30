package chat;

import jFrame.UI;

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoomList extends JFrame implements ActionListener{
	JList li1, li2;
	JTextField tf;
	JButton b;
	JScrollPane sp1, sp2;
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	public String id, roomtitle;
	UI r;
	
	static String username = "jaehyun";
	
	public RoomList(String id){
		super(" "+id+" 's chat");
		this.id = id;
		JPanel pa1 = new JPanel();//Ymm
		JPanel pa2 = new JPanel();//Ymm
		pa1.setLayout(new BorderLayout());//Y
		pa2.setLayout(new BorderLayout());//Y
		li1 = new JList();//Ymm
		li1.addMouseListener(    // �� ����Ʈ �߿��� Ŭ������ ��(�� ����) //Y
				new MouseAdapter(){//Ym
					public void mouseClicked(MouseEvent me){//Ym
						if(me.getButton()==1){
							if((String)li1.getSelectedValue()!=null){//Y
								String selectroom = (String)li1.getSelectedValue();//Y
								selectroom = selectroom.substring(0, selectroom.indexOf("/"));
								RoomList.this.selectRoom(selectroom);
							}
						}
					}
				}
				);
		li2 = new JList();//Ymm
		tf = new JTextField(30);//Ymm
		tf.addActionListener(this);//Y
		b = new JButton("make room");//Ymm
		b.addActionListener(this);//Y
		pa1.add(li1, "Center");//Ymm
		pa1.add(li2, "East");//Ymm
		pa2.add(tf, "Center");//Ymm
		pa2.add(b, "East");//Ymm
		Container container = getContentPane();//Y
		container.add(pa1, "Center");//Ymm
		container.add(pa2, "South");//Ymm
		try {
			s = new Socket("localhost", 4313);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendMsg("in/"+id);
		addWindowListener(     // ���ǿ��� ���� ��(����)//Y
				new WindowAdapter(){//Ym
					public void windowClosing(WindowEvent e){//Ym
						sendMsg("out/"+RoomList.this.id);
						terminate();
						System.exit(1);
					}
				}
				);
	}
	// �̺�Ʈ ó��
	public void actionPerformed(ActionEvent ae){
		// make room
		r = new UI(this, tf.getText(), this.id);//Y
		roomtitle = tf.getText();//Y
		sendMsg("makeroom/"+tf.getText()+"/"+id);//Y
		sendMsg("getIndex/"+roomtitle+"/"+id);
		setVisible(false);//Y
		tf.setText("");//Y
		r.setVisible(true);//Y

	}
	// �� ����Ʈ���� ����
	public void selectRoom(String selectroom){
		// �� ����
		r = new UI(this, selectroom, this.id);
		roomtitle = selectroom;
		sendMsg("enterroom/"+roomtitle+"/"+id);
		sendMsg("getIndex/"+roomtitle+"/"+id);
		setVisible(false);  //Y
		r.setVisible(true);//Y
	}
	// �޽��� ������
	public void sendMsg(String msg){
		try {
			bw.write(msg+"\n");
			bw.flush(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// �޽��� �ޱ�
	public void readMsg(){
		String line="";
		try {
			while((line=br.readLine())!=null){
				String[] arr = parseMsg(line.substring(line.indexOf("/")+1));
				if(line.startsWith("roomlist/")){      // �� ��� �Ѹ���
					li1.removeAll();//Y
					String[] room = new String[arr.length];
					for(int i=0, j=1; i<arr.length; i+=2, j+=2){
						room[i] = arr[i]+"/"+arr[j];
					}
					li1.setListData(room);//Y
				}else if(line.startsWith("guestlist/")){    // ����� ��� �Ѹ���
					li2.removeAll();//Y
					li2.setListData(arr);//Y
				}else if(line.startsWith("roomguestlist/")){  // �� ����� ��� �Ѹ���
					r.li.removeAll();//Y
					r.li.setListData(arr);//Y
				}else if(line.startsWith("enterroom/")){    // �� ���� �˸���
					r.ta.append("["+arr[1]+"] IN..."+"\n");     
				}else if(line.startsWith("exitroom/")){    // �� ���� �˸���
					r.ta.append("["+arr[1]+"] OUT..."+"\n");
				}else if(line.startsWith("say/")){      // �� ��ȭ �Ѹ���
					r.ta.append("["+arr[1]+"] : "+arr[2]+"\n");      
				}else if(line.startsWith("whisper/")){    // �ӼӸ�
					r.ta.append("[[["+arr[0]+"]]]"+arr[1]+"\n");
				}else if(line.startsWith("dice/")){
					r.Move(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),Integer.parseInt(arr[4]));
				}else if(line.startsWith("myIndex/")){ // �ڽ��� �ε��� ���ϱ�
					r.myIndex = Integer.parseInt(arr[0]);
					System.out.println("myIndex : "+r.myIndex);
				}else if(line.startsWith("money/")){ // ������
					r.moneyTextField[Integer.parseInt(arr[1])].setText(arr[2]+"��");//Y
				}else if(line.startsWith("move/")){
					r.Move(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// �޽��� �Ľ�
	public String[] parseMsg(String msg){
		StringTokenizer st = new StringTokenizer(msg, "/");
		String[] arr = new String[st.countTokens()];
		for(int i=0; st.hasMoreTokens(); i++){
			arr[i] = st.nextToken();
		}
		return arr;
	}
	// �ڿ� ����
	public void terminate(){
		try {
			br.close();
			bw.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 public static void main(String args[]){
		 
		  RoomList rl = new RoomList(username);
		  rl.setBounds(200, 200, 400, 300);//Y
		  rl.setVisible(true);//Y
		  rl.readMsg();
	}
}