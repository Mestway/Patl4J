package org.openfuxml.renderer.latex.content.table;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.table.Columns;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTabularWidthCalculator extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTabularWidthCalculator.class);
	
	private Columns cols;
	
	@Before
	public void initRenderer()
	{
		cols = createColumns();
	}
		
	public static Columns createColumns()
	{
		Columns cols = new Columns();
		
//		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.center));
		cols.getColumn().add(OfxColumnFactory.percentage(20));
		
		cols.getColumn().add(OfxColumnFactory.flex(1));
		
		return cols;
	}
	
    @Test
    public void getColDefinition() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarWidthCalculator tabUtil = new LatexTabluarWidthCalculator(createColumns());
    	Assert.assertEquals("p{\\tabLenA}", tabUtil.getColDefinition(1));
    	Assert.assertEquals(">{\\hsize=1.00\\hsize}X", tabUtil.getColDefinition(2));
    }
    
    @Test
    public void divide() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarWidthCalculator tabUtil = new LatexTabluarWidthCalculator(createColumns());
    	for(int i=0;i<cols.getColumn().size();i++)
    	{
    		Assert.assertEquals((int)cols.getColumn().get(i).getWidth().getValue()*LatexTabluarWidthCalculator.muliplier, tabUtil.getMultiplier(i));
    	}
    }
    
    @Test
    public void multiplier() throws IOException, OfxAuthoringException
    {    	    	
    	int expected = 20*LatexTabluarWidthCalculator.muliplier;
    	LatexTabluarWidthCalculator tabUtil = new LatexTabluarWidthCalculator(createColumns());
    	Assert.assertEquals(expected, tabUtil.getDivide());
    } 
    
    @Test
    public void lenghtCla() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarWidthCalculator tabUtil = new LatexTabluarWidthCalculator(createColumns());
    	List<String> list = tabUtil.getLatexLengthCalculations();
    	for(String s : list){logger.debug(s);}
    }
    
    @Test
    public void decimalFormat() throws OfxAuthoringException
    {
    	LatexTabluarWidthCalculator twc = new LatexTabluarWidthCalculator(createColumns());
    	String expected ="0.25";
    	Assert.assertEquals(expected, twc.precentage(25));
    }
}