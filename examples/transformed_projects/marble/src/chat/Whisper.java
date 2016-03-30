package chat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;

//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

public class Whisper extends Shell implements SelectionListener{
	Text tf;
	Button b;
	RoomList rl;
	String receivename;
	public Whisper(RoomList rl, String receivename, Display display) {
		super(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.setLayout(new BorderLayout());
		this.rl = rl;
		this.receivename=receivename;
		
		tf=new Text(this, SWT.NULL);
		tf.setTextLimit(25);
		tf.setLayoutData(BorderData.CENTER);
		tf.addSelectionListener(this);
		b = new Button(this, SWT.PUSH);
		b.setText("whisper");
		b.setLayoutData(BorderData.EAST);
		b.addSelectionListener(this);
	}

	protected void checkSubclass() {
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		rl.sendMsg("whisper/"+rl.roomtitle+"/"+receivename+"/"+rl.id+"/"+tf.getText());
		tf.setText("");
	}
}