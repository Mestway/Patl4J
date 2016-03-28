package org.openfuxml.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFileProcessingTest extends AbstractOfxWikiTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractFileProcessingTest.class);
	
	public static String srcDirName;
	
	protected File fTest;
	protected File fRef;
		
	protected static Collection<Object[]> initFileNames(String srcDir, String fileSuffix)
	{
		Collection<Object[]> c = new ArrayList<Object[]>();
		File dirSrc = new File(srcDir);
		for(File f : dirSrc.listFiles())
		{
			if(f.getName().endsWith(fileSuffix))
			{
				Object[] o = new Object[] {f};
				c.add(o);
			}
		}
		return c;
	}

}