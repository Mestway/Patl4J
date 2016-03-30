package com.fray.evo.ui.swingx;

import com.fray.evo.*;
import com.fray.evo.action.EcAction;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jgap.InvalidConfigurationException;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import static com.fray.evo.ui.swingx.EcSwingXMain.userSettings;

//TODO: Refactor this monster. - Lomilar
public class EcSwingX extends JXPanel implements EcReportable
{
	private static final long serialVersionUID = 4155107115709884263L;
	private static final Logger logger = Logger.getLogger(EcSwingX.class.getName());
	private JTextArea			outputText;
	private JLabel				status1;
	private JLabel				status2;
	private JLabel				status3;
	protected long				timeStarted;
	protected long				lastUpdate;
	private String				simpleBuildOrder;
	private String				detailedBuildOrder;
	private String				yabotBuildOrder;
	private boolean			isDetailedBuildOrder;
	private boolean			isYabotBuildOrder;
	private boolean			isSimpleBuildOrder;
	int							gridy			= 0;
	private JXStatusBar			statusbar;
	List<JComponent>			inputControls	= new ArrayList<JComponent>();

	private final EvolutionChamber ec;
	List<EcState> destination = new ArrayList<EcState>();
	
	private JPanel historyPanel;
	private List<JPanel> waypointPanels = new ArrayList<JPanel>();
	private JPanel newWaypointPanel;
	private JPanel statsPanel;
	private JPanel settingsPanel;
	
	private boolean running = false;
	
	private JButton				goButton;
	private JButton				stopButton;
	private LocaleComboBox		localeComboBox;
	private JButton				clipboardButton;
	private JButton				switchSimpleButton;
	private JButton				switchDetailedButton;
	private JButton				switchYabotButton;
	private JTextArea			statsText;
	private JTabbedPane			tabPane;
	private Component			lastSelectedTab;
	private JList				historyList;

	private JFrame				frame;

	/**
	 * Constructor.
	 * @param frame the window that holds this panel.
	 */
	public EcSwingX(JFrame frame)
	{
		File seedsDir = new File(EcSwingXMain.userConfigDir, EvolutionChamber.VERSION);
		seedsDir.mkdirs();
		ec = new EvolutionChamber(new File(seedsDir, "seeds.evo"), new File(seedsDir, "seeds2.evo"));
		ec.setReportInterface(this);
		
		this.frame = frame;
		initializeWaypoints();

		setLayout(new BorderLayout());    //Y

		JSplitPane outside = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//Ymm
		{ // Left
			JPanel leftbottom = new JPanel(new GridBagLayout());//Ymm
			JScrollPane stuffPanel = new JScrollPane(leftbottom);//Ymm
			{
                addControlParts(leftbottom);
                tabPane = new JTabbedPane(JTabbedPane.LEFT);//Ymm
                {
                    //history tab
                    historyPanel = new JPanel(new BorderLayout());//W//Ymm
                    addStart(historyPanel);

                    //waypoint tabs
                    for (EcState dest : destination)
                        addWaypointPanel(dest, false);

                    //new waypoint tab
                    newWaypointPanel = new JPanel(); //just make it an empty panel//W//Ymm
                    tabPane.addChangeListener(new ChangeListener(){//Ym
                        @Override
                        public void stateChanged(ChangeEvent event) {//Ym
                            if (running && tabPane.getSelectedComponent() == newWaypointPanel){//Y
                                tabPane.setSelectedComponent(lastSelectedTab);//Y
                            } else {
                                lastSelectedTab = tabPane.getSelectedComponent();//Y
                            }
                        }
                    });
                    tabPane.addMouseListener(new MouseListener(){//Ym
                        public void mouseClicked(MouseEvent event) {//Ym
                            if (running)
                                return;
                            if (tabPane.getSelectedComponent() == newWaypointPanel){//Y
                                //create a new waypoint
                                try{
                                    //create waypoint object
                                    EcState newWaypoint = (EcState) ec.getInternalDestination().clone();
                                    if (destination.size() > 1){
                                        //add 3 minutes to the last waypoint's time
                                        newWaypoint.targetSeconds = destination.get(destination.size()-2).targetSeconds + (60*3);
                                    } else {
                                        newWaypoint.targetSeconds = 60*3;
                                    }
                                    destination.add(destination.size()-1, newWaypoint); //final dest stays on end

                                    //create panel
                                    PanelWayPoint p = addWaypointPanel(newWaypoint, true); //Y

                                    //add the new waypoint to the tabs
                                    refreshTabs();

                                    //select new waypoint
                                    tabPane.setSelectedComponent(p);//Y
                                } catch (CloneNotSupportedException e){
                                }
                            } else if(event.getButton() == 2) { // wheel click
                                Component c = tabPane.getSelectedComponent();//Y
                                if (c instanceof PanelWayPoint) {
                                    PanelWayPoint wp = (PanelWayPoint)c;
                                    if (wp.getState() != destination.get(destination.size()-1))
                                        removeTab(wp);
                                }
                            }
                        }

                        public void mouseEntered(MouseEvent arg0) {
                        }
                        public void mouseExited(MouseEvent arg0) {
                        }
                        public void mousePressed(MouseEvent arg0) {
                        }
                        public void mouseReleased(MouseEvent arg0) {
                        }
                    });

                    //stats tab
                    statsPanel = new JPanel(new BorderLayout());//W//Ymm
                    addStats(statsPanel);

                    //settings tab
                    settingsPanel = new PanelSettings(this);

                    //add tabs to JTabbedPane
                    refreshTabs();

                    //select final waypoint tab
                    tabPane.setSelectedComponent(waypointPanels.get(waypointPanels.size()-1));//Y
                }
                GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ym
                gridBagConstraints.anchor = GridBagConstraints.WEST;//Ym
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ym
                gridBagConstraints.weightx = .25;//Ym
                gridBagConstraints.gridy = gridy;//Ym
                gridBagConstraints.gridwidth = 4;//Ym
                gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ym
                leftbottom.add(tabPane, gridBagConstraints);//Ym
                addStatusBar(leftbottom);
			}
			outside.setLeftComponent(stuffPanel);//Ymm
		}
		{ // Right
			JPanel right = new JPanel(new GridBagLayout());//Ymm
			addOutputContainer(right);
			addOutputButtons(right);
			outside.setRightComponent(new JScrollPane(right));//Ymm
		}

		add(outside);
		outside.setDividerLocation(490);
	}

