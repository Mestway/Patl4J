package org.openfuxml.model.xml.xpath;

import java.io.FileNotFoundException;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.model.xml.addon.wiki.TestXmlServers;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.xml.xpath.wiki.WikiAuthXpath;

public class TestWikiAuthXpath extends AbstractOfxXmlTest
{
	protected static NsPrefixMapperInterface nsPrefixMapper;
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Servers servers = TestXmlServers.create(true);
    	
    }
    
    @Test(expected=ExlpXpathNotFoundException.class)
    public void testAuthNoServerId() throws ExlpXpathNotFoundException
    {
    	Servers servers = TestXmlServers.create(true);
    	WikiAuthXpath.getAuth("-1", "-1");
    }
}