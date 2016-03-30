package org.openfuxml.client.gui.simple.dialog;

import java.io.FileNotFoundException;

import net.sf.exlp.io.resourceloader.ImageResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.openfuxml.client.gui.simple.Client;

/**
 * The class HelpAboutDialog implements the Dialog "About FuXML-Client"
 * and the SplashScreen which can be opened at the programstart. 
 * The type of the dialog is defined by the parameter in the constructor. 
 * <br><br>
 * 
 * The SplashScreen contains the label labelStatus. This dialog is closed 
 * by the main program. <br>
 * 
 * The AboutDialog contains an OK-Button. This dialog is closed
 * by pressing this OK-Button. <br>
 *   
 * @author Andrea Frank
 */
public class HelpAboutDialog extends Dialog
{
	final static Logger logger = LoggerFactory.getLogger(HelpAboutDialog.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;

	public final static int ABOUT_DIALOG	= 0;
	public final static int SPLASH_SCREEN	= 1;
	
	private int type;	
	private Color colBackground; 
	
	private Shell shell;

	private Label labelStatus;
	private Button btnClose;
	
	// Region f�r die Shell
	Region region;
	
	// Umriss der Shell
	private static final int[] OUTLINE = new int[]{
		0, 118,
		5, 113,
		253, 113,
		241, 47,
		465, 9,
		478, 82,
		579, 97,
		576, 113,
		595, 113,
		600, 118,
		600, 680,
		585, 695,  // diesen Wert habe ich nicht verstanden, sieht aber ok aus (andy) 
		5, 685,
		0, 680
	};
	
	// Loch in der Shell
	private static final int[] HOLLOW = new int[]{
		204, 327,
		236, 327,
		243, 344,
		248, 344,
		259, 327,
		293, 327,
		258, 370,
		279, 411,
		247, 411,
		240, 393,
		235, 393,
		221, 411,
		186, 411,
		224, 366
	};
	
	private Listener listener;
	private Configuration config;
	private ImageResourceLoader irl;
	
	/**
	 * Constructor of the class HelpAboutDialog.
	 * 
	 * @param parent
	 * @param type - defines the type (ABOUT_DIALOG or SPLASH_SCREEN)
	 */
	public HelpAboutDialog(Shell parent, int type, Configuration config, ImageResourceLoader irl)
	{
		super(parent, 0);
		this.config=config;
		this.type = type;
		this.irl=irl;
		colBackground = new Color(parent.getDisplay(), 231, 232, 235);		
	}
	
	/**
	 * The method open opens the dialog HelpAboutDialog.
	 */
	public void open()
	{
		final Shell parent = this.getParent();		
		
		if (type == ABOUT_DIALOG)
		{
			shell = new Shell(parent, SWT.APPLICATION_MODAL | SWT.NO_TRIM);
		}
		else if (type == SPLASH_SCREEN)
		{
			shell = new Shell(parent, SWT.NO_TRIM);
		}
		else
		{
			logger.error("Error in method HelpAboutDialog.open(). type = "+type);
			return;
		}
		
		//------------------------------
		// Region f�r den Umriss des Programms erzeugen
		region = new Region();
		region.add(OUTLINE);
		region.subtract(HOLLOW);
		
		// Gestalt der Region der Shell aufpr�gen
		shell.setRegion(region);
		
		// Gr��e der Region abfragen
		Rectangle size = region.getBounds();
		
		// Shell auf Prim�rmonitor positionieren
		Monitor mon1 = shell.getDisplay().getPrimaryMonitor();
		Rectangle r = mon1.getClientArea();
		shell.setBounds((r.width-size.width)/2, (r.height-size.height)/2, size.width, size.height);
		
		// Da die Shell kein Trim hat, muss man das Herumschieben
		// des Fensters selbst organisieren.
		listener = new Listener() {
			Point origin;

			public void handleEvent(Event e)
			{
				switch (e.type)
				{
				case SWT.MouseDown:
					origin = new Point(e.x, e.y);
					break;
				case SWT.MouseUp:
					origin = null;
					break;
				case SWT.MouseMove:
					if (origin != null)
					{
						Point p = shell.getDisplay().map(shell, null, e.x, e.y);
						shell.setLocation(p.x-origin.x, p.y-origin.y);
					}
					break;
				}
			}
			
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseUp, listener);
		shell.addListener(SWT.MouseMove, listener);		
		//---------------------------------		
		
		initGUI();
		
		shell.open();
		
		if (type == ABOUT_DIALOG)
		{
			final Display display = parent.getDisplay();
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		}
	} // open

	/**
	 * The method close closes the dialog HelpAboutDialog.
	 */
	public void close()
	{
		shell.close();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI()
	{
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			shell.setLayout(layout);
		}
		
		{
			Label labelImage = new Label(shell, SWT.NONE);
			
			GridData data = new GridData();
			data.widthHint = 600;
			data.heightHint = 421;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			labelImage.setLayoutData(data);
			labelImage.setBackground(shell.getBackground());
			
			String res = config.getString("logos/@dir")+fs+config.getString("logos/logo[@type='fuxgross']");
			
			try
			{
				Image img = irl.search(this.getClass().getClassLoader(), res, shell.getDisplay());
				labelImage.setImage(img);
			}
			catch (FileNotFoundException e)
			{
				labelImage.setText("ERROR: Image not found!");
				labelImage.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			
			// zum Verschieben
			labelImage.addListener(SWT.MouseDown, listener);
			labelImage.addListener(SWT.MouseUp, listener);
			labelImage.addListener(SWT.MouseMove, listener);		
		}
		{
			Composite compo = new Composite(shell, SWT.NONE);
			compo.setBackground(colBackground);

			// zum Verschieben
			compo.addListener(SWT.MouseDown, listener);
			compo.addListener(SWT.MouseUp, listener);
			compo.addListener(SWT.MouseMove, listener);		
			
			{
				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				layout.marginHeight = 20;
				layout.marginWidth = 30;
				layout.horizontalSpacing = 10;
				layout.verticalSpacing = 10;
				
				compo.setLayout(layout);
			}

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				compo.setLayoutData(data);
			}

			{
				Label label = new Label(compo, SWT.CENTER);
				String sTitelVersion = Client.Title;
				if (Client.Version != null)
				{
					sTitelVersion += " Version " +  Client.Version +"\n";
				}
				label.setText(sTitelVersion +
						"\n" +
						"Lehrgebiet Kommunikationssysteme" + "\n" +
						"FernUniversit�t Hagen" + "\n" +
						"2006" + "\n");
				label.setBackground(colBackground);

				// zum Verschieben
				label.addListener(SWT.MouseDown, listener);
				label.addListener(SWT.MouseUp, listener);
				label.addListener(SWT.MouseMove, listener);		

				{
					GridData data = new GridData();
					data.verticalIndent = 20;
					data.grabExcessHorizontalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					label.setLayoutData(data);
				}
			}
			{
				RollTextCanvas canvas = new RollTextCanvas(compo, SWT.NONE, 
						"\n" +
						"Dieses Produkt wurde am Lehrgebiet Kommunikationssysteme, \n" +
						"Prof. Dr.-Ing. Firoz Kaderali, der FernUniversit�t in Hagen \n"+
						"entwickelt.\n" +
						"\n" +
						"\n" +
						"\n" +
						"\n" +
						"Freundliche Gr��e!\n"+
						"Viel Spa�!"+
						"...\n" +
						"und was sonst noch dazu geh�rt!");
				canvas.setBackground(colBackground);

				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.heightHint = 100;
					canvas.setLayoutData(data);
				}
			}

			if (type == ABOUT_DIALOG)
			{
				{
					btnClose = new Button(compo, SWT.PUSH | SWT.CENTER);
					btnClose.setText("     OK     ");
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							btnClose();
						}
					});

					GridData data = new GridData();
					data.horizontalAlignment = GridData.CENTER;
					btnClose.setLayoutData(data);
				}
			} //if
			else if (type == SPLASH_SCREEN)
			{
				{
					labelStatus = new Label(compo, SWT.NONE);
					labelStatus.setBackground(colBackground);
					
					{
						GridData data = new GridData();
						data.verticalIndent = 20;
						data.grabExcessHorizontalSpace = true;
						data.horizontalAlignment = GridData.FILL;
						labelStatus.setLayoutData(data);
					}
				}
			} // else if
		}
	} // initGUI
	
	/**
	 * btnClose closes the HelpAboutDialog.
	 */
	private void btnClose()
	{
		close();
	} // btnClose
	
	/**
	 * The method setStatusline sets the text of the label labelStatus.
	 * @param text - the new text for the label labelStatus.
	 */
	public void setStatusline(String text)
	{
		labelStatus.setText(text);
	}
}