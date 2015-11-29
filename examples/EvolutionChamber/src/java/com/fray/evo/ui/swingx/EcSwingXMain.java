package com.fray.evo.ui.swingx;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.util.EcAutoUpdate;
import com.fray.evo.util.EcMessages;
/** 
 * Contains the main method to launch the GUI application.
 * @author mike.angstadt
 */
public class EcSwingXMain {
  private static final Logger logger=Logger.getLogger(EcSwingXMain.class.getName());
  /** 
 * I18n messages.
 */
  public static final EcMessages messages=new EcMessages("com/fray/evo/ui/swingx/messages");
  /** 
 * The directory that stores the user's configuration files.
 */
  public static final File userConfigDir;
  /** 
 * The user's settings.
 */
  public static final UserSettings userSettings;
static {
    java.lang.String genVar4063;
    genVar4063="user.home";
    java.lang.String genVar4064;
    genVar4064=System.getProperty(genVar4063);
    java.lang.String genVar4065;
    genVar4065=".evolutionchamber";
    userConfigDir=new File(genVar4064,genVar4065);
    userConfigDir.mkdir();
    java.lang.String genVar4066;
    genVar4066="settings.properties";
    java.io.File genVar4067;
    genVar4067=new File(userConfigDir,genVar4066);
    userSettings=new UserSettings(genVar4067);
  }
  /** 
 * The classpath location of the icon.
 */
  public static final String iconLocation="/com/fray/evo/ui/swingx/evolution_chamber.png";
  public static JFrame mainWindow;
  public static void main(  String args[]){
    try {
      java.util.logging.LogManager genVar4068;
      genVar4068=LogManager.getLogManager();
      java.lang.Class<com.fray.evo.ui.swingx.EcSwingXMain> genVar4069;
      genVar4069=EcSwingXMain.class;
      java.lang.String genVar4070;
      genVar4070="logging.properties";
      java.io.InputStream genVar4071;
      genVar4071=genVar4069.getResourceAsStream(genVar4070);
      genVar4068.readConfiguration(genVar4071);
    }
 catch (    Exception e) {
      e.printStackTrace();
    }
    Locale locale;
    locale=userSettings.getLocale();
    boolean genVar4072;
    genVar4072=locale != null;
    if (genVar4072) {
      messages.changeLocale(locale);
    }
 else {
      ;
    }
    java.lang.String genVar4073;
    genVar4073="title";
    java.lang.String genVar4074;
    genVar4074=messages.getString(genVar4073,EvolutionChamber.VERSION);
    boolean genVar4075;
    genVar4075=false;
    MacHandler genVar4076;
    genVar4076=new MacHandler(){
      @Override public void handleQuit(      Object applicationEvent){
        int currentExtendedState=mainWindow.getExtendedState();
        if (currentExtendedState != JFrame.NORMAL)         mainWindow.setExtendedState(JFrame.NORMAL);
        Dimension currentSize=mainWindow.getSize();
        userSettings.setWindowExtensionState(currentExtendedState);
        userSettings.setWindowSize(currentSize);
        System.exit(0);
      }
      @Override public void handleAbout(      Object applicationEvent){
        JOptionPane.showMessageDialog(null,messages.getString("about.message",EvolutionChamber.VERSION),messages.getString("about.title"),JOptionPane.INFORMATION_MESSAGE);
      }
    }
;
    MacSupport.initIfMac(genVar4074,genVar4075,iconLocation,genVar4076);
    Runnable genVar4077;
    genVar4077=new Runnable(){
      @Override public void run(){
        final JFrame frame=new JFrame();
        mainWindow=frame;
        frame.setTitle(messages.getString("title",EvolutionChamber.VERSION));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new EcSwingX(frame));
        frame.addWindowListener(new WindowAdapter(){
          @Override public void windowClosing(          WindowEvent windowevent){
            int currentExtendedState=frame.getExtendedState();
            if (currentExtendedState != JFrame.NORMAL)             frame.setExtendedState(JFrame.NORMAL);
            Dimension currentSize=frame.getSize();
            userSettings.setWindowExtensionState(currentExtendedState);
            userSettings.setWindowSize(currentSize);
          }
        }
);
        frame.setPreferredSize(calculateOptimalAppSize(1200,850));
        ImageIcon icon=new ImageIcon(EcSwingXMain.class.getResource(iconLocation));
        frame.setIconImage(icon.getImage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(getOptimalExtendedState(frame));
        final JFrame updateFrame=new JFrame();
        updateFrame.setTitle(messages.getString("update.title"));
        updateFrame.setIconImage(icon.getImage());
        JLabel waiting=new JLabel(messages.getString("update.checking"));
        updateFrame.getContentPane().setLayout(new FlowLayout());
        updateFrame.getContentPane().add(waiting);
        updateFrame.setSize(new Dimension(250,70));
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setVisible(true);
        SwingUtilities.invokeLater(new Runnable(){
          @Override public void run(){
            EcAutoUpdate ecUpdater=checkForUpdates();
            frame.setVisible(!ecUpdater.isUpdating());
            updateFrame.dispose();
          }
        }
);
      }
    }
;
    SwingUtilities.invokeLater(genVar4077);
  }
  /** 
 * checks if this JFrame is too big to be viewed on the main screen and returns the recommended extension state if the userSettings contains an preference, this is used as initial value. However, this may get overwritten if the screen resolution changed.
 * @param frame the frame to check
 * @return the state, to tell if this window should be maximized in any direction
 * @see JFrame#setExtendedState(int)
 */
  private static int getOptimalExtendedState(  JFrame frame){
    int extendedState;
    extendedState=frame.getExtendedState();
    Integer userPrefExtensionState;
    userPrefExtensionState=userSettings.getWindowExtensionState();
    boolean genVar4078;
    genVar4078=userPrefExtensionState != null;
    if (genVar4078) {
      extendedState=userPrefExtensionState.intValue();
    }
 else {
      ;
    }
    java.awt.Dimension genVar4079;
    genVar4079=frame.getPreferredSize();
    int width;
    width=genVar4079.width;
    java.awt.Dimension genVar4080;
    genVar4080=frame.getPreferredSize();
    int height;
    height=genVar4080.height;
    java.awt.Toolkit genVar4081;
    genVar4081=Toolkit.getDefaultToolkit();
    Dimension screenDimensions;
    screenDimensions=genVar4081.getScreenSize();
    double genVar4082;
    genVar4082=screenDimensions.getHeight();
    boolean genVar4083;
    genVar4083=genVar4082 <= height;
    if (genVar4083) {
      height=screenDimensions.height;
      extendedState=extendedState | JFrame.MAXIMIZED_VERT;
    }
 else {
      ;
    }
    double genVar4084;
    genVar4084=screenDimensions.getWidth();
    boolean genVar4085;
    genVar4085=genVar4084 <= width;
    if (genVar4085) {
      width=screenDimensions.width;
      extendedState=extendedState | JFrame.MAXIMIZED_HORIZ;
    }
 else {
      ;
    }
    return extendedState;
  }
  /** 
 * fits the app size to the resolution of the users screen if user settings exist, those get used as default value. However, if the screen size is too low, this value is overwritten.
 * @param width the default width
 * @param height the default height
 * @return the preferred values or lower sizes if the screen resolution is too low
 */
  private static Dimension calculateOptimalAppSize(  int width,  int height){
    Dimension userWindowSize;
    userWindowSize=userSettings.getWindowSize();
    boolean genVar4086;
    genVar4086=userWindowSize != null;
    if (genVar4086) {
      width=userWindowSize.width;
      height=userWindowSize.height;
    }
 else {
      ;
    }
    java.awt.Toolkit genVar4087;
    genVar4087=Toolkit.getDefaultToolkit();
    Dimension screenDimensions;
    screenDimensions=genVar4087.getScreenSize();
    double genVar4088;
    genVar4088=screenDimensions.getHeight();
    boolean genVar4089;
    genVar4089=genVar4088 < height;
    if (genVar4089) {
      height=screenDimensions.height;
    }
 else {
      ;
    }
    double genVar4090;
    genVar4090=screenDimensions.getWidth();
    boolean genVar4091;
    genVar4091=genVar4090 < width;
    if (genVar4091) {
      width=screenDimensions.width;
    }
 else {
      ;
    }
    java.awt.Dimension genVar4092;
    genVar4092=new Dimension(width,height);
    return genVar4092;
  }
  private static EcAutoUpdate checkForUpdates(){
    EcAutoUpdate.Callback genVar4093;
    genVar4093=new EcAutoUpdate.Callback(){
      @Override public void checksumFailed(){
        JOptionPane.showMessageDialog(null,messages.getString("update.checksumFailed.message"),messages.getString("update.checksumFailed.title"),JOptionPane.ERROR_MESSAGE);
      }
      @Override public void updateCheckFailed(){
        JOptionPane.showMessageDialog(null,messages.getString("update.updateCheckFailed.message"),messages.getString("update.updateCheckFailed.title"),JOptionPane.WARNING_MESSAGE);
      }
    }
;
    EcAutoUpdate ecUpdater;
    ecUpdater=new EcAutoUpdate(EvolutionChamber.VERSION,genVar4093);
    boolean genVar4094;
    genVar4094=ecUpdater.isUpdateAvailable();
    if (genVar4094) {
      java.lang.String genVar4095;
      genVar4095="update.updateAvailable.message";
      java.lang.String genVar4096;
      genVar4096=messages.getString(genVar4095);
      JOptionPane pane;
      pane=new JOptionPane(genVar4096);
      java.lang.String genVar4097;
      genVar4097="update.updateAvailable.yes";
      String yes;
      yes=messages.getString(genVar4097);
      java.lang.String genVar4098;
      genVar4098="update.updateAvailable.no";
      String no;
      no=messages.getString(genVar4098);
      java.lang.String[] genVar4099;
      genVar4099=new String[]{yes,yes,no,no};
      pane.setOptions(genVar4099);
      javax.swing.JFrame genVar4100;
      genVar4100=new JFrame();
      java.lang.String genVar4101;
      genVar4101="update.updateAvailable.title";
      java.lang.String genVar4102;
      genVar4102=ecUpdater.getLatestVersion();
      java.lang.String genVar4103;
      genVar4103=messages.getString(genVar4101,genVar4102);
      JDialog dialog;
      dialog=pane.createDialog(genVar4100,genVar4103);
      boolean genVar4104;
      genVar4104=true;
      dialog.setVisible(genVar4104);
      Object selection;
      selection=pane.getValue();
      boolean genVar4105;
      genVar4105=selection.equals(yes);
      if (genVar4105) {
        JFrame updateFrame;
        updateFrame=new JFrame();
        java.lang.String genVar4106;
        genVar4106="update.updating.title";
        java.lang.String genVar4107;
        genVar4107=messages.getString(genVar4106);
        updateFrame.setTitle(genVar4107);
        boolean genVar4108;
        genVar4108=false;
        updateFrame.setResizable(genVar4108);
        java.lang.Class<com.fray.evo.ui.swingx.EcSwingXMain> genVar4109;
        genVar4109=EcSwingXMain.class;
        java.net.URL genVar4110;
        genVar4110=genVar4109.getResource(iconLocation);
        ImageIcon icon;
        icon=new ImageIcon(genVar4110);
        java.awt.Image genVar4111;
        genVar4111=icon.getImage();
        updateFrame.setIconImage(genVar4111);
        int genVar4112;
        genVar4112=0;
        int genVar4113;
        genVar4113=100;
        final JProgressBar updateProgress;
        updateProgress=new JProgressBar(genVar4112,genVar4113);
        int genVar4114;
        genVar4114=0;
        updateProgress.setValue(genVar4114);
        boolean genVar4115;
        genVar4115=true;
        updateProgress.setStringPainted(genVar4115);
        updateFrame.add(updateProgress);
        int genVar4116;
        genVar4116=200;
        int genVar4117;
        genVar4117=100;
        java.awt.Dimension genVar4118;
        genVar4118=new Dimension(genVar4116,genVar4117);
        updateFrame.setPreferredSize(genVar4118);
        updateFrame.pack();
        updateFrame.setLocationRelativeTo(null);
        boolean genVar4119;
        genVar4119=true;
        updateFrame.setVisible(genVar4119);
        PropertyChangeListener genVar4120;
        genVar4120=new PropertyChangeListener(){
          public void propertyChange(          PropertyChangeEvent evt){
            if ("progress".equals(evt.getPropertyName())) {
              updateProgress.setValue((Integer)evt.getNewValue());
            }
          }
        }
;
        ecUpdater.addPropertyChangeListener(genVar4120);
        boolean genVar4121;
        genVar4121=true;
        ecUpdater.setUpdating(genVar4121);
        ecUpdater.execute();
      }
 else {
        ;
      }
    }
 else {
      ;
    }
    return ecUpdater;
  }
}
