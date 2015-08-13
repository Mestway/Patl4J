package org.openfuxml.client.gui.simple;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * In dem Dialog EinstellungenDialog werden die Einstellungen f�r die
 * Anwendung gesetzt.
 * 
 * @author Andrea Frank
 */
public class EinstellungenDialog extends Dialog
{
	final static Logger logger = LoggerFactory.getLogger(EinstellungenDialog.class);
	
	private Shell shell;

	private TabFolder tfEinstellungen;
	
	private TabItem tiServer;
	private Text textHost;
	private Text textPort;
	
	private TabItem tiVerzeichnisse;
	private Label labelRepository;
	private Button buttonRepository;
	private Label labelOutput;
	private Button buttonOutput;
	
	private TabItem tiAnwendungen;
	private Table tableAnwendungen;
	private Button BtnNeu;
	private Button BtnAendern;
	private Button BtnLoeschen;
	
	private Button BtnOK;
	private Button BtnCancel;
	
	private RGB rgbBackground;
	private Configuration config;
	
	/**
	 * Erzeugt eine Instanz von EinstellungenDialog.
	 * 
	 * @param parent
	 * @param properties
	 * @param rgb
	 */
	public EinstellungenDialog(Shell parent, RGB rgb, Configuration config)
	{
		super(parent, 0);
		this.rgbBackground = rgb;
		this.config=config;
	}

	/**
	 * �ffnet den Dialog EinstellungenDialog. Die Eingabefelder werden
	 * mit den Einstellungen aus den Porperties vorbelegt.
	 * 
	 * @return Liefert die neuen Einstellungen als Properties.
	 */
	public void open(Image[] images)
	{
		final Shell parent = this.getParent();
		
		shell = new Shell(parent, SWT.RESIZE | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		shell.setText("Einstellungen");
		
		initGUI();
		shell.pack();	
		shell.setImages(images);
		shell.open();
		
		final Display display = parent.getDisplay();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}

	/**
	* Initializes the GUI.
	*/
	private void initGUI()
	{
		shell.setBackground(new Color (shell.getDisplay(), rgbBackground));
		
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			shell.setLayout(layout);
		}
		
		{
			tfEinstellungen = new TabFolder(shell, SWT.TOP);
			tfEinstellungen.setBackground(new Color(shell.getDisplay(), rgbBackground));
			tfEinstellungen.setSelection(0);
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.grabExcessVerticalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				data.verticalAlignment = GridData.FILL;
				tfEinstellungen.setLayoutData(data);
			}
		}
		{
			// TabItems generieren
			// Die Inhalte der TabItems kommen weiter unten.
			tiServer = new TabItem(tfEinstellungen, SWT.NONE);
			tiServer.setText("Server");
			tiVerzeichnisse = new TabItem(tfEinstellungen, SWT.NONE);
			tiVerzeichnisse.setText("Verzeichnisse");
			tiAnwendungen = new TabItem(tfEinstellungen, SWT.NONE);
			tiAnwendungen.setText("Anwendungen");
		}
		
