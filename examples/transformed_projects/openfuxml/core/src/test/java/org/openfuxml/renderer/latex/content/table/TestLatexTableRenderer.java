package org.openfuxml.renderer.latex.content.table;

import java.io.IOException;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTableRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTableRenderer.class);
		
	private LatexTableRenderer renderer;
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTableRenderer(cmm,dsm);
	}
	
	@After public void close(){renderer=null;}
	
	public static Table createTable()
	{
		int[] words = {1,20};
		return createTable(words);
	}
	public static Table createTable(int[] words)
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		cols.getColumn().add(OfxColumnFactory.flex(10));
//		cols.getColumn().add(OfxColumnFactory.percentage(20));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		Body body = new Body();
		body.getRow().add(TestLatexRowFactory.create(words));
		body.getRow().add(TestLatexRowFactory.create(words));
		
		Content content = new Content();
		content.setHead(createTableHead());
		content.getBody().add(body);
		
		Table table = new Table();
		table.setTitle(XmlTitleFactory.build("TestTitle"));
		table.setSpecification(specification);
		table.setContent(content);
		return table;
	}
	
	public static Head createTableHead()
	{
		Head head = new Head();
		head.getRow().add(TestLatexRowFactory.create());
		return head;
	}
	
    @Test(expected=OfxAuthoringException.class)
    public void withoutSpecification() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.setSpecification(null);
    	renderer.render(table);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void withoutContent() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.setContent(null);
    	renderer.render(table);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void withoutBody() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.getContent().unsetBody();
    	renderer.render(table);
    }
    
    @Test
    public void noFloat() throws OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	table.getSpecification().setFloat(XmlFloatFactory.build(false));
    	
    	JaxbUtil.trace(table);
    	renderer.render(table);
    	List<String> content = renderer.getContent();
    	OfxContentDebugger.debug(content);
    	testLatex(content);
    }
    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxCoreTestBootstrap.init();
			
    	TestLatexTableRenderer.initLoremIpsum();
    	TestLatexTableRenderer test = new TestLatexTableRenderer();
    	test.initLatexTestEnvironment(config);
    	test.setSaveReference(true);
    	
//    	test.initRenderer();test.withoutSpecification();
    	test.initRenderer();test.noFloat();

    }
}