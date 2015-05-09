/*
 * Created on 11.02.2005
 */
package org.openfuxml.client.gui.swt.composites;

import org.apache.commons.configuration.Configuration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.openfuxml.client.gui.util.LogSyntaxHighlightner;

/**
 * @author Thorsten Kisner
 */
public class LogComposite extends Composite
{
	private StyledText textLog;
	
	private Shell shell;
	private LogSyntaxHighlightner lsh;
	
	public LogComposite(Composite parent,Configuration config)
	{
		super(parent, SWT.NONE);
		this.shell = getShell();
		

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent evt) {

			}
		});		
		
		{
			GridLayout layout = new GridLayout();
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			this.setLayout(layout);
		}

		textLog = new StyledText(this, SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		textLog.setLayoutData(data);
		lsh=new LogSyntaxHighlightner(textLog,shell.getDisplay(),config);
	}
	
	public void clearLog()
	{
		shell.getDisplay().asyncExec(new Runnable(){
			public void run()
			{
				if (!shell.isDisposed())
				{
					textLog.setText("");
					lsh.clear();
				}
			}
		  });
	}
		
	public synchronized void addLogline(final String line)
	{
		shell.getDisplay().asyncExec(new Runnable(){
			public void run()
			{
				if (!shell.isDisposed())
				{
					ScrollBar sb = textLog.getVerticalBar();
			        boolean autoScroll = sb.getSelection() == (sb.getMaximum() - sb.getThumb());
			        int nbLines = textLog.getLineCount();
			        textLog.append(line+"\n");
			        lsh.highlight(line+"\n");
					if (autoScroll){ textLog.setTopIndex(nbLines-1);}
				}
			}
		  });
	}
}
