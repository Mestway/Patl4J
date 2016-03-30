package jFrame;

import game.Game;
import game.User;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import chat.RoomList;
import chat.Whisper;
import chat.WindowDestroyer;

public class UI extends JFrame implements ActionListener {

	JPanel pa1, pa2;
	JButton b, b2;
	public JList li;
	JPopupMenu pm;
	JMenuItem mi;
	public static JLabel lblGoldkey;
	public JTextArea ta;
	public RoomList rl;
	private JPanel contentPane;
	private JTextField tf;
	JLabel lblDice,lblDice_1;
	JLabel[] horse = new JLabel[3];
	User[] user = new User[3];

	public int myIndex;
	public JTextField[] moneyTextField = new JTextField[3];
	/**
	 * Create the frame.
	 */
	public UI(RoomList rl, String roomtitle, String id) {

		super("Welcome "+ id+"'s "+ roomtitle);
		this.rl = rl;

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 670);//Y
		addWindowListener(new WindowDestroyer(this,rl));//Y
		contentPane = new JPanel();//Ymm
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//Ymm
		setContentPane(contentPane);//Ymm
		ImageIcon icon;
		contentPane.setLayout(null);//Y

		JPanel panel = new JPanel();//Ymm
		panel.setBounds(15, 15, 680, 600);//Y
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		contentPane.add(panel);//Ymm
		panel.setLayout(null);//Y
		
		horse[2] = new JLabel("");//Ymm
		horse[2].setIcon(new ImageIcon(UI.class.getResource("/image/horse_3.jpg")));//Y
		horse[2].setLocation(610, 550);//Y
		horse[2].setSize(50, 50);//Y
		panel.add(horse[2]);//Ymm

		horse[1] = new JLabel("");//Ymm
		horse[1].setIcon(new ImageIcon(UI.class.getResource("/image/horse_2.jpg")));//Y
		horse[1].setSize(50, 50);//Y
		horse[1].setLocation(595, 550);//Y
		panel.add(horse[1]);//Ymm


		horse[0] = new JLabel("");//Ymm
		horse[0].setSize(50, 50);//Y
		horse[0].setLocation(580, 550);//Y
		horse[0].setIcon(new ImageIcon(UI.class.getResource("/image/horse_1.jpg")));//Y
		panel.add(horse[0]);//Ymm

		for(int i=0;i<3;i++)
			user[i] = new User();
		
		JPanel panel_1 = new JPanel();//Ymm
		FlowLayout flowLayout_23 = (FlowLayout) panel_1.getLayout();//Y
		flowLayout_23.setVgap(0);//Y
		flowLayout_23.setHgap(0);//Y
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_1.setBounds(0, 0, 100, 100);//Y
		panel.add(panel_1);//Ymm

		JLabel label_1 = new JLabel();//Ymm
		label_1.setIcon(new ImageIcon(UI.class.getResource("/image/17.png")));//Y
		panel_1.add(label_1);//Ymm

		JPanel panel_2 = new JPanel();//Ymm
		FlowLayout flowLayout_11 = (FlowLayout) panel_2.getLayout();//Y
		flowLayout_11.setHgap(0);//Y
		flowLayout_11.setVgap(0);//Y
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_2.setBounds(100, 0, 80, 100);//Y
		panel.add(panel_2);//Ymm

		JLabel label_2 = new JLabel();//Ymm
		label_2.setIcon(new ImageIcon(UI.class.getResource("/image/11.jpg")));//Y
		panel_2.add(label_2);//Ymm

		JPanel panel_3 = new JPanel();//Ymm
		FlowLayout flowLayout_12 = (FlowLayout) panel_3.getLayout();//Y
		flowLayout_12.setHgap(0);//Y
		flowLayout_12.setVgap(0);//Y
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_3.setBounds(180, 0, 80, 100);//Y
		panel.add(panel_3);//Ymm

		JLabel label_3 = new JLabel();//Ymm
		label_3.setIcon(new ImageIcon(UI.class.getResource("/image/412.png")));//Y
		panel_3.add(label_3);//Ymm

