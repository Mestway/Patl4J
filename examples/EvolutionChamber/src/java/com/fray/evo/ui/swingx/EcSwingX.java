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
public class EcSwingX extends JXPanel implements EcReportable {
  private static final long serialVersionUID=4155107115709884263L;
  private static final Logger logger=Logger.getLogger(EcSwingX.class.getName());
  private JTextArea outputText;
  private JLabel status1;
  private JLabel status2;
  private JLabel status3;
  protected long timeStarted;
  protected long lastUpdate;
  private String simpleBuildOrder;
  private String detailedBuildOrder;
  private String yabotBuildOrder;
  private boolean isDetailedBuildOrder;
  private boolean isYabotBuildOrder;
  private boolean isSimpleBuildOrder;
  int gridy=0;
  private JXStatusBar statusbar;
  List<JComponent> inputControls=new ArrayList<JComponent>();
  private final EvolutionChamber ec;
  List<EcState> destination=new ArrayList<EcState>();
  private JPanel historyPanel;
  private List<JPanel> waypointPanels=new ArrayList<JPanel>();
  private JPanel newWaypointPanel;
  private JPanel statsPanel;
  private JPanel settingsPanel;
  private boolean running=false;
  private JButton goButton;
  private JButton stopButton;
  private LocaleComboBox localeComboBox;
  private JButton clipboardButton;
  private JButton switchSimpleButton;
  private JButton switchDetailedButton;
  private JButton switchYabotButton;
  private JTextArea statsText;
  private JTabbedPane tabPane;
  private Component lastSelectedTab;
  private JList historyList;
  private JFrame frame;
  /** 
 * Constructor.
 * @param frame the window that holds this panel.
 */
  public EcSwingX(  JFrame frame){
    File seedsDir;
    seedsDir=new File(EcSwingXMain.userConfigDir,EvolutionChamber.VERSION);
    seedsDir.mkdirs();
    java.lang.String genVar3397;
    genVar3397="seeds.evo";
    java.io.File genVar3398;
    genVar3398=new File(seedsDir,genVar3397);
    java.lang.String genVar3399;
    genVar3399="seeds2.evo";
    java.io.File genVar3400;
    genVar3400=new File(seedsDir,genVar3399);
    ec=new EvolutionChamber(genVar3398,genVar3400);
    com.fray.evo.ui.swingx.EcSwingX genVar3401;
    genVar3401=this;
    ec.setReportInterface(genVar3401);
    com.fray.evo.ui.swingx.EcSwingX genVar3402;
    genVar3402=this;
    genVar3402.frame=frame;
    EcSwingX genVar3403;
    genVar3403=this;
    genVar3403.initializeWaypoints();
    EcSwingX genVar3404;
    genVar3404=this;
    java.awt.BorderLayout genVar3405;
    genVar3405=new BorderLayout();
    genVar3404.setLayout(genVar3405);
    JSplitPane outside;
    int orient=JSplitPane.HORIZONTAL_SPLIT;
    outside=new JSplitPane(orient);
{
      java.awt.GridBagLayout genVar3406;
      genVar3406=new GridBagLayout();
      JPanel leftbottom;
      leftbottom=new JPanel();
      leftbottom.setLayout(genVar3406);
      JScrollPane stuffPanel;
      stuffPanel=new JScrollPane(leftbottom);
{
        EcSwingX genVar3407;
        genVar3407=this;
        genVar3407.addControlParts(leftbottom);
        int l=JTabbedPane.LEFT;
        tabPane=new JTabbedPane(l);
{
          java.awt.BorderLayout genVar3408;
          genVar3408=new BorderLayout();
          historyPanel=new JPanel();
          historyPanel.setLayout(genVar3408);
          EcSwingX genVar3409;
          genVar3409=this;
          genVar3409.addStart(historyPanel);
          for (          EcState dest : destination) {
            EcSwingX genVar3410;
            genVar3410=this;
            boolean genVar3411;
            genVar3411=false;
            genVar3410.addWaypointPanel(dest,genVar3411);
          }
          newWaypointPanel=new JPanel();
          ChangeListener genVar3412;
          genVar3412=new ChangeListener(){
            @Override public void stateChanged(            ChangeEvent event){
              if (running && tabPane.getSelectedComponent() == newWaypointPanel) {
                tabPane.setSelectedComponent(lastSelectedTab);
              }
 else {
                lastSelectedTab=tabPane.getSelectedComponent();
              }
            }
          }
;
          tabPane.addChangeListener(genVar3412);
          MouseListener genVar3413;
          genVar3413=new MouseListener(){
            public void mouseClicked(            MouseEvent event){
              if (running)               return;
              if (tabPane.getSelectedComponent() == newWaypointPanel) {
                try {
                  EcState newWaypoint=(EcState)ec.getInternalDestination().clone();
                  if (destination.size() > 1) {
                    newWaypoint.targetSeconds=destination.get(destination.size() - 2).targetSeconds + (60 * 3);
                  }
 else {
                    newWaypoint.targetSeconds=60 * 3;
                  }
                  destination.add(destination.size() - 1,newWaypoint);
                  PanelWayPoint p=addWaypointPanel(newWaypoint,true);
                  tabPane.removeAll();
                  java.lang.String genVar3433;
                  genVar3433="tabs.history";
                  java.lang.String genVar3434;
                  genVar3434=messages.getString(genVar3433);
                  tabPane.addTab(genVar3434,historyPanel);
                  int i=0;
                  for (; i < waypointPanels.size() - 1; i++) {
                    java.lang.String genVar3435;
                    genVar3435="tabs.waypoint";
                    java.lang.String genVar3436;
                    genVar3436=messages.getString(genVar3435,i);
                    javax.swing.JPanel genVar3437;
                    genVar3437=waypointPanels.get(i);
                    tabPane.addTab(genVar3436,genVar3437);
                  }
                  java.lang.String genVar3438;
                  genVar3438="tabs.waypoint";
                  java.lang.String genVar3439;
                  genVar3439="+";
                  java.lang.String genVar3440;
                  genVar3440=messages.getString(genVar3438,genVar3439);
                  tabPane.addTab(genVar3440,newWaypointPanel);
                  java.lang.String genVar3441;
                  genVar3441="tabs.final";
                  java.lang.String genVar3442;
                  genVar3442=messages.getString(genVar3441);
                  int genVar3443;
                  genVar3443=waypointPanels.size();
                  int genVar3444;
                  genVar3444=1;
                  int genVar3445;
                  genVar3445=genVar3443 - genVar3444;
                  javax.swing.JPanel genVar3446;
                  genVar3446=waypointPanels.get(genVar3445);
                  tabPane.addTab(genVar3442,genVar3446);
                  java.lang.String genVar3447;
                  genVar3447="tabs.stats";
                  java.lang.String genVar3448;
                  genVar3448=messages.getString(genVar3447);
                  tabPane.addTab(genVar3448,statsPanel);
                  java.lang.String genVar3449;
                  genVar3449="tabs.settings";
                  java.lang.String genVar3450;
                  genVar3450=messages.getString(genVar3449);
                  tabPane.addTab(genVar3450,settingsPanel);
                  tabPane.setSelectedComponent(p);
                }
 catch (                CloneNotSupportedException e) {
                }
              }
 else               if (event.getButton() == 2) {
                Component c=tabPane.getSelectedComponent();
                if (c instanceof PanelWayPoint) {
                  PanelWayPoint wp=(PanelWayPoint)c;
                  if (wp.getState() != destination.get(destination.size() - 1))                   removeTab(wp);
                }
              }
            }
            public void mouseEntered(            MouseEvent arg0){
            }
            public void mouseExited(            MouseEvent arg0){
            }
            public void mousePressed(            MouseEvent arg0){
            }
            public void mouseReleased(            MouseEvent arg0){
            }
          }
;
          tabPane.addMouseListener(genVar3413);
          java.awt.BorderLayout genVar3414;
          genVar3414=new BorderLayout();
          statsPanel=new JPanel();
          statsPanel.setLayout(genVar3414);
          EcSwingX genVar3415;
          genVar3415=this;
          genVar3415.addStats(statsPanel);
          com.fray.evo.ui.swingx.EcSwingX genVar3416;
          genVar3416=this;
          settingsPanel=new PanelSettings(genVar3416);
          tabPane.removeAll();
          java.lang.String genVar3433;
          genVar3433="tabs.history";
          java.lang.String genVar3434;
          genVar3434=messages.getString(genVar3433);
          tabPane.addTab(genVar3434,historyPanel);
          int i=0;
          for (; i < waypointPanels.size() - 1; i++) {
            java.lang.String genVar3435;
            genVar3435="tabs.waypoint";
            java.lang.String genVar3436;
            genVar3436=messages.getString(genVar3435,i);
            javax.swing.JPanel genVar3437;
            genVar3437=waypointPanels.get(i);
            tabPane.addTab(genVar3436,genVar3437);
          }
          java.lang.String genVar3438;
          genVar3438="tabs.waypoint";
          java.lang.String genVar3439;
          genVar3439="+";
          java.lang.String genVar3440;
          genVar3440=messages.getString(genVar3438,genVar3439);
          tabPane.addTab(genVar3440,newWaypointPanel);
          java.lang.String genVar3441;
          genVar3441="tabs.final";
          java.lang.String genVar3442;
          genVar3442=messages.getString(genVar3441);
          int genVar3443;
          genVar3443=waypointPanels.size();
          int genVar3444;
          genVar3444=1;
          int genVar3445;
          genVar3445=genVar3443 - genVar3444;
          javax.swing.JPanel genVar3446;
          genVar3446=waypointPanels.get(genVar3445);
          tabPane.addTab(genVar3442,genVar3446);
          java.lang.String genVar3447;
          genVar3447="tabs.stats";
          java.lang.String genVar3448;
          genVar3448=messages.getString(genVar3447);
          tabPane.addTab(genVar3448,statsPanel);
          java.lang.String genVar3449;
          genVar3449="tabs.settings";
          java.lang.String genVar3450;
          genVar3450=messages.getString(genVar3449);
          tabPane.addTab(genVar3450,settingsPanel);
          int genVar3418;
          genVar3418=waypointPanels.size();
          int genVar3419;
          genVar3419=1;
          int genVar3420;
          genVar3420=genVar3418 - genVar3419;
          javax.swing.JPanel genVar3421;
          genVar3421=waypointPanels.get(genVar3420);
          tabPane.setSelectedComponent(genVar3421);
        }
        GridBagConstraints gridBagConstraints;
        gridBagConstraints=new GridBagConstraints();
        gridBagConstraints.anchor=GridBagConstraints.WEST;
        gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx=.25;
        gridBagConstraints.gridy=gridy;
        gridBagConstraints.gridwidth=4;
        int genVar3422;
        genVar3422=1;
        int genVar3423;
        genVar3423=1;
        int genVar3424;
        genVar3424=1;
        int genVar3425;
        genVar3425=1;
        gridBagConstraints.insets=new Insets(genVar3422,genVar3423,genVar3424,genVar3425);
        leftbottom.add(tabPane,gridBagConstraints);
        EcSwingX genVar3426;
        genVar3426=this;
        genVar3426.addStatusBar(leftbottom);
      }
      outside.setLeftComponent(stuffPanel);
    }
{
      java.awt.GridBagLayout genVar3427;
      genVar3427=new GridBagLayout();
      JPanel right;
      right=new JPanel();
      right.setLayout(genVar3427);
      EcSwingX genVar3428;
      genVar3428=this;
      genVar3428.addOutputContainer(right);
      EcSwingX genVar3429;
      genVar3429=this;
      genVar3429.addOutputButtons(right);
      javax.swing.JScrollPane genVar3430;
      genVar3430=new JScrollPane(right);
      outside.setRightComponent(genVar3430);
    }
    EcSwingX genVar3431;
    genVar3431=this;
    genVar3431.add(outside);
    int genVar3432;
    genVar3432=490;
    outside.setDividerLocation(genVar3432);
  }
  private void addStart(  JPanel start){
    historyList=new JList();
    int genVar3451;
    genVar3451=80;
    int genVar3452;
    genVar3452=100;
    java.awt.Dimension genVar3453;
    genVar3453=new Dimension(genVar3451,genVar3452);
    historyList.setMaximumSize(genVar3453);
    JScrollPane scrollPane;
    scrollPane=new JScrollPane(historyList);
    java.awt.Dimension genVar3454;
    genVar3454=start.getSize();
    scrollPane.setPreferredSize(genVar3454);
    start.add(scrollPane);
    ListSelectionListener genVar3455;
    genVar3455=new ListSelectionListener(){
      @Override public void valueChanged(      ListSelectionEvent e){
        displayBuild((EcBuildOrder)historyList.getSelectedValue());
      }
    }
;
    historyList.addListSelectionListener(genVar3455);
    java.lang.String genVar3456;
    genVar3456="history.options";
    java.lang.String genVar3457;
    genVar3457=messages.getString(genVar3456);
    final PopupMenu deleteMenu;
    deleteMenu=new PopupMenu(genVar3457);
    java.lang.String genVar3458;
    genVar3458="history.delete";
    java.lang.String genVar3459;
    genVar3459=messages.getString(genVar3458);
    MenuItem menuItem;
    menuItem=new MenuItem(genVar3459);
    ActionListener genVar3460;
    genVar3460=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        ec.getHistory().remove(historyList.getSelectedValue());
        refreshHistory();
        ec.saveSeeds();
      }
    }
