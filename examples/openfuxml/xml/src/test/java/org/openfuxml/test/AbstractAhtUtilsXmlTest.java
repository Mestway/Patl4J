package org.openfuxml.test;

import java.io.File;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsXmlTest.class);

	private boolean debug;
	protected static File fXml;
	
	public AbstractAhtUtilsXmlTest()
	{
		debug=true;
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("Actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11));
	}
	
	protected void save(Object xml, File f){save(xml,f,true);}
	protected void save(Object xml, File f, boolean formatted)
	{
		logger.debug("Saving Reference XML");
		if(debug){JaxbUtil.info(xml);}
    	JaxbUtil.save(f, xml, true);
	}
	
	protected static void setXmlFile(String dirSuffix, Class<?> cl)
	{
		setXmlFile(dirSuffix,cl.getSimpleName());
	}
	
	protected static void setXmlFile(String dirSuffix, String namePrefix)
	{
		fXml = new File(getXmlDir(dirSuffix),namePrefix+".xml");
	}
	
	protected static File getXmlDir(String suffix)
    {
        File f = new File(".");
        String s = FilenameUtils.normalizeNoEndSeparator(f.getAbsolutePath());

        // This hack is for intelliJ
        if(!s.endsWith("xml")){f = new File(s,"xml");}
        else {f = new File(s);}

        return new File(f,"src"+File.separator+"test"+File.separator+"resources"+File.separator+"data"+File.separator+"xml"+File.separator+suffix);
    }
}