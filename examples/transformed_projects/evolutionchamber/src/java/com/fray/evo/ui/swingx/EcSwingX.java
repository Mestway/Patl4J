package com.fray.evo.ui.swingx;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import static com.fray.evo.ui.swingx.EcSwingXMain.userSettings;

//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.StringSelection;
//import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
//import org.jdesktop.swingx.JXLabel;
//import org.jdesktop.swingx.JXPanel;
//import org.jdesktop.swingx.JXStatusBar;
import org.jgap.InvalidConfigurationException;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.action.EcAction;
import com.fray.evo.ui.swingx.borderLayout.BorderData;
import com.fray.evo.ui.swingx.borderLayout.BorderLayout;

public class EcSwingX extends Composite implements EcReportable
{
	private static final long serialVersionUID = 4155107115709884263L;
	private static final Logger logger = Logger.getLogger(EcSwingX.class.getName());
	private Map<Composite, TabItem> tabItemMap = new HashMap<Composite, TabItem>();
	private Text				outputText;
	private Label				status1;
	private Label				status2;
	private Label				status3;
	protected long				timeStarted;
	protected long				lastUpdate;
	private String				simpleBuildOrder;
	private String				detailedBuildOrder;
	private String				yabotBuildOrder;
	private boolean			isDetailedBuildOrder;
	private boolean			isYabotBuildOrder;
	private boolean			isSimpleBuildOrder;
	int							gridx			= 0;
	int							topIncreament		= 30;
	int							xIncreament			= 24;
	int 						marginleft        = 5;
	int							marginright       = 10;
	int							margintop       = 5;
	
	private Composite			statusbar;
	List<Control>				inputControls	= new ArrayList<Control>();

	private final EvolutionChamber ec;
	List<EcState> destination = new ArrayList<EcState>();
	
	private GridData gridData;
	
	private Composite historyPanel;
	private List<Composite> waypointPanels = new ArrayList<Composite>();
	private Composite newWaypointPanel;
	private Composite statsPanel;
	private Composite settingsPanel;
	
	private boolean running = false;
	
	private Button				goButton;
	private Button				stopButton;
	private LocaleComboBox		localeComboBox;
	private Button				clipboardButton;
	private Button				switchSimpleButton;
	private Button				switchDetailedButton;
	private Button				switchYabotButton;
	private Text				statsText;
	private TabFolder			tabPane;
	private TabItem				lastSelectedTab;
	private org.eclipse.swt.widgets.List historyList;
	private Shell				frame;

	private Runnable			runnable;
	public static Thread		timerThread;
	private int 				waypointCount = 0;