;
    menuItem.addActionListener(genVar3460);
    deleteMenu.add(menuItem);
    java.lang.String genVar3461;
    genVar3461="history.load";
    java.lang.String genVar3462;
    genVar3462=messages.getString(genVar3461);
    menuItem=new MenuItem(genVar3462);
    ActionListener genVar3463;
    genVar3463=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        expandWaypoints((EcState)historyList.getSelectedValue());
        tabPane.removeAll();
        java.lang.String genVar3433;
        genVar3433="tabs.history";
        java.lang.String genVar3434;
        genVar3434=messages.getString(genVar3433);
        tabPane.addTab(genVar3434,historyPanel);
        int i=0;
        for (; i < waypointPanels.size() - 1; i++) {
          java.lang.String genVar3435;
          genVar3435="tabs.waypoint";
          java.lang.String genVar3436;
          genVar3436=messages.getString(genVar3435,i);
          javax.swing.JPanel genVar3437;
          genVar3437=waypointPanels.get(i);
          tabPane.addTab(genVar3436,genVar3437);
        }
        java.lang.String genVar3438;
        genVar3438="tabs.waypoint";
        java.lang.String genVar3439;
        genVar3439="+";
        java.lang.String genVar3440;
        genVar3440=messages.getString(genVar3438,genVar3439);
        tabPane.addTab(genVar3440,newWaypointPanel);
        java.lang.String genVar3441;
        genVar3441="tabs.final";
        java.lang.String genVar3442;
        genVar3442=messages.getString(genVar3441);
        int genVar3443;
        genVar3443=waypointPanels.size();
        int genVar3444;
        genVar3444=1;
        int genVar3445;
        genVar3445=genVar3443 - genVar3444;
        javax.swing.JPanel genVar3446;
        genVar3446=waypointPanels.get(genVar3445);
        tabPane.addTab(genVar3442,genVar3446);
        java.lang.String genVar3447;
        genVar3447="tabs.stats";
        java.lang.String genVar3448;
        genVar3448=messages.getString(genVar3447);
        tabPane.addTab(genVar3448,statsPanel);
        java.lang.String genVar3449;
        genVar3449="tabs.settings";
        java.lang.String genVar3450;
        genVar3450=messages.getString(genVar3449);
        tabPane.addTab(genVar3450,settingsPanel);
        readDestinations();
      }
    }
