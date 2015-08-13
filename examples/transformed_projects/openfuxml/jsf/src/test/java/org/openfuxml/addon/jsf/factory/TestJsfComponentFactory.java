package org.openfuxml.addon.jsf.factory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.test.AbstractJsfTest;
import org.openfuxml.xml.addon.jsf.Attribute;
import org.openfuxml.xml.addon.jsf.Component;
import org.openfuxml.xml.xpath.JsfXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfComponentFactory extends AbstractJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(JsfComponentFactory.class);
	
	private JsfComponentFactory jcf;
	private final String componentXhtml = "src/main/resources/META-INF/resources/ofx/dummy.xhtml";
	
	@Before
	public void init()
	{
		jcf = new JsfComponentFactory();
	}
	
	@Test
	public void testInit()
	{
		Assert.assertNotNull(jcf);
		Component actual = jcf.buildComponent(componentXhtml);
		Assert.assertNotNull(actual);
		JaxbUtil.debug(actual);
	}
	
	@Test
	public void value() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Component xml = jcf.buildComponent(componentXhtml);
		Attribute actual = JsfXpath.getAttribute(xml, "value");
		Assert.assertEquals(true, actual.isRequired());
		Assert.assertEquals("value", actual.getName());
		Assert.assertEquals("myDefault", actual.getDefault());
		Assert.assertEquals("myDescription", actual.getShortDescription());		
	}
}