package org.openfuxml.client.gui.simple;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author Andrea Frank
 */
public class OeffnenDialog extends Dialog
{
	final static Logger logger = LoggerFactory.getLogger(OeffnenDialog.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Shell shell;

	private Table tableProducedEntities;
	private Button BtnSchliessen;
	
	private ArrayList<String[]> alProducedEntities;
	private RGB rgbBackground;
	
	private Configuration config;
	
	public OeffnenDialog(Shell parent, ArrayList<String[]> alProducedEntities, RGB rgb, Configuration config)
	{
		super(parent, 0);
		this.config=config;
		this.alProducedEntities = alProducedEntities;
		this.rgbBackground = rgb;
	}
	
	/**
	 * open �ffnet den Dialog.
	 * @param images
	 */
	public void open(Image[] images)
	{
		final Shell parent = this.getParent();
		
		shell = new Shell(parent, (SWT.RESIZE) | (SWT.DIALOG_TRIM) | SWT.APPLICATION_MODAL);
		
		shell.setText("�ffnen");
		
		initGUI();

		fuelleTableProducedEntities();

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
			tableProducedEntities = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);

			{
				GridData data = new GridData();
				data.widthHint = 550;
				data.heightHint = 250;
				data.grabExcessHorizontalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessVerticalSpace = true;
				data.verticalAlignment = GridData.FILL;
				tableProducedEntities.setLayoutData(data);
			}
			
			{
				TableColumn tableColumn = new TableColumn(tableProducedEntities, SWT.NONE);
				tableColumn.setText("Beschreibung");
				tableColumn.setWidth(170);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProducedEntities, SWT.NONE);
				tableColumn.setText("Serverausgabe");
				tableColumn.setWidth(180);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProducedEntities, SWT.NONE);
				tableColumn.setText("Dateiname");
				tableColumn.setWidth(100);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProducedEntities, SWT.NONE);
				tableColumn.setText("Produziert am");
				tableColumn.setWidth(100);
			}
			tableProducedEntities.setHeaderVisible(true);
			tableProducedEntities.setLinesVisible(true);
			
			tableProducedEntities.addSelectionListener(new SelectionAdapter() {
				/**
				 * Bei der Auswahl eines Eintrags (Doppel-Klick bzw. ENTER) wird 
				 * dieser im eingestellten Programm angezeigt.
				 * Das Programm wird mit Hilfe der File-Extension aus den
				 * Properties ermittelt.
				 */
				public void widgetDefaultSelected(SelectionEvent evt) {
					// Bestimmen des ausgewählten Eintrags.
					TableItem[] selection = tableProducedEntities.getSelection();
					TableItem selectedRow = selection[0];

					DateiOeffnen(selectedRow.getText(1),selectedRow.getText(2));
				}
			});
			
		}
		{
			BtnSchliessen = new Button(shell, SWT.PUSH | SWT.CENTER);
			BtnSchliessen.setText("   Schließen   ");
			BtnSchliessen.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					shell.close();
				}
			});

			GridData data = new GridData();
			data.horizontalAlignment = GridData.CENTER;
			BtnSchliessen.setLayoutData(data);
			
			BtnSchliessen.setFocus();
		}
	}
	
	/**
	 * Die Methode fuelleTableProducedEntities f�llt die Table 
	 * tableProducedEntities mit den Eintr�gen aus der ArrayList 
	 * alProducedEntities.
	 */
	public void fuelleTableProducedEntities()
	{
		// F�llen der Table mit den Werten aus der ArrayList.
		for (int i=0; i<alProducedEntities.size(); i++)
		{
			TableItem newItem = new TableItem(tableProducedEntities, 0);
			String pe[] = (String[])alProducedEntities.get(i);
			newItem.setText(new String[] {pe[0], pe[1], pe[2], pe[3]});
		} // for
	} // fuelleTableProducedEntities

	/**
	 * DateiOeffnen bestimmt anhand des Dateinamens das auszuf�hrende Programm.
	 * Das Programm wird mit Hilfe der File-Extension aus den Properties ermittelt.
	 * 
	 * Dieses Programm wird mit der Datei als Parameter gestartet.
	 * TODO: Ist in dem Parameter sVerzeichnis der String "validation"
	 * enthalten, wird diese Datei im browserCompo angezeigt.
	 * 
	 * @param sVerzeichis
	 * @param sDateiname
	 */
	private void DateiOeffnen(String sVerzeichnis, String sDateiname)
	{
		String sErweiterung = sDateiname.substring(sDateiname.lastIndexOf('.'));

		//TODO Erweiterungen f�r OpenDialog
		//String sProgramm = ClientConfigWrapper.getClientConf(sErweiterung);
		String sProgramm=null;
		
		if (sProgramm == null)
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie haben bisher kein Programm f�r Dateien mit der Erweiterung \""
					+ sErweiterung + "\" eingestellt."); 
			messageBox.open();
		}
		else
		{
			String sKommando = sProgramm + " ";
			
			// Die Ausgabedatei steht in dem Output-Verzeichnis
			File file = new File(config.getString("dirs/dir[@type='output']") + fs + sVerzeichnis, sDateiname);
			sKommando += file.toString();		 
			
			logger.debug("Kommando f�r das �ffnen: " + sKommando);
			
			try
			{
				Runtime.getRuntime().exec(sKommando);
			}
			catch (Exception e)
			{
				logger.fatal("Exception", e);
			}
		} // else
	}
}