		JPanel panel_4 = new JPanel();//Ymm
		FlowLayout flowLayout_13 = (FlowLayout) panel_4.getLayout();//Y
		flowLayout_13.setVgap(0);//Y
		flowLayout_13.setHgap(0);//Y
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_4.setBounds(260, 0, 80, 100);//Y
		panel.add(panel_4);//Ymm

		JLabel label_4 = new JLabel();//Ymm
		label_4.setIcon(new ImageIcon(UI.class.getResource("/image/10.jpg")));//Y
		panel_4.add(label_4);//Ymm

		JPanel panel_5 = new JPanel();//Ymm
		FlowLayout flowLayout_14 = (FlowLayout) panel_5.getLayout();//Y
		flowLayout_14.setVgap(0);//Y
		flowLayout_14.setHgap(0);//Y
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_5.setBounds(340, 0, 80, 100);//Y
		panel.add(panel_5);//Ymm

		JLabel label_5 = new JLabel();//Ymm
		label_5.setIcon(new ImageIcon(UI.class.getResource("/image/goldkey.jpg")));//Y
		panel_5.add(label_5);//Ymm

		JPanel panel_6 = new JPanel();//Ymm
		FlowLayout flowLayout_15 = (FlowLayout) panel_6.getLayout();//Y
		flowLayout_15.setVgap(0);//Y
		flowLayout_15.setHgap(0);//Y
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_6.setBounds(420, 0, 80, 100);//Y
		panel.add(panel_6);//Ymm

		JLabel label_6 = new JLabel();//Ymm
		label_6.setIcon(new ImageIcon(UI.class.getResource("/image/9.jpg")));//Y
		panel_6.add(label_6);//Ymm

		JPanel panel_7 = new JPanel();//Ymm
		FlowLayout flowLayout_16 = (FlowLayout) panel_7.getLayout();//Y
		flowLayout_16.setVgap(0);//Y
		flowLayout_16.setHgap(0);//Y
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_7.setBounds(500, 0, 80, 100);//Y
		panel.add(panel_7);//Ymm

		JLabel label_7 = new JLabel();//Ymm
		label_7.setIcon(new ImageIcon(UI.class.getResource("/image/8.jpg")));//Y
		panel_7.add(label_7);//Ymm

		JPanel panel_8 = new JPanel();//Ymm
		FlowLayout flowLayout_24 = (FlowLayout) panel_8.getLayout();//Y
		flowLayout_24.setVgap(0);//Y
		flowLayout_24.setHgap(0);//Y
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_8.setBounds(580, 0, 100, 100);//Y
		panel.add(panel_8);//Ymm

		JLabel label_8 = new JLabel();//Ymm
		label_8.setIcon(new ImageIcon(UI.class.getResource("/image/16.png")));//Y
		panel_8.add(label_8);//Ymm

		JPanel panel_9 = new JPanel();//Ymm
		FlowLayout flowLayout_10 = (FlowLayout) panel_9.getLayout();//Y
		flowLayout_10.setVgap(0);//Y
		flowLayout_10.setHgap(0);//Y
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_9.setBounds(0, 100, 100, 80);//Y
		panel.add(panel_9);//Ymm

		JLabel label_9 = new JLabel();//Ymm
		label_9.setIcon(new ImageIcon(UI.class.getResource("/image/7.jpg")));//Y
		panel_9.add(label_9);//Ymm

		JPanel panel_10 = new JPanel();//Ymm
		FlowLayout flowLayout_17 = (FlowLayout) panel_10.getLayout();//Y
		flowLayout_17.setHgap(0);//Y
		flowLayout_17.setVgap(0);//Y
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_10.setBounds(580, 100, 100, 80);//Y
		panel.add(panel_10);//Ymm

		JLabel label_10 = new JLabel();//Ymm
		label_10.setIcon(new ImageIcon(UI.class.getResource("/image/13.jpg")));//Y
		panel_10.add(label_10);//Ymm

		JPanel panel_11 = new JPanel();//Ymm
		FlowLayout flowLayout_9 = (FlowLayout) panel_11.getLayout();//Y
		flowLayout_9.setHgap(0);//Y
		flowLayout_9.setVgap(0);//Y
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_11.setBounds(0, 180, 100, 80);//Y
		panel.add(panel_11);//Ymm

