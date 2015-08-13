package org.openfuxml.producer.utilities;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;


public class LogErrorHandler implements ErrorHandler
{
  private String filename;
  private XMLLogger Loggy; //XML Logfile
  
  public LogErrorHandler(String s)
  {
    filename = s;						
	Loggy = new XMLLogger(s);		//create a new instance of XMetalXMLLog
  } // OwnErrorHandler

  public void error(SAXParseException spe)
  {
	AddErrorToLogfile(spe,"error");
  } // error

  public void fatalError(SAXParseException spe)
  {
	AddErrorToLogfile(spe,"fatal");
    System.exit(1);
  } // fatalError

  public void warning(SAXParseException spe)
  {
	AddErrorToLogfile(spe,"warning");
  } // warning
  
  private boolean AddErrorToLogfile(SAXParseException spe, String ErrorType)
  {
	Loggy.addError(ErrorType,"" + spe.getLineNumber(),"" + spe.getColumnNumber(),"",spe.getMessage(),"Korrigieren Sie die XML-Datei");
	return Loggy.Save();
  }
} // OwnErrorHandler
