package org.openfuxml.producer.utilities;


import java.io.*;

import org.w3c.dom.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;


import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
/*------------------------
	FileChecker
	Überprüft ein XML-File auf die enthaltenen Daten auf Existens
  ----------------------*/
public class FileChecker {

	//--------------------------------------
	//Main Methode
	//	Überprüft ob übergebene Werte OK
	//--------------------------------------
	public static void main(String[] argv) {
		//Benätigt werden 4 Parameter
		// 1. ProjectPath: 
		// 2. Source:
		// 3. Destination: 
		// 4. Element Name
		
		if (argv.length == 4)
		{
			java.io.File f1 = new java.io.File(argv[0]);
			if (!f1.isDirectory())	
			{
				System.out.println("Directory Not Exists: " +  argv[0]);
				return;
			}
			java.io.File f2 = new java.io.File(argv[1]);
			if (!f2.exists())
			{
				System.out.println("File not found: " +  argv[1]);
				return;
			}
			FileChecker FC = new FileChecker();
			boolean checkErfolgreich = FC.checkFolder(argv[0],argv[1],argv[2],argv[3]);
			System.out.println("Erfolgreich: " + checkErfolgreich);
		}
		else {
			System.out.println("Syntax: FileChecker <ProjektPath> <Source> <Destination> <XMLElement>");
			System.exit(1);
		}	
	}
	//-------------------------------------------------------------------------------
	//	Hauptfunktion
	//		Öffnet XML-File
	//		Liest die einzelnen Pfade
	//		Überprüft ob diese existieren
	//		Setzt dementsprechend das Attribut	
	//-------------------------------------------------------------------------------
	public boolean checkFolder(String ProjectPath, String Source, String Destination, String Element)
	{
		try
		{	//Dokument wird geöffnet
			Document refSources = UtilityClass.readDocument(Source, false);			
			
			//Nodes werden gesucht
			NodeList myNodeList = refSources.getElementsByTagName(Element);
			
			//Durchlauf der Ergebnisse und setzen des Attributes
			for (int i = 0;i < myNodeList.getLength();i++)
			{
				
				NamedNodeMap myNamedNodeMap = myNodeList.item(i).getAttributes();
				//Filename wird geholt
				String FileNameToCheck = myNamedNodeMap.getNamedItem("fileref").getNodeValue();
				//Attribut wird erzeugt
				Attr myAttr = refSources.createAttribute("available");
				//Wert wird gesetzt
				myAttr.setNodeValue(checkFile(ProjectPath,FileNameToCheck));
				//Attribut wird element zugeordnet
				((Element)myNodeList.item(i)).setAttributeNode(myAttr);
			}
			//Datei wird gespeichert
			return (Save(refSources,Destination));

		}catch(Exception e) {
			System.out.println("Caught an exception during production.");
			e.printStackTrace();
			return false;
		}
		
	}

	//------------------------------------------------------------
	//	�berpr�ft den �bergebenen Pfad und das File auf Existens
	//		Dabei wird zus�tzlich das trennzeichen �berpr�ft
	//------------------------------------------------------------
	private String checkFile(String ProjectPath, String Filename)
	{
		String FullFileName;
		if (ProjectPath.endsWith(File.separator))
		{	
			if (Filename.startsWith(File.separator))
				FullFileName = ProjectPath.substring(0,ProjectPath.length()-2) + Filename;	
			else
				FullFileName = ProjectPath + Filename;
		}
		else
		{	
			if (Filename.startsWith(File.separator))
				FullFileName = ProjectPath + Filename;
			else
				FullFileName = ProjectPath + File.separator + Filename;
		}
		File vReturn = new File(FullFileName);
		if(vReturn.exists())
			return "true";
		else
			return "false";

	}

	//----------------------------------------------------
	//Speichert das �bergebene XML-Document 
	//	Dateiname wird ebenfalls �bergeben
	//----------------------------------------------------
	private boolean Save(Document XMLDoc, String FileName)
	{
		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
		
			OutputStreamWriter osw =
				new OutputStreamWriter(
					new FileOutputStream(FileName),
					UtilityClass.Encoding);
			transformer.transform(new DOMSource(XMLDoc), new StreamResult(osw));
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	
	}
}




