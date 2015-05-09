/*
Each equation element gets an attribute "bezeichner" with the hash value of 
the math expression 
*/

package org.openfuxml.producer.preprocessors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openfuxml.producer.utilities.UtilityClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EquationTagger
{
	private static String[] elements = {"formel", "formel-imtext", 
							"formelarray"};
	

	public static void main(String[] args) {
		Document indoc, outdoc;

		if (args.length != 2) {
			System.err.println("Usage: EquationTagger <input> <output>");
			System.exit(1);
		}
		indoc = UtilityClass.readDocument(args[0], false);
		System.out.println("EquationTagger - START - " + args[0]);
		outdoc = tag(indoc);
		
		try {
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
					new FileOutputStream(args[1]),
					UtilityClass.Encoding);
			transformer.transform(new DOMSource(outdoc), new StreamResult(osw));
		} // try
		catch (TransformerConfigurationException e) {
			System.err.println(e);
		} // catch
		catch (TransformerException e) {
			System.err.println(e);
		} // catch
		catch (FileNotFoundException e) {
			System.err.println(e);
		} // catch
		catch (UnsupportedEncodingException e) {
			System.err.println(e);
		} // catch
		System.out.println("EquationTagger - ENDE - ");
	}

	public static Document tag(Document indoc) {
		Document doc = indoc;
		//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//		try {
		//			DocumentBuilder builder = factory.newDocumentBuilder();
		//			doc = builder.newDocument();
		//		} catch (ParserConfigurationException pce) {
		//			// Parser with specified options can't be built
		//			pce.printStackTrace();
		//		}
		//		doc.importNode(indoc.getDocumentElement(), true);

		NodeList nl;
		Element e;
		String text = "";
		String hash;
		
		for (int k = 0; k < elements.length; k++) {
			nl = doc.getElementsByTagName(elements[k]);
			
			for (int i = 0; i < nl.getLength(); i++) {
				e = (Element) nl.item(i);
				e.normalize();
				text = getNodeText(e);
				hash = Integer.toHexString(text.hashCode());
				e.setAttribute("hash", hash);
				//System.out.println(hash + ": " + text);
				//UtilityClass.echo(e,0,out);
			} // for
		} //for
		return doc;	}
	
	public static String getNodeText(Node n) {
		NodeList childs;
		String text="";
		
		if (n.getNodeType() == Node.TEXT_NODE)
				text = n.getNodeValue().trim();
		else if( n.hasChildNodes()){
			childs = n.getChildNodes();
			for (int j = 0; j < childs.getLength(); j++) {
				text = text + getNodeText(childs.item(j));		
				//System.out.print(formel.getNodeValue().hashCode());
			}
		}
		return text;
	}	
}

