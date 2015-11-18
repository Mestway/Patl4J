package org.openfuxml.addon.jsfapp.task;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.openfuxml.addon.jsfapp.AppInjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;

public class AppInjectionTask extends Task
{		
	private String htmlDir,jsfDir,injectionXml;
	private boolean useLog4j;

	public AppInjectionTask()
	{
		useLog4j=false;
	}
	
    public void execute() throws BuildException
    {
    	checkParameter();
    	
    	File fHtmlDir = new File(htmlDir);
    	File fJsfDir = new File(jsfDir);
    	Ofxinjections ofxI;
		try
		{
			ofxI = (Ofxinjections)JaxbUtil.loadJAXB(injectionXml, Ofxinjections.class);
			AppInjection ai = new AppInjection(ofxI,fJsfDir,useLog4j);
	    	ai.inject(fHtmlDir);
		}
		catch (FileNotFoundException e)
		{
			// TODO BuildException
			e.printStackTrace();
		}

    }
    
    private void checkParameter()
    {
    	if(htmlDir==null){throw new BuildException("htmlDir must be specified.");}
    	File fHtmlDir = new File(htmlDir);
    	if(!fHtmlDir.exists()){throw new BuildException(htmlDir+" does not exist.");}
    	if(!fHtmlDir.isDirectory()){throw new BuildException(htmlDir+" not a directory.");}
    	
    	if(jsfDir==null){throw new BuildException("jsfDir must be specified.");}
    	File fJsfDir = new File(jsfDir);
    	if(!fJsfDir.exists()){throw new BuildException(jsfDir+" does not exist.");}
    	if(!fJsfDir.isDirectory()){throw new BuildException(jsfDir+" not a directory.");}
    	
    	if(injectionXml==null){throw new BuildException("injectionXml must be specified.");}
    	File fInjectionXml = new File(injectionXml);
    	if(!fInjectionXml.exists()){throw new BuildException(fInjectionXml+" does not exist.");}
    }
    
	public void setHtmlDir(String htmlDir) {this.htmlDir = htmlDir;}
	public void setJsfDir(String jsfDir) {this.jsfDir = jsfDir;}
	public void setInjectionXml(String injectionXml) {this.injectionXml = injectionXml;}
	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		AppInjectionTask ait = new AppInjectionTask();
		ait.setUseLog4j(true);
		ait.setInjectionXml(args[0]);
		ait.setHtmlDir(args[1]);
		ait.setJsfDir(args[2]);
		ait.execute();
	}
}