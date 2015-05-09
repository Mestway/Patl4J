package org.openfuxml.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PreviousConfigFinder
{
	final static Logger logger = LoggerFactory.getLogger(PreviousConfigFinder.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Pattern p;
	private String mainConf;
	
	public PreviousConfigFinder(String mainConf)
	{
		this.mainConf=mainConf;
		p = Pattern.compile("([\\d]+)\\.([\\d]+)\\.([\\d]+)");
	}
    
	public Configuration find(File startDir, String version)
	{
		ArrayList<OfxVersion> alVersions = new ArrayList<OfxVersion>();
		for(File dir : startDir.listFiles())
		{			
			Matcher m=p.matcher(dir.getName());
			boolean isDir = dir.isDirectory();
			boolean isOfxVersion = m.matches();
			boolean isNewesVersion = dir.getName().equals(version);
			
			if(isDir && isOfxVersion && !isNewesVersion)
			{
				alVersions.add(new OfxVersion(m));
			}
		}
		if(alVersions.size()==0)
		{
			logger.debug("No previous Installation available");
			return null;
		}
		Collections.sort(alVersions);
		OfxVersion ofxVersion = alVersions.get(alVersions.size()-1);
		ConfigLoader.clear();
		ConfigLoader.add(startDir.getAbsolutePath()+fs+ofxVersion+fs+mainConf);
		Configuration previousConfig = ConfigLoader.init();
		logger.debug("Previous Configuration found in: "+startDir.getAbsolutePath()+fs+ofxVersion);
		return previousConfig;
	}

	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		File f = new File("/Users/thorsten/Library/openFuXML");
			
		PreviousConfigFinder pcf = new PreviousConfigFinder("openFuXML.xml"); 
		pcf.find(f,"1.2.2");
	}
	
	public class OfxVersion implements Comparable
	{
		private int major,minor,bugfix;
		public long version;
		
		public OfxVersion(Matcher m)
		{
			major = new Integer(m.group(1));
			minor = new Integer(m.group(2));
			bugfix = new Integer(m.group(3));
			version=major*1000000+minor*1000+bugfix;
		}
		
		public String toString()
		{
			return major+"."+minor+"."+bugfix;
		}
		
		  public int compareTo(Object o) throws ClassCastException
		  {
			    if (!(o instanceof OfxVersion)) throw new ClassCastException("A OfxVersion object expected.");
			    OfxVersion  anotherOfxVersion = (OfxVersion)o;
			    if(version==anotherOfxVersion.version) {return 0;}
			    else if(version > anotherOfxVersion.version) {return 1;}
			    else {return -1;}
		  }
	}
}
