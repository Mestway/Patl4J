package org.openfuxml.renderer.word;

import java.io.File;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.util.reference.OfxReferenceDocumentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestReferenceRendererWord extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestReferenceRendererWord.class);

	private Document ofxDocument;
	
	@Before
	public void init()
	{
		ofxDocument = OfxReferenceDocumentBuilder.ofxDocument();
	}
	
	@Test(expected=OfxAuthoringException.class)
	public void noContent() throws OfxAuthoringException
	{
		ofxDocument.setContent(null);
		OfxWordRenderer wordRenderer = new OfxWordRenderer();
		wordRenderer.render(ofxDocument, new File("target/test.docx"));
	}
	
	public void writeExpected() throws OfxAuthoringException
	{
		logger.info("Writing expected Document");
		JaxbUtil.info(ofxDocument);
		
		File f = new File("target/test.docx");
		
		OfxWordRenderer wordRenderer = new OfxWordRenderer();
		wordRenderer.render(ofxDocument,f);
	}
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestReferenceRendererWord rrWord = new TestReferenceRendererWord();
    	rrWord.init();
    	rrWord.writeExpected();
    }
}