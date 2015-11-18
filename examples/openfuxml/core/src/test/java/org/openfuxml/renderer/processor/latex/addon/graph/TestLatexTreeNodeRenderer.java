package org.openfuxml.renderer.processor.latex.addon.graph;

import org.junit.After;
import org.junit.Before;
import org.openfuxml.renderer.latex.content.graph.LatexTreeNodeRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTreeNodeRenderer extends AbstractLatexGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTreeNodeRenderer.class);
	
	private static enum Key {simple,withoutLabel}
	
	private LatexTreeNodeRenderer renderer;
	private String dir = "treeNode";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTreeNodeRenderer(cmm,dsm);
	}
	
	@After public void close(){renderer=null;}
/*	
	public static Node createNode(){return createNode("myLabel");}
	public static Node createNode(String label)
	{
		Node node = new Node();
		node.setLabel(label);
		return node;
	}
	
    @Test
    public void simpleNode() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.simple+".tex");
    	Node node = createNode();
    	renderer.render(node);
    	renderTest(renderer,f);
    }
    
    @Test
    public void withoutLabel() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.withoutLabel+".tex");
    	Node node = createNode();
    	node.setLabel(null);
    	renderer.render(node);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexTreeNodeRenderer.initLoremIpsum();
    	TestLatexTreeNodeRenderer test = new TestLatexTreeNodeRenderer();
    	test.setSaveReference(true);
    	
    	test.initRenderer();test.simpleNode();
    	test.initRenderer();test.withoutLabel();
    }
    */
}