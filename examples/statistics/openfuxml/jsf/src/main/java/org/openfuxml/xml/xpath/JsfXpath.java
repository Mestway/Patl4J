package org.openfuxml.xml.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.openfuxml.xml.addon.jsf.Attribute;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfXpath
{
	final static Logger logger = LoggerFactory.getLogger(JsfXpath.class);
	
	public static synchronized Attribute getAttribute(Component component, String name) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<component.getAttribute().size();i++)
		{
			if(component.getAttribute().get(i).getName().equals(name)){result.add(i);}
		}
		
		if(result.size()==0){throw new ExlpXpathNotFoundException("No "+Attribute.class.getSimpleName()+" for name="+name);}
		else if(result.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Attribute.class.getSimpleName()+" for name="+name);}
		return component.getAttribute().get(result.get(0));
/*		
		JXPathContext context = JXPathContext.newContext(component);
		
		StringBuffer sb = new StringBuffer();
		sb.append("attribute[@name='").append(name).append("']");
		
		logger.info(sb.toString());
		
		@SuppressWarnings("unchecked")
		List<Attribute> list = (List<Attribute>)context.selectNodes(sb.toString());
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Attribute.class.getSimpleName()+" for name="+name);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Attribute.class.getSimpleName()+" for name="+name);}
		return list.get(0);
*/
		}
}