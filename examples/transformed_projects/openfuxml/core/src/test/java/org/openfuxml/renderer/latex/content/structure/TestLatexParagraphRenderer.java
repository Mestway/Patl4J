package org.openfuxml.renderer.latex.content.structure;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexParagraphRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private static enum Key {withBlank,withoutBlank}
	
	private LatexParagraphRenderer renderer;
	private String dir = "paragraph";
	
	@After public void close(){renderer=null;}
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
    @Test
    public void withBlank() throws IOException, OfxAuthoringException
    {
    	renderer = new LatexParagraphRenderer(cmm,dsm,true);
    	
    	f = new File(rootDir,dir+"/"+Key.withBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    @Test
    public void withoutBlank() throws IOException, OfxAuthoringException
    {
    	renderer = new LatexParagraphRenderer(cmm,dsm,false);
    	
    	f = new File(rootDir,dir+"/"+Key.withoutBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexParagraphRenderer.initLoremIpsum();
    	TestLatexParagraphRenderer test = new TestLatexParagraphRenderer();
    	test.setSaveReference(true);
    	
 //   	test.withBlank();
    	test.withoutBlank();
    }
}