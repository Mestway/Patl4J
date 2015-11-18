package org.openfuxml.addon.wiki.processor.xhtml.mods;

import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XhtmlCodePreMover
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlCodePreMover.class);
	
	private XPath xpathCode, xpParent, xpathPre; 
	private Element rootElement; 
	
	public XhtmlCodePreMover()
	{
		try
		{
			xpathCode = DocumentHelper.createXPath("//code"); 
			xpParent = DocumentHelper.createXPath(".."); 
			xpathPre = DocumentHelper.createXPath("following-sibling::pre[position()=1]"); 
//			xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
//			xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));		
		}
		catch (Exception e) {logger.error("",e);}
	}
	
	public String move(String xHtmlText) throws Exception
	{
		Document doc = JDomUtil.txtToDoc(xHtmlText); 
		rootElement = doc.getRootElement(); 
		logger.debug(JDomUtil.docToTxt(doc));
		
		process();
		
		xHtmlText=JDomUtil.docToTxt(doc);
//		JDomUtil.debug(doc);
		return xHtmlText;
	}

	private void process() throws Exception
	{
		List<?> list = xpathCode.selectNodes(rootElement); 
		logger.debug(list.size()+" <code> elements found in "+rootElement.getName()); 
		for (Iterator<?> iter = list.iterator(); iter.hasNext();)
		{
			logger.trace("Processing code *************");
			Element eCode = (Element) iter.next(); 
			
			if(eCode.elements().size()==0) 
			{
				Element eP = (Element)xpParent.selectSingleNode(eCode); 
				if(eP!=null)
				{
					logger.trace("eP="+eP);
	
					Element ePre = (Element)xpathPre.selectSingleNode(eP); 
					if(ePre!=null)
					{
						logger.trace("ePre="+ePre);
		
						int iP = eP.getParent().indexOf(eP); 
						int iPre = ePre.getParent().indexOf(ePre);				 
						logger.trace(iP+" "+iPre);
						if(iPre==(iP+1))
						{
							eCode.setText(ePre.getText()); 
							ePre.detach(); 
							
							Element eCodeGrandParent = eCode.getParent().getParent(); 
							int iCodeParent = eCodeGrandParent.indexOf(eCode.getParent()); 
							logger.debug(iCodeParent+"");
							eCode.detach(); 
							eCodeGrandParent.remove(iCodeParent); 
                            Element ee = (Element) eCodeGrandParent.node(iCodeParent); 
                            ee.add(eCode); 
							//eCodeGrandParent.addContent(iCodeParent, eCode); 
						}
					}
				}
			}
		}
	}
}
