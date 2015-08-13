package org.openfuxml.xml.xpath.content.section;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.TestXmlSection;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.xml.xpath.content.SectionXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStatusXPathStatus extends AbstractOfxXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestStatusXPathStatus.class);
    
	private Section root;
	private Section s1,s2,s3;
	
	@Before
	public void iniDbseed()
	{
		root = TestXmlSection.create(false);

		s1 = TestXmlSection.create(false);s1.setId("ok");root.getContent().add(s1);
		s2 = TestXmlSection.create(false);s2.setId("multi");root.getContent().add(s2);
		s3 = TestXmlSection.create(false);s3.setId("multi");root.getContent().add(s3);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Section actual = SectionXpath.getRenderer(root, s1.getId());
	    Assert.assertEquals(s1,actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		SectionXpath.getRenderer(root, "-1");
	}
	
	 @Test(expected=ExlpXpathNotUniqueException.class)
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
		 SectionXpath.getRenderer(root, s2.getId());
	 }
}