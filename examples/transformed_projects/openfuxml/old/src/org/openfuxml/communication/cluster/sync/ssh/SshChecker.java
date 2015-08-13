/*
 * Created on 19.03.2005
 */
package org.openfuxml.communication.cluster.sync.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

/**
 * @author Thorsten
 */
public class SshChecker
{
	final static Logger logger = LoggerFactory.getLogger(SshChecker.class);
	
	private SshCheckedConnections checkedConnections;
	private int threadCounter;
	private ArrayList<SshCheckerException> alExceptions;
	private int maxSshTimeout;
	
	public SshChecker(int maxSshTimeout,int verifiedConnectionLifetime)
	{
		this.maxSshTimeout=maxSshTimeout;
		alExceptions = new ArrayList<SshCheckerException>();
		checkedConnections = new SshCheckedConnections(verifiedConnectionLifetime);
	} 

	public void check(ArrayList<String> alSshCheckTargets) throws SshCheckerException
	{
		ThreadGroup tg = new ThreadGroup("SSH Tests");
		ArrayList<SshCheckerThread> ssCCt=new ArrayList<SshCheckerThread>();
		for(String url : alSshCheckTargets)
		{
			logger.debug("Prüfe: "+url);
			SshTarget ssh = SshTarget.getSshTarget(url);
			if(!checkedConnections.stillVerified(ssh.getUser()+"@"+ssh.getHost()))
			{
				SshCheckerThread sct = new SshCheckerThread(tg,this,ssh.getUser(),ssh.getHost());
				sct.start();
				ssCCt.add(sct);
			}
		}
		
		logger.debug("Check "+ssCCt.size()+" connection for valid login.");
		Date start = new Date();
		threadCounter=ssCCt.size();
		while(threadCounter>0 && (new Date()).getTime()-start.getTime()<maxSshTimeout*1000)
		{
//			logger.debug("waiting ... tg="+threadCounter+"/"+ssCCt.size());
			synchronized(this)
			{
				try {this.wait(1000);}
				catch (InterruptedException e){e.printStackTrace();}
			}
		}
		
		for(SshCheckerThread sct : ssCCt){if(sct.isAktiv()){sct.close();}}
		if(!alExceptions.isEmpty()){throw alExceptions.get(0);}
		else
		{
			for(SshCheckerThread sct : ssCCt)
			{
				checkedConnections.verified(sct.getUser()+"@"+sct.getHost());
			}
		}
	}
		
	public void fertig()
	{
		synchronized(this){threadCounter--;}
	}
	
	public void addErrorException(SshCheckerException e)
	{
		alExceptions.add(e);
	}
	
	public static JSch initJSch() throws SshCheckerException
	{
		JSch jsch=new JSch();
		try
	    {
			jsch=new JSch();
			File fPrivateKey = new File(System.getProperty("user.home")+"/.ssh/id_rsa");
			if(!fPrivateKey.exists())
			{
				String msg="Private Key File "+fPrivateKey.getName()+" not found in directory "+fPrivateKey.getParent();
				throw new SshCheckerException(msg);
			}
			jsch.addIdentity(fPrivateKey.getAbsolutePath());
		      
			File fHosts = new File(System.getProperty("user.home")+"/.ssh/known_hosts");
			FileInputStream fis=null;
			try {fis = new FileInputStream(fHosts);}
			catch (FileNotFoundException e)
			{
				String msg="Hosts File "+fHosts.getName()+" not found in directory "+fHosts.getParent();
				throw new SshCheckerException(msg);
			}	
			jsch.setKnownHosts(fis);
	    }
		catch(JSchException e)
	    {
	    	String msg;
	    	if(e.getMessage().contains("UnknownHostKey: "))
	    	{

	    	}
	    	else
	    	{
	    		logger.fatal("Please report this to the openFuXML-Developer Team! "+ e.getMessage(),e);
	    		msg="Unknown Error: "+e.getMessage();
	    		logger.fatal(e);
	    		throw new SshCheckerException(msg);
	    	}
	    }
//		HostKeyRepository hkr = jsch.getHostKeyRepository();
//		for(HostKey hk : hkr.getHostKey()){logger.debug("HostKey: "+hk.getHost()+" "+hk.getKey());}
		return jsch;
	}	
}
