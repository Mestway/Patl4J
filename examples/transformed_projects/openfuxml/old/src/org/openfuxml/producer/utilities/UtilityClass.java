/**
 * In der Klasse UtilityClass werden allgemeine Felder und Methoden
 * abgelegt, die an den verschiedensten Stellen benutzt werden.
 */
package org.openfuxml.producer.utilities;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.*;


import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class UtilityClass
{
	public final static String xmlExt = ".xml";
	public final static String Encoding = "UTF-8";
	/**
	* Die Methode readDocument liest die Datei mit dem übergebenen
	* Dateinamen ein, parst sie als XML und gibt das Ergebnis vom
	* Typ Document als Rückgabewert zurück.
	* Falls die Methode auf einen Fehler läuft, wird der Wert null
	* zurückgegeben. Der Fehler wird an den FehlerStream System.err ausgegeben.
	* Das aufrufenden Programm kann dann selbst entscheiden, ob es nach dem
	* Fehler abgebrochen wird.
	* Da diese Methode an vielen Stellen in unserem Projekt benötigt
	* wird, wird sie in der Klasse DEFINE implementiert.
	*/
	
	public static Document readDocument(String Dateiname, boolean validating)
	{
		// Step 1: create a DocumentBuilderFactory and configure it
		//System.setProperty("javax.xml.parsers.DocumentBuilderFactory","net.sf.saxon.om.DocumentBuilderFactoryImpl");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(validating);

		// Step 2: create a DocumentBuilder that satisfies the constraints
		// specified by the DocumentBuilderFactory
		DocumentBuilder db = null;
		Document doc = null;

		try { db = dbf.newDocumentBuilder();}
		catch (ParserConfigurationException pce)
		{
		  System.err.println(pce);
		  return null;
		}
		//System.out.println("Ignoring Comments: " + dbf.isIgnoringComments());
		// Step 3: parse the input file
		try
		{
		  db.setErrorHandler(new OwnErrorHandler(Dateiname));
		  doc = db.parse(new File(Dateiname));
		
		  // Auch die folgenden Anweisungen brachten keinen Erfolg für
		  // die Ausgabe als ISO-8859-1.
		  // InputSource is = new InputSource(new FileInputStream(Dateiname));
		  // is.setEncoding(Encoding);
		  // doc = db.parse(is);
		} // try
		catch (SAXException se){System.err.println(se);return null;}
		catch (IOException ioe){System.err.println(ioe);return null;}
		
		return(doc);
  }

	/**
	* Echo common attributes of a DOM2 Node and terminate output with an
	* EOL character.
	*/
	private static void printlnCommon(Node n, PrintWriter out)
	{
		out.print(" nodeName=\"" + n.getNodeName() + "\"");

		String val = n.getNamespaceURI();
		if (val != null)
		{
			out.print(" uri=\"" + val + "\"");
		}

		val = n.getPrefix();
		if (val != null)
		{
			out.print(" pre=\"" + val + "\"");
		}

		val = n.getLocalName();
		if (val != null)
		{
			out.print(" local=\"" + val + "\"");
		}

    val = n.getNodeValue();
    if (val != null)
    {
      out.print(" nodeValue=");
      if (val.trim().equals(""))
      {
        // Whitespace
        out.print("[WS]");
      } else
      {
        out.print("\"" + n.getNodeValue() + "\"");
      }
    }
    out.println();
  } // printlncommon

  /**
   * Indent to the current level in multiples of basicIndent
   */
  private static void outputIndentation(int indent, String basicIndent, PrintWriter out)
  {
    for (int i = 0; i < indent; i++)
    {
      out.print(basicIndent);
    } // for
  } // outputIndentation

  /**
   * Recursive routine to print out DOM tree nodes
   */
  public static void echo(Node n, int indent, PrintWriter out)
  {
    // Indent to the current level before printing anything
    outputIndentation(indent, "  ", out);

    int type = n.getNodeType();
    switch (type) {
      case Node.ATTRIBUTE_NODE:
        out.print("ATTR:");
        printlnCommon(n, out);
        break;
      case Node.CDATA_SECTION_NODE:
        out.print("CDATA:");
        printlnCommon(n, out);
        break;
      case Node.COMMENT_NODE:
        out.print("COMM:");
        printlnCommon(n, out);
        break;
      case Node.DOCUMENT_FRAGMENT_NODE:
        out.print("DOC_FRAG:");
        printlnCommon(n, out);
        break;
      case Node.DOCUMENT_NODE:
        out.print("DOC:");
        printlnCommon(n, out);
        break;
      case Node.DOCUMENT_TYPE_NODE:
        out.print("DOC_TYPE:");
        printlnCommon(n, out);

        // Print entities if any
        NamedNodeMap nodeMap = ((DocumentType)n).getEntities();
        indent += 2;
        for (int i = 0; i < nodeMap.getLength(); i++)
        {
          Entity entity = (Entity)nodeMap.item(i);
          echo(entity, indent, out);
        } // for
        indent -= 2;
        break;
      case Node.ELEMENT_NODE:
        out.print("ELEM:");
        printlnCommon(n, out);

        // Print attributes if any.  Note: element attributes are not
        // children of ELEMENT_NODEs but are properties of their
        // associated ELEMENT_NODE.  For this reason, they are printed
        // with 2x the indent level to indicate this.
        NamedNodeMap atts = n.getAttributes();
        indent += 2;
        for (int i = 0; i < atts.getLength(); i++)
        {
          Node att = atts.item(i);
          echo(att, indent, out);
        } // for
        indent -= 2;
        break;
      case Node.ENTITY_NODE:
        out.print("ENT:");
        printlnCommon(n, out);
        break;
      case Node.ENTITY_REFERENCE_NODE:
        out.print("ENT_REF:");
        printlnCommon(n, out);
        break;
      case Node.NOTATION_NODE:
        out.print("NOTATION:");
        printlnCommon(n, out);
        break;
      case Node.PROCESSING_INSTRUCTION_NODE:
        out.print("PROC_INST:");
        printlnCommon(n, out);
        break;
      case Node.TEXT_NODE:
        out.print("TEXT:");
        printlnCommon(n, out);
        break;
      default:
        out.print("UNSUPPORTED NODE: " + type);
        printlnCommon(n, out);
        break;
    } // switch(type)

    // Print children if any
    indent++;
    for (Node child = n.getFirstChild(); child != null; child = child.getNextSibling())
    {
      echo(child, indent, out);
    } // for
    indent--;
  } // echo

} // UtilityClass

