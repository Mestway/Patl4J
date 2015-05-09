package org.openfuxml.addon.jsfapp.task;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.openfuxml.addon.jsfapp.factory.MenuFactory;

public class MenuParserTask extends Task
{	
	final static Logger logger = LoggerFactory.getLogger(MenuParserTask.class);
	
	private String htmlToc,addToc,xmlToc, prefix, suffix;

	private boolean useLog4j;

	public MenuParserTask()
	{
		useLog4j=false;
		suffix="html";
		prefix="";
	}
	
    public void execute() throws BuildException
    {
    	checkParameter();
    	
    	File fHtmlToc = new File(htmlToc);
    	File fXmlToc = new File(xmlToc);
    	
    	MenuFactory mf = new MenuFactory();
    	mf.parse(fHtmlToc,prefix,suffix);
    	
    	if(addToc!=null)
    	{
    		try
    		{
				mf.addToc(addToc);
				mf.save(fXmlToc);
			}
    		catch (FileNotFoundException e)
    		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	}
    }
    
    private void checkParameter()
    {    	
    	if(xmlToc==null){throw new BuildException("xmlToc must be specified.");}
    	
    	if(htmlToc==null){throw new BuildException("htmlToc must be specified.");}
    	File fHtmlToc = new File(htmlToc);
    	if(!fHtmlToc.exists()){throw new BuildException(htmlToc+" does not exist.");}
    	
    	if(addToc!=null)
    	{
    		File fAddToc = new File(addToc);
        	if(!fAddToc.exists()){throw new BuildException(addToc+" does not exist.");}
    	}
    }

	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	public void setHtmlToc(String htmlToc) {this.htmlToc = htmlToc;}
	public void setXmlToc(String xmlToc) {this.xmlToc = xmlToc;}
	public void setAddToc(String addToc) {this.addToc = addToc;}
	public void setSuffix(String suffix) {this.suffix = suffix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		MenuParserTask mpt = new MenuParserTask();
		mpt.setUseLog4j(true);
		mpt.setHtmlToc(args[0]);
		mpt.setXmlToc(args[1]);
		if(args.length==3){mpt.setAddToc(args[2]);}
		mpt.setSuffix("jsf");
		mpt.setSuffix("/sigire");
		mpt.execute();
	}
}