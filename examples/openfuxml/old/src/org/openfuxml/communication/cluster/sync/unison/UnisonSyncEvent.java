package org.openfuxml.communication.cluster.sync.unison;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.kisner.util.event.AbstractEvent;
import de.kisner.util.event.EventInterface;

public class UnisonSyncEvent extends AbstractEvent implements EventInterface,Serializable
{
	final static Logger logger = LoggerFactory.getLogger(UnisonSyncEvent.class);
	static final long serialVersionUID=1;
	
	public static enum SyncResult {UNDEFINED,NothingToDo,Complete};
	
	Date myDate;
	SyncResult result;
	private int transferred,skipped,failures;
	private ArrayList<String> logItems;
	
	
	public UnisonSyncEvent(Date myDate)
	{
		super();
		this.myDate=myDate;
	}
			
	public void debug()
	{
		super.debug();
		logger.debug("\tResult="+result);
		if(result==SyncResult.Complete)
		{
			logger.debug("\t"+transferred+" items transferred, "+skipped+" skipped, "+failures+" failures");
		}
		if(result==SyncResult.UNDEFINED)
		{
			logger.debug("\t"+transferred+" items transferred, "+skipped+" skipped, "+failures+" failures");
		}
	}
	
	public void complete(int transferred,int skipped,int failures)
	{
		result=SyncResult.Complete;
		this.transferred=transferred;
		this.skipped=skipped;
		this.failures=failures;
	}
	
	public void setResult(SyncResult result){this.result=result;}
	public SyncResult getResult(){return result;}
	public ArrayList<String> getLogItems() {return logItems;}
	public void setLogItems(ArrayList<String> logItems) {this.logItems = logItems;}
	
}
