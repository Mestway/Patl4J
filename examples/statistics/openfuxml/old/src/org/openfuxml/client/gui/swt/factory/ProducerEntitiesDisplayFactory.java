package org.openfuxml.client.gui.swt.factory;

import net.sf.exlp.io.resourceloader.ImageResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.OfxGuiAction;
import org.openfuxml.client.gui.simple.factory.SimpleLabelFactory;
import org.openfuxml.model.jaxb.ProducibleEntities;

public class ProducerEntitiesDisplayFactory
{
	final static Logger logger = LoggerFactory.getLogger(ProducerEntitiesDisplayFactory.class);
	
	private OfxGuiAction ofxAction;
	private Configuration config;
	private ImageResourceLoader irl;
	
	public ProducerEntitiesDisplayFactory(OfxGuiAction ofxAction, Configuration config, ImageResourceLoader irl)
	{
		this.ofxAction=ofxAction;
		this.config=config;
		this.irl=irl;
	}
	
	public TabFolder createTabFolder(Composite composite)
	{
		TabFolder tf = new TabFolder(composite, SWT.TOP);
		GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.horizontalSpan = 4;
			tf.setLayoutData(data);
		return tf;
	}
	
	public ScrolledComposite createMatrix(Composite composite)
	{
		ScrolledComposite scrolledCompositeMatrix = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.V_SCROLL);

		GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.horizontalSpan = 1;
			scrolledCompositeMatrix.setLayoutData(data);

		Composite compositeMatrix = new Composite(scrolledCompositeMatrix, SWT.NONE);
		SimpleLabelFactory slf = new SimpleLabelFactory(compositeMatrix,irl);
		
		GridLayout layout = new GridLayout();
			layout.numColumns = 5;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			//layout.makeColumnsEqualWidth = true;
			compositeMatrix.setLayout(layout);

		slf.createLabel("Kurseinheit");
		slf.createLabel("Lehrtext");
		slf.createLabel("Einsendeaufgaben");
		slf.createLabel("Musterl�sungen");
		slf.createLabel("Korrekturversion");

		int anzKE = 8;
		int anzEntities = 4;
		for(int i=0;i<anzKE;i++)
		{
			slf.createLabel(i);
			for(int j=0;j<anzEntities;j++)
			{
				Button b = new Button(compositeMatrix, SWT.CHECK);
				b.setEnabled(false);
			}
		}
		
		Point pt = compositeMatrix.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledCompositeMatrix.setContent(compositeMatrix);
		scrolledCompositeMatrix.setExpandHorizontal(true);
		scrolledCompositeMatrix.setExpandVertical(true);
		scrolledCompositeMatrix.setMinWidth(pt.x);
		scrolledCompositeMatrix.setMinHeight(pt.y);
		
		scrolledCompositeMatrix.getVerticalBar().setIncrement(10);
		scrolledCompositeMatrix.getVerticalBar().setPageIncrement(10);
		scrolledCompositeMatrix.getHorizontalBar().setIncrement(10);
		scrolledCompositeMatrix.getHorizontalBar().setPageIncrement(10);
		return scrolledCompositeMatrix;
	}
	
	public Table createTable(Composite composite)
	{
		final Table tabDiscoveredEntities = new Table(composite, SWT.CHECK | SWT.BORDER);

		GridData data = new GridData();
			data.widthHint = 450;
			data.heightHint = 200;
			data.horizontalSpan = 2;
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			tabDiscoveredEntities.setLayoutData(data);

		TableColumn tableColumn = new TableColumn(tabDiscoveredEntities, SWT.NONE);
			tableColumn.setText("");
			tableColumn.setWidth(20);

		tableColumn = new TableColumn(tabDiscoveredEntities, SWT.NONE);
			tableColumn.setText("Beschreibung");
			tableColumn.setWidth(160);

		tableColumn = new TableColumn(tabDiscoveredEntities, SWT.NONE);
			tableColumn.setText("Serverausgabe");
			tableColumn.setWidth(180);
			
		tableColumn = new TableColumn(tabDiscoveredEntities, SWT.NONE);
			tableColumn.setText("Dateiname");
			tableColumn.setWidth(100);

		tabDiscoveredEntities.setHeaderVisible(true);
		tabDiscoveredEntities.setLinesVisible(true);
		
		tabDiscoveredEntities.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent evt)
			{
				ProducibleEntities pe = new ProducibleEntities();
				for(int i=0; i<tabDiscoveredEntities.getItemCount(); i++)
				{
					TableItem tableItem = tabDiscoveredEntities.getItem(i);
					if (tableItem.getChecked())
					{
						ProducibleEntities.File f = (ProducibleEntities.File)tableItem.getData();
						pe.getFile().add(f);
					} 
				}
				ofxAction.tblEntitiesSelected(pe);
			}
			
			public void widgetDefaultSelected(SelectionEvent evt)
			{
				TableItem[] selection = tabDiscoveredEntities.getSelection();
				TableItem selectedRow = selection[0];
				ProducibleEntities.File f = (ProducibleEntities.File)selectedRow.getData();
				ofxAction.openDocument(f);
			}
		});
		return tabDiscoveredEntities;
	}
}