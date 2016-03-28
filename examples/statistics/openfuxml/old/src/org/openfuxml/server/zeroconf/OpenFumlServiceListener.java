package org.openfuxml.server.zeroconf;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class OpenFumlServiceListener implements ServiceListener
{
	final static Logger logger = LoggerFactory.getLogger(OpenFumlServiceListener.class);
	JmDNS jmdns;
	
	public OpenFumlServiceListener(JmDNS jmdns)
	{
		this.jmdns=jmdns;
	}
	
	public void serviceResolved(ServiceEvent event)
	{
		String name = event.getName();
        String type = event.getType();
        ServiceInfo info = event.getInfo();
        logger.info("serviceResolved type:"+type+" name:"+name);
        if (info == null) {logger.debug("service not found");}
        else
        {
        	logger.info(info.getAddress());
        }
	}
	
	public void serviceRemoved(ServiceEvent se)
	{
		StringBuffer sb = new StringBuffer();
			sb.append(" Name="+se.getName());
			sb.append(" Typ="+se.getType());
		logger.debug("serviceRemoved: " +sb);
	}
	
	public void serviceAdded(ServiceEvent se)
	{
		String type=se.getType();
		String name=se.getName();
		logger.debug("serviceAdded: type:"+type+" name:"+name);
		ServiceInfo service = jmdns.getServiceInfo(type, name);
        if (service == null)
        {
            logger.debug("service not found");
        }
        else
        {
        	logger.debug("valueChanged Service!=null "+service.getPort());
            jmdns.requestServiceInfo(type, name);
        }
	}
}
