package org.openfuxml.addon.epub.generator.epub;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.jdom2.JDOMException;
import org.openfuxml.addon.epub.data.factory.NcxFactory;
import org.openfuxml.addon.epub.data.jaxb.EpubJaxbXpathLoader;
import org.openfuxml.addon.epub.data.jaxb.ncx.Head;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap.NavPoint;
import org.openfuxml.addon.epub.data.jaxb.ncx.Ncx;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class NcxGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	private Ncx ncx;
	private int playOrder;
	
	private OfxNsPrefixMapper ofxNsPrefixMapper;
	
	public NcxGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		ofxNsPrefixMapper = new OfxNsPrefixMapper();
		playOrder=1;
	}
	
	public void create(Ofxdoc ofxDoc)
	{
		ncx = new Ncx();
		ncx.setVersion("2005-1");
		ncx.setHead(getHead());
		ncx.setDocTitle(NcxFactory.getTitle(ofxDoc.getMetadata().getTitle().getValue()));
		ncx.setNavMap(getNavMap(ofxDoc));
		save();
	}
	
	private void save()
	{
		File f = new File(targetDir,"toc.ncx");
		JaxbUtil.save(f, ncx, true);
	}
	
	private Head getHead()
	{
		logger.debug("Creating Head");
		Head head = new Head();
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:uid", UUID.randomUUID().toString()));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:depth", "1"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:totalPageCount", "0"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:maxPageNumber", "0"));		
		return head;
	}
	
	private NavMap getNavMap(Ofxdoc ofxDoc)
	{
		logger.debug("Creating NavMap");
		NavMap navmap = new NavMap();
		try
		{
			navmap.getNavPoint().addAll(getSectionNav(ofxDoc));
			
//			Document doc = JaxbUtil.toDocument(ofxDoc, ofxNsPrefixMapper);
		}
		catch (JDOMException e) {logger.error("",e);}
		return navmap;
	}
	
	private List<NavPoint> getSectionNav(Ofxdoc ofxDoc) throws JDOMException
	{
		List<NavPoint> result = new ArrayList<NavPoint>();
		
		int partNr=1;
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				logger.debug("secNo="+partNr+" "+section.getId());
				Title title = EpubJaxbXpathLoader.getTitle(section);
				result.add(NcxFactory.getNavPoint(section.getId(), playOrder, title.getValue(), "part-"+partNr+".xhtml"));
				playOrder++;partNr++;
			}
		}
		return result;
	}
}
