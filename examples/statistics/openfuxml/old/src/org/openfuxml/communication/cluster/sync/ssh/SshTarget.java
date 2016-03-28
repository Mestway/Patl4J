/*
 * Created on 19.03.2005
 */
package org.openfuxml.communication.cluster.sync.ssh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Thorsten
 */
public class SshTarget
{
	final static Logger logger = LoggerFactory.getLogger(SshTarget.class);
	
	private String user,host,path;
	
	public SshTarget(String user, String host, String path)
	{
		this.user=user;
		this.host=host;
		this.path=path;
	} 

	public static SshTarget getSshTarget(String url) throws SshCheckerException
	{
		int index = url.indexOf("ssh://");
		if(index<0){throw new SshCheckerException("Syntax for SSH Connection incorrect: "+url+" Use <ssh://user@host/dir> instead.");}
		String ssh=url.substring(6,url.length());
		
		index = ssh.indexOf("//");
		String path=ssh.substring(index+1,ssh.length());
		String connection;
		try{connection=ssh.substring(0,index);}
		catch (StringIndexOutOfBoundsException e)
		{
			String msg="Syntax for SSH Connection incorrect: "+url+". Use absolute Paths like <ssh://user@host//dir> instead.";
			throw new SshCheckerException(msg);
		}
		index = connection.indexOf("@");
		
		String user=connection.substring(0,index);
		String host=connection.substring(index+1,connection.length());
//		logger.debug("User="+user+" Host="+host+" Path="+path);
		return new SshTarget(user, host, path);
	}

	public String getHost() {return host;}
	public String getPath() {return path;}
	public String getUser() {return user;}
}
