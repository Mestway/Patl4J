package org.openfuxml.renderer.latex.content.text;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(StringRenderer.class);
	
	private List<String> result;
	
	public StringRenderer(String string)
	{
		result = new ArrayList<String>();
		result.add(string);
	}
	
	public StringRenderer(List<String> strings)
	{
		result = new ArrayList<String>();
		result.addAll(strings);
	}

	@Override public List<String> getContent()
	{
		return result;
	}
	
	@Override public void write(Writer w) throws IOException
	{
		for(String s : getContent())
		{
			w.write(s+SystemUtils.LINE_SEPARATOR);
		}
		w.flush();
	}
	
}
