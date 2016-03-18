package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;
import jFrame.UI;

public class RoomList extends Shell implements SelectionListener{
	List li1, li2;
	Text tf;
	Button b;
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	public String id, roomtitle;
	UI r;
	
	static String username = "jaehyun";
	
	protected void checkSubclass() {
	}
	
	public RoomList(String id){
		super();
		this.setText(" "+id+" 's chat");
		this.id = id;
		Shell container = getShell();
		container.setLayout(new BorderLayout());
		Composite pa1 = new Composite(container, SWT.NULL);
		pa1.setLayoutData(BorderData.CENTER);
		Composite pa2 = new Composite(container, SWT.NULL);
		pa2.setLayoutData(BorderData.SOUTH);
		pa1.setLayout(new BorderLayout());
		pa2.setLayout(new BorderLayout());
		
		li1 = new List(pa1, SWT.BORDER);
		
		li1.setLayoutData(BorderData.CENTER);
		
		li1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent me) {
				System.out.println("mouse down");
				if((String)li1.getSelection()[0]!=null){
					String selectroom = (String)li1.getSelection()[0];
					selectroom = selectroom.substring(0, selectroom.indexOf("/"));
					RoomList.this.selectRoom(selectroom);
				}
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		
		li2 = new List(pa1, SWT.BORDER);
		li2.setLayoutData(BorderData.EAST);
		tf = new Text(pa2, SWT.BORDER);
		tf.setTextLimit(30);
		tf.setLayoutData(BorderData.CENTER);
		tf.addSelectionListener(this);
		b = new Button(pa2, SWT.PUSH);
		b.setText("make room");
		b.setLayoutData(BorderData.EAST);
		b.addSelectionListener(this);
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
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}
	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// make room
		r = new UI(this, tf.getText(), this.id);
		roomtitle = tf.getText();
		sendMsg("makeroom/"+tf.getText()+"/"+id);
		sendMsg("getIndex/"+roomtitle+"/"+id);
		setVisible(false);
		tf.setText("");
		r.setVisible(true);
		r.pack();
		r.open();

	}
	
	public void selectRoom(String selectroom){
		r = new UI(this, selectroom, this.id);
		roomtitle = selectroom;
		sendMsg("enterroom/"+roomtitle+"/"+id);
		sendMsg("getIndex/"+roomtitle+"/"+id);
		setVisible(false);  
		r.setVisible(true);
		r.pack();
		r.open();
	}
	public void sendMsg(String msg){
		try {
			bw.write(msg+"\n");
			bw.flush(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readMsg(){
		String line="";
		try {
			while((line = br.readLine()) != null){
				String[] arr = parseMsg(line.substring(line.indexOf("/")+1));
				if(line.startsWith("roomlist/")){    
					li1.removeAll();
					String[] room = new String[arr.length];
					for(int i=0, j=1; i<arr.length; i+=2, j+=2){
						room[i] = arr[i]+"/"+arr[j];
					}
					li1.setItems(room);
				}else if(line.startsWith("guestlist/")){  
					li2.removeAll();
					li2.setItems(arr);
				}else if(line.startsWith("roomguestlist/")){ 
					r.li.removeAll();
					r.li.setItems(arr);
				}else if(line.startsWith("enterroom/")){ 
					r.ta.append("["+arr[1]+"] IN..."+"\n");     
				}else if(line.startsWith("exitroom/")){ 
					r.ta.append("["+arr[1]+"] OUT..."+"\n");
				}else if(line.startsWith("say/")){ 
					r.ta.append("["+arr[1]+"] : "+arr[2]+"\n");      
				}else if(line.startsWith("whisper/")){
					r.ta.append("[[["+arr[0]+"]]]"+arr[1]+"\n");
				}else if(line.startsWith("dice/")){
					r.Move(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]),Integer.parseInt(arr[4]));
				}else if(line.startsWith("myIndex/")){
					r.myIndex = Integer.parseInt(arr[0]);
					System.out.println("myIndex : "+r.myIndex);
				}else if(line.startsWith("money/")){ 
					r.moneyTextField[Integer.parseInt(arr[1])].setText(arr[2]+"锟斤拷");
				}else if(line.startsWith("move/")){
					r.Move(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] parseMsg(String msg){
		StringTokenizer st = new StringTokenizer(msg, "/");
		String[] arr = new String[st.countTokens()];
		for(int i=0; st.hasMoreTokens(); i++){
			arr[i] = st.nextToken();
		}
		return arr;
	}
	
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
		  Display display = new Display();
		  RoomList rl = new RoomList(username);
		  rl.setBounds(200, 200, 400, 300);
		  
		  System.out.println("finished");
		  rl.setVisible(true);
		  rl.pack();
		  rl.open();
		  while(!rl.isDisposed()){
			  if(!display.readAndDispatch()){
				  display.sleep();
			  }
		  }
		  display.dispose();
		  
		  rl.readMsg();
	}
}