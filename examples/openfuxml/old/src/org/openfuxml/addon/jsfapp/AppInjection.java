package org.openfuxml.addon.jsfapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.RecursiveFileFinder;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.exception.JDomUtilException;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Task;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.NamespaceStack;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;
import org.dom4j.QName;

import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;
import org.openfuxml.addon.jsfapp.factory.JsfJspxFactory;
import org.openfuxml.addon.jsfapp.factory.NsFactory;

public class AppInjection extends Task
{	
	final static Logger logger = LoggerFactory.getLogger(AppInjection.class);
	
	private boolean useLog4j;
	private RecursiveFileFinder rfi;
	private Ofxinjections ofxIs;
	private File jsfDir;
	
	public AppInjection(Ofxinjections ofxIs, File jsfDir)
	{
		this(ofxIs,jsfDir,false);
	}
	
	public AppInjection(Ofxinjections ofxIs,File jsfDir, boolean useLog4j)
	{
		this.ofxIs=ofxIs;
		this.jsfDir=jsfDir;
		this.useLog4j=useLog4j;
		IOFileFilter df = HiddenFileFilter.VISIBLE;
		IOFileFilter ff = FileFilterUtils.suffixFileFilter(".html");
	
		rfi = new RecursiveFileFinder(df,ff);
	}
	
	public void inject(File htmlDir)
	{
		List<File> htmlFile = new ArrayList<File>();
		try {htmlFile = rfi.find(htmlDir);}
		catch (IOException e)
		{
			if(useLog4j){logger.debug(e.getMessage());}
			else{System.err.println(e.getMessage());}
		}
		for(File f : htmlFile)
		{	
			File fJsf = getJsfFile(f);
			try
			{
				logger.debug(f.getAbsolutePath());
				Document docJsf = createJsf(JDomUtil.load(f,"ISO-8859-1")); 
				addJsfView(docJsf);
				
				if(ofxIs.isSetGenericinjection())
				{
					for(Ofxinjection ofxI : ofxIs.getGenericinjection().getOfxinjection())
					{
						genericInjection(ofxI,docJsf);
					}
				}
				
				idsearch:
				for(Ofxinjection ofxI : ofxIs.getOfxinjection())
				{
					try
					{
						XPath xpath = DocumentHelper.createXPath("//html:div[@id=\""+ofxI.getId()+"\"]"); 
						for(Namespace ns : NsFactory.getNs("html")){ 
                            NamespaceStack nstk = new NamespaceStack(); 
                            nstk.addNamespace(ns.getPrefix(), ns.getURI());
                        }
						Element element = (Element)xpath.selectSingleNode(docJsf); 
						
						if(element!=null)
						{
							StringBuffer sb = new StringBuffer();
							if(ofxI.isSetIframe())
							{
								sb.append("iframe: ");
								injectIframe(ofxI, element);
							}
							else if(ofxI.isSetJsf())
							{
								sb.append("jsf: ");
								injectJsf(ofxI, docJsf);
							}
							sb.append(f.getName());
							if(useLog4j){logger.debug(sb);}
							else{System.out.println(sb.toString());}
							break idsearch;
						}
					}
					catch (Exception e) {logger.error("",e);}
				}	
				JDomUtil.save(docJsf, fJsf, Format.getRawFormat(),"ISO-8859-1");
			}
			catch (JDomUtilException e)
			{
				if(useLog4j){logger.error(e.getMessage());}else{System.err.println(e.getMessage());}
			}	
		}
	}
	
	private void injectIframe(Ofxinjection ofxI, Element element) 
	{
		logger.debug("Injection iframe");
		Document d = JaxbUtil.toDocument(ofxI.getIframe()); 
		Element eIframe = d.getRootElement(); 
		eIframe.detach(); 

		eIframe.setNamespace(null); 
        eIframe.setQName(new QName(eIframe.getName())); 

		element.addContent(eIframe); 
	}
	
