package org.openfuxml.client.gui.simple;

import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.resourceloader.ImageResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.client.gui.simple.dialog.HelpAboutDialog;
import org.openfuxml.client.gui.simple.factory.SimpleLabelFactory;
import org.openfuxml.client.gui.simple.factory.SimpleMenuFactory;
import org.openfuxml.client.gui.swt.composites.AbstractProducerComposite;
import org.openfuxml.client.gui.swt.factory.ProducerButtonFactory;
import org.openfuxml.client.gui.swt.factory.ProducerComboFactory;
import org.openfuxml.client.gui.swt.factory.ProducerEntitiesDisplayFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProducedEntitiesEntityFile;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.util.config.OfxPathHelper;
import org.openfuxml.util.config.factory.ClientConfFactory;

/**
 * Client implementiert die Benutzeroberfläche für die FuXML-Produktion.
 * @author Andrea Frank
 */
public class Client extends AbstractProducerComposite implements ClientGuiCallback
{ 
	final static Logger logger = LoggerFactory.getLogger(Client.class);
    private static String fs = SystemUtils.FILE_SEPARATOR;
    
    public final static String Version = Client.class.getPackage().getImplementationVersion();
    public final static String Title = "openFuXML - Produktionssytem";

	public final static RGB rgbBackground = new RGB(231, 232, 235);
	
	private Menu menu;
	
	private Label lblRepository,lblEvent;
	private Button btnChange,btnUpdate,btnProduce;
	
	private Combo cboApplications,cboProjects;
	
	/**
	 * alProducedEntities speichert alle Eintr�ge, die in dieser Sitzung produziert wurden.
	 */
	private ArrayList<String[]> alProducedEntities;
	
	private Configuration config;
	private ImageResourceLoader irl;
	private Cursor cursor;
	