;
    menuItem.addActionListener(genVar3463);
    int genVar3464;
    genVar3464=0;
    deleteMenu.insert(menuItem,genVar3464);
    historyList.add(deleteMenu);
    MouseAdapter genVar3465;
    genVar3465=new MouseAdapter(){
      public void mouseClicked(      MouseEvent me){
        if (SwingUtilities.isRightMouseButton(me) && !historyList.isSelectionEmpty() && historyList.locationToIndex(me.getPoint()) == historyList.getSelectedIndex()) {
          deleteMenu.show(historyList,me.getX(),me.getY());
        }
      }
    }
;
    historyList.addMouseListener(genVar3465);
    EcSwingX genVar3466;
    genVar3466=this;
    genVar3466.refreshHistory();
  }
  private void displayBuild(  EcBuildOrder destination){
    boolean genVar3467;
    genVar3467=destination == null;
    if (genVar3467) {
      return;
    }
 else {
      ;
    }
    EcBuildOrder source;
    source=new EcBuildOrder();
    EcBuildOrder source2;
    source2=new EcBuildOrder();
    EcBuildOrder source3;
    source3=new EcBuildOrder();
    EcEvolver evolver;
    try {
      com.fray.evo.EcBuildOrder genVar3468;
      genVar3468=destination.clone();
      List<Class<? extends EcAction>> genVar3469;
      genVar3469=ec.getActions();
      evolver=new EcEvolver(source,genVar3468,genVar3469);
      ByteArrayOutputStream baos;
      java.io.ByteArrayOutputStream genVar3470;
      genVar3470=baos=new ByteArrayOutputStream();
      java.io.PrintStream genVar3471;
      genVar3471=new PrintStream(genVar3470);
      evolver.setLoggingStream(genVar3471);
      boolean genVar3472;
      genVar3472=true;
      evolver.enableLogging(genVar3472);
      for (      EcAction a : destination.actions) {
        java.lang.Class genVar3473;
        genVar3473=a.getClass();
        EcAction genVar3474;
        genVar3474=(EcAction)genVar3473.newInstance();
        source.addAction(genVar3474);
        java.lang.Class genVar3475;
        genVar3475=a.getClass();
        EcAction genVar3476;
        genVar3476=(EcAction)genVar3475.newInstance();
        source2.addAction(genVar3476);
        java.lang.Class genVar3477;
        genVar3477=a.getClass();
        EcAction genVar3478;
        genVar3478=(EcAction)genVar3477.newInstance();
        source3.addAction(genVar3478);
      }
      source.targetSeconds=destination.targetSeconds;
      source2.targetSeconds=destination.targetSeconds;
      source3.targetSeconds=destination.targetSeconds;
      source.settings=destination.settings;
      source2.settings=destination.settings;
      source3.settings=destination.settings;
      source.scoutDrone=destination.scoutDrone;
      source2.scoutDrone=destination.scoutDrone;
      source3.scoutDrone=destination.scoutDrone;
      EcBuildOrder result;
      result=evolver.doEvaluate(source);
      byte[] genVar3479;
      genVar3479=baos.toByteArray();
      String detailedText;
      detailedText=new String(genVar3479);
      String simpleText;
      simpleText=evolver.doSimpleEvaluate(source2);
      String yabotText;
      yabotText=evolver.doYABOTEvaluate(source3);
      EcSwingX genVar3480;
      genVar3480=this;
      genVar3480.receiveBuildOrders(detailedText,simpleText,yabotText);
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3481;
      genVar3481=new PrintWriter(sw);
      e.printStackTrace(genVar3481);
      java.lang.String genVar3482;
      genVar3482=sw.toString();
      logger.severe(genVar3482);
    }
catch (    InstantiationException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3483;
      genVar3483=new PrintWriter(sw);
      e.printStackTrace(genVar3483);
      java.lang.String genVar3484;
      genVar3484=sw.toString();
      logger.severe(genVar3484);
    }
catch (    IllegalAccessException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3485;
      genVar3485=new PrintWriter(sw);
      e.printStackTrace(genVar3485);
      java.lang.String genVar3486;
      genVar3486=sw.toString();
      logger.severe(genVar3486);
    }
  }
  private void refreshHistory(){
    ArrayList<EcBuildOrder> results;
    results=new ArrayList<EcBuildOrder>();
    java.util.List<com.fray.evo.EcBuildOrder> genVar3487;
    genVar3487=ec.getHistory();
    for (    EcBuildOrder destination : genVar3487) {
      EcBuildOrder source;
      source=new EcBuildOrder();
      List<Class<? extends EcAction>> genVar3488;
      genVar3488=ec.getActions();
      EcEvolver evolver;
      evolver=new EcEvolver(source,destination,genVar3488);
      boolean genVar3489;
      genVar3489=true;
      evolver.enableLogging(genVar3489);
      for (      EcAction a : destination.actions)       source.addAction(a);
      source.targetSeconds=destination.targetSeconds;
      source.scoutDrone=destination.scoutDrone;
      EcBuildOrder result;
      result=evolver.doEvaluate(source);
      int genVar3490;
      genVar3490=60;
      boolean genVar3491;
      genVar3491=result.seconds > genVar3490;
      if (genVar3491) {
        results.add(destination);
      }
 else {
        ;
      }
    }
    java.lang.Object[] genVar3492;
    genVar3492=results.toArray();
    historyList.setListData(genVar3492);
  }
  private void initializeWaypoints(){
    try {
      int i=1;
      for (; i < 5; i++) {
        com.fray.evo.EcState genVar3493;
        genVar3493=ec.getInternalDestination();
        java.lang.Object genVar3494;
        genVar3494=genVar3493.clone();
        EcState state;
        state=(EcState)genVar3494;
        int genVar3495;
        genVar3495=3;
        int genVar3496;
        genVar3496=i * genVar3495;
        int genVar3497;
        genVar3497=(genVar3496);
        int genVar3498;
        genVar3498=60;
        state.targetSeconds=genVar3497 * genVar3498;
        destination.add(state);
      }
      com.fray.evo.EcState genVar3499;
      genVar3499=ec.getInternalDestination();
      java.lang.Object genVar3500;
      genVar3500=genVar3499.clone();
      com.fray.evo.EcState genVar3501;
      genVar3501=(EcState)genVar3500;
      destination.add(genVar3501);
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3502;
      genVar3502=new PrintWriter(sw);
      e.printStackTrace(genVar3502);
      java.lang.String genVar3503;
      genVar3503=sw.toString();
      logger.severe(genVar3503);
    }
  }
  private void addStats(  JPanel stats){
    statsText=new JTextArea();
    stats.add(statsText);
    boolean genVar3505;
    genVar3505=false;
    statsText.setEditable(genVar3505);
    int genVar3506;
    genVar3506=0;
    statsText.setAlignmentX(genVar3506);
    int genVar3507;
    genVar3507=0;
    statsText.setAlignmentY(genVar3507);
    int genVar3508;
    genVar3508=4;
    statsText.setTabSize(genVar3508);
  }
  private void addStatusBar(  JPanel leftbottom){
    statusbar=new JXStatusBar();
    java.lang.String genVar3509;
    genVar3509="status.ready";
    java.lang.String genVar3510;
    genVar3510=messages.getString(genVar3509);
    status1=new JLabel(genVar3510);
    statusbar.add(status1);
    java.lang.String genVar3511;
    genVar3511="status.notRunning";
    java.lang.String genVar3512;
    genVar3512=messages.getString(genVar3511);
    status2=new JLabel(genVar3512);
    statusbar.add(status2);
    java.lang.String genVar3513;
    genVar3513="";
    status3=new JLabel(genVar3513);
    statusbar.add(status3);
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.anchor=GridBagConstraints.SOUTH;
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx=.5;
    gridBagConstraints.gridwidth=4;
    int genVar3514;
    genVar3514=1;
    gridBagConstraints.gridy=gridy + genVar3514;
    int genVar3515;
    genVar3515=1;
    int genVar3516;
    genVar3516=1;
    int genVar3517;
    genVar3517=1;
    int genVar3518;
    genVar3518=1;
    gridBagConstraints.insets=new Insets(genVar3515,genVar3516,genVar3517,genVar3518);
    leftbottom.add(statusbar,gridBagConstraints);
    int genVar3519;
    genVar3519=200;
    ActionListener genVar3520;
    genVar3520=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        if (timeStarted == 0)         status1.setText(messages.getString("status.ready"));
 else {
          long ms=new Date().getTime() - timeStarted;
          long seconds=ms / 1000;
          long minutes=seconds / 60;
          long hours=minutes / 60;
          status1.setText(messages.getString("status.running",hours % 60,minutes % 60,seconds % 60));
        }
        if (lastUpdate != 0) {
          long ms=new Date().getTime() - lastUpdate;
          long seconds=ms / 1000;
          long minutes=seconds / 60;
          long hours=minutes / 60;
          status2.setText(messages.getString("status.lastUpdate",hours % 60,minutes % 60,seconds % 60));
{
            double evaluations=ec.getGamesPlayed();
            double evalseconds=(System.currentTimeMillis() - timeStarted);
            evalseconds=evalseconds / 1000.0;
            double permsPerSecond=evaluations;
            permsPerSecond/=evalseconds;
            StringBuilder stats=new StringBuilder();
            int threadIndex=0;
            stats.append(messages.getString("stats.gamesPlayed",evaluations / 1000));
            stats.append("\n").append(messages.getString("stats.maxBuildOrderLength",ec.getChromosomeLength()));
            stats.append("\n").append(messages.getString("stats.stagnationLimit",ec.getStagnationLimit()));
            stats.append("\n").append(messages.getString("stats.gamesPlayedPerSec",(int)permsPerSecond));
            stats.append("\n").append(messages.getString("stats.mutationRate",ec.getBaseMutationRate() / ec.getChromosomeLength()));
            for (            Double d : ec.getBestScores())             stats.append("\n").append(messages.getString("stats.processor",threadIndex,ec.getEvolutionsSinceDiscovery(threadIndex++),d));
            statsText.setText(stats.toString());
          }
        }
        statusbar.repaint();
      }
    }
