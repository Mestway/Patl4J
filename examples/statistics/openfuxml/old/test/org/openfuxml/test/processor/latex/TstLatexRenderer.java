package org.openfuxml.test.processor.latex;

import java.io.File;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.renderer.processor.latex.OfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.TxtWriter;

public class TstLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TstLatexRenderer.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		String fNameCmp = config.getString("ofx.xml.cmp");
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
		
		OfxLatexRenderer renderer = new OfxLatexRenderer(cmp.getTargets().getPdf().get(0));
		renderer.render("resources/data/xml/latex/2.xml");
		
		File dstDir = new File(config.getString("wiki.latex.dir"));
		
		TxtWriter writer = new TxtWriter();
		writer.setTargetDirFile(dstDir, config.getString("wiki.latex.file"));
//		writer.debug(renderer.getContent());
		writer.writeFile(renderer.getContent());
	}
}