	/**
	 * Im Konstruktor f�r die Klasse Client werden neben den Initialiserungen
	 * der FuXMLLogger und die Properties gesetzt. Die Oberfl�che wird 
	 * generiert und ein sogenannter Splashscreen angezeigt. 
	 * @param parent
	 * @param disp
	 */
	public Client (Composite parent, Display disp, Configuration config)
	{
	    super(parent, SWT.NULL);
	    this.display=disp;
	    this.toplevelShell=(Shell)parent;
	    this.config=config;
		
	    irl = new ImageResourceLoader();
	    
		ofxCC = new OfxClientControl(config,this);
		
		HelpAboutDialog splashscreen = new HelpAboutDialog(this.getShell(), HelpAboutDialog.SPLASH_SCREEN,config,irl);
		splashscreen.open();
	
		alProducedEntities = new ArrayList<String[]>();

		logger.info("initGUI");
		guiInit();
		
		logger.info("pause");

		try{Thread.sleep(1000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		
		splashscreen.close();
		splashscreen = null;
	}
	
	/**
	 * initGUI initialisiert die grafische Oberfl�che.
	 */
	public void guiInit()
	{
		this.setBackground(new Color(this.getDisplay(), rgbBackground));
		
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 5;
			this.setLayout(layout);
		}
		
		SimpleLabelFactory slf = new SimpleLabelFactory(this,config,irl);
		ProducerButtonFactory sbf = new ProducerButtonFactory(this,this,ofxCC);
		ProducerComboFactory scf = new ProducerComboFactory(this,ofxCC);
		ProducerEntitiesDisplayFactory stf = new ProducerEntitiesDisplayFactory(ofxCC,config,irl);
		
		slf.createLogo();
		
		slf.createLabel("Repository");
		lblRepository = slf.createLabel(OfxPathHelper.getDir(config, "repository"));
		GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			lblRepository.setLayoutData(data);
		btnChange = sbf.createBtnChange("change");

		slf.createLabel("Application");
		cboApplications = scf.createCboApplication();
		slf.createDummyLabel(1);
		
		slf.createLabel("Project");
		cboProjects = scf.createCboProject();
		slf.createDummyLabel(1);
		
		slf.createLabel("Document");
		cboDocuments = scf.createCboDocument();
		slf.createDummyLabel(1);
		
		slf.createLabel("Format");
		cboFormats = scf.createCboFormats();
		slf.createDummyLabel(1);
		
		slf.createDummyLabel(2);
		btnUpdate = sbf.createBtnUpdate("update");
		
		tabDiscoveredEntities = stf.createTable(this);
		
		btnProduce = sbf.createBtnProduce("produce");
		lblEvent = slf.creatLblEvent();
		
		fillCboApplications();

		menu = SimpleMenuFactory.createMenu(getShell(),this);
	}
	
	public void fillCboApplications()
	{
		cboApplications.removeAll();
		try
		{
			this.lblEvent.setText("Einen Moment bitte ...");
			
			List<OfxApplication> lOfxA = ofxCC.getProducer().getAvailableApplications();
			this.lblEvent.setText("");
			
			if (lOfxA != null)
			{
				for(OfxApplication ofxA : lOfxA)
				{
					cboApplications.add(ofxA.getName());
					cboApplications.setData(ofxA.getName(),ofxA);
				}
			}
		}
		catch (ProductionSystemException e) {logger.error("getAvailableApplications nicht m�glich", e);}
		catch (ProductionHandlerException e)
		{
			logger.error("getAvailableApplications nicht m�glich", e);
			ServerFehler();
		}		
		
		// Vorauswahl der in Properties gespeicherten Einstellungen
/*		String sAnwendung = ClientConfigWrapper.getClientConf("application");
		if (sAnwendung.length() > 0)
		{
			int index = comboAnwendungen.indexOf(sAnwendung);
			if (index != -1)
			{
				comboAnwendungen.setText(sAnwendung);
			}
		}
*/
	}

	public void fillCboProjects()
	{
		cboProjects.removeAll();
		logger.debug("Search Projects ...");
		if (cboApplications.getText().length()>0)
		{
			List<OfxProject> lOfxP = ofxCC.getOfxProjectFactory().lProjects(cboApplications.getText());
			for(OfxProject ofxP : lOfxP)
			{
				cboProjects.add(ofxP.getName());
				cboProjects.setData(ofxP.getName(),ofxP);
				logger.debug(ofxP);
			}
		}
	}
	
	public void entitiesProduced(){}
	
	public void setStatus(final String status)
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed()) {lblEvent.setText(status);}
			}
		});
	}
	
	/**
	 * Die Methode addProducedEntities schreibt die produzierten
	 * Einheiten (producedEntities) in die ArrayList alProducedEntities.
	 * Falls der Eintrag bereits vorhanden ist, wird er aktualisiert.
	 * @param producedEntities
	 */
	public void addProducedEntities2(Productionresult presult)
	{
		
	}
	public void addProducedEntities3(ProducedEntities producedEntities)
	{
		if (producedEntities != null)
		{
			for (ProducedEntitiesEntityFile ef : producedEntities.getEntityFiles())
			{
		        String sTimestamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(new Date());
		        
				// Speichern in einem Array
		        // TODO @Thorsten: Ist es richtig den Application-Name hier vor 
		        // den Verzeichnisnamen zu setzen oder willst Du das schon in 
		        // producedEntities machen?
		        String sDir = cboApplications.getText() +
		        	System.getProperties().getProperty("file.separator") + 
		        	ef.getDirectory();
		        String pe[] = {ef.getDescription(), sDir, ef.getFilename(), sTimestamp};				

				// Speichern in der ArrayList
				// Ist das Element bereits produziert worden?
				// Dann wird der Eintrag in der ArrayList aktualisiert.
				int pos = getIndexOfArrayList(pe);
				if (pos == -1)
				{
					alProducedEntities.add(pe);
				}
				else
				{
					alProducedEntities.set(pos, pe); 
				}
			} // for
		} // if
	}
	
	/**
	 * Die Methode getIndexOfArrayList liefert den Index der alProducedEntities,
	 * an der das Element, das im Prameter �bergeben wird, steht.
	 * Ist das Element nicht enthalten, wird -1 geliefert.
	 * @param pe
	 * @return
	 */
	private int getIndexOfArrayList(String pe[])
	{
		for(int i=0;i<alProducedEntities.size();i++)
		{
			String s[] = (String[])alProducedEntities.get(i);
			if (s[0].equals(pe[0]) && s[1].equals(pe[1]) && s[2].equals(pe[2]))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Die Methode ClientBeenden schlie�t das Programm. 
	 */
	public void ClientBeenden()
	{
		this.getShell().close();
	}
	
	/**
	 * Die Methode Einstellungen �ffnet den Dialog Einstellungen.
	 * Die neuen Einstellungen werden in myProperties gespeichert,
	 * das labelVerzeichnis wird mit der Einstellung f�r Repository
	 * belegt und die Combo f�r Projekte wird aktualisiert.
	 */
	public void Einstellungen()
	{
		EinstellungenDialog dialog = new EinstellungenDialog(this.getShell(), rgbBackground,config);
		dialog.open(getShell().getImages());

		ofxCC = new OfxClientControl(config,this);
		
		String labelText=config.getString("dirs/dir[@type='repository']");;
		if(config.getBoolean("dirs/dir[@type='repository']/@rel"))
		{
			labelText=config.getString("dirs/dir[@type='basedir']")+fs+labelText;
		}
		lblRepository.setText(labelText);
		
		fillCboApplications();
	}
	
	/**
	 * Die Methode HilfeInfoUeber �ffnet den Dialog HilfeInfoUeber. 
	 */
	public void HilfeInfoUeber()
	{
		HelpAboutDialog dialog = new HelpAboutDialog(getShell(), HelpAboutDialog.ABOUT_DIALOG,config,irl);
		dialog.open();
	}

	/**
	 * Die Methode ServerFehler gibt eine Fehlermeldung aus und 
	 * �ffnet den Dialog Einstellungen.
	 */
	public void ServerFehler()
	{
		MessageBox d = new MessageBox(this.getShell(), SWT.ICON_ERROR| SWT.OK);
		d.setText("Fehler");
		d.setMessage("Es ist ein Fehler aufgetreten." + "\n" +
				"\n"+
				"�berpr�fen Sie bitte Ihre Servereinstellungen." + "\n" +
				"\n"+
				"Host: " + config.getString("net/host") + "\n" +
				"Port: " + config.getInt("net/port")
				); 
		d.open();

		Einstellungen();
	}
	
	/**
	 * Die Methode setAllEnabled sperrt die Benutzeroberfl�che f�r weitere Eingaben, bzw. 
	 * gibt sie wieder frei. 
	 * Sie ruft dabei f�r alle Bedienelemente die Methode setEnabled auf.
	 * Au�erem wird der Cursor auf "Warten" bzw. auf "normal" gestellt.
	 * 
	 * @param isEnabled - gibt an, ob die Bedienelemente enabled bzw. disabled werden.
	 */
	public void setProductionControlsEnabled(final boolean isEnabled)
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{				
					menu.setEnabled(isEnabled);

					btnChange.setEnabled(isEnabled);
					cboApplications.setEnabled(isEnabled);
					cboProjects.setEnabled(isEnabled);
					cboDocuments.setEnabled(isEnabled);
					cboFormats.setEnabled(isEnabled);
					btnUpdate.setEnabled(isEnabled);
					tabDiscoveredEntities.setEnabled(isEnabled);
					btnProduce.setEnabled(isEnabled);
				
					if (isEnabled){cursor = new Cursor(display, SWT.CURSOR_ARROW);}
					else {cursor = new Cursor(display, SWT.CURSOR_WAIT);}
					toplevelShell.setCursor(cursor);
				} 
			}
		});
	}
	
	/**
	 * Die Methode loescheErgebnis setzt das Label f�r den Status auf ""
	 * und productionResult auf null.
	 */
	public void loescheErgebnis()
	{
		lblEvent.setText("");
	}

	
	/**
	 * makeImages liefert ein Array von Images.
	 * Sollte ein Image nicht geladen werden k�nnen, ist das Ergebnisarray
	 * kleiner als das Array der Resourcedateinamen.
	 * @param Dateinamen - Array der Resourcedateinamen f�r die Images.
	 * @return Array der erzeugten Images
	 */
	public Image[] makeImages(String[] Dateinamen)
	{
		// Falls eine Datei f�r ein Image nicht existiert,
		// werden die Images erst in eine ArrayList und
		// dann in das Array Image[] f�r den return-Wert geschrieben.
		
		ArrayList<Image> alImages = new ArrayList<Image>();
		for (int i=0; i<Dateinamen.length; i++)
		{
			try
			{
				Image img = irl.search(this.getClass().getClassLoader(), Dateinamen[i], getDisplay());
				alImages.add(img);
			}
			catch (FileNotFoundException e){logger.warn(e);}
		}
		
		Image[] img = new Image[alImages.size()];
		for (int i=0; i<alImages.size(); i++)
		{
	        img[i] = alImages.get(i);
		}
		
        return img;		
	}
	
	public void error(String s)
	{
		MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Fehler");
		messageBox.setMessage(s); 
		messageBox.open();
	}
	
	public void cboApplicationSelected()
	{
		fillCboProjects();
		fillCboFormats();
		entitiesDiscovered();
		loescheErgebnis();
	}
	public void cboProjectSelected()
	{
		fillCboDocuments();
		entitiesDiscovered();
		loescheErgebnis();
	}
	public void cboFormatSelected(){}
	public void addLogline(String s){}
	public void clearLog(){}
	public void openUrl(URL url){}
	
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
		Client client = new Client(sh, disp, config);
		
		Point size = client.getSize();
		sh.setLayout(new FillLayout());
		sh.layout();
		if(size.x == 0 && size.y == 0)
		{
			client.pack();
			sh.pack();
		}
		else
		{
			Rectangle shellBounds = sh.computeTrim(0, 0, size.x, size.y);
			if (sh.getMenuBar() != null)
				shellBounds.height -= 22;
			sh.setSize(shellBounds.width, shellBounds.height);
		}
		sh.setText(Client.Title);

		String resIconFuxKlein = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fuxklein']");
		String resIconFux = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fux']");
		final String strImages[] = {resIconFuxKlein, resIconFux};
		sh.setImages(client.makeImages(strImages));

		sh.pack();
		sh.open();

		while (!sh.isDisposed())
		{
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		System.exit(0);
	}
}