;
    Timer t;
    t=new Timer(genVar3519,genVar3520);
    t.start();
  }
  private void addOutputContainer(  JPanel component){
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.fill=GridBagConstraints.BOTH;
    gridBagConstraints.weighty=1;
    gridBagConstraints.gridy=0;
    gridBagConstraints.gridwidth=4;
    int genVar3521;
    genVar3521=1;
    int genVar3522;
    genVar3522=1;
    int genVar3523;
    genVar3523=1;
    int genVar3524;
    genVar3524=1;
    gridBagConstraints.insets=new Insets(genVar3521,genVar3522,genVar3523,genVar3524);
    outputText=new JTextArea();
    javax.swing.JScrollPane genVar3526;
    genVar3526=new JScrollPane(outputText);
    component.add(genVar3526,gridBagConstraints);
    int genVar3527;
    genVar3527=0;
    outputText.setAlignmentX(genVar3527);
    int genVar3528;
    genVar3528=0;
    outputText.setAlignmentY(genVar3528);
    int genVar3529;
    genVar3529=4;
    outputText.setTabSize(genVar3529);
    boolean genVar3530;
    genVar3530=false;
    outputText.setEditable(genVar3530);
    boolean genVar3531;
    genVar3531=true;
    outputText.setLineWrap(genVar3531);
    java.lang.String genVar3532;
    genVar3532="welcome";
    String welcome;
    welcome=messages.getString(genVar3532);
    simpleBuildOrder=welcome;
    detailedBuildOrder=welcome;
    outputText.setText(welcome);
  }
  void removeTab(  PanelWayPoint wayPoint){
    java.awt.Component[] genVar3533;
    genVar3533=wayPoint.getComponents();
    java.util.List<java.awt.Component> genVar3534;
    genVar3534=Arrays.asList(genVar3533);
    inputControls.removeAll(genVar3534);
    int selectedIndex;
    selectedIndex=tabPane.getSelectedIndex();
    com.fray.evo.EcState genVar3535;
    genVar3535=wayPoint.getState();
    destination.remove(genVar3535);
    waypointPanels.remove(wayPoint);
    tabPane.removeAll();
    java.lang.String genVar3433;
    genVar3433="tabs.history";
    java.lang.String genVar3434;
    genVar3434=messages.getString(genVar3433);
    tabPane.addTab(genVar3434,historyPanel);
    int i=0;
    for (; i < waypointPanels.size() - 1; i++) {
      java.lang.String genVar3435;
      genVar3435="tabs.waypoint";
      java.lang.String genVar3436;
      genVar3436=messages.getString(genVar3435,i);
      javax.swing.JPanel genVar3437;
      genVar3437=waypointPanels.get(i);
      tabPane.addTab(genVar3436,genVar3437);
    }
    java.lang.String genVar3438;
    genVar3438="tabs.waypoint";
    java.lang.String genVar3439;
    genVar3439="+";
    java.lang.String genVar3440;
    genVar3440=messages.getString(genVar3438,genVar3439);
    tabPane.addTab(genVar3440,newWaypointPanel);
    java.lang.String genVar3441;
    genVar3441="tabs.final";
    java.lang.String genVar3442;
    genVar3442=messages.getString(genVar3441);
    int genVar3443;
    genVar3443=waypointPanels.size();
    int genVar3444;
    genVar3444=1;
    int genVar3445;
    genVar3445=genVar3443 - genVar3444;
    javax.swing.JPanel genVar3446;
    genVar3446=waypointPanels.get(genVar3445);
    tabPane.addTab(genVar3442,genVar3446);
    java.lang.String genVar3447;
    genVar3447="tabs.stats";
    java.lang.String genVar3448;
    genVar3448=messages.getString(genVar3447);
    tabPane.addTab(genVar3448,statsPanel);
    java.lang.String genVar3449;
    genVar3449="tabs.settings";
    java.lang.String genVar3450;
    genVar3450=messages.getString(genVar3449);
    tabPane.addTab(genVar3450,settingsPanel);
    int genVar3537;
    genVar3537=0;
    boolean genVar3538;
    genVar3538=selectedIndex > genVar3537;
    if (genVar3538) {
      int genVar3539;
      genVar3539=1;
      int genVar3540;
      genVar3540=selectedIndex - genVar3539;
      tabPane.setSelectedIndex(genVar3540);
    }
 else {
      ;
    }
  }
  private void readDestinations(){
    for (    JComponent component : inputControls) {
      boolean genVar3541;
      genVar3541=component instanceof JTextField;
      if (genVar3541) {
        javax.swing.JTextField genVar3542;
        genVar3542=(JTextField)component;
        JTextField genVar3543;
        genVar3543=(genVar3542);
        java.awt.event.ActionListener[] genVar3544;
        genVar3544=genVar3543.getActionListeners();
        int genVar3545;
        genVar3545=0;
        ActionListener actionListener;
        actionListener=genVar3544[genVar3545];
        boolean genVar3546;
        genVar3546=actionListener instanceof CustomActionListener;
        if (genVar3546) {
          com.fray.evo.ui.swingx.CustomActionListener genVar3547;
          genVar3547=(CustomActionListener)actionListener;
          CustomActionListener genVar3548;
          genVar3548=(genVar3547);
          genVar3548.reverse(component);
        }
 else {
          ;
        }
      }
 else {
        boolean genVar3549;
        genVar3549=component instanceof JCheckBox;
        if (genVar3549) {
          javax.swing.JCheckBox genVar3550;
          genVar3550=(JCheckBox)component;
          JCheckBox genVar3551;
          genVar3551=(genVar3550);
          java.awt.event.ActionListener[] genVar3552;
          genVar3552=genVar3551.getActionListeners();
          int genVar3553;
          genVar3553=0;
          ActionListener actionListener;
          actionListener=genVar3552[genVar3553];
          boolean genVar3554;
          genVar3554=actionListener instanceof CustomActionListener;
          if (genVar3554) {
            com.fray.evo.ui.swingx.CustomActionListener genVar3555;
            genVar3555=(CustomActionListener)actionListener;
            CustomActionListener genVar3556;
            genVar3556=(genVar3555);
            genVar3556.reverse(component);
          }
 else {
            ;
          }
        }
 else {
          ;
        }
      }
    }
  }
  private void addOutputButtons(  JPanel component){
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridy=1;
    gridBagConstraints.gridwidth=1;
    int genVar3557;
    genVar3557=1;
    int genVar3558;
    genVar3558=1;
    int genVar3559;
    genVar3559=1;
    int genVar3560;
    genVar3560=1;
    gridBagConstraints.insets=new Insets(genVar3557,genVar3558,genVar3559,genVar3560);
    gridBagConstraints.weightx=0.25;
    java.lang.String genVar3561;
    genVar3561="copyToClipboard";
    java.lang.String genVar3562;
    genVar3562=messages.getString(genVar3561);
    clipboardButton=new JButton(genVar3562);
    component.add(clipboardButton,gridBagConstraints);
    ActionListener genVar3563;
    genVar3563=new ActionListener(){
      public void actionPerformed(      ActionEvent e){
        Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(outputText.getText()),null);
      }
    }
