package org.openfuxml.test.xml.epub.jaxb;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.epub.data.factory.NcxFactory;
import org.openfuxml.addon.epub.data.jaxb.ncx.Head;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap;
import org.openfuxml.addon.epub.data.jaxb.ncx.Ncx;

public class TestNcx
{
	final static Logger logger = LoggerFactory.getLogger(TestNcx.class);
	
	public TestNcx()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Ncx ncx = new Ncx();
		ncx.setVersion("2005-1");
		ncx.setHead(getHead());
		ncx.setDocTitle(NcxFactory.getTitle("Test Title"));
		ncx.setNavMap(getNavMap());
		
		JaxbUtil.debug(ncx);
	}
	
	private NavMap getNavMap()
	{
		NavMap navmap = new NavMap();
		navmap.getNavPoint().add(NcxFactory.getNavPoint("title", 1, "Title Page", "title.xhtml"));
		navmap.getNavPoint().add(NcxFactory.getNavPoint("c1", 2, "Chapter1", "chapter1.xhtml"));
		return navmap;
	}
		
	private Head getHead()
	{
		Head head = new Head();
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:uid", "ofxTest"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:depth", "1"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:totalPageCount", "0"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:maxPageNumber", "0"));		
		return head;
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestNcx test = new TestNcx();
		test.xmlConstruct();
	}
}