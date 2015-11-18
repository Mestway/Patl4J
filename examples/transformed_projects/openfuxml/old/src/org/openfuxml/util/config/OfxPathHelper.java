package org.openfuxml.util.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;

public class OfxPathHelper
{
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	public synchronized static String getDir(Configuration config, String type)
	{
		String dir=config.getString("dirs/dir[@type='"+type+"']");
		if(config.getBoolean("dirs/dir[@type='"+type+"']/@rel"))
		{
			dir=config.getString("dirs/dir[@type='basedir']")+fs+dir;
		}
		return dir;
	}
}
