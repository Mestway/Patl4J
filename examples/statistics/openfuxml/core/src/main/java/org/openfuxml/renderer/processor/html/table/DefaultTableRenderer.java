package org.openfuxml.renderer.processor.html.table;

import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Table;
import org.openfuxml.renderer.processor.html.interfaces.OfxTableRenderer;
import org.openfuxml.renderer.processor.html.section.ParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTableRenderer implements OfxTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(DefaultTableRenderer.class);
	
	private Element table; 
	private ParagraphRenderer paragraphRenderer;

	public DefaultTableRenderer()
	{

	}
	
	public Element render(Table ofxTable) 
	{
		table = DocumentHelper.createElement("table"); 
		
		renderContent(ofxTable.getContent()); 
		
		return table;
	}
	
	private void renderContent(Content content)
	{
		renderHeader(content.getHead());
		for(Body body : content.getBody())
		{
			renderBody(body);
		}
	}
	
	private void renderHeader(Head head)
	{
		Element tr = DocumentHelper.createElement("tr"); 
		
		for(Cell cell : head.getRow().get(0).getCell())
		{
			Element tCell = renderCell("th", cell); 
			tr.add(tCell); 
		}
		
		Element thead = DocumentHelper.createElement("thead"); 
		thead.add(tr); 
		table.add(thead); 
	}
	
	private void renderBody(Body body)
	{
		Element tbody = DocumentHelper.createElement("tbody"); 
		
		for(Row row : body.getRow())
		{
			tbody.add(renderRow(row)); 
		}
		
		table.add(tbody); 
	}
	
	private Element renderRow(Row row) 
	{
		Element tr = DocumentHelper.createElement("tr"); 
		for(Cell cell : row.getCell())
		{
			Element tCell = renderCell("td", cell); 
			tr.add(tCell); 
		}
		return tr;
	}
	
	private Element renderCell(String eName, Cell cell) 
	{
		Element tCell = DocumentHelper.createElement(eName); 
		for(Object o : cell.getContent())
		{
			if(o instanceof String){tCell.add(new Text((String)o));} 
			else if(o instanceof Paragraph){tCell.add(getParagraphRenderer().render((Paragraph)o));} 
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		return tCell;
	}
	
	private ParagraphRenderer getParagraphRenderer()
	{
		if(paragraphRenderer==null){paragraphRenderer = new ParagraphRenderer();}
		return paragraphRenderer;
	}
}
