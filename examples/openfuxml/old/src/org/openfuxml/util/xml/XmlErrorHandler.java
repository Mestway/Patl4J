package org.openfuxml.util.xml;

import org.xml.sax.*;

public class XmlErrorHandler implements ErrorHandler
{
  private String filename;

  public XmlErrorHandler(String s)
  {
    filename = s;
  } // OwnErrorHandler

  public void error(SAXParseException spe)
  {
    System.err.println("Fehler in Datei: " +filename);
    System.err.println(spe);
    System.exit(1);
  } // error

  public void fatalError(SAXParseException spe)
  {
    System.err.println(spe);
    System.exit(1);
  } // fatalError

  public void warning(SAXParseException spe)
  {
    System.err.println(spe);
    System.exit(1);
  } // warning
} // OwnErrorHandler
