package org.openfuxml.client.gui.swt.dialog;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.openfuxml.client.gui.swt.OpenFuxmlClient;

/**
 * @author Andrea Frank
 */
public class EinstellungenDialog extends Dialog
{
	private Shell shell;

	private TabFolder tfEinstellungen;

	private TabItem tiBenutzer;
	private Composite compositeBenutzer;
	private Label labelBenutzername;
	private Text textBenutzername;
	private Button BtnAutoLogin;
	private Label labelKennwort;
	private Text textKennwort;
	private Button BtnKennwortSpeichern;
	private Label labelKennwortHinweis;
	private Button BtnRegistrieren;

	private TabItem tiServer;
	private Composite compositeServer;
	private Text textHost;
	private Text textPort;

	private TabItem tiVerzeichnisse;
	private Composite compositeVerzeichnisse;
	private Label labelRepository;
	private Button buttonRepository;
	private Label labelOutput;
	private Button buttonOutput;
	private Button BtnNetz;
	private Label lHTTPUser;
	private Label lHTTPKennwort;
	private Text tHTTPUser;
	private Text tHTTPKennwort;

	private TabItem tiAnwendungen;
	private Composite compositeAnwendungen;
	private Table tableAnwendungen;
	private Button BtnNeu;
	private Button BtnAendern;
	private Button BtnLoeschen;

	private Button BtnOK;
	private Button BtnCancel;
	
	private Properties properties;
	
	private OpenFuxmlClient client;
	
	public EinstellungenDialog(OpenFuxmlClient client, Properties properties)
	{
		super(client.getShell(), 0);
		this.client = client;
		this.properties = properties;
	}
	
	public Properties open()
	{
		final Shell parent = this.getParent();
		
		shell = new Shell(parent, (SWT.DIALOG_TRIM) | SWT.APPLICATION_MODAL);
		
		shell.setText(OpenFuxmlClient.Title + " - Einstellungen");
		
		initGUI();

		compositeVerzeichnisse.pack();
		tfEinstellungen.pack();
		shell.pack();
		
//		textHost.setText(jndiHost.getHost());

		shell.open();
		
		final Display display = parent.getDisplay();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		
		return properties;
	}