		JLabel label_11 = new JLabel();//Ymm
		label_11.setIcon(new ImageIcon(UI.class.getResource("/image/goldkeyY.jpg")));//Y
		panel_11.add(label_11);//Ymm

		JPanel panel_12 = new JPanel();//Ymm
		FlowLayout flowLayout_18 = (FlowLayout) panel_12.getLayout();//Y
		flowLayout_18.setVgap(0);//Y
		flowLayout_18.setHgap(0);//Y
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_12.setBounds(580, 180, 100, 80);//Y
		panel.add(panel_12);//Ymm

		JLabel label_12 = new JLabel();//Ymm
		label_12.setIcon(new ImageIcon(UI.class.getResource("/image/306.png")));//Y
		panel_12.add(label_12);//Ymm

		JPanel panel_13 = new JPanel();//Ymm
		FlowLayout flowLayout_8 = (FlowLayout) panel_13.getLayout();//Y
		flowLayout_8.setVgap(0);//Y
		flowLayout_8.setHgap(0);//Y
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_13.setBounds(0, 260, 100, 80);//Y
		panel.add(panel_13);//Ymm

		JLabel label_13 = new JLabel();//Ymm
		label_13.setIcon(new ImageIcon(UI.class.getResource("/image/6.jpg")));//Y
		panel_13.add(label_13);//Ymm

		JPanel panel_14 = new JPanel();//Ymm
		FlowLayout flowLayout_19 = (FlowLayout) panel_14.getLayout();//Y
		flowLayout_19.setHgap(0);//Y
		flowLayout_19.setVgap(0);//Y
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_14.setBounds(580, 260, 100, 80);//Y
		panel.add(panel_14);//Ymm

		JLabel label_14 = new JLabel();//Ymm
		label_14.setIcon(new ImageIcon(UI.class.getResource("/image/14.jpg")));//Y
		panel_14.add(label_14);//Ymm

		JPanel panel_15 = new JPanel();//Ymm
		FlowLayout flowLayout_6 = (FlowLayout) panel_15.getLayout();//Y
		flowLayout_6.setHgap(0);//Y
		flowLayout_6.setVgap(0);//Y
		panel_15.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_15.setBounds(0, 340, 100, 80);//Y
		panel.add(panel_15);//Ymm

		JLabel label_15 = new JLabel();//Ymm
		label_15.setIcon(new ImageIcon(UI.class.getResource("/image/study.png")));//Y
		panel_15.add(label_15);//Ymm

		JPanel panel_16 = new JPanel();//Ymm
		FlowLayout flowLayout_20 = (FlowLayout) panel_16.getLayout();//Y
		flowLayout_20.setHgap(0);//Y
		flowLayout_20.setVgap(0);//Y
		panel_16.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_16.setBounds(580, 340, 100, 80);//Y
		panel.add(panel_16);//Ymm

		JLabel label_16 = new JLabel();//Ymm
		label_16.setIcon(new ImageIcon(UI.class.getResource("/image/goldkeyB.jpg")));//Y
		panel_16.add(label_16);//Ymm

		JPanel panel_17 = new JPanel();//Ymm
		FlowLayout flowLayout_5 = (FlowLayout) panel_17.getLayout();//Y
		flowLayout_5.setHgap(0);//Y
		flowLayout_5.setVgap(0);//Y
		panel_17.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_17.setBounds(0, 420, 100, 80);//Y
		panel.add(panel_17);//Ymm

		JLabel label_17 = new JLabel();//Ymm
		label_17.setIcon(new ImageIcon(UI.class.getResource("/image/5.jpg")));//Y
		panel_17.add(label_17);//Ymm

		JPanel panel_18 = new JPanel();//Ymm
		FlowLayout flowLayout_21 = (FlowLayout) panel_18.getLayout();//Y
		flowLayout_21.setHgap(0);//Y
		flowLayout_21.setVgap(0);//Y
		panel_18.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_18.setBounds(580, 420, 100, 80);//Y
		panel.add(panel_18);//Ymm

