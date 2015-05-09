package org.openfuxml.util.filter;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxClassifierFilter extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxClassifierFilter.class);

	private String classifier1 = "c1";
	private String classifier2 = "c2";
	
	private Section section;
	
	@Before
	public void init()
	{
		section = XmlSectionFactory.build();
		
		section.getContent().add(XmlSectionFactory.build());
		section.getContent().add(XmlSectionFactory.build(null, null,classifier1));
		section.getContent().add(XmlSectionFactory.build(null, null,classifier2));
		section.getContent().add(XmlSectionFactory.build(null, null,classifier2));
		
	}
	
	@Test
	public void sections() throws OfxAuthoringException, OfxInternalProcessingException
	{
		Assert.assertEquals(4, section.getContent().size());
	}
	
	@Test
	public void section2() throws OfxAuthoringException, OfxInternalProcessingException
	{
		OfxClassifierFilter mlf = new OfxClassifierFilter(classifier1);
		section = mlf.filterLang(section);
		JaxbUtil.trace(section);
		Assert.assertEquals(2, section.getContent().size());
	}
	
	@Test
	public void section3()
	{
		OfxClassifierFilter mlf = new OfxClassifierFilter(classifier2);
		section = mlf.filterLang(section);
		JaxbUtil.trace(section);
		Assert.assertEquals(3, section.getContent().size());
	}
	
	@Test
	public void sectionWithParagraphs()
	{
		OfxClassifierFilter mlf = new OfxClassifierFilter(classifier1,classifier2);
		section = mlf.filterLang(section);
		JaxbUtil.trace(section);
		Assert.assertEquals(4, section.getContent().size());
	}
}