	/**
	* Initializes the GUI.
	*/
	private void initGUI()
	{
		{
			// Erst das Layout des Dialoges bestimmen.
			GridLayout layout = new GridLayout();
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			shell.setLayout(layout);
		}
		{
			// TabFolder generieren
			tfEinstellungen = new TabFolder(shell, SWT.TOP);
			tfEinstellungen.setSelection(0);
			GridData data = new GridData();
			tfEinstellungen.setLayoutData(data);
		}
		{
			// TabItems generieren
			// Die Inhalte der TabItems kommen weiter unten.
			tiBenutzer = new TabItem(tfEinstellungen, SWT.NONE);
			tiBenutzer.setText("Benutzer");
			tiServer = new TabItem(tfEinstellungen, SWT.NONE);
			tiServer.setText("Server");
			tiVerzeichnisse = new TabItem(tfEinstellungen, SWT.NONE);
			tiVerzeichnisse.setText("Verzeichnisse");
			tiAnwendungen = new TabItem(tfEinstellungen, SWT.NONE);
			tiAnwendungen.setText("Anwendungen");
		}
		{
			Composite cButtons = new Composite(shell, SWT.NONE);
	        GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.horizontalSpacing = 20;
			layout.makeColumnsEqualWidth = true;
			cButtons.setLayout(layout);

			GridData data1 = new GridData();
			data1.horizontalAlignment = GridData.END;
			cButtons.setLayoutData(data1);
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
		
		{
			// Inhalt des TabItem tiBenutzer
			compositeBenutzer = new Composite(tfEinstellungen, SWT.NONE);
	        GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			compositeBenutzer.setLayout(layout);

			tiBenutzer.setControl(compositeBenutzer);
			{
				labelBenutzername = new Label(compositeBenutzer, SWT.NONE);
				labelBenutzername.setText("Benutzername:");
			}
			{
				textBenutzername = new Text(compositeBenutzer, SWT.BORDER);
				textBenutzername.setText(properties.getProperty("Benutzername",""));
				
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.widthHint = 100;
				textBenutzername.setLayoutData(data);
			}
			{
				BtnAutoLogin = new Button(compositeBenutzer, SWT.CHECK | SWT.LEFT);
				BtnAutoLogin.setText("Auto-Login");
			}
			{
				labelKennwort = new Label(compositeBenutzer, SWT.NONE);
				labelKennwort.setText("Kennwort:");
			}
			{
				textKennwort = new Text(compositeBenutzer, SWT.BORDER | SWT.PASSWORD);
				textKennwort.setText(properties.getProperty("Kennwort",""));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				textKennwort.setLayoutData(data);
			}
			{
				BtnKennwortSpeichern = new Button(compositeBenutzer, SWT.CHECK | SWT.LEFT);
				BtnKennwortSpeichern.setText("Kennwort speichern");
			
				BtnKennwortSpeichern.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						BtnAutoLogin.setEnabled(!BtnAutoLogin.getEnabled());
						labelKennwortHinweis.setVisible(!labelKennwortHinweis.getVisible());
					}
				});
			}
			{
				labelKennwortHinweis = new Label(compositeBenutzer, SWT.NONE);
				labelKennwortHinweis.setText("Hinweis: Das Kennwort wird unverschl�sselt gespeichert und versendet.");

				GridData data = new GridData();
				data.horizontalSpan = 3;
				labelKennwortHinweis.setLayoutData(data);
			}
			{
				BtnRegistrieren = new Button(compositeBenutzer, SWT.PUSH | SWT.CENTER);
				BtnRegistrieren.setText("   Als neuer Benutzer registrieren ...   ");

				GridData data = new GridData();
				data.horizontalSpan = 3;
				data.horizontalAlignment = GridData.END;
				BtnRegistrieren.setLayoutData(data);
				
				BtnRegistrieren.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
//						btnRegistrieren();
					}
				});
			}
			
			{
				// H�kchen f�r KennwortSpeichern und AutoLogin setzen
				if (properties.getProperty("KennwortSpeichern","0").equals("1"))
				{
					BtnKennwortSpeichern.setSelection(true);
					BtnAutoLogin.setEnabled(true);
				}
				else
				{
					BtnKennwortSpeichern.setSelection(false);
					BtnAutoLogin.setEnabled(false);
				}
				if (properties.getProperty("AutoLogin","0").equals("1"))
				{
					BtnAutoLogin.setSelection(true);
				}
				else
				{
					BtnAutoLogin.setSelection(false);
				}
				labelKennwortHinweis.setVisible(BtnKennwortSpeichern.getSelection());
			}
		}
		{
			// Inhalt des TabItem tiServer
			compositeServer = new Composite(tfEinstellungen, SWT.NONE);
	        GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			compositeServer.setLayout(layout);

			tiServer.setControl(compositeServer);
			{
				Label label = new Label(compositeServer, SWT.NONE);
				label.setText("Host:");
			}
			{
				textHost = new Text(compositeServer, SWT.BORDER);
				textHost.setText(properties.getProperty("Host",""));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.widthHint = 300;
				textHost.setLayoutData(data);
			}
			{
				Label label = new Label(compositeServer, SWT.NONE);
				label.setText("Port:");
			}
			{
				textPort = new Text(compositeServer, SWT.BORDER);
				textPort.setText(properties.getProperty("Port",""));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				textPort.setLayoutData(data);
			}
		}
		
		{
			// Inhalt des TabItem tiVerzeichnisse
			compositeVerzeichnisse = new Composite(tfEinstellungen, SWT.NONE);
	        GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			compositeVerzeichnisse.setLayout(layout);

			tiVerzeichnisse.setControl(compositeVerzeichnisse);
			{
				Label label = new Label(compositeVerzeichnisse, SWT.NONE);
				label.setText("Repository:");
			}
			{
				labelRepository = new Label(compositeVerzeichnisse, SWT.NONE);
				labelRepository.setText(properties.getProperty("Verzeichnis",""));
				
				GridData data = new GridData();
				data.horizontalSpan = 2;
				data.horizontalAlignment = GridData.FILL;
				data.widthHint = 500;
				labelRepository.setLayoutData(data);
			}
			{
				buttonRepository = new Button(compositeVerzeichnisse, SWT.PUSH | SWT.CENTER);
				buttonRepository.setText("...");
				
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				buttonRepository.setLayoutData(data);

				buttonRepository.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						DirectoryDialog dialog = new DirectoryDialog(shell);
						dialog.setText(OpenFuxmlClient.Title);
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
				labelOutput.setText(properties.getProperty("Output",""));

				GridData data = new GridData();
				data.horizontalSpan = 2;
				data.horizontalAlignment = GridData.FILL;
				labelOutput.setLayoutData(data);
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
						dialog.setText(OpenFuxmlClient.Title);
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
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			{
				BtnNetz = new Button(compositeVerzeichnisse, SWT.CHECK);
				BtnNetz.setText("Netzlaufwerk des Servers");

				GridData data = new GridData();
				data.horizontalSpan = 2;
				data.horizontalAlignment = GridData.FILL;
				BtnNetz.setLayoutData(data);

				BtnNetz.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						lHTTPUser.setEnabled(!lHTTPUser.getEnabled());
						tHTTPUser.setEnabled(!tHTTPUser.getEnabled());
						lHTTPKennwort.setEnabled(!lHTTPKennwort.getEnabled());
						tHTTPKennwort.setEnabled(!tHTTPKennwort.getEnabled());
					}
				});
			}
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			{
				lHTTPUser = new Label(compositeVerzeichnisse, SWT.NONE);
				lHTTPUser.setText("HTTP - Benutzername");
			}
			{
				tHTTPUser = new Text(compositeVerzeichnisse, SWT.BORDER);
				tHTTPUser.setText(properties.getProperty("HTTP-User",""));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				tHTTPUser.setLayoutData(data);
			}
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			{
				lHTTPKennwort = new Label(compositeVerzeichnisse, SWT.NONE);
				lHTTPKennwort.setText("HTTP - Kennwort");
			}
			{
				tHTTPKennwort = new Text(compositeVerzeichnisse, SWT.BORDER | SWT.PASSWORD);
				tHTTPKennwort.setText(properties.getProperty("HTTP-Kennwort",""));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				tHTTPKennwort.setLayoutData(data);
			}
			{
				// Dummylabel
				new Label(compositeVerzeichnisse, SWT.NONE);
			}
			
			{
				// H�kchen f�r NetzOutput

				if (properties.getProperty("NetzOutput","0").equals("1"))
				{
					BtnNetz.setSelection(true);
					lHTTPUser.setEnabled(false);
					tHTTPUser.setEnabled(false);
					lHTTPKennwort.setEnabled(false);
					tHTTPKennwort.setEnabled(false);
				}
				else
				{
					BtnNetz.setSelection(false);
					lHTTPUser.setEnabled(true);
					tHTTPUser.setEnabled(true);
					lHTTPKennwort.setEnabled(true);
					tHTTPKennwort.setEnabled(true);
				}
			}
		}
		
		{
			// Inhalt des TabItem tiAnwendungen
			compositeAnwendungen = new Composite(tfEinstellungen, SWT.NONE);
			{
		        GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				layout.marginHeight = 20;
				layout.marginWidth = 20;
				layout.horizontalSpacing = 20;
				layout.verticalSpacing = 20;
				compositeAnwendungen.setLayout(layout);
			}

			tiAnwendungen.setControl(compositeAnwendungen);
			{
				tableAnwendungen = new Table(compositeAnwendungen, SWT.FULL_SELECTION | SWT.SINGLE | SWT.CHECK);
				GridData data = new GridData();
				data.widthHint = 500;
				data.heightHint = 200;
				tableAnwendungen.setLayoutData(data);
				{
					TableColumn tableColumn = new TableColumn(tableAnwendungen, SWT.NONE);
					tableColumn.setText("Download");
					tableColumn.setWidth(100);
				}
				{
					TableColumn tableColumn = new TableColumn(tableAnwendungen, SWT.NONE);
					tableColumn.setText("Dateityp");
					tableColumn.setWidth(80);
				}
				{
					TableColumn tableColumn = new TableColumn(tableAnwendungen, SWT.NONE);
					tableColumn.setText("Anwendung");
					tableColumn.setWidth(400);
				}
				tableAnwendungen.setHeaderVisible(true);
				tableAnwendungen.setLinesVisible(true);
				
				tableAnwendungen.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						BtnAendern.setEnabled(true);
						BtnLoeschen.setEnabled(true);
					}
				});
				
			}
			{
				// Ermitteln, f�r welche Dateierweiterungen der AutoDownload gestartet werden soll
				String sDownload = properties.getProperty("AutoDownload", "");
				StringTokenizer st = new StringTokenizer(sDownload, ",");
				Hashtable<String, String> ht = new Hashtable<String, String>();
				while (st.hasMoreTokens())
				{
					String s = st.nextToken();
					ht.put(s,s);
				}

				for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
				{
					// alle Properties, die mit einem "." beginnen, sind Anwendungen
					String sProperty = e.nextElement().toString();
					if (sProperty.charAt(0)=='.')
					{
						TableItem newItem = new TableItem(tableAnwendungen, 0);
						newItem.setText(new String[] {"Download", sProperty, properties.getProperty(sProperty)});
						
						if (ht.get(sProperty) != null)
						{
							newItem.setChecked(true);
						}
					}
				}
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
					BtnNeu.setText("   Neu   ");
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
					BtnAendern.setText("   ändern   ");
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
					BtnLoeschen.setText("   Löschen   ");
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
	}
	
	public void btnOK()
	{
		// Testen ob �nderungen gemacht wurden, die ein erneutes Login erfordern.
		boolean flagLogin = false;
		
		if ( (!textBenutzername.getText().equals(properties.getProperty("Benutzername", ""))) ||
			(!textKennwort.getText().equals(properties.getProperty("Kennwort", ""))) ||
			(!textHost.getText().equals(properties.getProperty("Host", ""))) ||
			(!textPort.getText().equals(properties.getProperty("Port", ""))) )
		{
			flagLogin = true;
		}
		
		// Werte f�r Properties �bernehmen
		// TODO @Andy Plausis
		properties.setProperty("Benutzername", textBenutzername.getText());
		properties.setProperty("Kennwort", textKennwort.getText());
		if (BtnKennwortSpeichern.getSelection())
		{
			properties.setProperty("KennwortSpeichern", "1");
		}
		else
		{
			properties.setProperty("KennwortSpeichern", "0");
		}
		if (BtnAutoLogin.getSelection())
		{
			properties.setProperty("AutoLogin", "1");
		}
		else
		{
			properties.setProperty("AutoLogin", "0");
		}

		properties.setProperty("Host", textHost.getText());
		properties.setProperty("Port", textPort.getText());
		properties.setProperty("Verzeichnis", labelRepository.getText());
		properties.setProperty("Output", labelOutput.getText());
		if (BtnNetz.getSelection())
		{
			properties.setProperty("NetzOutput", "1");			
		}
		else
		{
			properties.setProperty("NetzOutput", "0");			
		}
		properties.setProperty("HTTP-User", tHTTPUser.getText());
		properties.setProperty("HTTP-Kennwort", tHTTPKennwort.getText());

		// Alle Properties l�schen, die mit einem "." beginnen.
		for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
		{
			String sProperty = e.nextElement().toString();
			if (sProperty.charAt(0)=='.')
			{
				properties.remove(sProperty);
			}
		}
		
		// Alle Eintr�ge von tableAnwendungen als Properties setzen.
		String sDownload = "";
		for (int i=0; i<tableAnwendungen.getItemCount(); i++)
		{
			TableItem ti = tableAnwendungen.getItem(i);
			properties.setProperty(ti.getText(1), ti.getText(2));
			
			if (ti.getChecked())
			{
				sDownload += ","+ti.getText(1);
			}
		}
		properties.remove("AutoDownload");
		if (!sDownload.equals(""))
		{
			sDownload = sDownload.substring(1);
			properties.setProperty("AutoDownload", sDownload);
		}

		if (flagLogin)
		{
			properties.setProperty("Login", "");
		}
		else
		{
			properties.remove("Login");
		}
		
		shell.close();
	}

	public void btnCancel()
	{
		properties.setProperty("Beenden", "");
		shell.close();
	}
	
