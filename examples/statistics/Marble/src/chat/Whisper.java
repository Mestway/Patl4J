package chat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Whisper extends JDialog implements ActionListener{
	JTextField tf;
	JButton b;
	RoomList rl;
	String receivename;
	public Whisper(RoomList rl, String receivename){
		super(rl.r);
		this.rl = rl;
		this.receivename=receivename;
		tf=new JTextField(25);//Ymm
		tf.addActionListener(this);//Y
		b=new JButton("whisper");//Ymm
		b.addActionListener(this);//Y
		add(tf,"Center");//Ymm
		add(b,"East");//Ymm
	}
	// �̺�Ʈ ó��
	public void actionPerformed(ActionEvent ae){
		rl.sendMsg("whisper/"+rl.roomtitle+"/"+receivename+"/"+rl.id+"/"+tf.getText());//Y
		tf.setText("");//Y
	}
}