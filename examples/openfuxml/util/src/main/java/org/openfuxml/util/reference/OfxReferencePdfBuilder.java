package org.openfuxml.util.reference;

import org.openfuxml.xml.renderer.cmp.Pdf;
import org.openfuxml.xml.renderer.cmp.Toc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxReferencePdfBuilder
{
	final static Logger logger = LoggerFactory.getLogger(OfxReferencePdfBuilder.class);
	
	public static Pdf referencePdfSettings()
	{
		Pdf pdf = new Pdf();
	
		pdf.setToc(new Toc());
		pdf.getToc().setPrint(true);
		
		return pdf;
	}
}
