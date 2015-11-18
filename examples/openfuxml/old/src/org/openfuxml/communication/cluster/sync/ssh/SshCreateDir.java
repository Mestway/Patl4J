package org.openfuxml.communication.cluster.sync.ssh;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshCreateDir
{
	final static Logger logger = LoggerFactory.getLogger(SshCreateDir.class);
		
	public static void createDir(String user,String host, String absolutePath)
	{
	    try
	    {
	      JSch jsch=SshChecker.initJSch();
	 
	      Session session=jsch.getSession(user, host, 22);
	      session.connect();
	      
	      Channel channel=session.openChannel("exec");
	      String command = "mkdir -p "+absolutePath;
	      
	      ((ChannelExec)channel).setCommand(command);
	      channel.setXForwarding(true);

	      InputStream in=channel.getInputStream();
	      
	      channel.connect();
	      
	      byte[] tmp=new byte[1024];
	      while(true)
	      {
	        while(in.available()>0)
	        {
	          int i=in.read(tmp, 0, 1024);
	          if(i<0){break;}
	          System.out.print(new String(tmp, 0, i));
	        }
	        if(channel.isClosed()){break;}
	        try{Thread.sleep(100);}catch(Exception ee){}
	      }
	      
	      channel.disconnect();
	      int exitStatus=channel.getExitStatus();
	      session.disconnect();
	      logger.debug("Verzeichnis "+user+"@"+host+"/"+absolutePath+" ExitStatus="+exitStatus);
	    }
	    catch(JSchException e){logger.fatal(e.getMessage());}
	    catch(Exception e){ e.printStackTrace();}
	}
}
