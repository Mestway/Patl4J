package org.openfuxml.addon.chart.test;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxChartTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxChartTestBootstrap.class);
	
	public static Configuration init()
	{		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("src/test/resources/config.test.ofx-chart");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		ConfigLoader.add("src/main/resources/config.ofx-chart/ofx-chart.xml");
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");
		return config;
	}
}