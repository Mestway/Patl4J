package org.openfuxml.factory.content;

import org.openfuxml.content.text.Emphasis;

public class OfxEmphasisFactory
{
	private boolean bold;
	private boolean italic;
	
	public OfxEmphasisFactory(Emphasis emphasis)
	{
		this(emphasis.isSetBold()&&emphasis.isBold(),emphasis.isSetItalic()&&emphasis.isItalic());
	}
	
	public OfxEmphasisFactory(boolean bold, boolean italic)
	{
		this.bold=bold;
		this.italic=italic;
	}
	
	public Emphasis build(String text)
	{
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(bold);
		emphasis.setItalic(italic);
		emphasis.setValue(text);
		return emphasis;
	}
}
