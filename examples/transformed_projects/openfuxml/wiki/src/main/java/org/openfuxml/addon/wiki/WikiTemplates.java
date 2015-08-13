package org.openfuxml.addon.wiki;

public class WikiTemplates
{
	private static boolean inited;
	
	public static String xmlDoctype, htmlDoctype;
	public static String htmlHeader,htmlFooter;
	
	public WikiTemplates()
	{
		
	}
	
	public static void init()
	{
		if(!inited)
		{
			inited = true;
			xmlDoctype = "<!DOCTYPE book PUBLIC \"-//OASIS//DTD DocBook XML V4.5//EN\" \"http://www.oasis-open.org/docbook/xml/4.5/docbookx.dtd\">";
			xmlDoctype = "";
			
			htmlDoctype = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
			htmlDoctype = "";
			
			htmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ htmlDoctype
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
				+ "<head></head>\n"
				+ "    <body>"
				+ "<title>{0}</title>";
			
			
			htmlFooter="</body>\n</html>";
		}
	}
}
