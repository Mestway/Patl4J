package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexGridTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexGridTableRenderer.class);
	
	public LatexGridTableRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		if(!table.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!table.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		
		renderTabular(table.getSpecification(),table.getContent());
	}
	
	private void renderTabular(Specification specification, Content tgroup) throws OfxAuthoringException
	{
		renderTableHeader(tgroup.getHead());
		
		if(tgroup.getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		renderBody(tgroup.getBody().get(0));
		
	}
	
	@Override
	public String buildTabularCols(LatexTabluarWidthCalculator tabularWidthCalculator, Specification spec)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("{|");
		for(int i=1;i<=spec.getColumns().getColumn().size();i++)
		{
			sb.append(tabularWidthCalculator.getColDefinition(i));
			sb.append("|");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private void renderTableHeader(Head head) throws OfxAuthoringException
	{
		renderer.add(new StringRenderer("\\hline"));
		if(head!=null)
		{
			for(Row row : head.getRow())
			{
				LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
				f.render(row);
				renderer.add(f);
				renderer.add(new StringRenderer("\\hline"));
			}
			renderer.add(new StringRenderer("\\hline"));
		}
	}
	
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		for(Row row : tbody.getRow())
		{
			LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
			f.render(row);
			renderer.add(f);
			renderer.add(new StringRenderer("\\hline"));
		}
	}
}