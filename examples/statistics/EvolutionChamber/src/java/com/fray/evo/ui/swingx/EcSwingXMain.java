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
 * 
 * @author mike.angstadt
 * 
 */
public class EcSwingXMain
{
	private static final Logger logger = Logger.getLogger(EcSwingXMain.class.getName());
	
	/**
	 * I18n messages.
	 */
	public static final EcMessages messages = new EcMessages("com/fray/evo/ui/swingx/messages");
	
	/**
	 * The directory that stores the user's configuration files.
	 */
	public static final File userConfigDir;
	
	/**
	 * The user's settings.
	 */
	public static final UserSettings userSettings;
	
	static{
		userConfigDir = new File(System.getProperty("user.home"), ".evolutionchamber");
		userConfigDir.mkdir();
		userSettings = new UserSettings(new File(userConfigDir, "settings.properties"));
	}

	/**
	 * The classpath location of the icon.
	 */
	public static final String iconLocation = "/com/fray/evo/ui/swingx/evolution_chamber.png";

	public static JFrame mainWindow;

	public static void main(String args[])
	{
		//setup the logger
		try
		{
			LogManager.getLogManager().readConfiguration(EcSwingXMain.class.getResourceAsStream("logging.properties"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//setup i18n
		Locale locale = userSettings.getLocale();
		if (locale != null)
		{
			messages.changeLocale(locale);
		}
		
		//run Mac OS X customizations if user is on a Mac
		//this code must run before *anything* else graphics-related
		MacSupport.initIfMac(messages.getString("title", EvolutionChamber.VERSION), false, iconLocation, new MacHandler()
		{
			@Override
			public void handleQuit(Object applicationEvent)
			{
				// save the window settings on exit
				int currentExtendedState = mainWindow.getExtendedState();
				
				// get the preferred size of the non-maximized view
				if( currentExtendedState != JFrame.NORMAL)
					mainWindow.setExtendedState(JFrame.NORMAL);
				Dimension currentSize = mainWindow.getSize();
				
				userSettings.setWindowExtensionState(currentExtendedState);
				userSettings.setWindowSize(currentSize);
				
				System.exit(0);
			}

			@Override
			public void handleAbout(Object applicationEvent)
			{
				JOptionPane.showMessageDialog(null, messages.getString("about.message", EvolutionChamber.VERSION), messages
						.getString("about.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{

				final JFrame frame = new JFrame();//Y
				mainWindow = frame; //for when a Mac user selects "Quit" from the application menu
				frame.setTitle(messages.getString("title", EvolutionChamber.VERSION));//Y
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Y
				frame.getContentPane().add(new EcSwingX(frame));//Ymm
				
				frame.addWindowListener(new WindowAdapter() {		//Ym		
					@Override
					public void windowClosing(WindowEvent windowevent) {//Ym
						// save the window settings on exit
						int currentExtendedState = frame.getExtendedState();
						
						// get the preferred size of the non-maximized view
						if( currentExtendedState != JFrame.NORMAL)
							frame.setExtendedState(JFrame.NORMAL);
						Dimension currentSize = frame.getSize();//Y
						
						userSettings.setWindowExtensionState(currentExtendedState);
						userSettings.setWindowSize(currentSize);
					}
				});
				
				
				frame.setPreferredSize(calculateOptimalAppSize(1200, 850));//Y
				ImageIcon icon = new ImageIcon(EcSwingXMain.class.getResource(iconLocation));//Y
				frame.setIconImage(icon.getImage());//Y
				frame.pack();//Y
				frame.setLocationRelativeTo(null);
				
				frame.setExtendedState(getOptimalExtendedState(frame));
				
				

				final JFrame updateFrame = new JFrame();//Y
				updateFrame.setTitle(messages.getString("update.title"));//Y
				updateFrame.setIconImage(icon.getImage());//Y
				JLabel waiting = new JLabel(messages.getString("update.checking"));//Ymm
				updateFrame.getContentPane().setLayout(new FlowLayout());//Y
				updateFrame.getContentPane().add(waiting);//Ymm
				updateFrame.setSize(new Dimension(250, 70));//Y
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);//Y

				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						EcAutoUpdate ecUpdater = checkForUpdates();
						// Show the main window only when there are no updates
						// running
						frame.setVisible(!ecUpdater.isUpdating());//Y
						updateFrame.dispose();//Y
					}
				});
			}
		});
	}
	
	/**
	 * checks if this JFrame is too big to be viewed on the main screen and returns the recommended extension state
	 * if the userSettings contains an preference, this is used as initial value. However, this may get overwritten if the screen resolution changed.
	 * @param frame the frame to check
	 * @return the state, to tell if this window should be maximized in any direction
	 * @see JFrame#setExtendedState(int)
	 */
	private static int getOptimalExtendedState(JFrame frame){
		int extendedState = frame.getExtendedState();
		
		Integer userPrefExtensionState = userSettings.getWindowExtensionState();
		if( userPrefExtensionState != null){
			extendedState = userPrefExtensionState.intValue();
		}
		
		
		int width = frame.getPreferredSize().width;
		int height = frame.getPreferredSize().height;
		
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() <= height){
			height = screenDimensions.height;
			extendedState = extendedState | JFrame.MAXIMIZED_VERT;
		}
		
		if( screenDimensions.getWidth() <= width){
			width = screenDimensions.width;
			extendedState = extendedState | JFrame.MAXIMIZED_HORIZ;
		}
		
		return extendedState;
	}
	
