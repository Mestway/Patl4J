package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;

public class Room extends Shell implements SelectionListener{
	Shell f;
	Composite pa1, pa2;
	Text ta;
	List li;
	Text tf;
	Button b;
	Menu pm;
	MenuItem mi;
	RoomList rl;
	BufferedReader br;
	BufferedWriter bw;
	String roomtitle;
	
	
	protected void checkSubclass() {
	}
	
	public Room(RoomList rl, String roomtitle, Display display){
		super(display);
		this.rl.setText(""+roomtitle+"Room("+rl.id+")");
		this.rl = rl;
		this.roomtitle = roomtitle;
		Shell container = this.getShell();
		container.setLayout(new BorderLayout());
		pa1 = new Composite(container, SWT.V_SCROLL);
		pa1.setLayoutData(BorderData.CENTER);
		pa2 = new Composite(container, SWT.V_SCROLL);
		pa2.setLayoutData(BorderData.NORTH);
		pa1.setLayout(new BorderLayout());
		pa2.setLayout(new BorderLayout());
		ta = new Text(pa1, SWT.MULTI);
		ta.setSize(15, 30);
		ta.setLayoutData(BorderData.CENTER);
		Group group = new Group(pa2, SWT.NULL);
		group.setText("user List");
		group.setLayoutData(BorderData.EAST);
		group.setLayout(new FillLayout());
		li = new List(group, SWT.NULL);
		pm = new Menu(this.getShell(), SWT.POP_UP);
		mi = new MenuItem(pm, SWT.PUSH);
		mi.setText("whisper");
		mi.addSelectionListener(this);
		li.setMenu(pm);
		
		tf = new Text(pa2, SWT.NULL);
		tf.addSelectionListener(this);
		tf.setLayoutData(BorderData.CENTER);		
		b= new Button(pa2, SWT.PUSH);
		b.addSelectionListener(this);
		b.setText("exit room");
		
		this.addDisposeListener(new DisposeListener(){

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				Room.this.rl.sendMsg("exitroom/"+Room.this.rl.roomtitle+"/"+Room.this.rl.id);
				setVisible(false);
				Room.this.rl.setVisible(true);
			}
			
		});
		pack();
		setVisible(true);
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent ae) {
		if(ae.getSource()==tf){  
			rl.sendMsg("say/"+rl.roomtitle+"/"+rl.id+"/"+tf.getText());
			tf.setText("");   
		}else if(ae.getSource()==b){  
			rl.sendMsg("exitroom/"+rl.roomtitle+"/"+rl.id);
			setVisible(false);
			rl.setVisible(true);
		}else if(ae.getSource()==mi){ 
			Whisper w = new Whisper(rl, (String)li.getSelection()[0], Display.getCurrent());
			w.pack();
			w.setVisible(true);
		}
	}
}