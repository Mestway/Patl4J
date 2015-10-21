package org.openfuxml.communication.cluster.sync.unison;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.kisner.util.event.handler.EventHandlerInterface;
import de.kisner.util.parser.AbstractParser;
import de.kisner.util.parser.LogParser;

public class UnisonParser extends AbstractParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(UnisonParser.class);
	
	private final static int maxPattern=2;
	Pattern pattern[] = new Pattern[maxPattern];
	UnisonSyncEvent use;
	
	public UnisonParser(EventHandlerInterface ehi)
	{
		super(ehi);
		int i=0;
		pattern[i] = Pattern.compile("Nothing to do: replicas have not changed since last sync(.*)");i++;
		pattern[i] = Pattern.compile("Synchronization complete  \\(([0-9]*) item[s]? transferred, ([0-9]*) skipped, ([0-9]*) failures\\)(.*)");i++;		
	}

	public void parseLine(String line)
	{
//		logger.debug(line);
		for(int i=0;i<maxPattern;i++)
		{
			Matcher m=pattern[i].matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: nothingtoDo();break;
					case 1: complete(m.group(1), m.group(2), m.group(3));break;

				}
				i=maxPattern;
			}
		}
	}

	public void parseItem(ArrayList<String> item)
	{
		use = new UnisonSyncEvent(new Date());
		use.setResult(UnisonSyncEvent.SyncResult.UNDEFINED);
		for(String line : item){parseLine(line);}
		if(use.getResult()==UnisonSyncEvent.SyncResult.UNDEFINED)
		{
			use.setLogItems(item);
		}
		
		ehi.handleEvent(use);
	}
	
	private void nothingtoDo()
	{
		use.setResult(UnisonSyncEvent.SyncResult.NothingToDo);
	}
	
	private void complete(String transferred,String skipped,String failures)
	{
		use.complete(new Integer(transferred), new Integer(skipped), new Integer(failures));
	}
}

