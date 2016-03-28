package org.openfuxml.util.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxMultilangFilter extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxMultilangFilter.class);

	private Section sectionTitle,sectionSection,sectionParagraph;
	
	@Before
	public void init()
	{
		sectionTitle = XmlSectionFactory.build();
		sectionTitle.getContent().add(XmlTitleFactory.build("de","test"));
		sectionTitle.getContent().add(XmlTitleFactory.build("en","test"));
		
		sectionSection = XmlSectionFactory.build();
		sectionSection.getContent().add(XmlSectionFactory.build("de"));
		sectionSection.getContent().add(XmlSectionFactory.build("en"));
		
		sectionParagraph = XmlSectionFactory.build();
		sectionParagraph.getContent().add(XmlParagraphFactory.build("de"));
		sectionParagraph.getContent().add(XmlParagraphFactory.build("en"));
	}
	
	@Test
	public void sectionWithTitles() throws OfxAuthoringException, OfxInternalProcessingException
	{
		Assert.assertEquals(2, sectionTitle.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionTitle = mlf.filterLang(sectionTitle);
		Assert.assertEquals(1, sectionTitle.getContent().size());
	}
	
	@Test
	public void sectionWithSections()
	{
		Assert.assertEquals(2, sectionSection.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionSection = mlf.filterLang(sectionSection);
		Assert.assertEquals(1, sectionSection.getContent().size());
	}
	
	@Test
	public void sectionWithParagraphs()
	{
		Assert.assertEquals(2, sectionParagraph.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionParagraph = mlf.filterLang(sectionParagraph);
		Assert.assertEquals(1, sectionParagraph.getContent().size());
	}
}