	public EcSwingX(Shell parent, int style)
	{
		super(parent, style);
		File seedsDir = new File(EcSwingXMain.userConfigDir, EvolutionChamber.VERSION);
		seedsDir.mkdirs();
		ec = new EvolutionChamber(new File(seedsDir, "seeds.evo"), new File(seedsDir, "seeds2.evo"));
		ec.setReportInterface(this);
		
		System.out.println("EcSwingX @136 this : "+this);
		
		this.frame = parent;
		this.frame.setLayout(new FillLayout());
		initializeWaypoints();
		
		System.out.println("EcSwingX @136 this : "+this);
		
		setLayout(new FillLayout());
		
		SashForm outside = new SashForm(this, SWT.HORIZONTAL | SWT.BORDER);
		{
			
			Composite leftbottom = new Composite(outside, SWT.V_SCROLL);
			
			leftbottom.setLayout(new FormLayout());
			{
                addControlParts(leftbottom);
                tabPane = new TabFolder(leftbottom, SWT.NONE);
                FormData formData = new FormData();
                formData.left = new FormAttachment(gridx, marginleft);
                formData.right = new FormAttachment(99, marginright);
                formData.top = new FormAttachment(0, margintop);
                formData.bottom = new FormAttachment(99, -40);
                tabPane.setLayoutData(formData);
                {
                	historyPanel = new Composite(tabPane, SWT.NONE);
                	TabItem item = new TabItem(tabPane, SWT.NONE);
                	item.setText(messages.getString("tabs.history"));
                	item.setControl(historyPanel);
                	tabItemMap.put(historyPanel, item);
                    addStart(historyPanel);

                    for (int i = 0; i < destination.size() - 1; i++){
                    	addWaypointPanel(destination.get(i), false);
                    }

                    newWaypointPanel = new Composite(tabPane, SWT.NONE);
                    item = new TabItem(tabPane, SWT.NONE);
                    item.setText(messages.getString("tabs.waypoint", "+"));
                    item.setControl(newWaypointPanel);
                    tabItemMap.put(newWaypointPanel, item);
                    
                    EcState state = destination.get(destination.size() -1);
                    PanelWayPoint p = addWaypointPanel(state, false);
                    tabItemMap.get(p).setText(messages.getString("tabs.final"));
                    
                    tabPane.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
							if(running && tabPane.getSelection()[0].getControl() == newWaypointPanel){
								tabPane.setSelection(lastSelectedTab);
							}else{
								lastSelectedTab = tabPane.getSelection()[0];
							}
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
						}
					});

                    tabPane.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseUp(MouseEvent arg0) {
							if (running)
                                return;
                            if (tabPane.getSelection()[0].getControl() == newWaypointPanel){
                                try{
                                    EcState newWaypoint = (EcState) ec.getInternalDestination().clone();
                                    if (destination.size() > 1){
                                        newWaypoint.targetSeconds = destination.get(destination.size()-2).targetSeconds + (60*3);
                                    } else {
                                        newWaypoint.targetSeconds = 60*3;
                                    }
                                    destination.add(destination.size()-1, newWaypoint); 

                                    PanelWayPoint p = addWaypointPanel(newWaypoint, true);

                                    tabPane.setSelection(tabItemMap.get(p));
                                } catch (CloneNotSupportedException e){
                                }
                            } else if(arg0.button == 2) {
                            	
                                Composite c = (Composite) tabPane.getSelection()[0].getControl();
                                if (c instanceof PanelWayPoint) {
                                    PanelWayPoint wp = (PanelWayPoint)c;
                                    if (wp.getState() != destination.get(destination.size()-1))
                                        removeTab(wp);
                                }
                            }
						}
						
						@Override
						public void mouseDown(MouseEvent arg0) {
						}
						
						@Override
						public void mouseDoubleClick(MouseEvent arg0) {
						}
					});

                    statsPanel = new Composite(tabPane, SWT.NONE);
                    statsPanel.setLayout(new FillLayout());
                    TabItem tabitem = new TabItem(tabPane, SWT.NONE);
                    tabitem.setText(messages.getString("tabs.stats"));
                    tabitem.setControl(statsPanel);
                    tabItemMap.put(statsPanel, tabitem);
                    addStats(statsPanel);

                    settingsPanel = new PanelSettings(tabPane, this);
                    tabitem = new TabItem(tabPane, SWT.NONE);
                    tabitem.setText(messages.getString("tabs.settings"));
                    tabitem.setControl(settingsPanel);
                    tabItemMap.put(settingsPanel, tabitem);

                    tabPane.setSelection(tabItemMap.get(waypointPanels.get(waypointPanels.size()-1)));
                }
                margintop = 5;
                addStatusBar(leftbottom);
			}
		}  
		{
			
			
			Composite right = new Composite(outside, SWT.V_SCROLL);
			right.setLayout(new BorderLayout());
			
			addOutputContainer(right);
			addOutputButtons(right);
			
			outside.setWeights(new int[] {50, 50});
		}

	}

	private void refreshTabs(){
		
		System.out.println("refresh tabs");
	}

	private void addStart(Composite start)
	{
		start.setLayout(new FillLayout());
		historyList = new org.eclipse.swt.widgets.List(start, SWT.V_SCROLL);
		historyList.setSize(start.getSize());
		historyList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				displayBuild((EcBuildOrder)historyList.getData(String.valueOf(historyList.getSelectionIndex())));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		final Menu deleteMenu = new Menu(getShell(), SWT.POP_UP);
		deleteMenu.setData(messages.getString("history.options"));
		MenuItem menuItem = new MenuItem(deleteMenu, SWT.PUSH);
		menuItem.setText(messages.getString("history.delete"));
		menuItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ec.getHistory().remove(historyList.getData(String.valueOf(historyList.getSelectionIndex())));
				refreshHistory();
				ec.saveSeeds();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		menuItem = new MenuItem(deleteMenu, SWT.PUSH);
		menuItem.setText(messages.getString("history.load"));
		menuItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				expandWaypoints((EcState) historyList.getData(String.valueOf(historyList.getSelectionIndex())));
				refreshTabs();
				readDestinations();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		historyList.setMenu(deleteMenu);
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
		for(int i = 0; i < results.size(); i++){
			historyList.add("Required Bases:1");
			historyList.setData(String.valueOf(i), results.get(i));
		}
	}

	private void initializeWaypoints()
	{
		try
		{
			for (int i = 1; i < 5; i++){
				EcState state = (EcState) ec.getInternalDestination().clone();
				state.targetSeconds = (i*3) * 60;
				destination.add(state);
			}
			destination.add((EcState) ec.getInternalDestination().clone()); 
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private void addStats(Composite stats)
	{
		statsText = new Text(stats, SWT.MULTI | SWT.LEFT);
		statsText.setEditable(false);
		statsText.setTabs(4);
	}

	private void addStatusBar(Composite leftbottom)
	{
		statusbar = new Composite(leftbottom, SWT.BORDER);
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, marginleft);
		formData.right = new FormAttachment(99, marginright);
		formData.bottom = new FormAttachment(99, 0);
		statusbar.setLayoutData(formData);
		statusbar.setLayout(new FormLayout());
		
		status1 = new Label(statusbar, SWT.NONE);
		status1.setText(messages.getString("status.ready"));
		formData = new FormData();
		formData.left = new FormAttachment(0, 10);
		formData.right = new FormAttachment(25, 0);
		formData.bottom = new FormAttachment(99, 0);
		status1.setLayoutData(formData);
		
		status2 = new Label(statusbar, SWT.NONE);
		status2.setText(messages.getString("status.notRunning"));
		formData = new FormData();
		formData.left = new FormAttachment(25, 10);
		formData.right = new FormAttachment(50, 0);
		formData.bottom = new FormAttachment(99, 0);
		status2.setLayoutData(formData);
		
		status3 = new Label(statusbar, SWT.NONE);
		status3.setText("");
		formData = new FormData();
		formData.left = new FormAttachment(50, 10);
		formData.right = new FormAttachment(75, 0);
		formData.bottom = new FormAttachment(99, 0);
		status3.setLayoutData(formData);
		
		timerThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {

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
						}
					});
				}
			}
		});
		timerThread.start();
	}

	private void addOutputContainer(Composite component)
	{
		outputText = new Text(component, SWT.MULTI);
		outputText.setLayoutData(BorderData.CENTER);
		outputText.setTabs(4);
		outputText.setEnabled(false);
		
		String welcome = messages.getString("welcome");
		simpleBuildOrder = welcome;
		detailedBuildOrder = welcome;
		outputText.setText(welcome);
	}

    void removeTab(PanelWayPoint wayPoint) {
    	int selectedIndex = tabPane.getSelectionIndex();
    	inputControls.remove(selectedIndex);
    	destination.remove(wayPoint.getState());
    	waypointPanels.remove(wayPoint);
    	TabItem item = tabItemMap.get(wayPoint);
    	tabItemMap.remove(wayPoint);
    	item.dispose();
    	tabItemMap.remove(wayPoint);
    	if(selectedIndex > 0)
    		tabPane.setSelection(selectedIndex - 1);
    	
    }

    private void readDestinations()
	{
    	for(Control control : inputControls){
    		if(control instanceof Text){
    			SelectionListener selectionListener =(SelectionListener) ((Text)control).getListeners(SWT.Selection)[0];
    			if(selectionListener instanceof CustomActionListener){
    				((CustomActionListener) selectionListener).reverse(control);
    			}
    		}else if(control instanceof Button){
    			SelectionListener selectionListener = (SelectionListener)((Button)control).getListeners(SWT.Selection)[0];
    			if(selectionListener instanceof CustomActionListener){
    				((CustomActionListener) selectionListener).reverse(control);
    			}
    		}
    	}
	}
    	
	
	private void addOutputButtons(Composite component)
	{
		Composite composite = new Composite(component, SWT.NONE);
		composite.setLayoutData(BorderData.SOUTH);
		GridLayout gridLayout = new GridLayout(4, true);
		composite.setLayout(gridLayout);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessHorizontalSpace = true;
		
		clipboardButton = new Button(composite, SWT.PUSH);
		clipboardButton.setText(messages.getString("copyToClipboard"));
		clipboardButton.setLayoutData(gridData);
		clipboardButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				clipboard.setContents(new Object[]{outputText.getSelectionText()}, new Transfer[]{TextTransfer.getInstance()});
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		

		switchDetailedButton = new Button(composite, SWT.PUSH);
		switchDetailedButton.setText(messages.getString("detailedFormat"));
		switchDetailedButton.setLayoutData(gridData);
		isDetailedBuildOrder = true;
		switchDetailedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				outputText.setText(detailedBuildOrder);
				outputText.setTabs(4);
				isDetailedBuildOrder = true;
				isYabotBuildOrder = false;
				isSimpleBuildOrder = false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		switchSimpleButton = new Button(composite, SWT.PUSH);
		switchSimpleButton.setText(messages.getString("simpleFormat"));
		switchSimpleButton.setLayoutData(gridData);
		isSimpleBuildOrder = false;
		switchSimpleButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				outputText.setText(simpleBuildOrder);
				outputText.setTabs(14);
				isSimpleBuildOrder = true;
				isYabotBuildOrder = false;
				isDetailedBuildOrder = false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});


		switchYabotButton = new Button(composite, SWT.PUSH);
		switchYabotButton.setText(messages.getString("yabotFormat"));
		switchYabotButton.setLayoutData(gridData);
		isYabotBuildOrder = false;
		switchYabotButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				outputText.setText(yabotBuildOrder);
				outputText.setTabs(14);
				isYabotBuildOrder = true;
				isSimpleBuildOrder = false;
				isDetailedBuildOrder = false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
	}

	private void addControlParts(Composite component)
	{
		localeComboBox = new LocaleComboBox(component, SWT.READ_ONLY, new Locale[]{new Locale("en"), new Locale("ko"), new Locale("de"), new Locale("es"), new Locale("fr")}, messages.getLocale());
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(gridx, marginleft);
		formData.right = new FormAttachment(gridx+24, marginright);
		formData.top = new FormAttachment(0, margintop);
		localeComboBox.setLayoutData(formData);
		margintop += 40;
		
		localeComboBox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Locale selected = localeComboBox.getSelectedLocale();
				Locale current = messages.getLocale();
				if(selected.getLanguage().equals(current.getLanguage()) && (current.getCountry() == null || selected.getCountry().equals(current.getCountry()))){
					return;
				}
				
				messages.changeLocale(selected);
				userSettings.setLocale(selected);
				
				
				final Shell newFrame = new Shell(Display.getCurrent());
				newFrame.setSize(EcSwingXMain.mainWindow.getSize());
				newFrame.setImage(EcSwingXMain.mainWindow.getImage());
				
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								
								EcSwingXMain.mainWindow.dispose();
								EcSwingXMain.mainWindow = newFrame;
								newFrame.setText(messages.getString("title", EvolutionChamber.VERSION));
								new EcSwingX(newFrame, SWT.NONE);
								newFrame.addListener(SWT.Dispose, new Listener() {
									
									@Override
									public void handleEvent(Event arg0) {
										int currentExtendedState = newFrame.getMaximized() ? 1 : (newFrame.getMinimized() ? -1 : 0);
										Point size = newFrame.getSize();
										userSettings.setWindowExtensionState(currentExtendedState);
										userSettings.setWindowSize(size);
									}
								});
								
								newFrame.pack();
								newFrame.open();
							}
						});
					}
				}).start();;
				while(!newFrame.isDisposed()){
					while(!Display.getDefault().readAndDispatch()){
						Display.getDefault().sleep();
					}
				}
				EcSwingX.timerThread.interrupt();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		
		addInput(component, messages.getString("processors"), NumberTextField.class, new CustomActionListener()
		{
			void reverse(Object o)
			{
				((Text) o).setText(Integer.toString(ec.getThreads()));
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				ec.setThreads(getDigit(e));
				((Text) e.getSource()).setText(Integer.toString(ec.getThreads()));
			}
		}).setText(Integer.toString(ec.getThreads()));
		stopButton = addButton(component, messages.getString("stop"), new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ec.stopAllThreads();
				running = false;
				
				goButton.setEnabled(true);
				stopButton.setEnabled(false);
				historyList.setEnabled(true);
				localeComboBox.setEnabled(true);
				timeStarted = 0;
				for(Control control : inputControls){
					control.setEnabled(true);
				}				
				lastUpdate = 0;
				refreshHistory();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		gridx += 25;
		
		stopButton.setEnabled(false);
		goButton = addButton(component, messages.getString("start"), new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				running = true;
				for(Control control : inputControls){
					control.setEnabled(false);
				}
				
				restartChamber();
				tabPane.setSelection(tabItemMap.get(statsPanel));
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);
				stopButton.setEnabled(true);
				historyList.setEnabled(false);
				localeComboBox.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		gridx = 0;
		margintop += 40;
	}

	private Button addButton(Composite container, String string, SelectionListener selectionListener)
	{
		final Button button = new Button(container, SWT.PUSH);
		button.setText(string);
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(gridx, marginleft);
		formData.right = new FormAttachment(gridx+xIncreament, marginright);
		formData.top = new FormAttachment(0, margintop);
		button.setLayoutData(formData);
		
		button.addSelectionListener(selectionListener);
		return button;
	}

	private Label addLabel(Composite container, String string){
		final Label label = new Label(container, SWT.NONE);
		label.setText(string);
		return label;
	}
	
	Button addButton(Composite container, String string, int gridwidth, SelectionListener selectionListener)
	{
		final Button button = new Button(container, SWT.PUSH);
		button.setText(string);
		
		FormData formData = new FormData();
		formData.left = new FormAttachment(gridx, marginleft);
		formData.right = new FormAttachment(gridx+25*gridwidth, marginright);
		formData.top = new FormAttachment(0, margintop);
		button.setLayoutData(formData);
		gridx += 25;
		
		button.addSelectionListener(selectionListener);
		
		return button;
	}

	static int getDigit(SelectionEvent e)
	{
		Text tf = (Text) e.getSource();
		String text = tf.getText();
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
			tf.setText("0");
			return 0;
		}
		catch (NumberFormatException ex)
		{
			tf.setText("0");
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
			
			System.out.println("EcSwingX @977 finalDestination : "+finalDestination);
			
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
			destination.clear();
			
			EcState finalDestination = (EcState) s.clone();
			finalDestination.waypoints.clear();
			for (int i = 0; i < s.waypoints.size(); i++)
			{
				destination.add((EcState) s.waypoints.get(i).clone());
			}
			destination.add(finalDestination); 
			
			for(Composite comp : waypointPanels){
				inputControls.remove(comp);
			}
			waypointPanels.clear();
			
            for (EcState aDestination : destination) {
                addWaypointPanel(aDestination, false);
            }

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
        PanelWayPoint p = new PanelWayPoint(tabPane, this, dest);
        TabItem tabItem = new TabItem(tabPane, SWT.NONE);
        tabItem.setText(messages.getString("tabs.waypoint", waypointCount));
        waypointCount ++;
        tabItem.setControl(p);
        tabItemMap.put(p, tabItem);
        if (isNew){
        	waypointPanels.add(waypointPanels.size()-1,p);
        }
        else
            waypointPanels.add(p);
        
        return p;
    }

	protected String getSelected(SelectionEvent e)
	{
		Button radioButton = (Button) e.getSource();
		return radioButton.getText();
	}

	protected boolean getTrue(SelectionEvent e)
	{
		Button tf = (Button) e.getSource();
		return tf.getSelection();
	}
	
	Text addInput(Composite container, String name, Class<? extends MText> clazz, final CustomActionListener a)
	{
		try{
			
			Label label = new Label(container, SWT.NONE);
			label.setText("  "+name);
			FormData formData = new FormData();
			formData.left = new FormAttachment(gridx, marginleft);
			formData.right = new FormAttachment(gridx+xIncreament, marginright);
			formData.top = new FormAttachment(0, margintop);
			label.setLayoutData(formData);
			gridx += xIncreament+1;
			
			final MText textField = (MText) clazz.getConstructors()[0].newInstance(container, SWT.NONE);
			textField.setText("0");
			formData = new FormData();
			formData.left = new FormAttachment(gridx, marginleft);
			formData.right = new FormAttachment(gridx+xIncreament, marginright);
			formData.top = new FormAttachment(0, margintop);
			textField.setLayoutData(formData);
			gridx += xIncreament+1;
			
			textField.addSelectionListner(a);
			textField.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					a.widgetSelected(new SelectionEvent(new Event()));
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
				}
			});
	
			inputControls.add(label);

			inputControls.add(textField.Text());
			
			return textField.Text();
		}catch (Exception e){
			logger.log(Level.SEVERE, "Error creating input field object.", e);
			return null;
		}
	}

	@Override
	public void bestScore(final EcState finalState, int intValue, final String detailedText, final String simpleText,
			final String yabotText)
	{

		System.out.println("EcSwingX @1125 bestSrcore arguments : "+finalState);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("in bestScore function.....");
				
				Display.getDefault().asyncExec(new Runnable(){
					@Override
					public void run()
					{
						System.out.println("ExSwingX @1138 detailedText: "+detailedText);
						System.out.println("ExSwingX @1138 simpleText: "+simpleText);
						System.out.println("ExSwingX @1138 yabotText: "+yabotText);
						
						receiveBuildOrders(detailedText, simpleText, yabotText);
						lastUpdate = new Date().getTime();
					}

				});
			}
		}).start();
	
	}

	private void receiveBuildOrders(final String detailedText, final String simpleText, final String yabotText)
	{
		
		System.out.println("out put message : "+detailedText);
		
		simpleBuildOrder = simpleText;
		detailedBuildOrder = detailedText;
		yabotBuildOrder = yabotText;
		if (isSimpleBuildOrder)
		{
			outputText.setText(simpleText);
		}
		else if (isYabotBuildOrder)
		{
			outputText.setText(yabotBuildOrder);
		}
		else
		{
			outputText.setText(detailedText);
		}
	}

	@Override
	public void threadScore(int threadIndex, String output)
	{
	}

}
