package org.openfuxml.communication.cluster.sync.ssh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author kisner
 *
 */
public class SshCheckerThread extends Thread 
{
	final static Logger logger = LoggerFactory.getLogger(SshChecker.class);
	
	private SshChecker sshC;
	private String user,host;
	private boolean aktiv;
	private JSch jsch;
	
	public SshCheckerThread(ThreadGroup tg, SshChecker sshC,String user, String host)
	{
		super(tg,user+"@"+host);
		this.sshC=sshC;
		this.user=user;
		this.host=host;
		aktiv=false;
	}
	
	public void run()
	{
		aktiv=true;

		try {testserver();}
		catch (SshCheckerException e){sshC.addErrorException(e);}

	    sshC.fertig();
	    aktiv=false;
	    synchronized(sshC){sshC.notify();}
	}
	

	
	private void testserver() throws SshCheckerException
	{
		try
	    {
	      jsch=SshChecker.initJSch();
	      

	      
	      Session session=jsch.getSession(user, host, 22);
	      session.connect();
	      session.disconnect();
	    }
	    catch(JSchException e)
	    {
	    	String msg;
	    	if(e.getMessage().contains("UnknownHostException: "))
	    	{
	    		msg="UnknownHost: "+host+". Try <ping "+host+">";
		    	sshC.addErrorException(new SshCheckerException(msg));
	    	}
	    	else if(e.getMessage().contains("Auth fail"))
	    	{
	    		msg="Authentication fails. Public keys available? Try manually <ssh "+user+"@"+host+"> to verify the connection";
		    	sshC.addErrorException(new SshCheckerException(msg));
	    	}
	    	else if(e.getMessage().contains("UnknownHostKey:"))
	    	{
	    		msg=e.getMessage()+" Try manually <ssh "+user+"@"+host+"> to verify the connection";
		    	sshC.addErrorException(new SshCheckerException(msg));
	    	}
	    	else
	    	{
	    		logger.fatal("Please report this to the openFuXML-Developer Team! "+ e.getMessage(),e);
	    		msg="Unknown Error: "+e.getMessage();
	    		logger.fatal(e);
	    		sshC.addErrorException(new SshCheckerException(msg));
	    	}
	    }
	}
	
	public void close()
	{
		logger.error("Process will be killed: ");
		jsch=null;
		aktiv=false;
		SshCheckerException sshE = new SshCheckerException("Connection to "+user+"@"+host+" timed out. Public keys available? Try manually: ssh "+user+"@"+host);
		sshC.addErrorException(sshE);
	}

	public boolean isAktiv() {return aktiv;}
	public String getHost() {return host;}
	public String getUser() {return user;}
	
}
