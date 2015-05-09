package org.openfuxml.client.gui.simple.factory;

import net.sf.exlp.io.resourceloader.ImageResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SimpleLabelFactory
{
	final static Logger logger = LoggerFactory.getLogger(SimpleLabelFactory.class);
	 
	private static String fs = SystemUtils.FILE_SEPARATOR;
	private Composite composite;
	private Configuration config;
	private ImageResourceLoader irl;
	
	public SimpleLabelFactory(Composite composite, Configuration config, ImageResourceLoader irl)
	{
		this.composite=composite;
		this.config=config;
		this.irl=irl;
	}
	
	public SimpleLabelFactory(Composite composite, ImageResourceLoader irl)
	{
		this.composite=composite;
		this.irl=irl;
	}
	
	public Label createLabel(int i){return createLabel(""+i, 1);}
	public Label createLabel(String text){return createLabel(text, 1);}
	public Label createLabel(String text, int horizontalSpan)
	{
		Label label = new Label(composite, SWT.NONE);
		label.setText(text);

		GridData data = new GridData();
		data.horizontalSpan = horizontalSpan;
		label.setLayoutData(data);
		return label;
	}
	
	public void createDummyLabel(int horizontalSpan)
	{
		createLabel("",horizontalSpan);
	}
	
	public void createLogo()
	{
		String[] logoRes = config.getStringArray("logos/logo[@type='fuxklein']");
		logger.debug("Creating Logos: "+logoRes.length);
		
		for(int i=0;i<logoRes.length;i++)
		{
		
			Label labelImage = new Label(composite, SWT.NONE);
			labelImage.setBackground(composite.getBackground());
	
			GridData data = new GridData();
			if(logoRes.length==1){data.horizontalSpan = 3;}
			else if(logoRes.length==2){data.horizontalSpan = 2-i;}
			else if(logoRes.length==3){data.horizontalSpan = 1;}
			data.horizontalAlignment = GridData.END;
			data.verticalAlignment = GridData.FILL;
			
			try
			{
				String res = config.getString("logos/@dir")+fs+logoRes[i];
				Image img = irl.search(this.getClass().getClassLoader(), res, composite.getDisplay());
				data.widthHint = img.getBounds().width;
				data.heightHint = img.getBounds().height;
				labelImage.setImage(img);
			}
			catch (Exception e)
			{
				labelImage.setText("ERROR");
				logger.error("",e);
				labelImage.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			labelImage.setLayoutData(data);
		}
	}
		
	public Label creatLblEvent()
	{
		Label lblEvent = new Label(composite, SWT.NONE);
		lblEvent.setText("");
		lblEvent.setBackground(composite.getBackground());

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		lblEvent.setLayoutData(data);
		return lblEvent;
	}
}