;
    clipboardButton.addActionListener(genVar3563);
    java.lang.String genVar3564;
    genVar3564="detailedFormat";
    java.lang.String genVar3565;
    genVar3565=messages.getString(genVar3564);
    switchDetailedButton=new JButton(genVar3565);
    isDetailedBuildOrder=true;
    gridBagConstraints.weightx=0.25;
    component.add(switchDetailedButton,gridBagConstraints);
    ActionListener genVar3566;
    genVar3566=new ActionListener(){
      public void actionPerformed(      ActionEvent e){
        outputText.setText(detailedBuildOrder);
        outputText.setTabSize(4);
        isDetailedBuildOrder=true;
        isYabotBuildOrder=false;
        isSimpleBuildOrder=false;
      }
    }
;
    switchDetailedButton.addActionListener(genVar3566);
    java.lang.String genVar3567;
    genVar3567="simpleFormat";
    java.lang.String genVar3568;
    genVar3568=messages.getString(genVar3567);
    switchSimpleButton=new JButton(genVar3568);
    isSimpleBuildOrder=false;
    gridBagConstraints.weightx=0.25;
    component.add(switchSimpleButton,gridBagConstraints);
    ActionListener genVar3569;
    genVar3569=new ActionListener(){
      public void actionPerformed(      ActionEvent e){
        outputText.setText(simpleBuildOrder);
        outputText.setTabSize(14);
        isSimpleBuildOrder=true;
        isYabotBuildOrder=false;
        isDetailedBuildOrder=false;
      }
    }
;
    switchSimpleButton.addActionListener(genVar3569);
    java.lang.String genVar3570;
    genVar3570="yabotFormat";
    java.lang.String genVar3571;
    genVar3571=messages.getString(genVar3570);
    switchYabotButton=new JButton(genVar3571);
    isYabotBuildOrder=false;
    gridBagConstraints.weightx=0.25;
    component.add(switchYabotButton,gridBagConstraints);
    ActionListener genVar3572;
    genVar3572=new ActionListener(){
      public void actionPerformed(      ActionEvent e){
        outputText.setText(yabotBuildOrder);
        outputText.setTabSize(14);
        isYabotBuildOrder=true;
        isSimpleBuildOrder=false;
        isDetailedBuildOrder=false;
      }
    }
