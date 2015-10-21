package org.openfuxml.client.control;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Format.Options.Option;

public interface OfxGuiAction
{
	//Combos
	public void cboApplicationSelected(OfxApplication selectedOfxA);
	public void cboProjectSelected(OfxProject selectedOfxP);
	public void cboDocumentSelected(OfxDocument selectedofxD);
	public void cboFormateSelected(OfxFormat selectedOfxF);
	
	//Boxes
	public void boxOptionsSelected(OfxFormat ofxF, Option o);
	
	//Tables
	public void tblEntitiesSelected(ProducibleEntities pe);
	
	//Buttons
	public void btnUpdate();
	public void btnProduce();
	
	public void openDocument(ProducibleEntities.File f);
}
