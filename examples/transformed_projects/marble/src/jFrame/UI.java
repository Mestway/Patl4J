package jFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;
import chat.RoomList;
import chat.Whisper;
import chat.WindowDestroyer;
import game.Game;
import game.User;

public class UI extends Shell implements SelectionListener {

	Composite pa1, pa2;
	Button b, b2;
	public List li;
	Menu pm;
	MenuItem mi;
	public static Label lblGoldkey;
	public Text ta;
	public RoomList rl;
	private Composite contentPane;
	private Text tf;
	Label lblDice, lblDice_1;
	Label[] horse = new Label[3];
	User[] user = new User[3];

	public int myIndex;
	public Text[] moneyTextField = new Text[3];
	
	protected void checkSubclass(){
		
	}
	/**
	 * Create the frame.
	 */
	public UI(RoomList rl, String roomtitle, String id) {

		super();
		this.setText("Welcome "+ id+"'s "+ roomtitle);
		this.rl = rl;

		setBounds(100, 100, 960, 670);
		addDisposeListener(new WindowDestroyer(this, rl));
		Shell container = getShell();
		container.setLayout(new FillLayout());
		contentPane = new Composite(container, SWT.BORDER);
		Image icon;
		contentPane.setLayout(new FillLayout());

		Composite panel = new Composite(container, SWT.BORDER);
		panel.setBounds(15, 15, 680, 600);
		panel.setLayout(null);

		
		horse[2] = new Label(panel, SWT.NULL);
		horse[2].setText("");
		horse[2].setImage(new Image(Display.getCurrent(), "image/horse_3.jpg"));
		horse[2].setLocation(610, 550);
		horse[2].setSize(50, 50);

		horse[1] = new Label(panel, SWT.NULL);
		horse[1].setText("");
		horse[1].setImage(new Image(Display.getCurrent(), "image/horse_2.jpg"));
		horse[1].setSize(50, 50);
		horse[1].setLocation(595, 550);


		horse[0] = new Label(panel, SWT.NULL);
		horse[0].setText("");
		horse[0].setSize(50, 50);
		horse[0].setLocation(580, 550);
		horse[0].setImage(new Image(Display.getCurrent(), "image/horse_1.jpg"));

		for(int i=0;i<3;i++)
			user[i] = new User();
		
		Composite panel_1 = new Composite(panel, SWT.BORDER);
		panel_1.setLayout(new FillLayout());
		panel_1.setBounds(0, 0, 100, 100);

		Label label_1 = new Label(panel_1, SWT.NULL);
		label_1.setImage(new Image(Display.getCurrent(), "image/17.png"));

		Composite panel_2 = new Composite(panel, SWT.BORDER);
		panel_2.setLayout(new FillLayout());
		panel_2.setBounds(100, 0, 80, 100);

		Label label_2 = new Label(panel_2, SWT.NULL);
		label_2.setImage(new Image(Display.getCurrent(), "/image/11.jpg"));

		Composite panel_3 = new Composite(panel, SWT.BORDER);
		panel_3.setLayout(new FillLayout());
		panel_3.setBounds(180, 0, 80, 100);

		Label label_3 = new Label(panel_3, SWT.NULL);
		label_3.setImage(new Image(Display.getCurrent(), "/image/412.png"));

		Composite panel_4 = new Composite(panel, SWT.BORDER);
		panel_4.setLayout(new FillLayout());
		panel_4.setBounds(260, 0, 80, 100);

		Label label_4 = new Label(panel_4, SWT.NULL);
		label_4.setImage(new Image(Display.getCurrent(), "/image/10.jpg"));

		Composite panel_5 = new Composite(panel, SWT.BORDER);
		panel_5.setLayout(new FillLayout());
		panel_5.setBounds(340, 0, 80, 100);

		Label label_5 = new Label(panel_5, SWT.NULL);
		label_5.setImage(new Image(Display.getCurrent(), "/image/goldkey.jpg"));

		Composite panel_6 = new Composite(panel, SWT.BORDER);
		panel_6.setLayout(new FillLayout());
		panel_6.setBounds(420, 0, 80, 100);

		Label label_6 = new Label(panel_6, SWT.NULL);
		label_6.setImage(new Image(Display.getCurrent(), "/image/9.jpg"));

		Composite panel_7 = new Composite(panel, SWT.BORDER);
		panel_7.setLayout(new FillLayout());
		panel_7.setBounds(500, 0, 80, 100);

		Label label_7 = new Label(panel_7, SWT.NULL);
		label_7.setImage(new Image(Display.getCurrent(), "/image/8.jpg"));

		Composite panel_8 = new Composite(panel, SWT.BORDER);
		panel_8.setLayout(new FillLayout());
		panel_8.setBounds(580, 0, 100, 100);

		Label label_8 = new Label(panel_8, SWT.NULL);
		label_8.setImage(new Image(Display.getCurrent(), "/image/16.png"));	
		
		Composite panel_9 = new Composite(panel, SWT.BORDER);
		panel_9.setLayout(new FillLayout());
		panel_9.setBounds(0, 100, 100, 80);

		Label label_9 = new Label(panel_9, SWT.NULL);
		label_9.setImage(new Image(Display.getCurrent(), "/image/7.jpg"));

		Composite panel_10 = new Composite(panel, SWT.BORDER);
		panel_10.setLayout(new FillLayout());
		panel_10.setBounds(580, 100, 100, 80);

		Label label_10 = new Label(panel_10, SWT.NULL);
		label_10.setImage(new Image(Display.getCurrent(), "image/13.jpg"));

		
		Composite panel_11 = new Composite(panel, SWT.BORDER);
		panel_11.setLayout(new FillLayout());
		panel_11.setBounds(0, 180, 100, 80);

		Label label_11 = new Label(panel_11, SWT.NULL);
		label_11.setImage(new Image(Display.getCurrent(), "/imgae/goldkeyY.jpg"));

		Composite panel_12 = new Composite(panel, SWT.BORDER);
		panel_12.setLayout(new FillLayout());
		panel_12.setBounds(580, 180, 100, 80);

		Label label_12 = new Label(panel_12, SWT.NULL);
		label_12.setImage(new Image(Display.getCurrent(), "/image/306.png"));

		Composite panel_13 = new Composite(panel, SWT.BORDER);
		panel_13.setLayout(new FillLayout());
		panel_13.setBounds(0, 260, 100, 80);

		Label label_13 = new Label(panel_13, SWT.NULL);
		label_13.setImage(new Image(Display.getCurrent(), "/image/6.jpg"));

		Composite panel_14 = new Composite(panel, SWT.BORDER);
		panel_14.setLayout(new FillLayout());
		panel_14.setBounds(580, 260, 100, 80);

		Label label_14 = new Label(panel_14, SWT.NULL);
		label_14.setImage(new Image(Display.getCurrent(), "/image/14.jpg"));

		Composite panel_15 = new Composite(panel, SWT.BORDER);
		panel_15.setLayout(new FillLayout());
		panel_15.setBounds(0, 340, 100, 80);

		Label label_15 = new Label(panel_15, SWT.NULL);
		label_15.setImage(new Image(Display.getCurrent(), "/image/study.png"));

		Composite panel_16 = new Composite(panel, SWT.BORDER);
		panel_16.setLayout(new FillLayout());
		panel_16.setBounds(580, 340, 100, 80);

		Label label_16 = new Label(panel_16, SWT.NULL);
		label_16.setImage(new Image(Display.getCurrent(), "/image/goldkeyB.jpg"));

		Composite panel_17 = new Composite(panel, SWT.BORDER);
		panel_17.setLayout(new FillLayout());
		panel_17.setBounds(0, 420, 100, 80);

		Label label_17 = new Label(panel_17, SWT.NULL);
		label_17.setImage(new Image(Display.getCurrent(), "/image/5.jpg"));

		Composite panel_18 = new Composite(panel, SWT.BORDER);
		panel_18.setLayout(new FillLayout());
		panel_18.setBounds(580, 420, 100, 80);

		Label label_18 = new Label(panel_18, SWT.NULL);
		label_18.setImage(new Image(Display.getCurrent(), "/image/12.jpg"));
		
		Composite panel_19 = new Composite(panel, SWT.BORDER);
		panel_19.setLayout(new FillLayout());
		panel_19.setBounds(0, 500, 100, 100);

		Label label_19 = new Label(panel_19, SWT.NULL);
		label_19.setImage(new Image(Display.getCurrent(), "/image/15.png"));

		Composite panel_20 = new Composite(panel, SWT.BORDER);
		panel_20.setLayout(new FillLayout());
		panel_20.setBounds(580, 500, 100, 100);

		Label label_20 = new Label(panel_20, SWT.NULL);
		label_20.setImage(new Image(Display.getCurrent(), "/image/start.jpg"));

		Composite panel_21 = new Composite(panel, SWT.BORDER);
		panel_21.setLayout(new FillLayout());
		panel_21.setBounds(100, 500, 80, 100);

		Label label_21 = new Label(panel_21, SWT.NULL);
		label_21.setImage(new Image(Display.getCurrent(), "/image/4.jpg"));

		Composite panel_22 = new Composite(panel, SWT.BORDER);
		panel_22.setLayout(new FillLayout());
		panel_22.setBounds(180, 500, 80, 100);

		Label label_22 = new Label(panel_22, SWT.NULL);
		label_22.setImage(new Image(Display.getCurrent(), "/iamge/goldkey.jpg"));
		
		Composite panel_23 = new Composite(panel, SWT.BORDER);
		panel_23.setLayout(new FillLayout());
		panel_23.setBounds(260, 500, 80, 100);

		Label label_23 = new Label(panel_23, SWT.NULL);
		label_23.setImage(new Image(Display.getCurrent(), "/image/3.jpg"));

		Composite panel_24 = new Composite(panel, SWT.BORDER);
		panel_24.setLayout(new FillLayout());
		panel_24.setBounds(340, 500, 80, 100);

		Label label_24 = new Label(panel_24, SWT.NULL);
		label_24.setImage(new Image(Display.getCurrent(), "/image/vosion.jpg"));

		Composite panel_25 = new Composite(panel, SWT.BORDER);
		panel_25.setLayout(new FillLayout());
		panel_25.setBounds(420, 500, 80, 100);

		Label label_25 = new Label(panel_25, SWT.NULL);
		label_25.setImage(new Image(Display.getCurrent(), "/image/2.jpg"));

		Composite panel_26 = new Composite(panel, SWT.BORDER);
		panel_26.setLayout(new FillLayout());
		panel_26.setBounds(500, 500, 80, 100);

		Label label_26 = new Label(panel_26, SWT.NULL);
		label_26.setImage(new Image(Display.getCurrent(), "/image/1.jpg"));

		Composite panel_29 = new Composite(panel, SWT.BORDER);
		panel_29.setLayout(new FillLayout());

		Composite panel_27 = new Composite(panel_29, SWT.BORDER);
		panel_27.setLayout(new BorderLayout());
		panel_27.setBounds(36, 232, 400, 181);


		//********************************chat GUI

		pa1 = new Composite(panel_27, SWT.NULL);
		pa1.setLayoutData(BorderData.CENTER);
		pa1.setLayout(new FillLayout());

		ta = new Text(pa1, SWT.MULTI | SWT.V_SCROLL);
		ta.setBounds(0, 0, 249, 148);
		ta.setTextLimit(17);

		Group group = new Group(pa1, SWT.NULL);
		group.setBounds(0, 0, 249, 148);
		group.setLayout(new FillLayout());
		group.setText("user List");
		
		li = new List(group, SWT.MULTI | SWT.V_SCROLL);
		
		pm = new Menu(getShell(), SWT.POP_UP);

		li.setMenu(pm);

		mi = new MenuItem(pm, SWT.PUSH);
		mi.setText("whisper");

		//*******Enter the message
		pa2 = new Composite(panel_27, SWT.NULL);
		pa2.setLayoutData(BorderData.SOUTH);
		pa2.setLayout(new FillLayout());

		tf = new Text(pa2, SWT.NULL);
		tf.setTextLimit(23);

		b = new Button(pa2, SWT.PUSH);
		b.setText("send");
		b.setAlignment(SWT.RIGHT);
		b.addSelectionListener(this);

		lblGoldkey = new Label(panel_29, SWT.NULL);
		lblGoldkey.setText("Goldkey");
		lblGoldkey.setImage(new Image(Display.getCurrent(), "/image/gold.jpg"));
		lblGoldkey.setBounds(36, 27, 140, 190);

		Composite panel_28 = new Composite(panel_29, SWT.NULL);
		panel_28.setLayout(new FillLayout());
		panel_28.setBounds(205, 111, 230, 106);

		b2 = new Button(panel_28, SWT.PUSH);
		b2.setText("Dice");
		b2.setBounds(161, 73, 57, 23);

		lblDice = new Label(panel_28, SWT.NULL);
		lblDice.setText("Dice1");
		lblDice.setBounds(12, 10, 70, 70);

		lblDice_1 = new Label(panel_28, SWT.NULL);
		lblDice_1.setText("Dice2");
		lblDice_1.setImage(new Image(Display.getCurrent(), "/image/dice5.jpg"));
		lblDice_1.setBounds(90, 10, 70, 70);
		
		Composite user_1P = new Composite(contentPane, SWT.NULL);
		user_1P.setLayout(new FillLayout());
		user_1P.setBounds(707, 15, 225, 180);
		
		moneyTextField[0] = new Text(user_1P, SWT.NULL);
		moneyTextField[0].setText(user[0].money+"��");
		moneyTextField[0].setBounds(72, 39, 116, 21);
		moneyTextField[0].setTextLimit(10);
		
		Label user1 = new Label(user_1P, SWT.NULL);
		user1.setText("");
		user1.setImage(new Image(Display.getCurrent(), "/image/user1.jpg"));
		user1.setBounds(3, 5, 220, 87);
		
		Composite user_2P = new Composite(contentPane, SWT.NULL);
		user_2P.setLayout(new FillLayout());
		user_2P.setBounds(707, 209, 225, 180);
		
		moneyTextField[1] = new Text(user_2P, SWT.NULL);
		moneyTextField[1].setTextLimit(10);
		moneyTextField[1].setText(user[0].money+"��");
		moneyTextField[1].setBounds(86, 40, 116, 21);
		
		Label label = new Label(user_2P, SWT.NULL);
		label.setText("");
		label.setImage(new Image(Display.getCurrent(), "/image/user2.jpg"));
		label.setBounds(3, 5, 220, 87);
		
		Composite user_3P = new Composite(contentPane, SWT.NULL);
		user_3P.setLayout(new FillLayout());
		user_3P.setBounds(707, 404, 225, 180);
		
		moneyTextField[2] = new Text(user_3P, SWT.NULL);
		moneyTextField[2].setTextLimit(10);
		moneyTextField[2].setText(user[0].money+"��");
		moneyTextField[2].setBounds(97, 40, 116, 21);
		
		Label user3 = new Label(user_3P, SWT.NULL);
		user3.setImage(new Image(Display.getCurrent(), "/image/user3.jpg"));
		user3.setBounds(3, 5, 220, 87);
		
		b2.addSelectionListener(this);
		
		Game.init();
	}