;
    switchYabotButton.addActionListener(genVar3572);
  }
  private void addControlParts(  JPanel component){
    java.lang.String genVar3573;
    genVar3573="en";
    java.util.Locale genVar3574;
    genVar3574=new Locale(genVar3573);
    java.lang.String genVar3575;
    genVar3575="ko";
    java.util.Locale genVar3576;
    genVar3576=new Locale(genVar3575);
    java.lang.String genVar3577;
    genVar3577="de";
    java.util.Locale genVar3578;
    genVar3578=new Locale(genVar3577);
    java.lang.String genVar3579;
    genVar3579="es";
    java.util.Locale genVar3580;
    genVar3580=new Locale(genVar3579);
    java.lang.String genVar3581;
    genVar3581="fr";
    java.util.Locale genVar3582;
    genVar3582=new Locale(genVar3581);
    java.util.Locale[] genVar3583;
    genVar3583=new Locale[]{genVar3574,genVar3574,genVar3576,genVar3576,genVar3578,genVar3578,genVar3580,genVar3580,genVar3582,genVar3582};
    java.util.Locale genVar3584;
    genVar3584=messages.getLocale();
    localeComboBox=new LocaleComboBox(genVar3583,genVar3584);
    ActionListener genVar3585;
    genVar3585=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent event){
        Locale selected=localeComboBox.getSelectedLocale();
        Locale current=messages.getLocale();
        if (selected.getLanguage().equals(current.getLanguage()) && (current.getCountry() == null || selected.getCountry().equals(current.getCountry()))) {
          return;
        }
        messages.changeLocale(selected);
        userSettings.setLocale(selected);
        final JFrame newFrame=new JFrame();
        EcSwingXMain.mainWindow=newFrame;
        newFrame.setTitle(messages.getString("title",EvolutionChamber.VERSION));
        newFrame.setDefaultCloseOperation(frame.getDefaultCloseOperation());
        newFrame.getContentPane().add(new EcSwingX(newFrame));
        newFrame.addWindowListener(new WindowAdapter(){
          @Override public void windowClosing(          WindowEvent windowevent){
            int currentExtendedState=newFrame.getExtendedState();
            if (currentExtendedState != JFrame.NORMAL)             newFrame.setExtendedState(JFrame.NORMAL);
            Dimension currentSize=frame.getSize();
            userSettings.setWindowExtensionState(currentExtendedState);
            userSettings.setWindowSize(currentSize);
          }
        }
);
        newFrame.setPreferredSize(frame.getPreferredSize());
        newFrame.setIconImage(frame.getIconImage());
        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        frame.dispose();
        newFrame.setVisible(true);
      }
    }
;
    localeComboBox.addActionListener(genVar3585);
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.anchor=GridBagConstraints.WEST;
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx=.25;
    gridBagConstraints.gridy=gridy;
    gridBagConstraints.gridwidth=1;
    int genVar3586;
    genVar3586=1;
    int genVar3587;
    genVar3587=1;
    int genVar3588;
    genVar3588=1;
    int genVar3589;
    genVar3589=1;
    gridBagConstraints.insets=new Insets(genVar3586,genVar3587,genVar3588,genVar3589);
    component.add(localeComboBox,gridBagConstraints);
    gridy++;
    EcSwingX genVar3590;
    genVar3590=this;
    java.lang.String genVar3591;
    genVar3591="processors";
    java.lang.String genVar3592;
    genVar3592=messages.getString(genVar3591);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3593;
    genVar3593=NumberTextField.class;
    CustomActionListener genVar3594;
    genVar3594=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        ec.setThreads(getDigit(e));
        ((JTextField)e.getSource()).setText(Integer.toString(ec.getThreads()));
      }
      void reverse(      Object o){
        ((JTextField)o).setText(Integer.toString(ec.getThreads()));
      }
    }
;
    javax.swing.JTextField genVar3595;
    genVar3595=genVar3590.addInput(component,genVar3592,genVar3593,genVar3594);
    int genVar3596;
    genVar3596=ec.getThreads();
    java.lang.String genVar3597;
    genVar3597=Integer.toString(genVar3596);
    genVar3595.setText(genVar3597);
    EcSwingX genVar3598;
    genVar3598=this;
    java.lang.String genVar3599;
    genVar3599="stop";
    java.lang.String genVar3600;
    genVar3600=messages.getString(genVar3599);
    ActionListener genVar3601;
    genVar3601=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        ec.stopAllThreads();
        running=false;
        goButton.setEnabled(true);
        stopButton.setEnabled(false);
        historyList.setEnabled(true);
        localeComboBox.setEnabled(true);
        timeStarted=0;
        for (        JComponent j : inputControls)         j.setEnabled(true);
        lastUpdate=0;
        refreshHistory();
      }
    }
;
    stopButton=genVar3598.addButton(component,genVar3600,genVar3601);
    boolean genVar3602;
    genVar3602=false;
    stopButton.setEnabled(genVar3602);
    EcSwingX genVar3603;
    genVar3603=this;
    java.lang.String genVar3604;
    genVar3604="start";
    java.lang.String genVar3605;
    genVar3605=messages.getString(genVar3604);
    ActionListener genVar3606;
    genVar3606=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        running=true;
        for (        JComponent j : inputControls)         j.setEnabled(false);
        restartChamber();
        tabPane.setSelectedComponent(statsPanel);
        timeStarted=new Date().getTime();
        goButton.setEnabled(false);
        stopButton.setEnabled(true);
        historyList.setEnabled(false);
        localeComboBox.setEnabled(false);
      }
    }