		{
			// Inhalt des TabItem tiServer
			Composite compositeServer = new Composite(tfEinstellungen, SWT.NONE);
			tiServer.setControl(compositeServer);
			{
		        GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				layout.marginHeight = 20;
				layout.marginWidth = 20;
				layout.horizontalSpacing = 20;
				layout.verticalSpacing = 20;
				compositeServer.setLayout(layout);
			}
			{
				Label label = new Label(compositeServer, SWT.NONE);
				label.setText("Host:");
			}
			{
				textHost = new Text(compositeServer, SWT.BORDER);
				textHost.setText(config.getString("net/host"));

				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					textHost.setLayoutData(data);
				}
			}
			{
				Label label = new Label(compositeServer, SWT.NONE);
				label.setText("Port:");
			}
			{
				textPort = new Text(compositeServer, SWT.BORDER);
				textPort.setText(config.getString("net/port"));

				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					textPort.setLayoutData(data);
				}
			}
		}
		{
			// Inhalt des TabItem tiVerzeichnisse
			Composite compositeVerzeichnisse = new Composite(tfEinstellungen, SWT.NONE);
			tiVerzeichnisse.setControl(compositeVerzeichnisse);
			{
		        GridLayout layout = new GridLayout();
				layout.numColumns = 3;
				layout.marginHeight = 20;
				layout.marginWidth = 20;
				layout.horizontalSpacing = 20;
				layout.verticalSpacing = 20;
				compositeVerzeichnisse.setLayout(layout);
			}
			{
				Label label = new Label(compositeVerzeichnisse, SWT.NONE);
				label.setText("Repository:");
			}
			{
				labelRepository = new Label(compositeVerzeichnisse, SWT.NONE);
				labelRepository.setText(config.getString("dirs/dir[@type='repository']"));
	
				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					labelRepository.setLayoutData(data);
				}
			}
			{
				buttonRepository = new Button(compositeVerzeichnisse, SWT.PUSH | SWT.CENTER);
				buttonRepository.setText("...");

				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					buttonRepository.setLayoutData(data);
				}

				buttonRepository.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						DirectoryDialog dialog = new DirectoryDialog(shell);
						dialog.setText(Client.Title);
						dialog.setMessage("Verzeichnisauswahl f�r die Produktion");
						dialog.setFilterPath(labelRepository.getText());
						String dirname = dialog.open();
						if (dirname!=null)
						{
							labelRepository.setText(dirname);
						}
					}
				});
			}
			{
				Label label = new Label(compositeVerzeichnisse, SWT.NONE);
				label.setText("Output:");
			}
			{
				labelOutput = new Label(compositeVerzeichnisse, SWT.NONE);
				labelOutput.setText(config.getString("dirs/dir[@type='output']"));

				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					labelOutput.setLayoutData(data);
				}
			}
			{
				buttonOutput = new Button(compositeVerzeichnisse, SWT.PUSH | SWT.CENTER);
				buttonOutput.setText("...");
				
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				buttonOutput.setLayoutData(data);

				buttonOutput.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						DirectoryDialog dialog = new DirectoryDialog(shell);
						dialog.setText(Client.Title);
						dialog.setMessage("Verzeichnisauswahl f�r OUTPUT");
						dialog.setFilterPath(labelOutput.getText());
						String dirname = dialog.open();
						if (dirname!=null)
						{
							labelOutput.setText(dirname);
						}
					}
				});
			}
		}		
		{
			// Inhalt des TabItem tiAnwendungen
			Composite compositeAnwendungen = new Composite(tfEinstellungen, SWT.NONE);
			tiAnwendungen.setControl(compositeAnwendungen);
			{
		        GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				layout.marginHeight = 20;
				layout.marginWidth = 20;
				layout.horizontalSpacing = 20;
				layout.verticalSpacing = 20;
				compositeAnwendungen.setLayout(layout);
			}
			
			{
				tableAnwendungen = new Table(compositeAnwendungen, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER);
				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.grabExcessVerticalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					data.verticalAlignment = GridData.FILL;
					tableAnwendungen.setLayoutData(data);
				}
				{
					TableColumn tableColumn = new TableColumn(tableAnwendungen, SWT.NONE);
					tableColumn.setText("Dateityp");
					tableColumn.setWidth(80);
				}
				{
					TableColumn tableColumn = new TableColumn(tableAnwendungen, SWT.NONE);
					tableColumn.setText("Anwendung");
					tableColumn.setWidth(250);
				}
				tableAnwendungen.setHeaderVisible(true);
				tableAnwendungen.setLinesVisible(true);
				
				tableAnwendungen.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						BtnAendern.setEnabled(true);
						BtnLoeschen.setEnabled(true);
					}
				});
				
				// F�llen der Table tableAnwendungen mit den Eintr�gen
				// aus den Properties, die mit einem "." beginnen.
				logger.warn("Not implemented from here ...");/*
				for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
				{
					// alle Properties, die mit einem "." beginnen, sind Anwendungen
					String sProperty = e.nextElement().toString();
					if (sProperty.charAt(0)=='.')
					{
						TableItem newItem = new TableItem(tableAnwendungen, 0);
						newItem.setText(new String[] {sProperty, properties.getProperty(sProperty)});
					} // if
				} // for
				*/
				logger.warn("......... to here");
			}
			{
				Composite cButtons = new Composite(compositeAnwendungen, SWT.NONE);
		        GridLayout layout = new GridLayout();
				layout.verticalSpacing = 20;
				cButtons.setLayout(layout);

				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.END;
					data.verticalAlignment = GridData.END;
					cButtons.setLayoutData(data);
				}
				{
					BtnNeu = new Button(cButtons, SWT.PUSH | SWT.CENTER);
					BtnNeu.setText("   Neu ...  ");
					BtnNeu.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							btnNeu();
						}
					});
	
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					BtnNeu.setLayoutData(data);
				}
				{
					BtnAendern = new Button(cButtons, SWT.PUSH | SWT.CENTER);
					BtnAendern.setText("   �ndern ...  ");
					BtnAendern.setEnabled(false);
					BtnAendern.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							btnAendern();
						}
					});
	
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					BtnAendern.setLayoutData(data);
				}
				{
					BtnLoeschen = new Button(cButtons, SWT.PUSH | SWT.CENTER);
					BtnLoeschen.setText("   L�schen   ");
					BtnLoeschen.setEnabled(false);
					BtnLoeschen.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							btnLoeschen();
						}
					});
	
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					BtnLoeschen.setLayoutData(data);
				}
			}
		}
		{
			Composite cButtons = new Composite(shell, SWT.NONE);
			cButtons.setBackground(new Color(shell.getDisplay(), rgbBackground));
			{
		        GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				layout.horizontalSpacing = 20;
				layout.makeColumnsEqualWidth = true;
				cButtons.setLayout(layout);
			}

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.CENTER;
				cButtons.setLayoutData(data);
			}

			{
				BtnOK = new Button(cButtons, SWT.PUSH | SWT.CENTER);
				BtnOK.setText("   OK   ");
				BtnOK.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						btnOK();
					}
				});

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				BtnOK.setLayoutData(data);
				
				BtnOK.setFocus();
			}
			{
				BtnCancel = new Button(cButtons, SWT.PUSH | SWT.CENTER);
				BtnCancel.setText("   Abbrechen   ");
				BtnCancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						btnCancel();
					}
				});

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				BtnCancel.setLayoutData(data);
			}
		}
	}
	
	/**
	 * btnNeu startet den Dialog AnwendungenDialog, mit dem neue 
	 * Anwendungen f�r Dateierweiterungen eingerichtet werden k�nnen.
	 */
	public void btnNeu()
	{
		AnwendungenDialog dialog = new AnwendungenDialog(shell);
		String sErg[] = dialog.open();
		
		// TODO @Andy Plausi, damit Eintr�ge nicht doppelt vorkommen

		// Falls "Abbrechen" gedr�ckt wurde, ist sErg == null.
		if ( sErg != null )
		{
			TableItem newItem = new TableItem(tableAnwendungen, 0);
			newItem.setText(new String[] {sErg[0], sErg[1]});
		}
	}
	
	/**
	 * btnAendern startet den Dialog AnwendungenDialog, mit dem die 
	 * aktuell ausgew�hlte Zeile der Table tableAnwendungen 
	 * bearbeitet werden kann.
	 */
	public void btnAendern()
	{
		// Die in der ausgew�hlten Zeile angezeigte Einstellung
		// wird ge�ndert.
		int index = tableAnwendungen.getSelectionIndex();
		
		if (index >= 0)
		{
			TableItem ti = tableAnwendungen.getItem(index);

			AnwendungenDialog dialog = new AnwendungenDialog(shell, ti.getText(0), ti.getText(1));
			String sErg[] = dialog.open();
	
			// TODO @Andy Plausi, damit Eintr�ge nicht doppelt vorkommen
			
			// Falls "Abbrechen" gedr�ckt wurde, ist sErg == null.
			if ( sErg != null )
			{
				ti.setText(new String[] {sErg[0], sErg[1]});
			}
		}
	}
	
	/**
	 * btnLoeschen l�scht die aktuell ausgew�hlte Zeile der Table 
	 * tableAnwendungen. 
	 */
	public void btnLoeschen()
	{
		// Die ausgew�hlte Zeile wird gel�scht.
		int index = tableAnwendungen.getSelectionIndex();
		if (index >= 0)
		{
			tableAnwendungen.remove(tableAnwendungen.getSelectionIndex());
		}
		
		BtnAendern.setEnabled(false);
		BtnLoeschen.setEnabled(false);
	}

	/**
	 * Beim Bestätigen des Buttons btnOK werden die neuen Einträge 
	 * als Properties gesetzt und der Dialog geschlossen.
	 */
	public void btnOK()
	{
		// TODO @Andy Plausis fehlen noch
		ConfigLoader.update("net/port", textPort.getText());
		ConfigLoader.update("net/host", textHost.getText());
		ConfigLoader.update("dirs/dir[@type='repository']", labelRepository.getText());
		ConfigLoader.update("dirs/dir[@type='output']", labelOutput.getText());
		
		// Erst werden alle Properties, die mit einem "." beginnen gel�scht,
		// dann werden die Properties, die in der Table tableAnwendungen
		// eingetragen sind, gesetzt. 
		// Alle Properties l�schen, die mit einem "." beginnen.
		
		logger.warn("Not implemented from here ...");/*
		for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
		{
			String sProperty = e.nextElement().toString();
			if (sProperty.charAt(0)=='.')
			{
				properties.remove(sProperty);
			}
		}
		
		// Alle Eintr�ge von tableAnwendungen als Properties setzen.
		for (int i=0; i<tableAnwendungen.getItemCount(); i++)
		{
			TableItem ti = tableAnwendungen.getItem(i);
			properties.setProperty(ti.getText(0), ti.getText(1));
		}*/
		logger.warn("......... to here");

		shell.close();
	}
	
	/**
	 * btnCancel schlie�t den Dialog ohne R�ckgabe der 
	 * eingetragenen Werte.
	 * Die Properties werden nicht neu gesetzt.
	 */
	public void btnCancel()
	{
		shell.close();
	}
}
