/*
 * Created on 24.11.2003
 */
package org.openfuxml.producer.utilities;

/**
 * @author Naschedf
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.*;


import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Parser{
	
	public static void main(String[] args){
			if (args.length != 2) {
				System.err.println("Usage: Parser <filename> <logfile>");
				System.exit(1);
			}
			System.out.println("Parsing document " + args[0] + "\nWriting logfile " + args[1]);
			readDocument(args[0], args[1]);
			System.out.println("Finished parsing.");
	}
	// Read Document bekommt zwei 
	public static Document readDocument(String Dateiname,String  Logfile)
	 {
	   // Step 1: create a DocumentBuilderFactory and configure it
	   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   dbf.setValidating(true);

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
		 db.setErrorHandler(new LogErrorHandler(Logfile));
		 doc = db.parse(new File(Dateiname));

		 // Auch die folgenden Anweisungen brachten keinen Erfolg für
		 // die Ausgabe als ISO-8859-1.
		 // InputSource is = new InputSource(new FileInputStream(Dateiname));
		 // is.setEncoding(Encoding);
		 // doc = db.parse(is);
	   } // try
	   catch (SAXException se)
	   {
		 System.err.println(se);
		 return null;
	   } // catch SAXException
	   catch (IOException ioe)
	   {
		 System.err.println(ioe);
		 return null;
	   } // catch IOException

	   return(doc);
	 } // readDocument

}

