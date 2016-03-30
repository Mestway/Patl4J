package chat;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Room extends JFrame implements ActionListener{
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
	
	public Room(RoomList rl, String roomtitle){
		super(" "+roomtitle+" Room("+rl.id+")");

		this.rl = rl;
		this.roomtitle = roomtitle;
		pa1 = new JPanel();//Ymm
		pa2 = new JPanel();//Ymm
		pa1.setLayout(new BorderLayout());//Y
		pa2.setLayout(new BorderLayout());//Y
		ta = new JTextArea(15, 30);//Ymm
		li = new JList();//Ymm
		pm = new JPopupMenu();//Ymm
		mi = new JMenuItem("whisper");//Ymm
		mi.addActionListener(this);//Y
		pm.add(mi);//Ymm
		li.setBorder(new TitledBorder("user List"));//Y
		li.add(pm);//Ymm
		li.addMouseListener(   // �ӼӸ� �޴�//Y
				new MouseAdapter(){//Ym
					public void mouseClicked(MouseEvent me){//Ym
						if(me.getButton()==3){
							pm.show(li, me.getX(), me.getY());	//N
						}
					}
				});
		tf = new JTextField();//Ymm
		tf.addActionListener(this);//Y
		b = new JButton("exit room");//Ymm
		b.addActionListener(this);//Y
		sp1 = new JScrollPane(ta);//Ymm
		sp2 = new JScrollPane(li);//Ymm
		pa1.add(sp1, "Center");//Ymm
		pa1.add(sp2, "East");//Ymm
		pa2.add(tf, "Center");//Ymm
		pa2.add(b, "East");//Ymm
		Container container = getContentPane();//Y
		container.add(pa1, "Center");//Ymm
		container.add(pa2, "South");//Ymm
		addWindowListener(   // ��ȭ�� ������//Y
				new WindowAdapter(){//Ym
					public void windowClosing(WindowEvent we){
						Room.this.rl.sendMsg("exitroom/"+Room.this.rl.roomtitle+"/"+Room.this.rl.id);
						setVisible(false);//Y
						Room.this.rl.setVisible(true);//Y
					}
				}
				);
		pack();
		setVisible(true);//Y
	}
	// �̺�Ʈ ó��
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==tf){     // ��ȭ�ϱ�
			rl.sendMsg("say/"+rl.roomtitle+"/"+rl.id+"/"+tf.getText());//Y
			tf.setText("");   //Y
		}else if(ae.getSource()==b){   // ��ȭ�� ������
			rl.sendMsg("exitroom/"+rl.roomtitle+"/"+rl.id);
			setVisible(false);//Y
			rl.setVisible(true);//Y
		}else if(ae.getSource()==mi){   // �ӼӸ�
			Whisper w = new Whisper(rl, (String)li.getSelectedValue());//Y
			w.pack();
			w.setVisible(true);//Y
		}
	}
}