	/**
	 * fits the app size to the resolution of the users screen
	 * if user settings exist, those get used as default value. However, if the screen size is too low, this value is overwritten.
	 * 
	 * @param width the default width
	 * @param height the default height
	 * @return the preferred values or lower sizes if the screen resolution is too low
	 */
	private static Dimension calculateOptimalAppSize(int width, int height){
		Dimension userWindowSize = userSettings.getWindowSize();
		if( userWindowSize != null ){
			width = userWindowSize.width;
			height = userWindowSize.height;
		}
		
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() < height){
			height = screenDimensions.height;
		}
		
		if( screenDimensions.getWidth() < width){
			width = screenDimensions.width;
		}
		
		return new Dimension(width, height);
	}

	private static EcAutoUpdate checkForUpdates()
	{
		EcAutoUpdate ecUpdater = new EcAutoUpdate(EvolutionChamber.VERSION, new EcAutoUpdate.Callback(){
			@Override
			public void checksumFailed()
			{
				JOptionPane.showMessageDialog(null, messages.getString("update.checksumFailed.message"), messages.getString("update.checksumFailed.title"), JOptionPane.ERROR_MESSAGE);
			}
			
			@Override
			public void updateCheckFailed()
			{
				JOptionPane.showMessageDialog(null, messages.getString("update.updateCheckFailed.message"), messages.getString("update.updateCheckFailed.title"), JOptionPane.WARNING_MESSAGE);
			}
		});
		if (ecUpdater.isUpdateAvailable())
		{
			JOptionPane pane = new JOptionPane(messages.getString("update.updateAvailable.message"));
			String yes = messages.getString("update.updateAvailable.yes");
			String no = messages.getString("update.updateAvailable.no");
			pane.setOptions(new String[] { yes, no });
			JDialog dialog = pane.createDialog(new JFrame(), messages.getString("update.updateAvailable.title", ecUpdater.getLatestVersion()));
			dialog.setVisible(true);

			Object selection = pane.getValue();

			if (selection.equals(yes))
			{
				JFrame updateFrame = new JFrame();
				updateFrame.setTitle(messages.getString("update.updating.title"));
				updateFrame.setResizable(false);
				ImageIcon icon = new ImageIcon(EcSwingXMain.class.getResource(iconLocation));
				updateFrame.setIconImage(icon.getImage());

				final JProgressBar updateProgress = new JProgressBar(0, 100);
				updateProgress.setValue(0);
				updateProgress.setStringPainted(true);
				updateFrame.add(updateProgress);
				updateFrame.setPreferredSize(new Dimension(200, 100));
				updateFrame.pack();
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);
				ecUpdater.addPropertyChangeListener(new PropertyChangeListener()
				{
					public void propertyChange(PropertyChangeEvent evt)
					{
						if ("progress".equals(evt.getPropertyName()))
						{
							updateProgress.setValue((Integer) evt.getNewValue());
						}
					}
				});
				ecUpdater.setUpdating(true);
				ecUpdater.execute();
			}
		}
		return ecUpdater;
	}
}
