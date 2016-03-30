package org.openfuxml.client.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.ObjectIO;
import net.sf.exlp.io.resourceloader.ImageResourceLoader;
import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.openfuxml.client.gui.simple.Client;
import org.openfuxml.client.gui.simple.dialog.HelpAboutDialog;
import org.openfuxml.client.gui.swt.OpenFuxmlClient;
import org.openfuxml.util.config.factory.ClientConfFactory;

public class WsOpenFuXML
{
	final static Logger logger = LoggerFactory.getLogger(WsOpenFuXML.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Configuration config;
	
	public WsOpenFuXML()
	{

	}
	
	public void loadProperties()
	{
		ClientConfFactory ccf = new ClientConfFactory();
		ccf.init("openFuXML.xml");
		config = ccf.getConfiguration();
	}
	
	public void checkExtract()
	{
		File baseDir = new File(config.getString("dirs/dir[@type='basedir']"));
		File appDir = new File(baseDir,"applications");
		if(!appDir.exists())
		{
			logger.info("Extracting content ...");
			baseDir.mkdirs();
			File logDir = new File(baseDir.getAbsoluteFile(),"logs");
			logDir.mkdir();
			extract("dist","openFuXML-app.zip",baseDir.getAbsolutePath(),baseDir,"applications");
			extract("dist","openFuXML-lib.zip",baseDir.getAbsolutePath(),baseDir,"libraries");
			extract("dist","openFuXML-rscr.zip",baseDir.getAbsolutePath(),baseDir,"configuration");
		}
	}
	
	private void extract(String zipDir, String zipFileName,String baseDir,File appDir, String name)
	{
		try
		{
			MultiResourceLoader mrl = new MultiResourceLoader();
			InputStream is = mrl.searchIs(zipDir+"/"+zipFileName);
			if(is!=null)
			{
				logger.debug(is.getClass().getSimpleName()+" available: "+is.available());
				
				File zipFile = new File(baseDir+fs+zipFileName);
				ObjectIO.writeToFile(baseDir+fs+zipFileName, is);
				boolean result = ObjectIO.extractZip(zipFile, appDir);
				logger.debug("New Version ("+appDir.getName()+") of "+name+" extracted ... success?"+result);
				zipFile.deleteOnExit();
			}
		}
		catch (IOException e){logger.error("",e);}
	}
	
	public void initSimpleGui()
	{		
		Display disp = Display.getDefault();
		Shell sh = new Shell(disp);
		Client client = new Client(sh, disp,config);
		
		Point size = client.getSize();
		sh.setLayout(new FillLayout());
		sh.layout();
		if(size.x == 0 && size.y == 0)
		{
			client.pack();
			sh.pack();
		} else {
			Rectangle shellBounds = sh.computeTrim(0, 0, size.x, size.y);
			if (sh.getMenuBar() != null)
				shellBounds.height -= 22;
			sh.setSize(shellBounds.width, shellBounds.height);
		}
		sh.setText(Client.Title);

		String resIconFuxKlein = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fuxklein']");
		String resIconFux = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fux']");
		final String strImages[] = {resIconFuxKlein, resIconFux};
		sh.setImages(client.makeImages(strImages));

		sh.pack();
		sh.open();

		while (!sh.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		System.exit(0);
	}
	
	public void initSwtGui()
	{
		Display disp = Display.getDefault();
		Shell sh = new Shell(disp);
		
		ImageResourceLoader irl = new ImageResourceLoader();
		HelpAboutDialog splashscreen = new HelpAboutDialog(sh, HelpAboutDialog.SPLASH_SCREEN,config,irl);
		splashscreen.open();
		try{Thread.sleep(3000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		splashscreen.close();
		splashscreen = null;
		
		OpenFuxmlClient client = new OpenFuxmlClient(sh, SWT.NULL, config);
		
		sh.setLayout(new FillLayout());
		sh.layout();
		
		sh.setBounds(10, 30, 300, 900);
		sh.pack();
		
		sh.setText(OpenFuxmlClient.Title);
		
		String resIconFux = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fux']");
		String resIconFuxKlein = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fuxklein']");
		final String strImages[] = {resIconFuxKlein, resIconFux};
		sh.setImages(client.makeImages(strImages));

		sh.open();
		
		while (!sh.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}
	
	public static void main(String[] args)
	{		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WsOpenFuXML openFuXML = new WsOpenFuXML();
		openFuXML.loadProperties();
		openFuXML.checkExtract();
		openFuXML.loadProperties();
		openFuXML.initSwtGui();
	}
}