/*	public void btnRegistrieren()
	{
		// Beim Registrieren werden die Servereinstellungen automatisch 
		// in die Properties �bernommen.
		client.getMyProperties().setProperty("Host", textHost.getText());
		client.getMyProperties().setProperty("Port", textPort.getText());
		properties.setProperty("Host", textHost.getText());
		properties.setProperty("Port", textPort.getText());
		
		InitialContext iniCtx = client.initNet();
		
		RegistrierenDialog dialog = new RegistrierenDialog(shell, iniCtx);
		String sErg [] = dialog.open();

		textBenutzername.setText(sErg[0]);
		textKennwort.setText(sErg[1]);
	}
*/
	public void btnNeu()
	{
		AnwendungenDialog dialog = new AnwendungenDialog(shell, false, "", "");
		String sErg[] = dialog.open();
		
		// TODO @Andy Plausi, damit Eintr�ge nicht doppelt vorkommen
		
		TableItem newItem = new TableItem(tableAnwendungen, 0);
		newItem.setText(new String[] {"Download", sErg[1], sErg[2]});
		if (sErg[0].equals("1"))
		{
			newItem.setChecked(true);
		}
	}
	
	public void btnAendern()
	{
		// Die in der ausgew�hlten Zeile angezeigte Einstellung
		// wird ge�ndert.
		int index = tableAnwendungen.getSelectionIndex();
		
		if (index >= 0)
		{
			TableItem ti = tableAnwendungen.getItem(index);

			AnwendungenDialog dialog = new AnwendungenDialog(shell, ti.getChecked(), ti.getText(1), ti.getText(2));
			String sErg[] = dialog.open();
	
			// TODO @Andy Plausi, damit Eintr�ge nicht doppelt vorkommen
			
			if (!sErg[1].equals("") && !sErg[2].equals(""))
			{
				ti.setText(new String[] {"Download", sErg[1], sErg[2]});
				
				if (sErg[0].equals("1"))
				{
					ti.setChecked(true);
				}
				else
				{
					ti.setChecked(false);
				}
					
			}
		}
	}
	
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
}
