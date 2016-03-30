package org.openfuxml.renderer.latex.util;

import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTexSpecialChars extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestTexSpecialChars.class);

	private Document ofxDocument;
	private Pdf pdfSettings;

	
	@Test
	public void underScore() throws OfxAuthoringException
	{
		String txt = "xx_2";
        String result = TexSpecialChars.replace(txt);
        logger.info(result);
	}

    public void backslash()
    {
        String txt = "x\\x";
        String result = TexSpecialChars.replace(txt);
        logger.info(result);
    }
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestTexSpecialChars rrLatex = new TestTexSpecialChars();
    	rrLatex.underScore();
        rrLatex.backslash();
    }
}