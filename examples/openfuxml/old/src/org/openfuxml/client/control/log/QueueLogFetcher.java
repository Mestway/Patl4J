package org.openfuxml.client.control.log;

import java.io.Writer;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueLogFetcher extends Writer
{
	private StringBuffer sb;
	private LinkedBlockingQueue<String> q;
	
	public QueueLogFetcher(LinkedBlockingQueue<String> q)
	{
		this.q=q;
		sb = new StringBuffer();
	}
	
	public void write(byte[] b)
	{
		String str = new String(b);
		sb.append(str);
	}
	
	public void write(char[] b, int off, int len)
	{
		String str = new String(b, off, len);
		sb.append(str);
	}

	public void write(int b)
	{
		String str = Integer.toString(b);
		sb.append(str);
	}
	
	public void close(){}
	public void flush()
	{
		String logline=sb.substring(2, sb.length()-1).toString();
		System.out.println(logline);
		q.add(logline);
		sb = new StringBuffer();
	}
}
