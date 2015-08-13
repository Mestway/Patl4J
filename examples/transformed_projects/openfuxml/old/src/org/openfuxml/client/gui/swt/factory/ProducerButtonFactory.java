package org.openfuxml.client.gui.swt.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.openfuxml.client.control.OfxGuiAction;
import org.openfuxml.client.gui.simple.Client;

public class ProducerButtonFactory
{
	private Client client;
	private Composite composite;
	private OfxGuiAction ofxAction;
	
	public ProducerButtonFactory(Client client,Composite composite,OfxGuiAction ofxAction)
	{
		this.client=client;
		this.composite=composite;
		this.ofxAction=ofxAction;
	}
	
	public ProducerButtonFactory(Composite composite,OfxGuiAction ofxAction)
	{
		this.composite=composite;
		this.ofxAction=ofxAction;
	}
	
	public Button createBtnChange(String label)
	{
		Button btnChange = new Button(composite, SWT.PUSH | SWT.CENTER);
		btnChange.setText(label);

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		btnChange.setLayoutData(data);

		btnChange.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent evt) {
				client.Einstellungen();
				}
			});
		return btnChange;
	}
	
	public Button createBtnUpdate(String label)
	{
		Button btnUpdate = new Button(composite, SWT.PUSH | SWT.CENTER);
		btnUpdate.setText(label);

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		btnUpdate.setLayoutData(data);

		btnUpdate.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent evt) {ofxAction.btnUpdate();}
		});
		return btnUpdate;
	}
	
	public Button createBtnProduce(String label)
	{
		Button btnProduce = new Button(composite, SWT.PUSH | SWT.CENTER);
		btnProduce.setText(label);

		GridData data = new GridData();
		data.verticalAlignment = GridData.END;
		data.horizontalAlignment = GridData.FILL;
		btnProduce.setLayoutData(data);

		btnProduce.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				ofxAction.btnProduce();
			}
		});
		return btnProduce;
	}
}
