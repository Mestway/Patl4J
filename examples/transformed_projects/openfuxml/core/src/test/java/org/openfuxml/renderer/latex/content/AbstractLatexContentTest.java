package org.openfuxml.renderer.latex.content;

import java.io.File;
import java.util.List;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.util.OfxLatexDefinitionBuilder;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLatexContentTest extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLatexContentTest.class);
	
	protected static final String rootDir = "src/test/resources/data/latex/content";

	protected File latexBase;
	
	protected void initLatexTestEnvironment(Configuration config)
	{
		latexBase = new File(config.getString(OfxCoreTestBootstrap.cfgKeyLatexTarget));
		logger.info("Test Environment "+latexBase.getAbsolutePath());
		
		try
		{
			OfxLatexDefinitionBuilder latexBuilder = new OfxLatexDefinitionBuilder(latexBase);
			latexBuilder.copyPackages();
			latexBuilder.copyTest();
		}
		catch (OfxConfigurationException e) {e.printStackTrace();}
	}
	
	protected void testLatex(List<String> content)
	{
		if(latexBase!=null && latexBase.exists() && latexBase.isDirectory())
		{
			ExlpTxtWriter w = new ExlpTxtWriter();
			w.add(content);
			w.writeFile(new File(latexBase,"test.tex"));
		}
		
	}
}