		JLabel label_18 = new JLabel();//Ymm
		label_18.setIcon(new ImageIcon(UI.class.getResource("/image/12.jpg")));//Y
		panel_18.add(label_18);//Ymm

		JPanel panel_19 = new JPanel();//Ymm
		FlowLayout flowLayout_25 = (FlowLayout) panel_19.getLayout();//Y
		flowLayout_25.setVgap(0);//Y
		flowLayout_25.setHgap(0);//Y
		panel_19.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_19.setBounds(0, 500, 100, 100);//Y
		panel.add(panel_19);//Ymm

		JLabel label_19 = new JLabel();//Ymm
		label_19.setIcon(new ImageIcon(UI.class.getResource("/image/15.png")));//Y
		panel_19.add(label_19);//Ymm

		JPanel panel_20 = new JPanel();//Ymm
		FlowLayout flowLayout_22 = (FlowLayout) panel_20.getLayout();//Y
		flowLayout_22.setVgap(0);//Y
		flowLayout_22.setHgap(0);//Y
		panel_20.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_20.setBounds(580, 500, 100, 100);//Y
		panel.add(panel_20);//Ymm


		JLabel label_20 = new JLabel();//Ymm
		label_20.setIcon(new ImageIcon(UI.class.getResource("/image/start.jpg")));//Y
		panel_20.add(label_20);//Ymm

		JPanel panel_21 = new JPanel();//Ymm
		FlowLayout flowLayout_4 = (FlowLayout) panel_21.getLayout();//Y
		flowLayout_4.setVgap(0);//Y
		flowLayout_4.setHgap(0);//Y
		panel_21.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_21.setBounds(100, 500, 80, 100);//Y
		panel.add(panel_21);//Ymm

		JLabel label_21 = new JLabel();//Ymm
		label_21.setIcon(new ImageIcon(UI.class.getResource("/image/4.jpg")));//Y
		panel_21.add(label_21);//Ymm

		JPanel panel_22 = new JPanel();//Ymm
		FlowLayout flowLayout_7 = (FlowLayout) panel_22.getLayout();//Y
		flowLayout_7.setVgap(0);//Y
		flowLayout_7.setHgap(0);//Y
		panel_22.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		panel_22.setBounds(180, 500, 80, 100);//Y
		panel.add(panel_22);//Ymm

		JLabel label_22 = new JLabel();//Ymm
		label_22.setIcon(new ImageIcon(UI.class.getResource("/image/goldkey.jpg")));//Y
		panel_22.add(label_22);//Ymm

		JPanel panel_23 = new JPanel();//Ymm
		panel_23.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		FlowLayout flowLayout_3 = (FlowLayout) panel_23.getLayout();//Y
		flowLayout_3.setHgap(0);//Y
		flowLayout_3.setVgap(0);//Y
		panel_23.setBounds(260, 500, 80, 100);//Y
		panel.add(panel_23);//Ymm

		JLabel label_23 = new JLabel();//Ymm
		label_23.setIcon(new ImageIcon(UI.class.getResource("/image/3.jpg")));//Y
		panel_23.add(label_23);//Ymm

		JPanel panel_24 = new JPanel();//Ymm
		panel_24.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		FlowLayout flowLayout_2 = (FlowLayout) panel_24.getLayout();//Y
		flowLayout_2.setVgap(0);//Y
		flowLayout_2.setHgap(0);//Y
		panel_24.setBounds(340, 500, 80, 100);//Y
		panel.add(panel_24);//Ymm

		JLabel label_24 = new JLabel();//Ymm
		label_24.setIcon(new ImageIcon(UI.class.getResource("/image/vision.jpg")));//Y
		panel_24.add(label_24);//Ymm

		JPanel panel_25 = new JPanel();//Ymm
		panel_25.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		FlowLayout flowLayout = (FlowLayout) panel_25.getLayout();//Y
		flowLayout.setVgap(0);//Y
		flowLayout.setHgap(0);//Y
		panel_25.setBounds(420, 500, 80, 100);//Y
		panel.add(panel_25);//Ymm

