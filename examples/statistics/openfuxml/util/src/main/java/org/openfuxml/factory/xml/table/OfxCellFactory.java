package org.openfuxml.factory.xml.table;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;

public class OfxCellFactory
{
	public static Cell build(){return new Cell();}
	
	public static Cell createParagraphCell(int text){return createParagraphCell(""+text);}
	public static Cell createParagraphCell(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Cell cell = new Cell();
		cell.getContent().add(p);
		return cell;
	}
	
	public static Cell image(Image image)
	{
		Cell cell = new Cell();
		cell.getContent().add(image);
		return cell;
	}
}