	public void Move(int num, int num2, int index){
		lblDice.setImage(new Image(Display.getCurrent(), "/image/dice"+num+".jpg"));
		lblDice_1.setImage(new Image(Display.getCurrent(), "/image/dice"+num2+".jpg"));

		for(int i=0; i<(num+num2); i++){
			Point p = Game.moving(user[index].position, horse[index].getLocation());
			horse[index].setLocation(p.x, p.y);
			user[index].position = user[index].position + 1;
			user[index].position = Game.positionFormat(user[index].position);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {}
		}
		System.out.println("index:"+index);
		System.out.println("rl.myIndex:"+myIndex);
		System.out.println("position:"+ user[index].position);
		System.out.println();
		if(index == myIndex)
			Game.mission(user, index, horse, moneyTextField, rl);	
	}
	public void Move(int num, int index){
		for(int i=0; i<num; i++){
			Point p = Game.moving(user[index].position, horse[index].getLocation());
			horse[index].setLocation(p.x, p.y);
			user[index].position = user[index].position + 1;
			user[index].position = Game.positionFormat(user[index].position);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent ae) {
		if(ae.getSource()==tf || ae.getSource() ==b){ 
			rl.sendMsg("say/"+rl.roomtitle+"/"+rl.id+"/"+tf.getText());
			tf.setText("");
		}		
		else if(ae.getSource()==mi){ 
			Whisper w = new Whisper(rl, (String)li.getSelection()[0], Display.getCurrent());
			w.pack();
			w.setVisible(true);
		}
		else if(ae.getSource() == b2){ 
			int num = Game.dice();
			int num2 = Game.dice();	
			rl.sendMsg("dice/"+rl.roomtitle+"/"+rl.id+"/"+num+"/"+num2);

		}
		
	}
}
