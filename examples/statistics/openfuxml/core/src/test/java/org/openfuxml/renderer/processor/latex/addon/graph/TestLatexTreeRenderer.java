package org.openfuxml.renderer.processor.latex.addon.graph;

import org.junit.After;
import org.junit.Before;
import org.openfuxml.renderer.latex.content.graph.LatexTreeRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTreeRenderer extends AbstractLatexGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTreeRenderer.class);
	
	private static enum Key {simple,oneLevel,twoLevel,rootWithoutLabel}
	
	private LatexTreeRenderer renderer;
	private String dir = "tree";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTreeRenderer(cmm,dsm);
	}
	
	@After public void close(){renderer=null;}
/*
	public static Tree createTable()
	{
		Tree tree = new Tree();
		tree.setNode(TestLatexTreeNodeRenderer.createNode());
		
		return tree;
	}
	
    @Test
    public void rootNode() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.simple+".tex");
    	Tree tree = createTable();
    	renderer.render(tree);
    	renderTest(renderer,f);
    }
    
    @Test
    public void oneLevel() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.oneLevel+".tex");
    	Tree tree = createTable();
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("A"));
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("B"));
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("C"));
    	renderer.render(tree);
    	renderTest(renderer,f);
    }
    
    @Test
    public void twoLevel() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.twoLevel+".tex");
    	Tree tree = createTable();
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("A"));
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("B"));
    	tree.getNode().getNode().get(0).getNode().add(TestLatexTreeNodeRenderer.createNode("1"));
    	tree.getNode().getNode().get(0).getNode().add(TestLatexTreeNodeRenderer.createNode("2"));
    	renderer.render(tree);
    	renderTest(renderer,f);
    }
    
    @Test
    public void rootWithoutLabel() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.rootWithoutLabel+".tex");
    	Tree tree = createTable();
    	tree.getNode().setLabel(null);
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("A"));
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("B"));
    	tree.getNode().getNode().add(TestLatexTreeNodeRenderer.createNode("C"));
    	renderer.render(tree);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexTreeRenderer.initLoremIpsum();
    	TestLatexTreeRenderer test = new TestLatexTreeRenderer();
    	test.setSaveReference(true);
    	
    	test.initRenderer();test.rootNode();
    	test.initRenderer();test.oneLevel();
    	test.initRenderer();test.twoLevel();
    	test.initRenderer();test.rootWithoutLabel();
    }
    */
}