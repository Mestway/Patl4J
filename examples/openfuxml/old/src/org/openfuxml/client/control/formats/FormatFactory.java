package org.openfuxml.client.control.formats;

import java.util.List;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;

public interface FormatFactory
{
	public List<OfxFormat> getFormat(OfxApplication ofxA);
}
