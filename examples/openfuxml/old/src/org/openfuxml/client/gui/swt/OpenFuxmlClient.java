/*
 * Created on 19.01.2005
 */
package org.openfuxml.client.gui.swt;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.resourceloader.ImageResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.client.control.projects.ProjectFactory;
import org.openfuxml.client.control.projects.ProjectFactoryDirect;
import org.openfuxml.client.gui.simple.dialog.HelpAboutDialog;
import org.openfuxml.client.gui.simple.factory.SimpleLabelFactory;
import org.openfuxml.client.gui.swt.composites.ProjektComposite;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.util.config.factory.ClientConfFactory;

/**
 * Client implementiert die Benutzeroberfläche für die FuXML-Produktion.
 * @author Thorsten Kisner
 * @author Andrea Frank
 */
public class OpenFuxmlClient extends Composite implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(OpenFuxmlClient.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	public final static String Title = "openFuXML - Client";
	public final static String IMG_SYSTEMINFO		= "/swt/images/tab/systeminfo.png";
	
	private TabFolder tfProjekte;
	private TabItem tiSystemInfo;
//	private SystemInfoComposite systemInfoComposite;
	
	private Properties myProperties;
	/**
	 * propDir beschreibt das Verzeichnis, in dem die Properties-Datei 
	 * und die ProjectUserSettings-Dateien abgelegt sind.
	 */
//	private String propDir; 
//	private File propFile;
	private Composite parent;
		
	private Thread pingThread;
	private boolean pingThreadAktiv;
	private int pingThreadZaehler;
	private Configuration config;
	private ImageResourceLoader irl;
	private Shell shell;
	private OfxClientControl ofxCC;

	public OpenFuxmlClient (Composite parent, int style, Configuration config)
	{
		super(parent, style);
		this.config=config;
		SwtGuiCallback guiCallback = new SwtGuiCallback();
		ofxCC = new OfxClientControl(config,guiCallback);
		
		irl = new ImageResourceLoader();
		
		HelpAboutDialog splashscreen = new HelpAboutDialog(this.getShell(), HelpAboutDialog.SPLASH_SCREEN,config,irl);
		splashscreen.open();
		
		this.parent=parent;
		this.shell = getShell();
		pingThread = null;
		pingThreadAktiv = false;
		pingThreadZaehler = 0;
		
		splashscreen.setStatusline("Initialisiere Oberfl�che ...");

		initGUI();

/*		if (myProperties.getProperty("AutoLogin", "0").equals("0") ||
			myProperties.getProperty("KennwortSpeichern", "0").equals("0") )
		{
			splashscreen.setStatusline("Benutzereinstellungen");
			Einstellungen();
		}
*/			
/*		if (myProperties.getProperty("Beenden") != null)
		{
			splashscreen.setStatusline("FuXML-Client beenden");
			System.exit(0);
		}
*/		
		// new OpenFuxmlTray(parent,this);
		
		splashscreen.setStatusline("Login des Benutzers");
		login();
		
		// Starten des pingThreads ...
		pingThread = new Thread(this);
		pingThread.start();
		pingThreadAktiv = true;

		// Close the SplashScreen
		splashscreen.close();
	}
	
	public void initGUI()
	{
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent evt) {
				// pingThread stoppen
				pingThread = null;
				pingThreadAktiv = false;
				
				Rectangle rect = getShell().getBounds();
/*				myProperties.setProperty("x",      "" + rect.x);
				myProperties.setProperty("y",      "" + rect.y);
				myProperties.setProperty("width",  "" + rect.width);
				myProperties.setProperty("height", "" + rect.height);
*/				
				saveProperties();
				
//				try
//				{
//					myUserUi.remove();
//				}
//				catch (RemoteException e) {e.printStackTrace();}
//				catch (RemoveException e) {e.printStackTrace();}
			}
		});

		{
			this.setBackground(new Color(this.getDisplay(), new RGB(231, 232, 235)));
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 14;
			layout.marginWidth = 14;
			this.setLayout(layout);
		}
		
		SimpleLabelFactory slf = new SimpleLabelFactory(this,config,irl);
		slf.createLogo();
		
		{
			tfProjekte = new TabFolder(this, SWT.TOP);
			tfProjekte.setSelection(0);
			GridData data = new GridData();
			data.horizontalSpan = 3;
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			tfProjekte.setLayoutData(data);

			tfProjekte.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					// Merken des Projekt-Tabs f�r LastProject
					{
						// Setzen des Property-Wertes f�r LastTab
						Control control = tfProjekte.getItem(tfProjekte.getSelectionIndex()).getControl();
						
						Integer iLastProject = new Integer(0);
						if (control != null)
						{
							try
							{
//								ProjektComposite pComp = (ProjektComposite)control;
//								iLastProject = pComp.getProjectValue().getID();
							}
							catch (ClassCastException cce1) 
							{
								try
								{
									iLastProject = new Integer(-1);
								}
								catch (ClassCastException cce2)	{}
							}
//							myProperties.setProperty("LastProject", "" + iLastProject);
						}
					}
				}
			});
		}
		
		new OpenFuxmlMenu(getShell());
	} // initGUI

	public void saveProperties()
	{
		// erst Aufr�umen
		myProperties.remove("Beenden");
		myProperties.remove("Login");

		//TODO Speichern der Properties
//		if (myProperties.getProperty("KennwortSpeichern","0").equals("0"))
//		{myProperties.remove("Kennwort");}
	}
	
	public Properties getMyProperties(){return myProperties;}
