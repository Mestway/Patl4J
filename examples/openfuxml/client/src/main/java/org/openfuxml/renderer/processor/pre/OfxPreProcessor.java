package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.io.FileUtils;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.net.WikiContentFetcher;
import org.openfuxml.addon.wiki.processor.ofx.WikiXmlProcessor;
import org.openfuxml.addon.wiki.processor.pre.WikiExternalIntegrator;
import org.openfuxml.addon.wiki.processor.template.WikiTemplateCorrector;
import org.openfuxml.addon.wiki.processor.template.WikiTemplateProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.exception.OfxRenderingException;
import org.openfuxml.processor.pre.ExternalContentEagerLoader;
import org.openfuxml.renderer.OfxRenderProcessor.DirCode;
import org.openfuxml.renderer.OfxRenderProcessor.FileCode;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.openfuxml.xml.renderer.cmp.Preprocessor;
import org.openfuxml.xml.renderer.cmp.Wiki;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxPreProcessor
{
	final static Logger logger = LoggerFactory.getLogger(OfxPreProcessor.class);
		
	private OfxRenderConfiguration cmpConfigUtil;
	
	private Cmp cmp;
	private Preprocessor xmlPreProcessor;

	private Document ofxDoc;
	private Contents wikiQueries;

	
	public OfxPreProcessor(OfxRenderConfiguration cmpConfigUtil)
	{
		this.cmpConfigUtil=cmpConfigUtil;
	}

	public void chain() throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		xmlPreProcessor = cmpConfigUtil.getCmp().getPreprocessor();
		cmp = cmpConfigUtil.getCmp();
		File fOfxRoot = cmpConfigUtil.getFile(cmp.getSource().getDir(), DirCode.content.toString(), FileCode.root.toString(),false);
		
		File dWorking = cmpConfigUtil.getDir(xmlPreProcessor.getDir(), DirCode.working.toString());
		logger.debug("Working Dir: "+dWorking.getAbsolutePath());
		
		chain(dWorking, fOfxRoot);
	}
	
	public void chain(File dWorking, File actualVersion) throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		String wikiPlainDir = "wikiPlain";
		File dirWikiPlain = createDir(dWorking,wikiPlainDir);
		File dirWikiTemplate = createDir(dWorking,WikiProcessor.WikiDir.wikiTemplate.toString());
		File dirOfxTemplate = createDir(dWorking,WikiProcessor.WikiDir.ofxTemplate.toString());
		
		String wikiMarkupDir = "wikiMarkup";
		String wikiModelDir = "wikiModel";
		String xhtmlReplaceDir = "xhtmlReplace";
		String xhtmlFinalDir = "xhtmlFinal";
		String ofxXmlDir = "ofxXml";
		
		actualVersion = phaseExternalMerge(actualVersion, FileCode.external1);
		
		// These steps are documented in 
		// https://sourceforge.net/apps/mediawiki/openfuxml/index.php?title=Wiki_Transformation_Guide
		actualVersion = phaseWikiExternalIntegrator(actualVersion,FileCode.wikiIntegrate,ofxXmlDir);
		phaseWikiContentFetcher(dWorking,dirWikiPlain);
		phaseWikiProcessing(dWorking,wikiPlainDir,wikiMarkupDir,wikiModelDir,xhtmlReplaceDir,xhtmlFinalDir,ofxXmlDir, dirWikiTemplate);
		
		actualVersion = phaseExternalMerge(actualVersion, FileCode.external2);
		actualVersion = phaseContainerMerge(actualVersion,FileCode.container1);
		actualVersion = phaseTemplate(actualVersion, FileCode.template, dirWikiTemplate, dirOfxTemplate);
		
		actualVersion = phaseExternalMerge(actualVersion, FileCode.external3);
		actualVersion = phaseContainerMerge(actualVersion,FileCode.container2);
		actualVersion = idGenerator(actualVersion);
		finalCopy(actualVersion);
	}
	
	private File idGenerator(File srcFile) throws OfxConfigurationException, OfxInternalProcessingException
	{
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), FileCode.idsGenerated.toString(),true);
		logger.info("ID Generator: "+dstFile);
		
		OfxIdGenerator idCreator = new OfxIdGenerator();
		idCreator.createIds(srcFile, dstFile);
		return dstFile;
	}
	
	private void finalCopy(File srcFile) throws OfxConfigurationException, OfxInternalProcessingException
	{
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), FileCode.ofxPreFinished.toString(),true);
		try{FileUtils.copyFile(srcFile, dstFile);}
		catch (IOException e) {throw new OfxInternalProcessingException(e.getMessage());}
		logger.info("PreProcessing Finished: "+dstFile);
	}
	
	private File phaseExternalMerge(File srcFile, FileCode code) throws OfxInternalProcessingException, OfxConfigurationException
	{
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), code.toString(),true);
		try
		{
			ofxDoc = JaxbUtil.loadJAXB(srcFile.getAbsolutePath(), Document.class);
			
			ExternalContentEagerLoader exMerger = new ExternalContentEagerLoader();
			ofxDoc = exMerger.mergeToOfxDoc(srcFile);
			
			JaxbUtil.save(dstFile, ofxDoc, true);
		}
		catch (FileNotFoundException e)
		{
			logger.warn("OfxPreprocessorException");//TODO new exception
			e.printStackTrace();
		}
		return dstFile;
	}
	
	private File phaseContainerMerge(File srcFile, FileCode code) throws OfxInternalProcessingException, OfxConfigurationException
	{		
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), code.toString(),true);
		try
		{
			ofxDoc = JaxbUtil.loadJAXB(srcFile.getAbsolutePath(), Document.class);
			
			OfxContainerMerger containerMerger = new OfxContainerMerger();
			ofxDoc = containerMerger.merge(ofxDoc);
			
			JaxbUtil.save(dstFile, ofxDoc, true);
		}
		catch (FileNotFoundException e)
		{
			logger.warn("OfxPreprocessorException");//TODO new exception
			e.printStackTrace();
		}
		return dstFile;
	}
	
	private File phaseTemplate(File srcFile, FileCode code, File dirWikiTemplate, File dirOfxTemplate) throws OfxInternalProcessingException, OfxConfigurationException
	{
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), code.toString(),true);
		
		WikiInlineProcessor wikiInlineProcessor = new WikiInlineProcessor(cmp);
		
		try
		{
			ofxDoc = JaxbUtil.loadJAXB(srcFile.getAbsolutePath(), Document.class);
			WikiTemplateCorrector templateCorrector = new WikiTemplateCorrector();
			templateCorrector.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
			ofxDoc = templateCorrector.correctTemplateInjections(ofxDoc);
			JaxbUtil.save(dstFile, ofxDoc, true);
			
			WikiTemplateProcessor wtp = new WikiTemplateProcessor(wikiInlineProcessor,xmlPreProcessor.getWiki().getTemplates());
			wtp.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
			wtp.setDirectory(WikiProcessor.WikiDir.ofxTemplate, dirOfxTemplate);
			wtp.process();
		}
		catch (FileNotFoundException e){throw new OfxInternalProcessingException(e.getMessage());}
		return dstFile;
	}
	
	private File phaseWikiExternalIntegrator(File srcFile, FileCode code, String wikiXmlDir) throws OfxAuthoringException, OfxInternalProcessingException, OfxConfigurationException
	{		
		File dstFile = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), code.toString(),true);
		try
		{
			ofxDoc = JaxbUtil.loadJAXB(srcFile.getAbsolutePath(), Document.class);
			WikiExternalIntegrator wikiExIntegrator = new WikiExternalIntegrator(wikiXmlDir);
			wikiExIntegrator.integrateWikiAsExternal(ofxDoc);
			ofxDoc = wikiExIntegrator.getResult();
			wikiQueries = wikiExIntegrator.getWikiQueries();
			
			JaxbUtil.save(dstFile, ofxDoc, true);
		}
		catch (FileNotFoundException e){throw new OfxInternalProcessingException(e.getMessage());}
		return dstFile;
	}
	
	private void phaseWikiContentFetcher(File dWorking, File dirWikiPlain) throws OfxRenderingException, OfxInternalProcessingException, OfxAuthoringException, OfxConfigurationException
	{
		Wiki wiki = cmp.getPreprocessor().getWiki();
		if(!wiki.isSetFetchArticle())
		{
			throw new OfxConfigurationException("Attribute <wiki fetchArticle=true|false not set");
		}
		
		File fContents = new File(dWorking,"contens.xml");
		if(wiki.isFetchArticle())
		{
			if(wikiQueries.isSetContent())
			{
				WikiBotFactory wbf = new WikiBotFactory(wiki.getServers());
				
				//TODO Integrate AUTH in WBF
				logger.warn("NYI AUTH");
//				wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
//				wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
				
				WikiContentFetcher contentFetcher = new WikiContentFetcher(wbf);
				contentFetcher.setDirectories(null,dirWikiPlain);
				try
				{
					contentFetcher.process(wikiQueries);
					JaxbUtil.save(fContents, wikiQueries, true);
				}
				catch (OfxWikiException e)
				{
					throw new OfxRenderingException(e.getMessage());
				}
			}
		}
		else
		{
			try {wikiQueries = (Contents)JaxbUtil.loadJAXB(fContents.getAbsolutePath(), Contents.class);}
			catch (FileNotFoundException e) {throw new OfxInternalProcessingException(e.getMessage());}
		}
	}
	
	private void phaseWikiProcessing(File dWorking, String wikiPlainDir, String wikiMarkupDir, String wikiModelDir, String xhtmlReplace, String xhtmlFinal, String xmlOfx, File dirWikiTemplate) throws OfxConfigurationException, OfxWikiException, OfxAuthoringException, OfxInternalProcessingException
	{	
		File dirWikiPlain = createDir(dWorking, wikiPlainDir);
		File dirWikiMarkup = createDir(dWorking, wikiMarkupDir);
		File dirWikiModel = createDir(dWorking, wikiModelDir);
		File dirXhtmlReplace = createDir(dWorking, xhtmlReplace);
		File dirXhtmlFinal = createDir(dWorking, xhtmlFinal);
		File dirXmlOfx = createDir(dWorking, xmlOfx);
		
		MarkupProcessor mpXml = xmlPreProcessor.getWiki().getMarkupProcessor();
		XhtmlProcessor xpXml = xmlPreProcessor.getWiki().getXhtmlProcessor();
		Templates templates = xmlPreProcessor.getWiki().getTemplates();
		
		List<WikiProcessor> lWikiProcessors = new ArrayList<WikiProcessor>();
		
		WikiProcessor wpMarkup = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
		wpMarkup.setDirectories(dirWikiPlain, dirWikiMarkup);
		wpMarkup.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
		lWikiProcessors.add(wpMarkup);
		
		WikiProcessor wpModel = new WikiModelProcessor();
		wpModel.setDirectories(dirWikiMarkup, dirWikiModel);
		lWikiProcessors.add(wpModel);
		
		WikiProcessor wpXhtmlR = new XhtmlReplaceProcessor(xpXml.getReplacements());
		wpXhtmlR.setDirectories(dirWikiModel, dirXhtmlReplace);
		lWikiProcessors.add(wpXhtmlR);
		
		WikiProcessor wpXhtmlF = new XhtmlFinalProcessor();
		wpXhtmlF.setDirectories(dirXhtmlReplace, dirXhtmlFinal);
		lWikiProcessors.add(wpXhtmlF);
		
		for(WikiProcessor wikiProcessor : lWikiProcessors)
		{
			wikiProcessor.process(wikiQueries);
		}	

		WikiXmlProcessor ofxP = new WikiXmlProcessor();
		ofxP.setDirectories(dirXhtmlFinal, dirXmlOfx);
		ofxP.process(wikiQueries);
	}
	
	private File createDir(File dWorking, String dirName)
	{
		File dir = new File(dWorking,dirName);
		if(!dir.exists()){dir.mkdir();}
		return dir;
	}
}