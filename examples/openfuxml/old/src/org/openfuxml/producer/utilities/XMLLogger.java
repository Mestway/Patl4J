/*
 * Created on 04.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.openfuxml.producer.utilities;

import java.io.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;


import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * @author Naschedf
 */
public class XMLLogger
{
	private static String XML_DOC_ROOT = "failures";
	private static String XML_DOC_ERROR = "failure";
	
	private static String TYPE = "type";
	private static String XPATH = "xpath";
	private static String ASSISTANTURL = "assistanturl";
	private static String MESSAGE = "message";
	private static String HINT = "hint";
	private static String LINENUMBER = "linenumber";
	private static String COLUMNNUMBER = "columnnumber";
	
	Document XMLDoc;
	Element MainNode;
	String myXMLLog;
		
	public XMLLogger(String Filename)
	{
		XMLDoc =  CreateDocument();
		MainNode =  XMLDoc.createElement(XML_DOC_ROOT );
		XMLDoc.appendChild(MainNode);
		myXMLLog = Filename;
	}
	public boolean addError(String type, String linenumber, String columnnumber, String assistanturl, String message, String hint)
	{
		Element eError = XMLDoc.createElement(XML_DOC_ERROR);
		
		//Setzt die Attribute von dem failure Knoten
		eError.setAttribute(TYPE,type);
		eError.setAttribute(LINENUMBER,linenumber);
		eError.setAttribute(COLUMNNUMBER,columnnumber);
		
		eError.setAttribute(ASSISTANTURL,assistanturl);
		
		//Message wird hinzugef�gt
		Element eMessage = XMLDoc.createElement("message");
		eMessage.appendChild(XMLDoc.createTextNode(message));
		
		//Hint wird hinzugef�gt
		Element eHint = XMLDoc.createElement("hint");
		eHint.appendChild(XMLDoc.createTextNode(hint));
		
		eError.appendChild(eMessage);
		eError.appendChild(eHint);
		
		//Position und Message werden zum Error Hinzugef�gt
				
		MainNode.appendChild(eError);
		
		
		return true;
	}
	
	public boolean addError(String type, String xPath, String assistanturl, String message, String hint)
	{
		Element eError = XMLDoc.createElement(XML_DOC_ERROR);
		
		//Setzt die Attribute von dem failure Knoten
		eError.setAttribute(TYPE,type);
		eError.setAttribute(XPATH,xPath);
		eError.setAttribute(ASSISTANTURL,assistanturl);
		
		//Message wird hinzugefügt
		Element eMessage = XMLDoc.createElement("message");
		eMessage.appendChild(XMLDoc.createTextNode(message));
		
		//Hint wird hinzugefügt
		Element eHint = XMLDoc.createElement("hint");
		eHint.appendChild(XMLDoc.createTextNode(hint));
		
		eError.appendChild(eMessage);
		eError.appendChild(eHint);
		
		//Position und Message werden zum Error Hinzugefügt
				
		MainNode.appendChild(eError);
		
		return true;
	}
	
	public boolean addError(String ID,String Error)
	{
		Element eError = XMLDoc.createElement(XML_DOC_ERROR);
		//Element ePosition = XMLDoc.createElement("xpath");
		Element eMessage = XMLDoc.createElement(MESSAGE);
		Element eHint = XMLDoc.createElement(HINT);
		
		
		eError.setAttribute("type","error");
		eError.setAttribute("xpath",ID);
		//eError.setAttribute("assistanturl",".");
		//ePosition.setAttribute("xPath",ID);
		
		//ErrorMessage Wird erzeugt
		Text ErrorText = XMLDoc.createTextNode(Error);
		eMessage.appendChild(ErrorText);
		
		
		//		Hint wird hinzugefügt
		Text HintText = XMLDoc.createTextNode("Latex Syntax Error");
		eHint.appendChild(HintText);
		
		//		Message und Hint werden zum Error Hinzugefügt
		eError.appendChild(eMessage);
		eError.appendChild(eHint);
	
	
		MainNode.appendChild(eError);
		return true;
	}
	
	public boolean Save()
	{
		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			// alt:
			//transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(AusgabeDateiname)));
			// neu:
			// Die folgenden Anweisungen geben zwar den Code im richtigen Format aus.
			// Jedoch wird im XML-Tag nicht encoding="ISO-8859-1",
			// sondern immer noch encoding="UTF-8" ausgegeben.
			

			OutputStreamWriter osw =
				new OutputStreamWriter(
					new FileOutputStream(myXMLLog),UtilityClass.Encoding);
						
			transformer.transform(new DOMSource(XMLDoc), new StreamResult(osw));
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	
	}
	public Document CreateDocument()
	{
		
			// Step 1: create a DocumentBuilderFactory and configure it
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
		
			// Step 2: create a DocumentBuilder that satisfies the constraints
			// specified by the DocumentBuilderFactory
			DocumentBuilder db = null;
			Document doc = null;
		
			try
			{
			  db = dbf.newDocumentBuilder();
			} // try
			catch (ParserConfigurationException pce)
			{
			  System.err.println(pce);
			  return null;
			} // catch ParserConfigurationException
		
			// Step 3: parse the input file
			try
			{
			  //db.setErrorHandler(new OwnErrorHandler(Dateiname));
			  doc = db.newDocument();
				
			  // Auch die folgenden Anweisungen brachten keinen Erfolg f�r
			  // die Ausgabe als ISO-8859-1.
			  // InputSource is = new InputSource(new FileInputStream(Dateiname));
			  // is.setEncoding(Encoding);
			  // doc = db.parse(is);
			} // try
			catch (Exception se)
			{
			  System.err.println(se);
			  return null;
			}
			return(doc);
	}	

}
