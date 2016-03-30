package org.openfuxml.xml;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OfxNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://www.openfuxml.org".equals(namespaceUri) ){return "ofx";}
    	if("http://www.openfuxml.org/wiki".equals(namespaceUri) ){return "wiki";}
        if("http://www.openfuxml.org/chart".equals(namespaceUri) ){return "chart";}
        if("http://www.openfuxml.org/table".equals(namespaceUri) ){return "table";}
        if("http://www.openfuxml.org/layout".equals(namespaceUri) ){return "layout";}
        if("http://www.openfuxml.org/list".equals(namespaceUri) ){return "l";}
        if("http://www.openfuxml.org/text".equals(namespaceUri) ){return "text";}
        if("http://www.openfuxml.org/media".equals(namespaceUri) ){return "media";}
        
        if("http://www.openfuxml.org/jsf".equals(namespaceUri) ){return "jsf";}
        
        if("http://exlp.sf.net/io".equals(namespaceUri) ){return "io";}
  
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://www.openfuxml.org/chart";
    	result = new String[0];
        return result;
    }
}