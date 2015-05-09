package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.FilenameUtils;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.filter.Filters;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;
import org.dom4j.NamespaceStack;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalContentEagerLoader
{
	final static Logger logger = LoggerFactory.getLogger(ExternalContentEagerLoader.class);
	
	private RelativePathFactory rpf;
	private MultiResourceLoader mrl;
	
	private File rootFile;
	private org.dom4j.Document doc; 
	private XPathFactory xpFactory; 
	
	private XPath xpath;  
	
	public ExternalContentEagerLoader()
	{
		mrl = new MultiResourceLoader();
		rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.CURRENT);
		try
		{
			xpath = DocumentHelper.createXPath("//*[@external='true']"); 
            NamespaceStack nstk = new NamespaceStack(); 
			nstk.push(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			nstk.push(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));		
		}
		catch (Exception e) {logger.error("",e);}
		//xpFactory = XPathFactory.instance(); 
	}
	
    /*
    protected XPathExpression<Element> build() 
	{
//		Namespace ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org"); 
		
		XPathExpression<Element> xpe= xpFactory.compile("//*[@include]", Filters.element()); 
		return xpe;
	}
    */
	
	public org.dom4j.Document load(String resourceName) throws FileNotFoundException, OfxAuthoringException 
	{
		org.dom4j.Document doc = DocumentHelper.createDocument(); 
		doc.setRootElement(loadElement(resourceName)); 
		return doc;
	}
	
	private Element loadElement(String resourceName) throws OfxAuthoringException 
	{
		try
		{
			InputStream is = mrl.searchIs(resourceName);
			org.dom4j.Document doc = JDomUtil.load(is); 
			Element root = doc.getRootElement(); 
			
			//List<Element> list = build().evaluate(root); 
            List<Element> list; 
			logger.debug("Now processing childs: "+list.size());
			for(Element childElement : list) 
			{
				String source = childElement.attribute("include").getValue(); 
				String resourceChild = FilenameUtils.getFullPath(resourceName)+source;

				logger.debug("Found external in "+resourceChild);
				
				ExternalContentEagerLoader em = new ExternalContentEagerLoader();
				Element eExternal = em.loadElement(resourceChild); 
				eExternal.detach(); 
				
				int index = childElement.getParent().indexOf(childElement); 
				//childElement.getParent().setContent(index, eExternal);  
				childElement.detach(); 
			}
			root.detach(); 
			return root;
		}
		catch (FileNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	@Deprecated
	private void loadRoot(File rootFile) throws OfxInternalProcessingException
	{
		this.rootFile=rootFile;
		doc = JDomUtil.load(rootFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+rootFile.getAbsolutePath());}
		logger.trace("Loaded: "+rootFile.getAbsolutePath());
	}
	
	@Deprecated
	public Document mergeToOfxDoc(File rootFile) throws OfxInternalProcessingException
	{
		org.dom4j.Document doc = mergeToDoc(rootFile);  
		Document ofxDoc = (Document)JDomUtil.toJaxb(doc, Document.class);
		return ofxDoc;
	}
	
	@Deprecated
	public org.dom4j.Document mergeToDoc(File rootFile) throws OfxInternalProcessingException 
	{
		loadRoot(rootFile);
		
		Element rootElement = doc.getRootElement(); 
		Element result = mergeRecursive(rootElement); 
		result.detach(); 
		doc.setRootElement(result); 
		return doc;
	}
	
	@Deprecated
	public Element getExternal(File rootFile) throws OfxInternalProcessingException 
	{
		loadRoot(rootFile);
		Element rootElement = doc.getRootElement(); 
		
		return mergeRecursive(rootElement);
	}
	
	@Deprecated
	private Element mergeRecursive(Element rootElement) throws OfxInternalProcessingException 
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement); 
			logger.debug(list.size()+" external sources in "+rootElement.getName()+" in "+rootFile.getAbsolutePath()); 
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next(); 
				String source =childElement.attribute("source").getValue(); 
				File childFile = new File(rootFile.getParentFile(),source);
				logger.trace("Found external in "+rpf.relativate(rootFile.getParentFile(), childFile));
				ExternalContentEagerLoader em = new ExternalContentEagerLoader();
				Element eEx = em.getExternal(childFile); 
				eEx.detach(); 
				int index = childElement.getParent().indexOf(childElement); 
				//childElement.getParentElement().setContent(index, eEx);  
				childElement.detach(); 
			}
		}
		catch (Exception e) {logger.error("",e);}
		return rootElement;
	}
}
