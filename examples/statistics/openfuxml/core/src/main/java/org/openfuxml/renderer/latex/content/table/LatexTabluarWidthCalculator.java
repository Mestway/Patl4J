package org.openfuxml.renderer.latex.content.table;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Specification;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory.Horizontal;

public class LatexTabluarWidthCalculator
{
	static Log logger = LogFactory.getLog(LatexTabluarWidthCalculator.class);
	
	protected final static int muliplier = 1000;
	
	private DecimalFormat df;
	private Width widthTable;
	
	private Columns columns;
	private List<String> listDefinition;
	private List<String> listValue;
	
	private Map<Integer,String> mapCol;
	
	public LatexTabluarWidthCalculator(Columns columns) throws OfxAuthoringException
	{
		this.columns=columns;
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		df = new DecimalFormat("0.00",otherSymbols);
		
		listDefinition = new ArrayList<String>();
		listValue = new ArrayList<String>();
		mapCol = new Hashtable<Integer,String>();
		
		setDefaultTableWidth();
		calculate();
	}
	
	private void calculate() throws OfxAuthoringException
	{
		double sumFlex=0;
		for(Column column : columns.getColumn())
		{
			if(column.isSetWidth())
			{
				Width width = column.getWidth();
				
				if(width.isSetFlex() && width.isFlex() && width.isSetValue())
				{
					sumFlex=sumFlex+width.getValue();
				}
			}
		}
		
		logger.trace("SumFlex: "+sumFlex);
		
		int index=0;
		for(Column column : columns.getColumn())
		{	
			index++;
			
			if(column.isSetAlignment() && column.isSetWidth()){throw new OfxAuthoringException("Table.columen with width AND alignment currently not supported");} 
			
			if(column.isSetWidth())
			{
				Width width = column.getWidth();
				if(!width.isSetUnit()){width.setUnit("percentage");}
				
				byte[] b = {(byte)(index+64)};
				String var = "\\tabLen"+(new String(b));
				
				if(isFlex(column))
				{
					logger.trace("Width?" +(width.isSetValue()));
					StringBuffer sb = new StringBuffer();
					if(width.isSetValue())
					{
						double thisFlex = width.getValue()/sumFlex;
						sb.append(">{\\hsize=").append(df.format(thisFlex)).append("\\hsize}X");
					}
					else
					{
						sb.append("X");
					}
					mapCol.put(index, sb.toString());
					
				}
				else
				{
					StringBuffer sbDef = new StringBuffer();
					sbDef.append("\\ifthenelse");
					sbDef.append("{\\isundefined{").append(var).append("}}");
					sbDef.append("{\\newlength{").append(var).append("}}");
					sbDef.append("{}");
					listDefinition.add(sbDef.toString());
					
					if(width.getUnit().equals("percentage"))
					{
						double colWidth = (widthTable.getValue()/100)*(width.getValue()/100);
						
						StringBuffer sbValue = new StringBuffer();
						sbValue.append("\\setlength{").append(var).append("}");
						sbValue.append("{").append(df.format(colWidth)).append("\\textwidth}");
						listValue.add(sbValue.toString());
						mapCol.put(index, "p{"+var+"}");
					}
					else
					{
						logger.warn("NYI unit "+width.getUnit());
					}
				}
			}
			else if(column.isSetAlignment())
			{
				Alignment alg = column.getAlignment();
				Horizontal h = Horizontal.valueOf(alg.getHorizontal());
				switch(h)
				{
					case left: mapCol.put(index, "l");break;
					case center: mapCol.put(index, "c");break;
					default: {throw new OfxAuthoringException("Alignment "+alg.getHorizontal()+" not supported");} 
				}
				
			}
			
			logger.trace(index+" "+getColDefinition(index));
		}
	}
	
	private boolean isFlex(Column column)
	{
		return column.isSetWidth() && column.getWidth().isSetFlex() && column.getWidth().isFlex();
	}
	
	public String getColDefinition(int i)
	{
		return mapCol.get(i);
	}
	
	protected int getDivide()
	{
		double relativeSum=0;
		for(Column c : columns.getColumn())
		{
			if(!isFlex(c))
			{
				relativeSum=relativeSum+c.getWidth().getValue();
			}
		}
		return (int)relativeSum*muliplier;
	}
	
	protected int getMultiplier(int i)
	{
		return (int)(columns.getColumn().get(i).getWidth().getValue()*muliplier);
	}
	
	public List<String> getLatexLengthCalculations()
	{
		List<String> listLatexCalculation = new ArrayList<String>();
		listLatexCalculation.add("");
		listLatexCalculation.addAll(listDefinition);
		listLatexCalculation.add("");
		listLatexCalculation.addAll(listValue);
		return listLatexCalculation;
	}
	
	public boolean isFlexTable()
	{
		for(Column column : columns.getColumn())
		{
			if(column.isSetWidth() && column.getWidth().isSetFlex() && column.getWidth().isFlex()){return true;}
		}
		return false;
	}
	
	public String buildTableWidth(Specification specification)
	{
		if(specification.isSetWidth()){widthTable = specification.getWidth();}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\\textwidth");
		sb.append("}");
		return sb.toString();
	}
	
	public String precentage(double d)
	{
		return df.format(d/100);
	}
	
	private void setDefaultTableWidth()
	{
		widthTable = new Width();
		widthTable.setUnit("percentage");
		widthTable.setValue(100);
	}
}