	private void refreshTabs(){
		tabPane.removeAll();//Y
		tabPane.addTab(messages.getString("tabs.history"), historyPanel);//W//Ymm
		for (int i = 0; i < waypointPanels.size()-1; i++)
		{
			tabPane.addTab(messages.getString("tabs.waypoint", i), waypointPanels.get(i));//W//Ymm
		}
		tabPane.addTab(messages.getString("tabs.waypoint", "+"), newWaypointPanel); //W//Ymm
		tabPane.addTab(messages.getString("tabs.final"), waypointPanels.get(waypointPanels.size()-1));//W//Ymm
		tabPane.addTab(messages.getString("tabs.stats"), statsPanel);//W//Ymm
		tabPane.addTab(messages.getString("tabs.settings"), settingsPanel);//W//Ymm
	}

	private void addStart(JPanel start)
	{
		historyList = new JList();//Ymm
		historyList.setMaximumSize(new Dimension(80, 100));//Y
		JScrollPane scrollPane = new JScrollPane(historyList);//Ymm
		scrollPane.setPreferredSize(start.getSize());//Y
		start.add(scrollPane);//Ymm
		historyList.addListSelectionListener(new ListSelectionListener()//Ym
		{
			@Override
			public void valueChanged(ListSelectionEvent e)//Ym
			{
				displayBuild((EcBuildOrder) historyList.getSelectedValue());
			}
		});
		final PopupMenu deleteMenu = new PopupMenu(messages.getString("history.options"));//Ymm
		MenuItem menuItem = new MenuItem(messages.getString("history.delete"));//Ymm
		menuItem.addActionListener(new ActionListener()//Ym
		{
			@Override
			public void actionPerformed(ActionEvent e)//Ym
			{
				ec.getHistory().remove(historyList.getSelectedValue());
				refreshHistory();
				ec.saveSeeds();
			}
		});
		deleteMenu.add(menuItem);//Ymm
		menuItem = new MenuItem(messages.getString("history.load"));//Ymm
		menuItem.addActionListener(new ActionListener()//Ym
		{
			@Override
			public void actionPerformed(ActionEvent e)//Ym
			{
				expandWaypoints((EcState) historyList.getSelectedValue());
				refreshTabs();
				readDestinations();
			}
		});
		deleteMenu.insert(menuItem, 0);//Ymm
		historyList.add(deleteMenu);//Ymm
		historyList.addMouseListener(new MouseAdapter()//Ym
		{
			public void mouseClicked(MouseEvent me)//Ym
			{
				// if right mouse button clicked (or me.isPopupTrigger())
				if (SwingUtilities.isRightMouseButton(me) && !historyList.isSelectionEmpty()
						&& historyList.locationToIndex(me.getPoint()) == historyList.getSelectedIndex())
				{
					deleteMenu.show(historyList, me.getX(), me.getY());
				}
			}
		});
		refreshHistory();
	}

