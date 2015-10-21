/*
 * Created on 2006-12-09
 */
package org.openfuxml.communication.cluster.sync.unison;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.sync.ServerSync;
import org.openfuxml.communication.cluster.sync.ssh.SshChecker;
import org.openfuxml.communication.cluster.sync.ssh.SshCheckerException;
import org.openfuxml.communication.cluster.sync.ssh.SshCreateDir;
import org.openfuxml.communication.cluster.sync.ssh.SshTarget;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.SyncLocation;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.util.config.OfxPathHelper;

import de.kisner.util.event.EventInterface;
import de.kisner.util.event.handler.EventHandlerHash;
import de.kisner.util.event.handler.EventHandlerInterface;
import de.kisner.util.loglistener.LogListener;
import de.kisner.util.loglistener.LogListenerCmd;
import de.kisner.util.parser.LogParser;

/**
 * @author Thorsten
 */
public class UnisonSync implements ServerSync
{
	final static Logger logger = LoggerFactory.getLogger(UnisonSync.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	String remoteRepo,remoteOutput;
	String localRepo,localOutput;
	SshChecker sshCC;
	
	public UnisonSync(Configuration config)
	{
		String baseDir = config.getString("dirs/dir[@type='basedir']");
		
		localRepo=OfxPathHelper.getDir(config, "repository");
		localOutput=OfxPathHelper.getDir(config, "output");
		
		sshCC=new SshChecker(15,5);
	}

	public void setSyncLocations(Collection<SyncLocation> sls)
	{
		for(SyncLocation sl : sls)
		{
			switch(sl.getTyp())
			{
				case REPOSITORY:	remoteRepo=sl.getUrl();break;
				case OUTPUT:		remoteOutput=sl.getUrl();break;
			}
		}
		logger.debug("SyncURL remoteRepo="+remoteRepo);
		logger.debug("SyncURL remoteOutput="+remoteOutput);
	}
	
	public void getRepo(ProductionRequest pReq)
	{
		ArrayList<String> paths= new ArrayList<String>();
		paths.add(pReq.getApplication()+"/"+pReq.getProject());
		try
		{
			for(String path : paths)
			{
				boolean touchDummy=false;
				File dir = new File(localRepo+"/"+path);
				if(!dir.exists())
				{
					dir.mkdirs();
					touchDummy=true;
				}
				else if (dir.listFiles().length==0){touchDummy=true;}
				if(touchDummy)
				{
					File dummy = new File(dir.getAbsoluteFile()+File.separator+"dummy");
					dummy.createNewFile();
				}
			}
		}
		catch (IOException e) {e.printStackTrace();}
		sync(localRepo, remoteRepo, remoteRepo, paths);
	}
	
	public void getOutput(ProductionRequest pReq)
	{
		ArrayList<String> paths= new ArrayList<String>();
		paths.add(pReq.getApplication()+"/"+pReq.getProject());
		sync(localOutput, remoteOutput, remoteOutput, paths);
	}
	
	public void upOutput(ProductionRequest pReq)//,ProductionResult result)
	{
		SshTarget sshTarget=null;
		try {sshTarget = SshTarget.getSshTarget(remoteOutput);}
		catch (SshCheckerException e) {}//nothing, because checker before
				
		ArrayList<String> paths= new ArrayList<String>();
		
		if(pReq.getFormat().equals("latexpdf"))
		{
			StringBuffer pathLatexpdf = new StringBuffer();
			pathLatexpdf.append(  pReq.getApplication()+File.separator);
			pathLatexpdf.append(    pReq.getProject()+File.separator);
			pathLatexpdf.append(    "latexpdf"+File.separator);
			pathLatexpdf.append(      pReq.getDocument().substring(0, pReq.getDocument().indexOf(".xml")));
			paths.add(pathLatexpdf.toString());
			SshCreateDir.createDir(sshTarget.getUser(), sshTarget.getHost(), sshTarget.getPath()+File.separator+pathLatexpdf.toString());
		}
		sync(localOutput, remoteOutput, localOutput, paths);
/*				if(format.equals("html"))
		{
			path=" -path "+project+"/"+format+"/"+dir;
			sync(lokal, remote, force, path);
			path=" -path "+project+"/"+format+"/math";
			sync(lokal, remote, force, path);
			path=" -path "+project+"/"+format+"/"+dir+".zip";
			sync(lokal, remote, force, path);
		}
		if(format.equals("validation"))
		{
			path=" -path "+project+"/"+format;
//			String s = "rm /"+root+"/"+project+"/"+format+"/"+request.getProductionDir()+dir+"/"+"report.xml";
//			System.out.println("Report wird explizit geloescht: "+s);
//			writeFile(s,sysprops.getProperty("ilona.output")+"/batch.bat");
//			spawn("sftp -b "+sysprops.getProperty("ilona.output")+"/batch.bat "+ssh);
			logger.warn("Noch nicht implementiert: Explizites Löschen des Repors!");
			sync(lokal, remote, force, path);
		}
*/	}
	
	public void sync(String localRoot, String remoteRoot, String forceRoot, ArrayList<String> paths)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("unison ");
		sb.append(localRoot+" "+remoteRoot+" ");
		sb.append(" -force "+forceRoot);
		for(String s : paths){sb.append(" -path "+s);}
		sb.append(" -batch  -addversionno");
		
		Hashtable<String,EventInterface> ht = new Hashtable<String,EventInterface>();
		EventHandlerInterface eh = new EventHandlerHash(ht);
		LogParser lp = new UnisonParser(eh);
		LogListener ll = new LogListenerCmd(lp,null,false,false); 
		ll.processMulti(sb.toString());
		for(EventInterface ei : ht.values())
		{
			UnisonSyncEvent use = (UnisonSyncEvent)ei;
			logger.info("UnisonSync Ergebnis: "+use.getResult());
			if(use.getResult()==UnisonSyncEvent.SyncResult.UNDEFINED)
			{
				for(String s : use.getLogItems())
				{
					logger.debug("UnisonUNDEFINED: "+s);
				}
			}
		}
	}
	
	public boolean available() throws ProductionHandlerException
	{
		ArrayList<String> alSshCheckTargets = new ArrayList<String>();
		alSshCheckTargets.add(remoteRepo);
		alSshCheckTargets.add(remoteOutput);
		try{sshCC.check(alSshCheckTargets);}
		catch (SshCheckerException e){throw new ProductionHandlerException(e.getMessage());}
		return true;
	}
}
