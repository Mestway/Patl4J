package org.openfuxml.renderer.latex.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.layout.Width;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexWidthCalculator extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexWidthCalculator.class);

	private LatexWidthCalculator lwc;
	private Width width;

	@Before
	public void init()
	{
		lwc = new LatexWidthCalculator();
		width = new Width();
	}
	
	@Test
	public void cm() throws OfxAuthoringException
	{
		width.setUnit("cm");
		width.setValue(12);
		
		String expected = "12.0cm";
        String actual = lwc.buildWidth(width);
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void percentage() throws OfxAuthoringException
	{
		width.setUnit("percentage");
		width.setValue(12);
		
		String expected = "0.12\\linewidth";
        String actual = lwc.buildWidth(width);
        Assert.assertEquals(expected, actual);
	}
}