package org.openfuxml.renderer.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexItemFactory extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexItemFactory.class);
	
	private static enum Key {itemize,description}
	private LatexItemFactory renderer;
	private String dir = "item";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexItemFactory(cmm,dsm);
	}
	
	@After public void close(){renderer=null;}
	
	public static Item createItem(){return createItem(null);}
	public static Item createItem(String name)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(10));
		
		Item item = new Item();
		item.setName(name);
		item.getContent().add(p);
		return item;
	}
	
    @Test
    public void itemize() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.itemize+".txt");
    	renderer.render(LatexListRenderer.ListType.list,createItem());
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void descriptionNoItemName() throws IOException, OfxAuthoringException
    {
    	renderer.render(LatexListRenderer.ListType.description,createItem());
    }
    
    @Test
    public void description() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.description+".txt");
    	renderer.render(LatexListRenderer.ListType.description,createItem(li.getWords(1)));
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexItemFactory.initLoremIpsum();
    	TestLatexItemFactory test = new TestLatexItemFactory();
    	test.setSaveReference(true);
    	
   	test.initRenderer();test.itemize();
//    	test.initRenderer();test.description();
    }
   
}