package org.openfuxml.client.control.projects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.util.config.OfxPathHelper;

public class ProjectFactoryDirect implements ProjectFactory
{
	final static Logger logger = LoggerFactory.getLogger(ProjectFactoryDirect.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Configuration config;
	
	public ProjectFactoryDirect(Configuration config)
	{
		this.config=config;
	}
	
	public List<OfxProject> lProjects(String application)
	{
		ArrayList<OfxProject> result = new ArrayList<OfxProject>();
		
		String repoDir = OfxPathHelper.getDir(config, "repository");
		
		File dir = new File(repoDir+fs+application);
		File[] subDirs = dir.listFiles();
		if (subDirs != null)
		{
			for (File subDir : subDirs)
			{
				boolean isDir = subDir.isDirectory();
				boolean isSvn = subDir.getAbsolutePath().endsWith(".svn");
				if (isDir && !isSvn)
				{
					OfxProject ofxP = new OfxProject();
					ofxP.setDir(subDir.getAbsolutePath());
					ofxP.setName(subDir.getName());
					result.add(ofxP);
					logger.trace("Project found: "+ofxP);
				}
			}
		}
		
		return result;
	}
}
