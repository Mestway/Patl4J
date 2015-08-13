package org.openfuxml.client.control.documents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.gui.simple.ExtensionFilenameFilter;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.util.config.OfxPathHelper;

public class DocumentFactoryDirect implements DocumentFactory
{
	final static Logger logger = LoggerFactory.getLogger(DocumentFactoryDirect.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Configuration config;
	
	public DocumentFactoryDirect(Configuration config)
	{
		this.config=config;
	}
	
	public List<OfxDocument> lDocuments(OfxApplication ofxA, OfxProject ofxProject)
	{
		ArrayList<OfxDocument> result = new ArrayList<OfxDocument>();
		
		String repoDir = OfxPathHelper.getDir(config, "repository");
		
		File dir = new File(repoDir+fs+ofxA.getName()+fs+ofxProject.getName());
		logger.debug("Suche in "+dir.getAbsolutePath());
		File[] files = dir.listFiles(new ExtensionFilenameFilter(".xml"));
		  
		if (files != null)
		{
			for (File f : files)
			{
				OfxDocument ofxD = new OfxDocument();
				ofxD.setName(f.getName());
				ofxD.setFile(f.getAbsolutePath());
				ofxD.setName(f.getName());
				result.add(ofxD);
			}
		}
		return result;
	}
}
