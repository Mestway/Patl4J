package com.fray.evo.ui.swingx;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.fray.evo.EvolutionChamber;
import com.fray.evo.util.EcAutoUpdate;
import com.fray.evo.util.EcMessages;


public class EcSwingXMain
{
	private static final Logger logger = Logger.getLogger(EcSwingXMain.class.getName());

	public static final EcMessages messages = new EcMessages("com/fray/evo/ui/swingx/messages");

	public static final File userConfigDir;

	public static final UserSettings userSettings;
	
	static{
		userConfigDir = new File(System.getProperty("user.home"), ".evolutionchamber");
		userConfigDir.mkdir();
		userSettings = new UserSettings(new File(userConfigDir, "settings.properties"));
	}

	public static final String iconLocation = "/com/fray/evo/ui/swingx/evolution_chamber.png";

	public static Shell mainWindow;

	public static void main(String args[])
	{
		try
		{
			LogManager.getLogManager().readConfiguration(EcSwingXMain.class.getResourceAsStream("logging.properties"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Locale locale = userSettings.getLocale();
		if (locale != null)
		{
			messages.changeLocale(locale);
		}
		
		MacSupport.initIfMac(messages.getString("title", EvolutionChamber.VERSION), false, iconLocation, new MacHandler()
		{
			@Override
			public void handleQuit(Object applicationEvent)
			{
				int currentExtendedState = mainWindow.getMaximized() ? 1 : mainWindow.getMinimized() ? -1 : 0;
				Point currentSize = mainWindow.getSize();
				
				userSettings.setWindowExtensionState(currentExtendedState);
				userSettings.setWindowSize(currentSize);
				
				System.exit(0);
			}

			@Override
			public void handleAbout(Object applicationEvent)
			{
			}
		});
		
		final Shell frame = new Shell(Display.getDefault());
		mainWindow = frame; 
		frame.setText(messages.getString("title", EvolutionChamber.VERSION));
		
		
		new EcSwingX(frame, SWT.NONE);
		
		
		frame.addListener(SWT.Dispose, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				int currentExtendedState = frame.getMaximized() ? 1 : frame.getMinimized() ? -1 : 0;
				
				Point currentSize = frame.getSize();
				
				userSettings.setWindowExtensionState(currentExtendedState);
				userSettings.setWindowSize(currentSize);
			}
		});
		
		frame.setSize(calculateOptimalAppSize(850, 1200));
		final Image icon = new Image(Display.getDefault(), EcSwingXMain.class.getResource(iconLocation).getPath());
		frame.setImage(icon);
		
		final Shell updateFrame = new Shell(Display.getDefault());
		updateFrame.setLayout(new FillLayout());
		updateFrame.setText(messages.getString("update.title"));
		updateFrame.setImage(icon);
		Label waiting = new Label(updateFrame, SWT.NONE);
		waiting.setText(messages.getString("update.checking"));
		updateFrame.setSize(new Point(250, 70));
		
		updateFrame.pack();
		updateFrame.open();
		
		Thread a = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable()
				{
					@Override
					public void run()
					{
						Thread t = new Thread(new Runnable() {
							
							@Override
							public void run() {
								EcAutoUpdate ecUpdater = checkForUpdates();
								boolean update = ecUpdater.isUpdating();
								if(! update){
									Display.getDefault().asyncExec(new Runnable(){
										public void run(){
											updateFrame.dispose();
										}
									});
								}
							}
						});
						t.start();;
						
						while(!updateFrame.isDisposed()){
							if(!Display.getDefault().readAndDispatch()){
								Display.getDefault().sleep();
							}
						}
						t.interrupt();
						frame.open();
						frame.setVisible(true);
						frame.setFocus();
						
					}
				});
				
			}
		});
		a.start();
		
		while(!frame.isDisposed()){
			if(!Display.getDefault().readAndDispatch()){
				Display.getDefault().sleep();
			}
		}
		
		a.interrupt();
		EcSwingX.timerThread.interrupt();
	}
	
	private static int getOptimalExtendedState(Shell frame){
		int extendedState = frame.getMaximized() ? 1 : frame.getMinimized() ? -1 : 0;
		
		Integer userPrefExtensionState = userSettings.getWindowExtensionState();
		if( userPrefExtensionState != null){
			extendedState = userPrefExtensionState.intValue();
		}
		
		
		int width = frame.getSize().x;
		int height = frame.getSize().y;
				
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() <= height){
			height = screenDimensions.height;
		}
		
		if( screenDimensions.getWidth() <= width){
			width = screenDimensions.width;
		}
		
		return extendedState;
	}
	
	private static Point calculateOptimalAppSize(int width, int height){
		Point userWindowSize = userSettings.getWindowSize();
		if( userWindowSize != null ){
			width = userWindowSize.x;
			height = userWindowSize.y;
		}
		
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() < height){
			height = screenDimensions.height;
		}
		
		if( screenDimensions.getWidth() < width){
			width = screenDimensions.width;
		}
		
		return new Point(width, height);
	}

	private static EcAutoUpdate checkForUpdates()
	{
		EcAutoUpdate ecUpdater = new EcAutoUpdate(EvolutionChamber.VERSION, new EcAutoUpdate.Callback(){
			@Override
			public void checksumFailed()
			{
				MessageBox messageBox = new MessageBox(mainWindow, SWT.ICON_ERROR | SWT.IGNORE);
				messageBox.setText(messages.getString("update.checksumFailed.title"));
				messageBox.setMessage(messages.getString("update.checksumFailed.message"));
				messageBox.open();
			}
			
			@Override
			public void updateCheckFailed()
			{
				MessageBox messageBox = new MessageBox(mainWindow, SWT.ICON_WARNING | SWT.IGNORE);
				messageBox.setText(messages.getString("update.updateCheckFailed.title"));
				messageBox.setMessage(messages.getString("update.updateCheckFailed.message"));
				messageBox.open();
			}
		});
		return ecUpdater;
	}
}
