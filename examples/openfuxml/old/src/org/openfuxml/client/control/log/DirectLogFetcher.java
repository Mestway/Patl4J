package org.openfuxml.client.control.log;

import java.io.Writer;

import org.openfuxml.client.control.ClientGuiCallback;

public class DirectLogFetcher extends Writer
{
	private StringBuffer sb;
	private ClientGuiCallback guiCallback;
	
	public DirectLogFetcher(ClientGuiCallback guiCallback)
	{
		this.guiCallback=guiCallback;
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
		guiCallback.addLogline(logline);
		sb = new StringBuffer();
	}
}
