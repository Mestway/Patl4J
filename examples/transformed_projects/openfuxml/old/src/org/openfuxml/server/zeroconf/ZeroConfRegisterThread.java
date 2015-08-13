package org.openfuxml.server.zeroconf;

import java.io.IOException;
import java.util.Hashtable;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZeroConfRegisterThread extends Thread
{
	final static Logger logger = LoggerFactory.getLogger(ZeroConfRegisterThread.class);
	
	ServiceInfo serviceInfo;
	
	public ZeroConfRegisterThread()
	{
	    String type = "_openfuxml._tcp.local.";
	    String name = "openfuxml";
	    int port = 4455;
	    int weight =0;
	    int priority =0;
	    Hashtable props = new Hashtable();
		
		serviceInfo = new ServiceInfo(type,name,port,weight,priority,props);
	}
	
	public void run()
	{
		try
		{
			logger.debug("Registering ZeroConf "+serviceInfo.getHostAddress()+":"+serviceInfo.getPort());
			JmDNS jmdns = new JmDNS();
			jmdns.registerService(serviceInfo);
			logger.debug("ZeroConf Registration done");
		}
		catch (IOException e) {logger.warn("ZeroConf Registration failed");}
	}
}
