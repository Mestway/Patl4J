package org.openfuxml.client.gui.swt.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.openfuxml.client.control.OfxGuiAction;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;

public class ProducerComboFactory
{
	final static Logger logger = LoggerFactory.getLogger(ProducerComboFactory.class);
	
	private Composite composite;
	private OfxGuiAction ofxAction;
	
	public ProducerComboFactory(Composite composite, OfxGuiAction ofxAction)
	{
		this.composite=composite;
		this.ofxAction=ofxAction;
	}
	
	public Combo createCboApplication()
	{
		final Combo cboApplications = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboApplications.setData("Application");

		GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			cboApplications.setLayoutData(data);

		cboApplications.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				OfxApplication selectedOfxA = (OfxApplication)cboApplications.getData(cboApplications.getText());
				ofxAction.cboApplicationSelected(selectedOfxA);
			}
		});
		return cboApplications;
	}
	
	public Combo createCboProject()
	{
		final Combo cboProjects = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboProjects.setData("Project");
		
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboProjects.setLayoutData(data);

		cboProjects.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				OfxProject ofxP = (OfxProject)cboProjects.getData(cboProjects.getText());
				ofxAction.cboProjectSelected(ofxP);
			}
		});
		return cboProjects;
	}
	
	public Combo createCboDocument()
	{
		final Combo cboDocuments = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboDocuments.setData("Document");
		
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboDocuments.setLayoutData(data);

		cboDocuments.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				OfxDocument selectedofxD = (OfxDocument)cboDocuments.getData(cboDocuments.getText());
				ofxAction.cboDocumentSelected(selectedofxD);
			}
		});
		return cboDocuments;
	}
	
	public Combo createCboFormats()
	{
		final Combo cboFormats = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboFormats.setData("Format");

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboFormats.setLayoutData(data);

	
		cboFormats.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				OfxFormat selectedOfxF = (OfxFormat)cboFormats.getData(cboFormats.getText());
				ofxAction.cboFormateSelected(selectedOfxF);
			}
		});
		return cboFormats;
	}
}