		JLabel label_25 = new JLabel();//Ymm
		panel_25.add(label_25);//Ymm
		label_25.setIcon(new ImageIcon(UI.class.getResource("/image/2.jpg")));//Y
		panel_25.add(label_25);//Ymm

		JPanel panel_26 = new JPanel();//Ymm
		panel_26.setBorder(new LineBorder(new Color(0, 0, 0)));//Ymm
		FlowLayout flowLayout_1 = (FlowLayout) panel_26.getLayout();//Y
		flowLayout_1.setVgap(0);//Y
		flowLayout_1.setHgap(1);//Y
		panel_26.setBounds(500, 500, 80, 100);//Y
		panel.add(panel_26);//Ymm

		JLabel label_26 = new JLabel();//Ymm
		panel_26.add(label_26);//Y
		label_26.setIcon(new ImageIcon(UI.class.getResource("/image/1.jpg")));//Y
		panel_26.add(label_26);//Ymm

		JPanel panel_29 = new JPanel();//Ymm
		panel_29.setBackground(Color.PINK);//Y
		panel_29.setBounds(100, 77, 480, 423);//Y
		panel.add(panel_29);//Ymm
		panel_29.setLayout(null);//Y

		JPanel panel_27 = new JPanel();//Ymm
		panel_27.setBounds(36, 232, 400, 181);//Y
		panel_29.add(panel_27);//Ymm
		panel_27.setLayout(new BorderLayout(0, 0));//Y



		//********************************chat GUI

		pa1 = new JPanel();//Ymm
		panel_27.add(pa1, BorderLayout.CENTER);//Ym
		pa1.setLayout(null);//Y

		JScrollPane sp1 = new JScrollPane();//Ymm
		sp1.setBounds(0, 0, 249, 148);//Y
		pa1.add(sp1);//Ymm

		ta = new JTextArea();//Ymm
		ta.setColumns(17);//Y
		sp1.setViewportView(ta);//Ymm

		JScrollPane sp2 = new JScrollPane();//Ymm
		sp2.setBounds(248, 0, 152, 148);//Y
		pa1.add(sp2);//Ymm

		li = new JList();//Ym
		sp2.setColumnHeaderView(li);//Ym

		pm = new JPopupMenu();//Ymm
		
		li.setBorder(new TitledBorder("user List"));//Y
		li.add(pm);//Ymm
		li.addMouseListener(   // �ӼӸ� �޴�//Y
				new MouseAdapter(){//Ym
					public void mouseClicked(MouseEvent me){//Ym
						if(me.getButton()==3){
							pm.show(li, me.getX(), me.getY());//N
						}
					}
				});


		mi = new JMenuItem("whisper");//Ym
		pm.add(mi);//Ym

		//*******Enter the message
		pa2 = new JPanel();//Ym
		panel_27.add(pa2, BorderLayout.SOUTH);//Ym

		tf = new JTextField();//Ym
		pa2.add(tf);//Ym
		tf.setColumns(23);//Y

		b = new JButton("send");//Ymm
		b.setHorizontalAlignment(SwingConstants.RIGHT);//Y
		b.addActionListener(this);//Y
		pa2.add(b);//Ymm

		lblGoldkey = new JLabel("Goldkey");//Ymm
		lblGoldkey.setIcon(new ImageIcon(UI.class.getResource("/image/gold.jpg")));//Y
		lblGoldkey.setBounds(36, 27, 140, 190);//Y
		panel_29.add(lblGoldkey);//Ymm

		JPanel panel_28 = new JPanel();//Ymm
		panel_28.setBounds(205, 111, 230, 106);//Y
		panel_29.add(panel_28);//Ymm
		panel_28.setLayout(null);//Y

		b2 = new JButton("Dice");//Ymm
		b2.setBounds(161, 73, 57, 23);//Y
		panel_28.add(b2);//Ymm

		lblDice = new JLabel("Dice1");//Ymm
		lblDice.setIcon(new ImageIcon(UI.class.getResource("/image/dice1.jpg")));//Y
		lblDice.setBounds(12, 10, 70, 70);//Y
		panel_28.add(lblDice);//Ymm

