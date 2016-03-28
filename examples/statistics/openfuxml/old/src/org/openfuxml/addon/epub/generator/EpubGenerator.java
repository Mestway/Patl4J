package org.openfuxml.addon.epub.generator;

import java.io.File;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.openfuxml.addon.epub.generator.content.ContentGenerator;
import org.openfuxml.addon.epub.generator.epub.ContainerGenerator;
import org.openfuxml.addon.epub.generator.epub.MimetypeGenerator;
import org.openfuxml.addon.epub.generator.epub.NcxGenerator;
import org.openfuxml.addon.epub.generator.epub.OpfGenerator;
import org.openfuxml.addon.epub.util.EpubZipper;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.producer.preprocessors.IdTagger;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class EpubGenerator
{
	final static Logger logger = LoggerFactory.getLogger(EpubGenerator.class);
	
	private NcxGenerator ncxGenerator;
	private MimetypeGenerator mimeFactory;
	private ContainerGenerator containerFactory;
	private OpfGenerator opfFactory;
	private EpubZipper epubZipper;
	private ContentGenerator contentGenerator;
	
	private IdTagger idTagger;
	
	public EpubGenerator(File targetDir)
	{
		ncxGenerator = new NcxGenerator(targetDir);
		mimeFactory = new MimetypeGenerator(targetDir);
		containerFactory = new ContainerGenerator(targetDir);
		opfFactory = new OpfGenerator(targetDir);
		contentGenerator = new ContentGenerator(targetDir);
		epubZipper = new EpubZipper(targetDir);
		
		idTagger = new IdTagger();
	}
	
	public void process(File f) throws OfxInternalProcessingException
	{
		OfxExternalMerger exMerger = new OfxExternalMerger(f);
		Document doc = exMerger.mergeToDoc(); 
		idTagger.tag(doc);
		
		Ofxdoc ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		
		ncxGenerator.create(ofxDoc);
		containerFactory.create();
		mimeFactory.save();
		opfFactory.create(ofxDoc);
		contentGenerator.create(ofxDoc);
		
		epubZipper.zip();
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		File f = new File("resources/data/xml/epub/helloworld.xml");
		File baseDir = new File("dist");
		EpubGenerator epub = new EpubGenerator(baseDir);
		epub.process(f);
	}
}
