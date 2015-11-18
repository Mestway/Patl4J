/*
All equation elements whith an existing corresponding graphic are removed
*/

package org.openfuxml.producer.preprocessors;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openfuxml.producer.utilities.UtilityClass;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TaggedEquationFilter {
		
	
	private static String[] elements = {"formel","formel-imtext","formelarray"};

	private String ErrorMessage;
	public int FormelCounter;
	public int RemoveFormelCounter;
	private static String suffix="png";
	
	public static void main(String args[]) {
		System.out.println("EquationTagger - START - " );
	
	
		System.out.println("\n<input>	"+ args[0]);
		System.out.println("<output> "+ args[1]);
		System.out.println("<directory> "+ args[2]);
		System.out.println("suffix "+ args[3] +"\n");
	
		if (args.length != 4) {
			System.err.println("Usage: TaggedEquationFilter <input> <output> <directory> <suffix>");
			System.exit(1);
		}
		suffix = args[3];
		TaggedEquationFilter f = new TaggedEquationFilter();
		

		if (f.CheckDoc(args[0], args[1], args[2]))
		{
			System.out.println("Formel count: " + f.FormelCounter);
			System.out.println("Formel removed: " + f.RemoveFormelCounter);
			System.out.println(" - Success - ");
		}
		else
		{
			System.out.println("Error:\n" + f.getError());
		}
		
	}
	public String getError()
	{
		return ErrorMessage;
	}
	public boolean CheckDoc(String Source, String Destination, String path)
	{
		ErrorMessage = "";

		FormelCounter = 0;
		RemoveFormelCounter = 0;

		
		Document indoc, outdoc;
		
		
		java.io.File f = new java.io.File(Source);
		if (!f.exists())
		{
			ErrorMessage = "File existiert nicht";
			return false;
		}
		try
		{	
		indoc = UtilityClass.readDocument(Source, false);
		outdoc = tag(indoc, path);
		}
		catch (Exception e)
		{
			ErrorMessage = e.getMessage();
			return false;
		}	
		
		if (outdoc == null)
		{
			ErrorMessage = "Parse Error";
			return false;		
		}
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			OutputStreamWriter osw =
				new OutputStreamWriter(
					new FileOutputStream(Destination),
				UtilityClass.Encoding);
			transformer.transform(new DOMSource(outdoc), new StreamResult(osw));
		} // try
		catch (TransformerConfigurationException e) {
			ErrorMessage = e.getMessage();
			return false;
		} // catch
		catch (TransformerException e) {
			ErrorMessage = e.getMessage();
			return false;
		} // catch
		catch (Exception e)
		{
			ErrorMessage = e.getMessage();
			return false;
		}
		return true;
	}

	private Document tag(Document indoc, String path) {

		Document doc = indoc;

		NodeList nl;
		Element e;
		
		String hashString= "";
		Vector v = new Vector();

		nl = doc.getChildNodes();

		//Durchlauf aller FormelDefinitionen		
		for (int k = 0; k < elements.length; k++)
		{
			nl = doc.getElementsByTagName(elements[k]);

			//Durchlauf aller gefundenen Formeln		
			for (int i = 0; i < nl.getLength(); i++) {
				FormelCounter++;
				
				
				e = (Element) nl.item(i);
				e.normalize();
				//Überprüfung ob "FormelArray" oder "FormelArray_num"
				if (e.getParentNode().getNodeName().equalsIgnoreCase("formelarray"))
				{					
					continue;
				}
				
				hashString = e.getAttribute("hash");
				
				if (checkFile(hashString, path))
				{
					v.add(e);
					RemoveFormelCounter++;
					//System.out.println(hashString);
				}
				
			} // for
		} //for
		
		//Entfernt Vorhandene Formeln aus dem Document
		for (int i = 0; i < v.size();i++)
		{
				try
				{
					e = (Element) v.elementAt(i);
					e.getParentNode().removeChild(e);
					
				}
				catch(DOMException ex)
				{
					ex.printStackTrace();
					return null;
				}		
				
		}
		return doc;	}
	
	private boolean checkFile(String newFile, String path)
	{
		
		if (path.charAt(path.length() - 1) != java.io.File.separatorChar)
			path += java.io.File.separator;
	
		String filename = path +  newFile + "." + suffix;
		File f = new File(filename);
		return f.exists();
	}	
	
	
}

