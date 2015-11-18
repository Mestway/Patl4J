package org.openfuxml.factory.xml.table;

import org.openfuxml.content.layout.Width;
import org.openfuxml.content.table.Column;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory.Horizontal;

public class OfxColumnFactory
{
	
	public static Column build(Horizontal hAlignment)
	{
		Column col = new Column();
		col.setAlignment(XmlAlignmentFactory.buildHorizontal(hAlignment));
		return col;
	}
	
	public static Column percentage(double value)
	{
		Width width = new Width();
		width.setUnit("percentage");
		width.setValue(value);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column relative(int relative)
	{
		Width width = new Width();
		width.setValue(relative);
		
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column flex()
	{
		Width width = new Width();
		width.setFlex(true);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column flex(double value){return flex(value,false);}
	public static Column flex(double value,boolean narrow)
	{
		Width width = new Width();
		width.setNarrow(narrow);
		width.setFlex(true);
		width.setValue(value);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
}
