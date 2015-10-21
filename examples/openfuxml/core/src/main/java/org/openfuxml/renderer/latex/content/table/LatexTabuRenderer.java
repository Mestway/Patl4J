package org.openfuxml.renderer.latex.content.table;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.layout.Line;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTabuRenderer extends AbstractOfxLatexRenderer implements OfxLatexTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTabuRenderer.class);
	
	public LatexTabuRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		JaxbUtil.info(table);
		boolean longTable = table.getSpecification().isLong();
		
		String tableType = null;
		if(longTable){tableType="longtabu";}
		else{tableType="tabu";}
		
		StringBuffer preSb = new StringBuffer();
		preSb.append("\\begin{").append(tableType).append("} to \\linewidth ");
		preSb.append(renderPreamble(table.getSpecification()));
		
		preTxt.add(preSb.toString());
		
		renderTabu(table.getSpecification(),table.getContent());
		
		StringBuffer postSb = new StringBuffer();
		postSb.append("\\end{").append(tableType).append("}");
		if(longTable)
		{
			buildTitle(table);
			postTxt.add(postSb.toString());
		}
		else
		{
			postTxt.add(postSb.toString());
			buildTitle(table);
		}
	}
	
	private StringBuffer renderPreamble(Specification specification) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		
		for(Column c : specification.getColumns().getColumn())
		{
			if(c.isSetWidth() && c.getWidth().isSetFlex() && c.getWidth().isFlex())
			{
				int relative = (new Double(c.getWidth().getValue()*100)).intValue();
				
				sb.append("X[");
				if(c.getWidth().isSetNarrow() && c.getWidth().isNarrow()){sb.append("-");}
				sb.append(relative);
				sb.append("]");
			}
			else if(c.isSetAlignment())
			{
				if(c.getAlignment().getHorizontal().equals("center")){sb.append("c");}
			}
				
			
		}
		
		sb.append("}");		
		return sb;
	}
	
	private void renderTabu(Specification specification, Content tgroup) throws OfxAuthoringException
	{		
		renderTableHeader(tgroup.getHead());
		renderBody(tgroup.getBody().get(0));
	}
	
	private void renderTableHeader(Head head) throws OfxAuthoringException
	{	
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(true);
		
		renderer.add(new StringRenderer("\\toprule"));
		if(head!=null)
		{
			for(Row row : head.getRow())
			{
				LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
				f.setEmphasisOverride(emphasis);
				f.render(row);
				renderer.add(f);
			}
		}
		renderer.add(new StringRenderer("\\toprule"));
	}
	
	@SuppressWarnings("unused")
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		for(Row row : tbody.getRow())
		{
			if(row.isSetLayout())
			{
				for(Line line : row.getLayout().getLine())
				{
					renderer.add(new StringRenderer("\\midrule"));
				}
			}
			
			LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
			f.render(row);
			renderer.add(f);
		}
		renderer.add(new StringRenderer("\\bottomrule"));
	}
	
	@Override
	public String buildTabularCols(LatexTabluarWidthCalculator tabularWidthCalculator, Specification spec)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(int i=1;i<=spec.getColumns().getColumn().size();i++)
		{
			sb.append(tabularWidthCalculator.getColDefinition(i));
			sb.append("");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private void buildTitle(Table table)
	{
		if(table.isSetTitle())
		{
			postTxt.add("\\caption{"+table.getTitle().getValue()+"}");
		}
	}
}