;
    goButton=genVar3603.addButton(component,genVar3605,genVar3606);
    gridy++;
  }
  private JButton addButton(  JPanel container,  String string,  ActionListener actionListener){
    JButton button;
    button=new JButton();
    button.setText(string);
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.anchor=GridBagConstraints.WEST;
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx=.25;
    gridBagConstraints.gridy=gridy;
    gridBagConstraints.gridwidth=1;
    int genVar3607;
    genVar3607=1;
    int genVar3608;
    genVar3608=1;
    int genVar3609;
    genVar3609=1;
    int genVar3610;
    genVar3610=1;
    gridBagConstraints.insets=new Insets(genVar3607,genVar3608,genVar3609,genVar3610);
    container.add(button,gridBagConstraints);
    button.addActionListener(actionListener);
    return button;
  }
  private JLabel addLabel(  JPanel container,  String string){
    JLabel label;
    label=new JLabel();
    GridBagConstraints gridBagConstraints;
    label.setText(string);
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx=0.5;
    gridBagConstraints.gridwidth=2;
    gridBagConstraints.gridy=gridy;
    int genVar3611;
    genVar3611=1;
    int genVar3612;
    genVar3612=1;
    int genVar3613;
    genVar3613=1;
    int genVar3614;
    genVar3614=1;
    gridBagConstraints.insets=new Insets(genVar3611,genVar3612,genVar3613,genVar3614);
    container.add(label,gridBagConstraints);
    return label;
  }
  JButton addButton(  JPanel container,  String string,  int gridwidth,  ActionListener actionListener){
    JButton button;
    button=new JButton();
    button.setText(string);
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.anchor=GridBagConstraints.WEST;
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx=.25;
    gridBagConstraints.gridy=gridy;
    gridBagConstraints.gridwidth=gridwidth;
    int genVar3615;
    genVar3615=1;
    int genVar3616;
    genVar3616=1;
    int genVar3617;
    genVar3617=1;
    int genVar3618;
    genVar3618=1;
    gridBagConstraints.insets=new Insets(genVar3615,genVar3616,genVar3617,genVar3618);
    container.add(button,gridBagConstraints);
    button.addActionListener(actionListener);
    return button;
  }
  static int getDigit(  ActionEvent e){
    java.lang.Object genVar3619;
    genVar3619=e.getSource();
    JTextField tf;
    tf=(JTextField)genVar3619;
    String text;
    text=tf.getText();
    try {
      java.lang.String genVar3620;
      genVar3620=":";
      boolean genVar3621;
      genVar3621=text.contains(genVar3620);
      if (genVar3621) {
        java.lang.String genVar3622;
        genVar3622=":";
        String[] split;
        split=text.split(genVar3622);
        int genVar3623;
        genVar3623=0;
        java.lang.String genVar3624;
        genVar3624=split[genVar3623];
        int genVar3625;
        genVar3625=Integer.parseInt(genVar3624);
        int genVar3626;
        genVar3626=0;
        boolean genVar3627;
        genVar3627=genVar3625 < genVar3626;
        if (genVar3627) {
          java.lang.NumberFormatException genVar3628;
          genVar3628=new NumberFormatException();
          throw genVar3628;
        }
 else {
          ;
        }
        int genVar3629;
        genVar3629=1;
        java.lang.String genVar3630;
        genVar3630=split[genVar3629];
        int genVar3631;
        genVar3631=Integer.parseInt(genVar3630);
        int genVar3632;
        genVar3632=0;
        boolean genVar3633;
        genVar3633=genVar3631 < genVar3632;
        if (genVar3633) {
          java.lang.NumberFormatException genVar3634;
          genVar3634=new NumberFormatException();
          throw genVar3634;
        }
 else {
          ;
        }
        int genVar3635;
        genVar3635=0;
        java.lang.String genVar3636;
        genVar3636=split[genVar3635];
        int genVar3637;
        genVar3637=Integer.parseInt(genVar3636);
        int genVar3638;
        genVar3638=60;
        int genVar3639;
        genVar3639=genVar3637 * genVar3638;
        int genVar3640;
        genVar3640=1;
        java.lang.String genVar3641;
        genVar3641=split[genVar3640];
        int genVar3642;
        genVar3642=Integer.parseInt(genVar3641);
        int genVar3643;
        genVar3643=genVar3639 + genVar3642;
        return genVar3643;
      }
 else {
        ;
      }
      Integer i;
      i=Integer.parseInt(text);
      int genVar3644;
      genVar3644=0;
      boolean genVar3645;
      genVar3645=i < genVar3644;
      if (genVar3645) {
        java.lang.NumberFormatException genVar3646;
        genVar3646=new NumberFormatException();
        throw genVar3646;
      }
 else {
        ;
      }
      return i;
    }
 catch (    ArrayIndexOutOfBoundsException ex) {
      java.lang.String genVar3647;
      genVar3647="0";
      tf.setText(genVar3647);
      int genVar3648;
      genVar3648=0;
      return genVar3648;
    }
catch (    NumberFormatException ex) {
      java.lang.String genVar3649;
      genVar3649="0";
      tf.setText(genVar3649);
      int genVar3650;
      genVar3650=0;
      return genVar3650;
    }
  }
  private void restartChamber(){
    boolean genVar3651;
    genVar3651=ec.isRunning();
    if (genVar3651) {
      ec.stopAllThreads();
    }
 else {
      ;
    }
    try {
      EcSwingX genVar3652;
      genVar3652=this;
      EcState finalDestination;
      finalDestination=genVar3652.collapseWaypoints();
      ec.setDestination(finalDestination);
      ec.go();
    }
 catch (    InvalidConfigurationException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3653;
      genVar3653=new PrintWriter(sw);
      e.printStackTrace(genVar3653);
      java.lang.String genVar3654;
      genVar3654=sw.toString();
      logger.severe(genVar3654);
    }
catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3655;
      genVar3655=new PrintWriter(sw);
      e.printStackTrace(genVar3655);
      java.lang.String genVar3656;
      genVar3656=sw.toString();
      logger.severe(genVar3656);
    }
  }
  private EcState collapseWaypoints() throws CloneNotSupportedException {
    int genVar3657;
    genVar3657=destination.size();
    int genVar3658;
    genVar3658=1;
    int genVar3659;
    genVar3659=genVar3657 - genVar3658;
    com.fray.evo.EcState genVar3660;
    genVar3660=destination.get(genVar3659);
    java.lang.Object genVar3661;
    genVar3661=genVar3660.clone();
    EcState finalDestination;
    finalDestination=(EcState)genVar3661;
    int i=0;
    for (; i < destination.size() - 1; i++) {
      com.fray.evo.EcState genVar3662;
      genVar3662=destination.get(i);
      int genVar3663;
      genVar3663=genVar3662.getEstimatedActions();
      int genVar3664;
      genVar3664=1;
      boolean genVar3665;
      genVar3665=genVar3663 > genVar3664;
      if (genVar3665) {
        com.fray.evo.EcState genVar3666;
        genVar3666=destination.get(i);
        java.lang.Object genVar3667;
        genVar3667=genVar3666.clone();
        com.fray.evo.EcState genVar3668;
        genVar3668=(EcState)genVar3667;
        finalDestination.waypoints.add(genVar3668);
      }
 else {
        ;
      }
    }
    return finalDestination;
  }
  private void expandWaypoints(  EcState s){
    try {
      destination.clear();
      java.lang.Object genVar3669;
      genVar3669=s.clone();
      EcState finalDestination;
      finalDestination=(EcState)genVar3669;
      finalDestination.waypoints.clear();
      int i=0;
      for (; i < s.waypoints.size(); i++) {
        com.fray.evo.EcState genVar3670;
        genVar3670=s.waypoints.get(i);
        java.lang.Object genVar3671;
        genVar3671=genVar3670.clone();
        com.fray.evo.EcState genVar3672;
        genVar3672=(EcState)genVar3671;
        destination.add(genVar3672);
      }
      destination.add(finalDestination);
      for (      JPanel p : waypointPanels) {
        java.awt.Component[] genVar3673;
        genVar3673=p.getComponents();
        java.util.List<java.awt.Component> genVar3674;
        genVar3674=Arrays.asList(genVar3673);
        inputControls.removeAll(genVar3674);
      }
      waypointPanels.clear();
      for (      EcState aDestination : destination) {
        EcSwingX genVar3675;
        genVar3675=this;
        boolean genVar3676;
        genVar3676=false;
        genVar3675.addWaypointPanel(aDestination,genVar3676);
      }
      tabPane.removeAll();
      java.lang.String genVar3433;
      genVar3433="tabs.history";
      java.lang.String genVar3434;
      genVar3434=messages.getString(genVar3433);
      tabPane.addTab(genVar3434,historyPanel);
      i=0;
      for (; i < waypointPanels.size() - 1; i++) {
        java.lang.String genVar3435;
        genVar3435="tabs.waypoint";
        java.lang.String genVar3436;
        genVar3436=messages.getString(genVar3435,i);
        javax.swing.JPanel genVar3437;
        genVar3437=waypointPanels.get(i);
        tabPane.addTab(genVar3436,genVar3437);
      }
      java.lang.String genVar3438;
      genVar3438="tabs.waypoint";
      java.lang.String genVar3439;
      genVar3439="+";
      java.lang.String genVar3440;
      genVar3440=messages.getString(genVar3438,genVar3439);
      tabPane.addTab(genVar3440,newWaypointPanel);
      java.lang.String genVar3441;
      genVar3441="tabs.final";
      java.lang.String genVar3442;
      genVar3442=messages.getString(genVar3441);
      int genVar3443;
      genVar3443=waypointPanels.size();
      int genVar3444;
      genVar3444=1;
      int genVar3445;
      genVar3445=genVar3443 - genVar3444;
      javax.swing.JPanel genVar3446;
      genVar3446=waypointPanels.get(genVar3445);
      tabPane.addTab(genVar3442,genVar3446);
      java.lang.String genVar3447;
      genVar3447="tabs.stats";
      java.lang.String genVar3448;
      genVar3448=messages.getString(genVar3447);
      tabPane.addTab(genVar3448,statsPanel);
      java.lang.String genVar3449;
      genVar3449="tabs.settings";
      java.lang.String genVar3450;
      genVar3450=messages.getString(genVar3449);
      tabPane.addTab(genVar3450,settingsPanel);
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar3678;
      genVar3678=new PrintWriter(sw);
      e.printStackTrace(genVar3678);
      java.lang.String genVar3679;
      genVar3679=sw.toString();
      logger.severe(genVar3679);
    }
  }
  private PanelWayPoint addWaypointPanel(  EcState dest,  boolean isNew){
    com.fray.evo.ui.swingx.EcSwingX genVar3680;
    genVar3680=this;
    PanelWayPoint p;
    p=new PanelWayPoint(genVar3680,dest);
    if (isNew) {
      int genVar3681;
      genVar3681=waypointPanels.size();
      int genVar3682;
      genVar3682=1;
      int genVar3683;
      genVar3683=genVar3681 - genVar3682;
      waypointPanels.add(genVar3683,p);
    }
 else {
      waypointPanels.add(p);
    }
    return p;
  }
  protected String getSelected(  ActionEvent e){
    java.lang.Object genVar3684;
    genVar3684=e.getSource();
    JRadioButton radioButton;
    radioButton=(JRadioButton)genVar3684;
    java.lang.String genVar3685;
    genVar3685=radioButton.getText();
    return genVar3685;
  }
  protected boolean getTrue(  ActionEvent e){
    java.lang.Object genVar3686;
    genVar3686=e.getSource();
    JCheckBox tf;
    tf=(JCheckBox)genVar3686;
    boolean genVar3687;
    genVar3687=tf.isSelected();
    return genVar3687;
  }
  JTextField addInput(  JPanel container,  String name,  Class<? extends JTextField> clazz,  final CustomActionListener a){
    try {
      GridBagConstraints gridBagConstraints;
      JTextField genVar3688;
      genVar3688=clazz.newInstance();
      JTextField textField;
      textField=(JTextField)genVar3688;
      int genVar3689;
      genVar3689=5;
      textField.setColumns(genVar3689);
      java.lang.String genVar3690;
      genVar3690="0";
      textField.setText(genVar3690);
      textField.addActionListener(a);
      FocusListener genVar3691;
      genVar3691=new FocusListener(){
        @Override public void focusLost(        FocusEvent e){
          a.actionPerformed(new ActionEvent(e.getSource(),0,"changed"));
        }
        @Override public void focusGained(        FocusEvent e){
        }
      }
;
      textField.addFocusListener(genVar3691);
      java.lang.String genVar3692;
      genVar3692="  ";
      java.lang.String genVar3693;
      genVar3693=genVar3692 + name;
      JXLabel label;
      label=new JXLabel(genVar3693);
      float genVar3694;
      genVar3694=.5f;
      label.setAlignmentX(genVar3694);
      gridBagConstraints=new GridBagConstraints();
      gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
      gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
      gridBagConstraints.weightx=.25;
      gridBagConstraints.gridy=gridy;
      int genVar3695;
      genVar3695=1;
      int genVar3696;
      genVar3696=1;
      int genVar3697;
      genVar3697=1;
      int genVar3698;
      genVar3698=1;
      gridBagConstraints.insets=new Insets(genVar3695,genVar3696,genVar3697,genVar3698);
      container.add(label,gridBagConstraints);
      inputControls.add(label);
      gridBagConstraints=new GridBagConstraints();
      gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
      gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx=.25;
      gridBagConstraints.gridy=gridy;
      int genVar3699;
      genVar3699=1;
      int genVar3700;
      genVar3700=1;
      int genVar3701;
      genVar3701=1;
      int genVar3702;
      genVar3702=1;
      gridBagConstraints.insets=new Insets(genVar3699,genVar3700,genVar3701,genVar3702);
      container.add(textField,gridBagConstraints);
      inputControls.add(textField);
      return textField;
    }
 catch (    Exception e) {
      java.lang.String genVar3703;
      genVar3703="Error creating input field object.";
      logger.log(Level.SEVERE,genVar3703,e);
      return null;
    }
  }
  @Override public void bestScore(  final EcState finalState,  int intValue,  final String detailedText,  final String simpleText,  final String yabotText){
    Runnable genVar3704;
    genVar3704=new Runnable(){
      @Override public void run(){
        receiveBuildOrders(detailedText,simpleText,yabotText);
        lastUpdate=new Date().getTime();
      }
    }
;
    SwingUtilities.invokeLater(genVar3704);
  }
  private void receiveBuildOrders(  final String detailedText,  final String simpleText,  final String yabotText){
    simpleBuildOrder=simpleText;
    detailedBuildOrder=detailedText;
    yabotBuildOrder=yabotText;
    if (isSimpleBuildOrder) {
      outputText.setText(simpleText);
    }
 else {
      if (isYabotBuildOrder) {
        outputText.setText(yabotBuildOrder);
      }
 else {
        outputText.setText(detailedText);
      }
    }
  }
  @Override public void threadScore(  int threadIndex,  String output){
  }
}
