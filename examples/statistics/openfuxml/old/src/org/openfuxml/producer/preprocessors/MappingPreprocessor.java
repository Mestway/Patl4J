package org.openfuxml.producer.preprocessors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.openfuxml.producer.utilities.UtilityClass;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Die Klasse wandelt Zeichenfolgen in andere Zeichenfolgen um. Die Zeichen, die
 * umzuwandeln sind werden in Form eines XML-Objektes bzw. -Datei �bergeben. Die
 * Elemente, in denen die Zeichen nicht gewandelt werden d�rfen werden in einer
 * sogenannten Auschlu�liste spezifiziert.
 */
public class MappingPreprocessor
{
  // Das Array f�r die Ersetzungsliste
  // als sortierte Menge
  SortedSet EntrySet;

  // Das Array f�r die Ausschlu�liste
  private String[] ExclusionElement;
  
  
  private String[] AttributeInclusionList;	//Added By Firas 18.08.2003
  
  /**
   * Konvertiert die in dem config File aufgelisteten Dateien mit der ebenfalls
   * angegebenen Konvertierungsliste. Die konvertierten Dateien werden in eine
   * neue Datei geschrieben.
   */
  public MappingPreprocessor(String filename, String AusgabeDateiname, String conversionList, String xslfile)
  {
    
    String FilenameConversionList = conversionList;
    Document docConversionList = UtilityClass.readDocument(FilenameConversionList, false);

    System.out.println("Dateiname ConversionList: " + FilenameConversionList);
    
    // Bestimmen der Dateierweiterung f�r die Ausgabedatei
    String sNewFileExtension = "." + "tex.xml";
      
    //------------------------------------------------
    // Erzeugen eines Arrays f�r die Ersetzungsliste
    NodeList nlFrom = docConversionList.getElementsByTagName("From");
    NodeList nlTo   = docConversionList.getElementsByTagName("To");

    EntrySet = new TreeSet();

    for (int i=0; i<nlFrom.getLength(); i++)
    {
    	String to = "";
    	try{
    		to = nlTo.item(i).getFirstChild().getNodeValue();
    	}catch(Exception e){
    		to = "";
    	}
      EntrySet.add(new ConversionTableEntry(
        nlFrom.item(i).getFirstChild().getNodeValue(),
        to ) );
    } // for

    /* Kontrollausdruck
    for (Iterator it = EntrySet.iterator(); it.hasNext(); )
      System.out.println(it.next());
    */

    //------------------------------------------------
    // Erzeugen eines Arrays für die Ausschlußliste
    NodeList nl = docConversionList.getElementsByTagName("Element");
    ExclusionElement = new String[nl.getLength()];

    for (int i=0; i<nl.getLength(); i++)
    {
      ExclusionElement[i] = nl.item(i).getFirstChild().getNodeValue();
    } // for
	
	//--------------------------------------------------------------------
	//				Start
	//			Added by Firas
	//--------------------------------------------------------------------

    NodeList nlAttribute = docConversionList.getElementsByTagName("Attribute");
    AttributeInclusionList = new String[nlAttribute.getLength()];

    for (int i=0; i<nlAttribute.getLength(); i++)
    {
      AttributeInclusionList[i] = nlAttribute.item(i).getFirstChild().getNodeValue();
    } // for
	//--------------------------------------------------------------------
	//				End
	//			Added by Firas
	//--------------------------------------------------------------------
		
    /* Kontrollausdruck
    for (i=0; i<ExclusionElement.length; i++)
    {
      System.out.println("ExclusionElement[" + i + "] = " + ExclusionElement[i]);
    } // for
    */

    //------------------------------------------------
    // Die Datei wird gelesen (geparst), konvertiert
    // und wieder weggeschrieben.
    Document doc = null;

    
      // Einlesen (Parsen) der Eingabedatei
      doc = UtilityClass.readDocument(filename, false);
	  System.out.println(filename);
      /* Kontrollausdruck
      UtilityClass.echo(doc, 0, out);
      */

      //------------------------------------------------
      // Konvertieren des eingelesenen DOMTrees
      convert(doc);

      //------------------------------------------------
      // Erzeugen des Ausgabedateinamens
//      File f = new File(filename);
//      String AusgabeDateiname = f.getAbsolutePath();
//      // am Ende des Strings AusgabeDateiname wird .xml durch
//      // sNewFileExtension ersetzt
//      if (AusgabeDateiname.endsWith(UtilityClass.xmlExt))
//      {
//        AusgabeDateiname =
//          AusgabeDateiname.substring(0, AusgabeDateiname.length()-UtilityClass.xmlExt.length())
//          + sNewFileExtension;
//      } // if
//      else
//      {
//        AusgabeDateiname += sNewFileExtension;
//      } // else
	
      /* Kontrollausdruck
      System.out.println("Ausgabedateiname: " + AusgabeDateiname);
      */

      //------------------------------------------------
      // Wegschreiben des konvertierten DOM in die Ausgabedatei
      try
      {
		File stylesheet = new File(xslfile);
        TransformerFactory tFactory = TransformerFactory.newInstance();
		StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = tFactory.newTransformer(stylesource);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(AusgabeDateiname), UtilityClass.Encoding);
        transformer.transform(new DOMSource(doc), new StreamResult(osw));
      }
      catch (TransformerConfigurationException e){System.err.println(e);}
      catch (TransformerException e){System.err.println(e);}
      catch (FileNotFoundException e){System.err.println(e);}
      catch (UnsupportedEncodingException e){System.err.println(e);}
  }

  /**
   * Die Methode convert ist eine rekursive Methode.
   * Es werden alle Knoten des �bergebenen DOM-Trees rekursiv durchlaufen.
   * Dabei wird auf alle Knoten, die nicht zur Ausschlu�liste geh�ren,
   * die Ersetzungstabelle angewandt.
   */
	public void convert(Node n)
	{
		// - alle Knoten durchlaufen
		// - Knoten des Typs Node.ELEMENT_NODE, deren Namen in der Ausschlu�liste steht
		//   werden �bersprungen
		// - Knoten des Typs Node.TEXT_NODE werden mit der Ersetzungstabelle bearbeitet

		int i;
		boolean inExclusionList = false;
   
   //--------------------------------------------------
   //			Start
   //		Added by Firas
   //--------------------------------------------------
   
   NamedNodeMap NodeAttrib = n.getAttributes();
   
   if (NodeAttrib != null)
   {
      for (i = 0; i < NodeAttrib.getLength(); i++)
      {
      	for (int j = 0;j < this.AttributeInclusionList.length;j++)
      	{
      		if (NodeAttrib.item(i).getNodeName().compareTo(AttributeInclusionList[j])  == 0)
      		{
      				
   
      		//----------------
   					String s = NodeAttrib.item(i).getNodeValue();
      		      int pos = 0;  // Zeichenposition
			      boolean weiter;
			      Iterator it;
			
			      ConversionTableEntry cte = (ConversionTableEntry) EntrySet.first();
			      while ( pos <= s.length() )
			      {
			        weiter = true;
			        // f�r jeden Eintrag in der Entry-Liste
			        for (it = EntrySet.iterator(); (it.hasNext() && weiter); )
			        {
			          cte = (ConversionTableEntry) it.next();
			          if ( s.startsWith(cte.from(), pos) )
			          {
			            // Der String s beginnt an Position pos mit dem String "from".
			            // Er wird dann durch den "to"-String ersetzt.
			            s = s.substring(0, pos) + cte.to() + s.substring(pos+cte.from().length());
			            // Wenn der Eintrag ersetzt wurde, wird die Position pos im String s
			            // auf das letzte Zeichen des ersetzten Teilstrings gesetzt.
			            pos += cte.to().length() - 1;
			            // Die for-Schleife wird nicht weiter durchlaufen.
			            weiter = false;
			          } // if
			        } // for
			        pos = pos+1;
			      }
			         		
					NodeAttrib.item(i).setNodeValue(s);
					  		
					//----------------
      		}
      				
      	}
      } // for
   }//if
   //--------------------------------------------------
   //			End
   //		Added by Firas
   //--------------------------------------------------

   if (n.getNodeType() == Node.ELEMENT_NODE){
      for (i=0; i<ExclusionElement.length; i++)
      {
        if ( n.getNodeName().compareTo(ExclusionElement[i]) == 0 )
        {
          inExclusionList = true;
        } // if
      } // for

      NamedNodeMap atts = n.getAttributes();
      for (i = 0; i < atts.getLength(); i++)
      {
        Node att = atts.item(i);
        //convert(att); deaktiviert von Gerd 30.07.2002
      } // for
    } // if
    else if (n.getNodeType() == Node.TEXT_NODE)
    {
      String s = n.getNodeValue();
      //System.out.println("ELEMENT_NODE: " + s);
      int pos = 0;  // Zeichenposition
      boolean weiter;
      Iterator it;

      ConversionTableEntry cte = (ConversionTableEntry) EntrySet.first();
      while ( pos <= s.length() )
      {
        weiter = true;
        // f�r jeden Eintrag in der Entry-Liste
        for (it = EntrySet.iterator(); (it.hasNext() && weiter); )
        {
          cte = (ConversionTableEntry) it.next();
          if ( s.startsWith(cte.from(), pos) )
          {
            // Der String s beginnt an Position pos mit dem String "from".
            // Er wird dann durch den "to"-String ersetzt.
            s = s.substring(0, pos) + cte.to() + s.substring(pos+cte.from().length());
            // Wenn der Eintrag ersetzt wurde, wird die Position pos im String s
            // auf das letzte Zeichen des ersetzten Teilstrings gesetzt.
            pos += cte.to().length() - 1;
            // Die for-Schleife wird nicht weiter durchlaufen.
            weiter = false;
          } // if
        } // for
        pos = pos+1;
      } // while
      n.setNodeValue(s);
    } // if

    if (!inExclusionList)
    {
      for (Node child = n.getFirstChild(); child != null; child = child.getNextSibling())
      {
        convert(child);
      } // for
    } // if
  } // convert

  /**
   * �bergabe  des Dateinamens der Config-Datei
   * @param argv[]
   * @roseuid 3B83803902F8
   */
	public static void main(String[] argv)
	{
		if (argv.length != 4)
		{
			System.err.println("Usage: MappingPreprocessor <infile> <outfile> <conversionlist> <xslfile>");
			System.exit(1);
		}
    
		System.out.println("MappingPreprocessor - START - " + argv[0]);
		MappingPreprocessor mp = new MappingPreprocessor(argv[0], argv[1], argv[2], argv[3]);
		System.out.println("MappingPreprocessor - ENDE - ");

		/*
		System.out.println("ELEMENT_NODE: " + Node.ELEMENT_NODE);
		System.out.println("ATTRIBUTE_NODE: " + Node.ATTRIBUTE_NODE);
		System.out.println("TEXT_NODE: " + Node.TEXT_NODE);
		System.out.println("CDATA_SECTION_NODE: " + Node.CDATA_SECTION_NODE);
		System.out.println("ENTITY_REFERENCE_NODE: " + Node.ENTITY_REFERENCE_NODE);
		System.out.println("ENTITY_NODE: " + Node.ENTITY_NODE);
		System.out.println("PROCESSING_INSTRUCTION_NODE: " + Node.PROCESSING_INSTRUCTION_NODE);
		System.out.println("COMMENT_NODE: " + Node.COMMENT_NODE);
		System.out.println("DOCUMENT_NODE: " + Node.DOCUMENT_NODE);
		System.out.println("DOCUMENT_TYPE_NODE: " + Node.DOCUMENT_TYPE_NODE);
		System.out.println("DOCUMENT_FRAGMENT_NODE: " + Node.DOCUMENT_FRAGMENT_NODE);
		System.out.println("NOTATION_NODE: " + Node.NOTATION_NODE);
		*/
	}
}
