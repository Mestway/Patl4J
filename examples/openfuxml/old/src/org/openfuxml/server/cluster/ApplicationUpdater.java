package org.openfuxml.server.cluster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.ZipOutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.ApplicationRepository;
import org.openfuxml.communication.cluster.facade.ApplicationRepositoryFacade;
import org.openfuxml.communication.cluster.facade.ApplicationRepositoryFacadeBean;

import de.kisner.util.io.ObjectIO;
import de.kisner.util.xml.XmlConfig;

public class ApplicationUpdater
{
	final static Logger logger = LoggerFactory.getLogger(ApplicationUpdater.class);
	
	public static enum Aktion {SAVEINDB, LOADFROMDB};
	private static String fSep = System.getProperty("file.separator");
		
	XmlConfig xCnf;
	ApplicationRepositoryFacade fAr;
	
	public ApplicationUpdater(XmlConfig xCnf,InitialContext ctx)
	{
		this.xCnf=xCnf;
		try{fAr = (ApplicationRepositoryFacade) ctx.lookup(ApplicationRepositoryFacadeBean.class.getSimpleName()+"/remote");}
		catch (NamingException e) {e.printStackTrace();}
	}
	
	public void loadFromDb(String application, int version)
	{
		String baseDir = xCnf.getText("dirs/dir[@type=\"applicationsrepository\"]");
		File zipFile = new File(baseDir+fSep+"tmp.zip");
		ApplicationRepository ar;
		if((ar = restoreFromDb(zipFile,version))!=null)
		{
			File fBaseDir = new File(baseDir);
			ObjectIO.extractZip(zipFile, fBaseDir);
			logger.info(zipFile.getName() + " nach "+fBaseDir.getAbsolutePath()+" etpackt." );
			zipFile.renameTo(new File(baseDir+fSep+application+"-"+ar.getVersion()+".zip"));
		}
		else {logger.info(zipFile.getName()+" ist aktuell.");}		
	}
	
	public void saveInDb(String application)
	{
		logger.debug("XmlConfig.getDirBase="+xCnf.getWorkingDir());
		File zipFile = new File(xCnf.getWorkingDir()+fSep+"logs/tmp.zip");
		if(zipFile.exists()){zipFile.delete();}
		try
		{
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			File baseDir = new File(xCnf.getWorkingDir()+fSep+xCnf.getText("dirs/dir[@type=\"applications\"]")+fSep+application);
			logger.info("saveInDb:"+baseDir.getAbsolutePath());
			ObjectIO.addFileToZip(baseDir,zos,"");
			zos.close();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		int id = storeInDb(zipFile,application);
//		zipFile.delete();
		logger.info("Gespeichert. Id="+id);
	}
		
	private int storeInDb(File f,String application)
	{
		CRC32 crc = new CRC32();
		crc.update(ObjectIO.loadByte(f));

		ApplicationRepository ar = new ApplicationRepository();

		ar.setB(ObjectIO.loadByte(f));
		ar.setRecord(new Date());
		ar.setCrc32(crc.getValue());
		ar.setApplication(application);
		
		int id =fAr.newApplicationRepositoryVersion(ar);
		
		crc.reset();
		return id;
	}
	
	private ApplicationRepository restoreFromDb(File f, int version)
	{
		ApplicationRepository ar = null;
		if(!f.exists())
		{
			 ar = (ApplicationRepository)fAr.findObject(ApplicationRepository.class, 8);
			 if(ar!=null)
			 {
				 ObjectIO.saveByte(ar.getB(),f);
				 return ar;
			 }
		}
		return ar;
	}
}
