package org.openfuxml.client.control.documents;

import java.util.List;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxProject;

public interface DocumentFactory
{
	public List<OfxDocument> lDocuments(OfxApplication ofxA, OfxProject ofxProject);
}