//	public UserValue getMyUserValue(){return myUserValue;}
//	public ProjectUiHome getHProjectUi(){return hProjectUi;}
//	public SSIMessage getAvailableFormats(){return availableFormats;}
	
	public void login()
	{
		
//		try
		{
			ProjectFactory pf = new ProjectFactoryDirect(config);
			List<OfxProject> lProjects = ofxCC.getOfxProjectFactory().lProjects("fuxml");

			while (tfProjekte.getItemCount()>0)
			{	// L�schen der alten TabItems f�r die Projekte
				tfProjekte.getItem(0).getControl().dispose();
				tfProjekte.getItem(0).dispose();
			}

			if (lProjects.size() == 0)
			{
				StringBuffer sb = new StringBuffer();
					sb.append("Keine Projekte verf�gbar");
				Label label = new Label(tfProjekte, SWT.NONE);
				label.setText(sb.toString());

				TabItem tabItem = new TabItem(tfProjekte, SWT.NONE);
				tabItem.setControl(label);
			} // if
			
			for(OfxProject ofxProject : lProjects)
			{
				logger.debug("Adding "+ofxProject.getName());

				TabItem tabItem = new TabItem(tfProjekte, SWT.NONE);
				String res = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='project']");
				try
				{
					ImageResourceLoader irl = new ImageResourceLoader();
					Image img = irl.search(this.getClass().getClassLoader(), res, getDisplay());
					tabItem.setImage(img);
				}
				catch (FileNotFoundException e) {logger.error("",e);}
				tabItem.setText(ofxProject.getName());
				ProjektComposite pComp = new ProjektComposite(tfProjekte, this, ofxProject,config);
				tabItem.setControl(pComp);
			}

/*			tiSystemInfo = new TabItem(tfProjekte, SWT.NONE);
			tiSystemInfo.setImage(makeImage(IMG_SYSTEMINFO));
			tiSystemInfo.setText("Systeminfo");
			systemInfoComposite = new SystemInfoComposite(tfProjekte, isSupervisor, iniCtx, hUserUi);
			tiSystemInfo.setControl(systemInfoComposite);
*/		}
//		catch (CreateException e) {e.printStackTrace();}
//		catch (RemoteException e) {e.printStackTrace();}
//		catch (RemoveException e) {e.printStackTrace();}
		
/*		// Das letzte angezeigte Projekt bzw. Systeminfo wird angezeigt.
		int iLastProject = -2;
		try
		{
			iLastProject = Integer.parseInt(myProperties.getProperty("LastProject", "-2"));
		}
		catch (NumberFormatException e) {}
		
		if ( (iLastProject == -1) && (tiSystemInfo!=null) )
		{
			// SystemInfo-Tab ausw�hlen.
			TabItem ti[] = {tiSystemInfo};
			tfProjekte.setSelection(ti);
		}
		else
		{
			// Projekt-Tab ausw�hlen.

			// Welches Projekt-Tab hat die Projekt-ID iLastProject???
			for (int i=0; i<tfProjekte.getItemCount(); i++)
			{
				TabItem ti = tfProjekte.getItem(i);
				Control control = ti.getControl();
				
				// try-Block f�r ClassCastException (Control k�nnte auch ein SystemInfoComposite sein)
				try 
				{
					ProjektComposite pComp = (ProjektComposite)control;
					Integer ID = pComp.getProjectValue().getID();

					// vergleichen der IDs
					if (ID.equals(new Integer(iLastProject)))
					{
						TabItem ti2[] = {ti};
						tfProjekte.setSelection(ti2);
						break;
					}
				}
				catch (ClassCastException cce) {}
			}
		}
*/	}
	
    public void run()
	{  
		while (pingThreadAktiv)
		{
			if (pingThreadZaehler >= 300) // 300 Sekunden ^= 5 Minuten
			{
				pingProjekte();
				pingThreadZaehler = 0;
			}
			pingThreadZaehler++;

			try 
			{
    			Thread.sleep(1000); // 1 Sekunde
    		} 
    		catch (InterruptedException e) {}
    	}
	}
    
    public void pingProjekte()
    {
		this.getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				if (!shell.isDisposed())
				{
					// Ping f�r User
/*					try
					{
						myUserUi.ping(System.currentTimeMillis());
					}
					catch (RemoteException e) {e.printStackTrace();}
*/					
					// Ping f�r jedes Projekt
					for (int i=0; i<tfProjekte.getItemCount(); i++)
					{
						Object obj = tfProjekte.getItem(i).getControl();
//						if(obj.getClass().getName().equals(ProjektComposite.class.getName()))
						{
//							((ProjektComposite)obj).ping();
						}
					} // for
				} // if
			}
		});
    } // pingProjekte

	public void ClientBeenden()
	{
		this.getShell().close();
	}
	
	public void Einstellungen()
	{
//		EinstellungenDialog dialog = new EinstellungenDialog(this, myProperties);
//		myProperties = dialog.open();
	}
	
