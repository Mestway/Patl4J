package org.openfuxml.client.gui.swt.composites;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.jaxb.ProducibleEntities;

public class AbstractProducerComposite extends Composite
{
	final static Logger logger = LoggerFactory.getLogger(AbstractProducerComposite.class);
	
	protected OfxClientControl ofxCC;
	protected Combo cboFormats, cboDocuments;
	protected Table tabDiscoveredEntities;
	
	protected Shell toplevelShell;
	protected Display display;
	
	public AbstractProducerComposite(Composite parent, int swt)
	{
		super(parent, swt);
	}
	
	/**
	 * Die Methode fuelleTableProductionEntities füllt die Tabelle
	 * tableProductionEntities.
	 * Dabei werden anhand der ausgewählten 3 Auswahlfelder aus der Hashtable 
	 * die producableEntities in Form einer SSIMessage ermittelt.
	 * Diese werden nach den Elementen Beschreibung, Verzeichnis und Dateiname
	 * "aufgedröselt" und als neuen Eintrag in die Tabelle eingefügt.
	 */
	public void entitiesDiscovered()
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{
					tabDiscoveredEntities.removeAll();
					ProducibleEntities pe = ofxCC.getCachedProducibleEntities();
					if (pe != null)
					{
						for(ProducibleEntities.File f :pe.getFile())
						{
							TableItem tblItem = new TableItem(tabDiscoveredEntities, 0);
							tblItem.setData(f);
							tblItem.setText(new String[] {"", f.getDescription(),f.getDirectory(), f.getFilename()});
						}
					};
				}
			}
		});
	}
	
	public void fillCboFormats()
	{
		cboFormats.removeAll();

		List<OfxFormat> lFormats = ofxCC.getAvailableFormats();
		if(lFormats!=null && lFormats.size()>0)
		{
			for(OfxFormat ofxF : lFormats)
			{
				cboFormats.add(ofxF.getFormat().getTitle());
				cboFormats.setData(ofxF.getFormat().getTitle(),ofxF);
			}
		}
		else {logger.error("Server meldet keine Formate!");}
	}
	
	/**
	 * Die Methode fuelleComboDokumente schreibt alle Dateien aus dem Verzeichnis 
	 * "labelVerzeichnis.getText()/comboAnwendungen.getText()/comboProjekte.getText()", 
	 * die die Endung ".xml" haben, in die Combo comboDokumente.
	 */
	public void fillCboDocuments()
	{
		cboDocuments.removeAll();
//		tabDiscoveredEntities.removeAll();
		

		List<OfxDocument> lOfxD = ofxCC.lDocuments();
		
		for (OfxDocument ofxD : lOfxD)
		{
			cboDocuments.add(ofxD.getName());
			cboDocuments.setData(ofxD.getName(),ofxD);
		}

	}
}
