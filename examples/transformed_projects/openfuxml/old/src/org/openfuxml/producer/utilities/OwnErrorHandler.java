package org.openfuxml.producer.utilities;

import org.xml.sax.*;

public class OwnErrorHandler implements ErrorHandler
{
	private String filename;

	public OwnErrorHandler(String s)
	{
		filename = s;
	}

	public void error(SAXParseException spe)
	{
		System.err.println("Fehler in Datei: " +filename);
		System.err.println(spe);
		System.exit(1);
	}

	public void fatalError(SAXParseException spe)
	{
		System.err.println(spe);
		System.exit(1);
	}

	public void warning(SAXParseException spe)
	{
		System.err.println(spe);
		System.exit(1);
	}
}