		lblDice_1 = new JLabel("Dice2");//Ymm
		lblDice_1.setIcon(new ImageIcon(UI.class.getResource("/image/dice5.jpg")));//Y
		lblDice_1.setBounds(90, 10, 70, 70);//Y
		panel_28.add(lblDice_1);//Ymm
		
		JPanel user_1P = new JPanel();//Ymm
		user_1P.setBounds(707, 15, 225, 180);//Y
		contentPane.add(user_1P);//Ymm
		user_1P.setLayout(null);//Y
		
		moneyTextField[0] = new JTextField();//Ymm
		moneyTextField[0].setBounds(72, 39, 116, 21);//Y
		user_1P.add(moneyTextField[0]);//Ymm
		moneyTextField[0].setColumns(10);//Y
		moneyTextField[0].setText(user[0].money+"��");//Y
		
		JLabel user1 = new JLabel("");//Ymm
		user1.setBounds(3, 5, 220, 87);//Y
		user1.setIcon(new ImageIcon(UI.class.getResource("/image/user1.jpg")));//Y
		user_1P.add(user1);//Ymm
		
		JPanel user_2P = new JPanel();//Ymm
		user_2P.setBounds(707, 209, 225, 180);//Y
		contentPane.add(user_2P);//Ymm
		user_2P.setLayout(null);//Y
		
		moneyTextField[1] = new JTextField();//Ymm
		moneyTextField[1].setBounds(86, 40, 116, 21);//Y
		moneyTextField[1].setColumns(10);//Y
		user_2P.add(moneyTextField[1]);//Ymm
		moneyTextField[1].setText(user[0].money+"��");//Y
		
		JLabel label = new JLabel("");//Ymm
		label.setBounds(3, 5, 220, 87);//Y
		label.setIcon(new ImageIcon(UI.class.getResource("/image/user2.jpg")));//Y
		user_2P.add(label);//Ymm
		
		JPanel user_3P = new JPanel();//Ymm
		user_3P.setBounds(707, 404, 225, 180);//Y
		contentPane.add(user_3P);//Ymm
		user_3P.setLayout(null);//Y
		
		moneyTextField[2] = new JTextField();//Ymm
		moneyTextField[2].setBounds(97, 40, 116, 21);//Y
		user_3P.add(moneyTextField[2]);//Ymm
		moneyTextField[2].setColumns(10);//Y
		moneyTextField[2].setText(user[0].money+"��");//Y
		
		JLabel user3 = new JLabel("");//Ymm
		user3.setBounds(3, 5, 220, 87);//Y
		user3.setIcon(new ImageIcon(UI.class.getResource("/image/user3.jpg")));//Y
		user_3P.add(user3);//Ymm
		
		b2.addActionListener(this);//Y
		
		Game.init();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {//Ym
			public void mousePressed(MouseEvent e) {//Ym
				if (e.isPopupTrigger()) {//N
					showMenu(e);	//N
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {//N
					showMenu(e);//N
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());//N
			}
		});
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==tf || ae.getSource() ==b){     // ��ȭ�ϱ�
			rl.sendMsg("say/"+rl.roomtitle+"/"+rl.id+"/"+tf.getText());//Y
			tf.setText("");//Y
		}		
		else if(ae.getSource()==mi){   // �ӼӸ�
			Whisper w = new Whisper(rl, (String)li.getSelectedValue());//Y
			w.pack();
			w.setVisible(true);//Y
		}
		else if(ae.getSource() == b2){ // �ֻ�����ư
			int num = Game.dice();
			int num2 = Game.dice();	
			rl.sendMsg("dice/"+rl.roomtitle+"/"+rl.id+"/"+num+"/"+num2);

		}
	}
	public void Move(int num, int num2, int index){
		lblDice.setIcon(new ImageIcon(UI.class.getResource("/image/dice"+num+".jpg")));//Y
		lblDice_1.setIcon(new ImageIcon(UI.class.getResource("/image/dice"+num2+".jpg")));//Y

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
}
