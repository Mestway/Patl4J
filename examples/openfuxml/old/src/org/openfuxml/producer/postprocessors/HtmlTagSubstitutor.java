package org.openfuxml.producer.postprocessors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.BrowserCompactXmlSerializer;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CleanerTransformations;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagTransformation;
import org.openfuxml.util.OfxApp;

public class HtmlTagSubstitutor extends DirectoryWalker
{
	final static Logger logger = LoggerFactory.getLogger(OfxApp.class);
	
	private Configuration config;
	private int anzElements;
	private CleanerTransformations ct;
	
	public HtmlTagSubstitutor(String suffix)
	{
		super(HiddenFileFilter.VISIBLE,FileFilterUtils.suffixFileFilter("."+suffix),10);
		initCT();
	}
	
	private void initCT()
	{
		ct =  new CleanerTransformations();
		TagTransformation tt = new TagTransformation("div", "div", true);
			tt.addAttributeTransformation("xmlns:saxon");
			tt.addAttributeTransformation("xmlns:xs");
		ct.addTransformation(tt);
	}
	
	@SuppressWarnings("unchecked")
	protected void handleFile(File file, int depth, Collection results)
	{
		File f = new File(FilenameUtils.normalize(file.getAbsolutePath()));
		logger.debug(f.getAbsoluteFile());
		try
		{
			HtmlCleaner cleaner = new HtmlCleaner();
			cleaner.setTransformations(ct);
			
			CleanerProperties props = cleaner.getProperties();
//				props.setAdvancedXmlEscape(false);
				props.setUseEmptyElementTags(false);
//				props.setTranslateSpecialEntities(false);
//				props.setRecognizeUnicodeChars(false);
				
			TagNode node = cleaner.clean(f);
			
			TagNode tnBody = node.getAllElements(false)[1];
			List l = tnBody.getChildren();
			if(l!=null && l.size()>0)
			{	//This is a hack to remove the <?xml in the beginning of body
				tnBody.removeChild(l.get(0));
			}
			
			for(int i=1;i<=anzElements;i++)
			{
				String tag = config.getString("substitute["+i+"]/@tag");
				String att = config.getString("substitute["+i+"]/@att");
				String from = config.getString("substitute["+i+"]/from");
				String to = config.getString("substitute["+i+"]/to");
				to=subSpecial(to);
				
				TagNode[] imgs = node.getElementsByName(tag, true);
				
				for(TagNode tn : imgs)
				{
					String srcAtt = tn.getAttributeByName(att);
					int index = srcAtt.indexOf(from);
					if(index>=0)
					{
						tn.addAttribute(att, to);
					}
				}
			}
			
			BrowserCompactXmlSerializer serializer = new BrowserCompactXmlSerializer(props);
//			PrettyXmlSerializer serializer = new PrettyXmlSerializer(props);
			
			String s = serializer.getXmlAsString(node, "ISO-8859-1"); 
		
			Writer fw = null; 	 
			try 
			{ 
			  fw = new FileWriter(f); 
			  fw.write(s); 
			} 
			catch ( IOException e ) {logger.error("",e);} 
			finally
			{
				if (fw != null) 
			    try {fw.close();} catch (IOException e) {} 
			}
			
			results.add(f.getAbsoluteFile());
		}
		catch (IOException e) {logger.error("",e);}
	}
	
	public void start(File startDir, Collection results, String xmlFileName)
	{
		logger.debug("File: "+xmlFileName);
		File fXml = new File(xmlFileName);
		if(fXml.exists() && fXml.isFile())
		{
			config = ConfigLoader.load(xmlFileName);
			
			anzElements = config.getStringArray("substitute/from").length;
			logger.info("HtmlTagSubstitute elements="+anzElements);
			
			try {walk(startDir,results);}
			catch (IOException e) {logger.error("",e);}
			logger.debug("Processed files: "+results.size());
		}
	}
	
	protected String subSpecial(String s)
	{
		s = s.replaceAll("#grrrrrrr", "&");
		return s;
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		File f = new File("/Users/thorsten/Documents/workspace/openfuxml/output/fuxml/jWan/html/jWAN");
		Collection<String> c = new ArrayList<String>();
			
		HtmlTagSubstitutor hpf = new HtmlTagSubstitutor("html"); 
		hpf.start(f, c,"/Users/thorsten/Documents/workspace/svn/jwan/system/config/HtmlTagSubstitute.xml");
	}
}
