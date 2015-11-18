package org.openfuxml.test.addon.jsf;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsfapp.data.jaxb.Menu;
import org.openfuxml.addon.jsfapp.data.jaxb.Menuitem;

public class TestMenu
{
	final static Logger logger = LoggerFactory.getLogger(TestMenu.class);
	
	public TestMenu()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Menu menu = new Menu();
		
		Menuitem m1 = new Menuitem();
		m1.setLabel("1A");
		menu.getMenuitem().add(m1);
		
		Menuitem m2 = new Menuitem();
		m2.setLabel("1B");
		
		Menuitem l1 = new Menuitem();
		l1.setLabel("2A");
		m2.getMenuitem().add(l1);
		
		Menuitem l2 = new Menuitem();
		l2.setLabel("2A");
		m2.getMenuitem().add(l2);
		
		menu.getMenuitem().add(m2);
		
		JaxbUtil.debug(menu);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestMenu tpr = new TestMenu();
		
		tpr.xmlConstruct();
	}
}