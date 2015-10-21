package org.openfuxml.factory.ofx.table;

import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.table.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnFactory
{
	final static Logger logger = LoggerFactory.getLogger(ColumnFactory.class);
	
	public static synchronized Column create(String alignmentHorizontal) 
	{
		Alignment alignment = new Alignment();
		alignment.setHorizontal(alignmentHorizontal);
		
		Column column = new Column();
		column.setAlignment(alignment);
		
		return column;
	}
	
	public static synchronized Column create(String alignmentHorizontal, double length) 
	{
		Width width = new Width();
		width.setValue(length);
		width.setUnit("*");
		
		Alignment alignment = new Alignment();
		alignment.setHorizontal(alignmentHorizontal);
		
		Column column = new Column();
		column.setAlignment(alignment);
		column.setWidth(width);
		
		return column;
	}
}