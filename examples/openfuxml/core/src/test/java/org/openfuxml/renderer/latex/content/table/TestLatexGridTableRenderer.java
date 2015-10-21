package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexGridTableRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexGridTableRenderer.class);
	
	private static enum Key {table,tableWithHead}
	
	private LatexTableRenderer renderer;
	private String dir = "grid";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTableRenderer(cmm,dsm);
		renderer.setPreBlankLine(false);
	}
	
	@After public void close(){renderer=null;}

    @Test @Ignore
    public void table() throws IOException, OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	f = new File(rootDir,dir+"/"+Key.table+".tex");
    	renderer.render(table);
    	renderTest(renderer,f);
    }
    
    @Test @Ignore
    public void withHead() throws OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	table.getContent().setHead(TestLatexTableRenderer.createTableHead());
    	f = new File(rootDir,dir+"/"+Key.tableWithHead+".tex");
    	renderer.render(table);
    	OfxContentDebugger.debug(renderer.getContent());
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexGridTableRenderer.initLoremIpsum();
    	TestLatexGridTableRenderer test = new TestLatexGridTableRenderer();
    	test.setSaveReference(true);
    	
    	test.initRenderer();test.table();
    	test.initRenderer();test.withHead();
    }
}