	private void injectJsf(Ofxinjection ofxI, Document docJsf) 
	{
		try
		{
			//XPath xpathNewJsf = XPath.newInstance("//html:div[@id='lehrinhalt']");
            XPath xpathNewJsf = DocumentHelper.createXPath("//html:div[@id='lehrinhalt']"); 
            NamespaceStack nstk = new NamespaceStack();
			for(Namespace ns : NsFactory.getNs("html")) {
                //xpathNewJsf.addNamespace(ns); 
                nstk.push(ns);
            }
			
			Element eNewJsf = (Element)xpathNewJsf.selectSingleNode(docJsf); 
			
			File fOrigJsf = new File(jsfDir,ofxI.getJsf().getJsfile());
			Document docOrigJsf = JDomUtil.load(fOrigJsf); 
			
			//XPath xpathOrigJsf = XPath.newInstance("//f:view");
			XPath xpathOrigJsf = DocumentHelper.createXPath("//f:view"); 
            NamespaceStack nstk_Orig = new NamespaceStack(); 
			for(Namespace ns : NsFactory.getNs("f")){nstk_Orig.push(ns);}
			Element eOrigJsf = (Element)xpathOrigJsf.selectSingleNode(docOrigJsf); 

			//eNewJsf.removeContent();
            eNewJsf.clearContent(); 
			//eNewJsf.addContent(eOrigJsf.cloneContent());		
            eNewJsf.appendContent(eOrigJsf.createCopy()); 
		}
		catch (Exception e) {logger.error("",e);}
	}
	private File getJsfFile(File fHtml)
	{
		File dir = fHtml.getParentFile();
		String name = fHtml.getName();
		name=name.substring(0, name.lastIndexOf(".html"))+".jspx";
		File fJsf = new File(dir,name);
		return fJsf;
	}
	
	private Document createJsf(Document docHtml) 
	{
		Namespace htmlNs  = Namespace.getNamespace("http://www.w3.org/1999/xhtml"); 
		Element html = (Element)docHtml.getRootElement().clone(); 
		JDomUtil.setNameSpaceRecursive(html, htmlNs);
		Document docJsf = JsfJspxFactory.createDOMjspx(); 
		docJsf.getRootElement().addContent(html);		 
		return docJsf;
	}
	
	@SuppressWarnings("unchecked")
	private void addJsfView(Document docJsf) 
	{
		try
		{
			//XPath xpath = XPath.newInstance("//html:body");
			XPath xpath = DocumentHelper.createXPath("//html:body"); 
            NamespaceStack nstk = new NamespaceStack();
			for(Namespace ns : NsFactory.getNs("html","jsp")){ 
                //xpath.addNamespace(ns);
                nstk.push(ns); 
            }
			
			Element eBody = (Element)xpath.selectSingleNode(docJsf); 
			
			if(eBody!=null)
			{
				//List<Element> l = eBody.removeContent(); 

                Element eView = DocumentHelper.createElement("view", NsFactory.getSingelNs("f")); 
				//Element eView = new Element("view",NsFactory.getSingelNs("f"));
				//eView.setAttribute("locale", ofxIs.getLocale());
				eView.addAttribute("locale", ofxIs.getLocale()); 
				//eView.addContent(l);
                eView.appendContent(eBody); 
                eBody.clearContent();
                eBody.add(eView); 
				//eBody.addContent(eView);
			}
		}
		catch (Exception e) {logger.error("",e);}
	}
	
	private void genericInjection(Ofxinjection ofxI, Document docJsf) 
	{
		try
		{
			//XPath xpathResultJsf = XPath.newInstance("//html:div[@id='navi1']");
			XPath xpathResultJsf = DocumentHelper.createXPath("//html:div[@id='navi1']"); 
			//xpathResultJsf.addNamespace(NsFactory.getSingelNs("html")); 
            NamespaceStack nstk = new NamespaceStack();
            nstk.push(NsFactory.getSingelNs("html"));

			Element element = (Element)xpathResultJsf.selectSingleNode(docJsf); 
			
			if(element!=null)
			{
				//element.removeContent();
                element.clearContent(); 
				File fOrigJsf = new File(jsfDir,ofxI.getJsf().getJsfile());
				Document docOrigJsf = JDomUtil.load(fOrigJsf); 
				//XPath xpath = XPath.newInstance(ofxI.getXsrc());
			    XPath xpath = DocumentHelper.createXPath(ofxi.getxsrc()); 
				for(Namespace ns : NsFactory.getNs("h","jsp")){ 
                    //xpath.addNamespace(ns);
                    NamespaceStack nstk = new NamespaceStack(); 
                    nstk.push(ns);
                }

				
				Element eMenu = (Element)xpath.selectSingleNode(docOrigJsf); 
				eMenu.detach(); 
				//element.addContent(eMenu);
                element.add(eMenu); 
			}
		}
		catch (Exception e) {logger.error("",e);}
	}
}
