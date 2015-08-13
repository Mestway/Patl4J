package org.openfuxml.model.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.model.jaxb.Format.Options.Option;

import de.kisner.util.LoggerInit;

public class OfxRequestFactory extends AbstractJaxbFactory
{
	private OfxApplication ofxA;
	private OfxProject ofxP;
	private OfxDocument ofxD;
	private OfxFormat ofxF;
	private ProducibleEntities pe;
	private Collection<Option> fOptions;
	
	public OfxRequestFactory()
	{
		
	}
	
	public Sessionpreferences create()
	{
		Sessionpreferences spref = new Sessionpreferences();
			spref.setVersion("TODO");
			spref.setReqType("hmmm");
			spref.setApplication(ofxA.getName());
			spref.setProject(ofxP.getName());
			spref.setDocument(ofxD.getName());
			if(ofxF!=null){spref.setFormat(ofxF.getFormat().getId());}
			
			if(pe!=null && pe.getFile()!=null && pe.getFile().size()>0)
			{
				Sessionpreferences.Productionentities peSes = new Sessionpreferences.Productionentities();
				for(ProducibleEntities.File fPe : pe.getFile())
				{
					Sessionpreferences.Productionentities.File fSe = new Sessionpreferences.Productionentities.File();
					fSe.setDescription(fPe.getDescription());
					fSe.setDirectory(fPe.getDirectory());
					fSe.setFilename(fPe.getFilename());
					peSes.getFile().add(fSe);
				}
				spref.setProductionentities(peSes);
			}
			if(fOptions!=null && fOptions.size()>0)
			{
				Sessionpreferences.Options opSes = new Sessionpreferences.Options();
				for(Option o : fOptions)
				{
					Sessionpreferences.Options.Option opSe = new Sessionpreferences.Options.Option();
					opSe.setName(o.getName());
					opSe.setValue(o.getValue());
					opSes.getOption().add(opSe);
				}
				spref.setOptions(opSes);
			}
			
			spref.setUsername("changeme");
//		writeJaxb(System.out, spref);
//		System.out.close();
		return spref;
	}
	
	public void setOfxA(OfxApplication ofxA) {this.ofxA = ofxA;}
	public void setOfxP(OfxProject ofxP) {this.ofxP = ofxP;}
	public void setOfxD(OfxDocument ofxD) {this.ofxD = ofxD;}
	public void setOfxF(OfxFormat ofxF) {this.ofxF = ofxF;}
	public void setProducibleEntities(ProducibleEntities pe){this.pe=pe;}
	public void setOptions(Collection<Option> fOptions){this.fOptions=fOptions;}
	
	public void write(Sessionpreferences spref, File f)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			writeJaxb(fos,spref);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		OfxRequestFactory orf = new OfxRequestFactory();
		orf.create();
	}
}