	private void displayBuild(EcBuildOrder destination)
	{
		if (destination == null)
			return;
		EcBuildOrder source = new EcBuildOrder();
		EcBuildOrder source2 = new EcBuildOrder();
		EcBuildOrder source3 = new EcBuildOrder();
		EcEvolver evolver;
		try
		{
			evolver = new EcEvolver(source, destination.clone(), ec.getActions());
			ByteArrayOutputStream baos;
			evolver.setLoggingStream(new PrintStream(baos = new ByteArrayOutputStream()));
			evolver.enableLogging(true);
			for (EcAction a : destination.actions)
			{
				source.addAction(a.getClass().newInstance());
				source2.addAction(a.getClass().newInstance());
				source3.addAction(a.getClass().newInstance());
			}
			source.targetSeconds = destination.targetSeconds;
			source2.targetSeconds = destination.targetSeconds;
			source3.targetSeconds = destination.targetSeconds;
			source.settings = destination.settings;
			source2.settings = destination.settings;
			source3.settings = destination.settings;
			source.scoutDrone = destination.scoutDrone;
			source2.scoutDrone = destination.scoutDrone;
			source3.scoutDrone = destination.scoutDrone;
			EcBuildOrder result = evolver.doEvaluate(source);
			String detailedText = new String(baos.toByteArray());
			String simpleText = evolver.doSimpleEvaluate(source2);
			String yabotText = evolver.doYABOTEvaluate(source3);
			receiveBuildOrders(detailedText, simpleText, yabotText);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (InstantiationException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (IllegalAccessException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private void refreshHistory()
	{
		ArrayList<EcBuildOrder> results = new ArrayList<EcBuildOrder>();
		for (EcBuildOrder destination : ec.getHistory())
		{
			EcBuildOrder source = new EcBuildOrder();
			EcEvolver evolver = new EcEvolver(source, destination, ec.getActions());
			evolver.enableLogging(true);
			for (EcAction a : destination.actions)
				source.addAction(a);
			source.targetSeconds = destination.targetSeconds;
			source.scoutDrone = destination.scoutDrone;
			EcBuildOrder result = evolver.doEvaluate(source);
			if (result.seconds > 60)
				results.add(destination);
		}
		historyList.setListData(results.toArray());
	}

	private void initializeWaypoints()
	{
		try
		{
			for (int i = 1; i < 5; i++){ //add 4 waypoints
				EcState state = (EcState) ec.getInternalDestination().clone();
				state.targetSeconds = (i*3) * 60;
				destination.add(state);
			}
			destination.add((EcState) ec.getInternalDestination().clone()); //final destination
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private void addStats(JPanel stats)
	{
		stats.add(statsText = new JTextArea());//Ym
		statsText.setEditable(false);//Y
		statsText.setAlignmentX(0);//Y
		statsText.setAlignmentY(0);//Y
		statsText.setTabSize(4);//Y
	}

	private void addStatusBar(JPanel leftbottom)
	{
		statusbar = new JXStatusBar();//Ymm
		status1 = new JLabel(messages.getString("status.ready"));//Ym
		statusbar.add(status1);//Ym
		status2 = new JLabel(messages.getString("status.notRunning"));//Ym
		statusbar.add(status2);//Ym
		status3 = new JLabel("");//Ym
		statusbar.add(status3);//Ym

		GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ym
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;//Ym
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ym
		gridBagConstraints.weightx = .5;//Ym
		gridBagConstraints.gridwidth = 4;//Ym
		gridBagConstraints.gridy = gridy + 1;//Ym
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ym
		leftbottom.add(statusbar, gridBagConstraints);//Ymm
		Timer t = new Timer(200, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (timeStarted == 0)
					status1.setText(messages.getString("status.ready"));
				else
				{
					long ms = new Date().getTime() - timeStarted;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status1.setText(messages.getString("status.running", hours % 60, minutes % 60, seconds % 60));
				}
				if (lastUpdate != 0)
				{
					long ms = new Date().getTime() - lastUpdate;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status2.setText(messages.getString("status.lastUpdate", hours % 60, minutes % 60, seconds % 60));
					{
						double evaluations = ec.getGamesPlayed();
						double evalseconds = (System.currentTimeMillis() - timeStarted);
						evalseconds = evalseconds / 1000.0;
						double permsPerSecond = evaluations;
						permsPerSecond /= evalseconds;
						StringBuilder stats = new StringBuilder();
						int threadIndex = 0;
						stats.append(messages.getString("stats.gamesPlayed", evaluations / 1000));
                        stats.append("\n").append(messages.getString("stats.maxBuildOrderLength", ec.getChromosomeLength()));
                        stats.append("\n").append(messages.getString("stats.stagnationLimit", ec.getStagnationLimit()));
                        stats.append("\n").append(messages.getString("stats.gamesPlayedPerSec", (int) permsPerSecond));
                        stats.append("\n").append(messages.getString("stats.mutationRate", ec.getBaseMutationRate() / ec.getChromosomeLength()));
						for (Double d : ec.getBestScores())
                            stats.append("\n").append(messages.getString("stats.processor", threadIndex, ec.getEvolutionsSinceDiscovery(threadIndex++), d));
						statsText.setText(stats.toString());
					}
				}
				statusbar.repaint();
			}
		});
		t.start();
	}

	private void addOutputContainer(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints(); //Ym
		gridBagConstraints.fill = GridBagConstraints.BOTH;//Ym
		gridBagConstraints.weighty = 1;//Ym
		gridBagConstraints.gridy = 0;//Ym
		gridBagConstraints.gridwidth = 4;//Ym
		gridBagConstraints.insets = new Insets(1, 1, 1, 1); //Ym
		component.add(new JScrollPane(outputText = new JTextArea()), gridBagConstraints);//Ym
		outputText.setAlignmentX(0);//Y
		outputText.setAlignmentY(0);//Y
		outputText.setTabSize(4);//Y
		outputText.setEditable(false);//Y
		outputText.setLineWrap(true);
//		outputText.setPreferredSize(new Dimension(0,0));
		String welcome = messages.getString("welcome");
		simpleBuildOrder = welcome;
		detailedBuildOrder = welcome;
		outputText.setText(welcome);
	}

    void removeTab(PanelWayPoint wayPoint) {
        inputControls.removeAll(Arrays.asList(wayPoint.getComponents()));
        int selectedIndex = tabPane.getSelectedIndex();//Y
        destination.remove(wayPoint.getState());
        waypointPanels.remove(wayPoint);
        refreshTabs();
        if (selectedIndex > 0)
            tabPane.setSelectedIndex(selectedIndex-1); //if WP3 was removed, select WP2
    }

    private void readDestinations()
	{
        for (JComponent component : inputControls) {
            if (component instanceof JTextField) {
                ActionListener actionListener = ((JTextField) component).getActionListeners()[0];//Y
                if (actionListener instanceof CustomActionListener)
                    ((CustomActionListener) actionListener).reverse(component);
            } else if (component instanceof JCheckBox) {
                ActionListener actionListener = ((JCheckBox) component).getActionListeners()[0];//Y
                if (actionListener instanceof CustomActionListener)
                    ((CustomActionListener) actionListener).reverse(component);
            }
        }
	}
	
	private void addOutputButtons(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ymm
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
		gridBagConstraints.gridy = 1;//Ymm
		gridBagConstraints.gridwidth = 1;//Ymm
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
		gridBagConstraints.weightx = 0.25;//Ymm
		clipboardButton = new JButton(messages.getString("copyToClipboard"));//Ymm
		component.add(clipboardButton, gridBagConstraints);//Ymm
		clipboardButton.addActionListener(new ActionListener()//Ym
		{
			public void actionPerformed(ActionEvent e)//Ym
			{
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(outputText.getText()), null);
			}
		});

		switchDetailedButton = new JButton(messages.getString("detailedFormat"));//Ymm
		isDetailedBuildOrder = true;
		gridBagConstraints.weightx = 0.25;//Ymm
		component.add(switchDetailedButton, gridBagConstraints);//Ymm
		switchDetailedButton.addActionListener(new ActionListener()//Ym
		{
			public void actionPerformed(ActionEvent e)//Ym
			{
				outputText.setText(detailedBuildOrder);//Y
				outputText.setTabSize(4);//Y
				isDetailedBuildOrder = true;
				isYabotBuildOrder = false;
				isSimpleBuildOrder = false;
			}
		});

		switchSimpleButton = new JButton(messages.getString("simpleFormat"));//Ymm
		isSimpleBuildOrder = false;
		gridBagConstraints.weightx = 0.25;//Ymm
		component.add(switchSimpleButton, gridBagConstraints);//Ymm
		switchSimpleButton.addActionListener(new ActionListener()//Ym
		{
			public void actionPerformed(ActionEvent e)//Ym
			{
				outputText.setText(simpleBuildOrder);//Y
				outputText.setTabSize(14);//Y
				isSimpleBuildOrder = true;
				isYabotBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});

		switchYabotButton = new JButton(messages.getString("yabotFormat"));//Ymm
		isYabotBuildOrder = false;
		gridBagConstraints.weightx = 0.25;//Ymm
		component.add(switchYabotButton, gridBagConstraints);//Ymm
		switchYabotButton.addActionListener(new ActionListener()//Ym
		{
			public void actionPerformed(ActionEvent e)//Ym
			{
				outputText.setText(yabotBuildOrder);//Y
				outputText.setTabSize(14);//Y
				isYabotBuildOrder = true;
				isSimpleBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});
	}

	private void addControlParts(JPanel component)
	{
		localeComboBox = new LocaleComboBox(new Locale[]{new Locale("en"), new Locale("ko"), new Locale("de"), new Locale("es"), new Locale("fr")}, messages.getLocale());
		localeComboBox.addActionListener(new ActionListener(){//Ym
			@Override
			public void actionPerformed(ActionEvent event) {//Ym
				Locale selected = localeComboBox.getSelectedLocale();
				Locale current = messages.getLocale();
				if (selected.getLanguage().equals(current.getLanguage()) && (current.getCountry() == null || selected.getCountry().equals(current.getCountry()))){
					//do nothing if the current language was selected
					return;
				}
				
				//change the language
				messages.changeLocale(selected);
				userSettings.setLocale(selected);
				
				//re-create the window
				final JFrame newFrame = new JFrame();//Y
				EcSwingXMain.mainWindow = newFrame; //for when a Mac user selects "Quit" from the application menu
				newFrame.setTitle(messages.getString("title", EvolutionChamber.VERSION));//Y
				newFrame.setDefaultCloseOperation(frame.getDefaultCloseOperation());//Y
				newFrame.getContentPane().add(new EcSwingX(newFrame));//W//Ym
				
				newFrame.addWindowListener(new WindowAdapter() {		//Ym		
					@Override
					public void windowClosing(WindowEvent windowevent) {//Ym
						// save the window settings on exit
						int currentExtendedState = newFrame.getExtendedState();
						
						// get the preferred size of the non-maximized view
						if( currentExtendedState != JFrame.NORMAL)
							newFrame.setExtendedState(JFrame.NORMAL);
						Dimension currentSize = frame.getSize();//Y
						
						userSettings.setWindowExtensionState(currentExtendedState);
						userSettings.setWindowSize(currentSize);
					}
				});
				
				newFrame.setPreferredSize(frame.getPreferredSize());//Y
				newFrame.setIconImage(frame.getIconImage());//Y
				newFrame.pack();//Y
				newFrame.setLocationRelativeTo(null);
				
				//remove the old window
				frame.dispose();
				
				//display the new window
				newFrame.setVisible(true);//Y
			}
		});
		GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ym
		gridBagConstraints.anchor = GridBagConstraints.WEST;//Ym
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ym
		gridBagConstraints.weightx = .25;//Ym
		gridBagConstraints.gridy = gridy;//Ym
		gridBagConstraints.gridwidth = 1;//Ym
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ym
		component.add(localeComboBox, gridBagConstraints);//Ymm
		
		gridy++;
		
		addInput(component, messages.getString("processors"), NumberTextField.class, new CustomActionListener()//Ym
		{
			public void actionPerformed(ActionEvent e)//Ym
			{
				ec.setThreads(getDigit(e));
				((JTextField) e.getSource()).setText(Integer.toString(ec.getThreads()));//Y
			}
			void reverse(Object o)
			{
				((JTextField) o).setText(Integer.toString(ec.getThreads()));//Y
			}
		}).setText(Integer.toString(ec.getThreads()));//Y
		stopButton = addButton(component, messages.getString("stop"), new ActionListener()//Ym
		{
			@Override
			public void actionPerformed(ActionEvent arg0)//Ym
			{
				ec.stopAllThreads();
				running = false;
				goButton.setEnabled(true);//Y
				stopButton.setEnabled(false);//Y
				historyList.setEnabled(true);//Y
				localeComboBox.setEnabled(true);//Y
				timeStarted = 0;
				for (JComponent j : inputControls)
					j.setEnabled(true);//Y
				lastUpdate = 0;
				refreshHistory();
			}
		});
		stopButton.setEnabled(false);
		goButton = addButton(component, messages.getString("start"), new ActionListener()//Ym
		{
			@Override
			public void actionPerformed(ActionEvent e)//Ym
			{
				running = true;

				for (JComponent j : inputControls)
					j.setEnabled(false);//Y
				restartChamber();
				tabPane.setSelectedComponent(statsPanel);//Y
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);//Y
				stopButton.setEnabled(true);//Y
				historyList.setEnabled(false);//Y
				localeComboBox.setEnabled(false);//Y
			}
		});
		gridy++;
	}

	private JButton addButton(JPanel container, String string, ActionListener actionListener)
	{
		final JButton button = new JButton();//Ymm

		button.setText(string);//Y
		GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ymm
		gridBagConstraints.anchor = GridBagConstraints.WEST;//Ymm
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
		gridBagConstraints.weightx = .25;//Ymm
		gridBagConstraints.gridy = gridy;//Ymm
		gridBagConstraints.gridwidth = 1;//Ymm
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
		container.add(button, gridBagConstraints);//Ymm
		button.addActionListener(actionListener);//Y
		return button;
	}

	private JLabel addLabel(JPanel container, String string)
	{
		final JLabel label = new JLabel();//Ymm

		GridBagConstraints gridBagConstraints;//Ymm
		label.setText(string);//Ymm
		gridBagConstraints = new GridBagConstraints();//Ymm
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;//Ymm
		gridBagConstraints.weightx = 0.5;//Ymm
		gridBagConstraints.gridwidth = 2;//Ymm
		gridBagConstraints.gridy = gridy;//Ymm
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
		container.add(label, gridBagConstraints);//Ymm

		return label;
	}

	JButton addButton(JPanel container, String string, int gridwidth, ActionListener actionListener)
	{
		final JButton button = new JButton();//Ymm

		button.setText(string);//Y
		GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ymm
		gridBagConstraints.anchor = GridBagConstraints.WEST;//Ymm
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
		gridBagConstraints.weightx = .25;//Ymm
		gridBagConstraints.gridy = gridy;//Ymm
		gridBagConstraints.gridwidth = gridwidth;//Ymm
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
		container.add(button, gridBagConstraints);//Ymm
		button.addActionListener(actionListener);//Y
		return button;
	}

	static int getDigit(ActionEvent e)
	{
		JTextField tf = (JTextField) e.getSource();//Y
		String text = tf.getText();//Y
		try
		{
			if (text.contains(":"))
			{
				String[] split = text.split(":");
				if (Integer.parseInt(split[0]) < 0)
					throw new NumberFormatException();
				if (Integer.parseInt(split[1]) < 0)
					throw new NumberFormatException();
				return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
			}

			Integer i = Integer.parseInt(text);
			if (i < 0)
				throw new NumberFormatException();
			return i;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			tf.setText("0");//Y
			return 0;
		}
		catch (NumberFormatException ex)
		{
			tf.setText("0");//Y
			return 0;
		}
	}

	private void restartChamber()
	{
		if (ec.isRunning())
			ec.stopAllThreads();
		try
		{
			EcState finalDestination = collapseWaypoints();
			ec.setDestination(finalDestination);
			ec.go();
		}
		catch (InvalidConfigurationException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private EcState collapseWaypoints() throws CloneNotSupportedException
	{
		EcState finalDestination = (EcState) destination.get(destination.size()-1).clone();
		for (int i = 0; i < destination.size() - 1; i++)
		{
			if (destination.get(i).getEstimatedActions() > 1)
				finalDestination.waypoints.add((EcState) destination.get(i).clone());
		}
		return finalDestination;
	}

	private void expandWaypoints(EcState s)
	{
		try
		{
			//clear destinations
			destination.clear();
			
			//rebuild destinations
			EcState finalDestination = (EcState) s.clone();
			finalDestination.waypoints.clear();
			for (int i = 0; i < s.waypoints.size(); i++)
			{
				destination.add((EcState) s.waypoints.get(i).clone());
			}
			destination.add(finalDestination); //final destination goes last
			
			//clear panels
			for (JPanel p : waypointPanels){
				inputControls.removeAll(Arrays.asList(p.getComponents()));
			}
			waypointPanels.clear();
			
			//rebuild panels
            for (EcState aDestination : destination) {
                addWaypointPanel(aDestination, false);
            }

			//rebuild the tabs
			refreshTabs();
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

    private PanelWayPoint addWaypointPanel(EcState dest, boolean isNew) {
        PanelWayPoint p = new PanelWayPoint(this, dest);
        if (isNew)
            waypointPanels.add(waypointPanels.size()-1,p);
        else
            waypointPanels.add(p);
        return p;
    }

	protected String getSelected(ActionEvent e)
	{
		JRadioButton radioButton = (JRadioButton) e.getSource();//Y
		return radioButton.getText();
	}

	protected boolean getTrue(ActionEvent e)
	{
		JCheckBox tf = (JCheckBox) e.getSource();//Y
		//this.ec.bestScore = new Double(0); //why is this here??
		return tf.isSelected();
	}
	
	JTextField addInput(JPanel container, String name, Class<? extends JTextField> clazz, final CustomActionListener a)
	{
		try{
			GridBagConstraints gridBagConstraints;//Ym
			
			final JTextField textField = (JTextField)clazz.newInstance();//Ymm
			textField.setColumns(5);//Ym
			textField.setText("0");//Ym
			textField.addActionListener(a);//Y
			textField.addFocusListener(new FocusListener()//Ym
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					a.actionPerformed(new ActionEvent(e.getSource(), 0, "changed"));//Ym
				}
	
				@Override
				public void focusGained(FocusEvent e)
				{
				}
			});
	
			JXLabel label = new JXLabel("  " + name);//Ymm
			label.setAlignmentX(.5f);//Y
			
			gridBagConstraints = new GridBagConstraints();//Ymm
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;//Ymm
			gridBagConstraints.weightx = .25;//Ymm
			gridBagConstraints.gridy = gridy;//Ymm
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
			container.add(label, gridBagConstraints);//Ymm
			inputControls.add(label);
			
			gridBagConstraints = new GridBagConstraints();//Ymm
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;//Ymm
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
			gridBagConstraints.weightx = .25;//Ymm
			gridBagConstraints.gridy = gridy;//Ymm
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
			container.add(textField, gridBagConstraints);//Ymm
			inputControls.add(textField);
			
			return textField;
		}catch (Exception e){
			logger.log(Level.SEVERE, "Error creating input field object.", e);
			return null;
		}
	}

	@Override
	public void bestScore(final EcState finalState, int intValue, final String detailedText, final String simpleText,
			final String yabotText)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				receiveBuildOrders(detailedText, simpleText, yabotText);
				lastUpdate = new Date().getTime();
			}

		});
	}

	private void receiveBuildOrders(final String detailedText, final String simpleText, final String yabotText)
	{
		simpleBuildOrder = simpleText;
		detailedBuildOrder = detailedText;
		yabotBuildOrder = yabotText;
		if (isSimpleBuildOrder)
		{
			outputText.setText(simpleText);//Y
		}
		else if (isYabotBuildOrder)
		{
			outputText.setText(yabotBuildOrder);//Y
		}
		else
		{
			outputText.setText(detailedText);//Y
		}
	}

	@Override
	public void threadScore(int threadIndex, String output)
	{
		// TODO Auto-generated method stub

	}

}