/*	public void ExtrasNeuesProjekt()
	{
		NewProjectDialog dialog = new NewProjectDialog(shell, iniCtx, myUserValue);
		Rolle r = dialog.open();
		
		// Dieses Projekt als neues TabItem einf�gen
		if (r != null)
		{
			try
			{
				RolleValue rv = (RolleValue)r.getRolleValue();
				System.out.println("r.getProjektID()="+rv.getProjektID());
				ProjectUi myProjectUi = hProjectUi.create(rv.getBenutzerID(),rv.getProjektID());
				ProjectValue myProjectValue = myProjectUi.getProjectValue();
				// TODO index 
				TabItem tabItem = new TabItem(tfProjekte, SWT.NONE);
				tabItem.setImage(makeImage(IMG_PROJECT));
				tabItem.setText(myProjectValue.getProjectShortName());
				ProjektComposite pComp = new ProjektComposite(tfProjekte, this,  
						hDispatcher, myUserUi, myProjectUi, iniCtx);
				tabItem.setControl(pComp);			
			}
			catch (CreateException e) {e.printStackTrace();}
			catch (RemoteException e) {e.printStackTrace();}		
		}
	}
*/	
	public void HilfeInfoUeber()
	{
		HelpAboutDialog dialog = new HelpAboutDialog(getShell(), HelpAboutDialog.ABOUT_DIALOG, config,irl);
		dialog.open();
	}

	public Image[] makeImages(String[] Dateinamen)
	{
		int anzImages = Dateinamen.length;
		Image[] img = new Image[anzImages];
		ImageResourceLoader irl = new ImageResourceLoader();
		for (int i=0; i<anzImages; i++)
		{
	        try{img[i] = irl.search(this.getClass().getClassLoader(), Dateinamen[i], getDisplay());}
	        catch (FileNotFoundException e) {logger.error("",e);}
		}
        return img;		
	}

	public static void main(String[] args)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ClientConfFactory ccf = new ClientConfFactory();
		ccf.init("openFuXML.xml");
		
		Configuration config = ccf.getConfiguration();	
			
		Display disp = Display.getDefault();
		Shell sh = new Shell(disp);
		
		ImageResourceLoader irl = new ImageResourceLoader();
		HelpAboutDialog splashscreen = new HelpAboutDialog(sh, HelpAboutDialog.SPLASH_SCREEN,config,irl);
		splashscreen.open();
		try{Thread.sleep(3000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		splashscreen.close();
		splashscreen = null;
		
		OpenFuxmlClient client = new OpenFuxmlClient(sh, SWT.NULL, config);
		
		sh.setLayout(new FillLayout());
		sh.layout();

		// Die alte Gr��e wird benutzt.
/*		try
		{
			String sX		= client.getMyProperties().getProperty("x");
			String sY		= client.getMyProperties().getProperty("y");
			String sWidth	= client.getMyProperties().getProperty("width");
			String sHeight	= client.getMyProperties().getProperty("height");
			
			if ( (sX!=null) && (sY!=null) && (sWidth!=null) && (sHeight != null))
			{
				int x		= Integer.parseInt(sX);
				int y		= Integer.parseInt(sY);
				int width	= Integer.parseInt(sWidth);
				int height	= Integer.parseInt(sHeight);
				sh.setBounds(x, y, width, height);
			}
			else
			{
				sh.pack();
			}
		}
		catch (NumberFormatException e) {sh.pack();}
*/		
		
		sh.setBounds(10, 30, 300, 900);
		sh.pack();
		
		// Titelzeile
		sh.setText(OpenFuxmlClient.Title);
		
		String resIconFux = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fux']");
		String resIconFuxKlein = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fuxklein']");
		final String strImages[] = {resIconFuxKlein, resIconFux};
		sh.setImages(client.makeImages(strImages));

		sh.open();
		
		while (!sh.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}
}