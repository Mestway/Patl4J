package com.mediaymedia.commons.io.metadata.identification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Marco Schmidt
 */
public class FormatDescriptionReader
{
	private BufferedReader in;

	public FormatDescriptionReader(Reader reader)
	{
		in = new BufferedReader(reader);
	}

	public FormatDescription read() throws IOException
	{
		String line;
		do
		{
			line = in.readLine();
			if (line == null)
			{
				return null;
			}
		}
		while (line.length() < 1 || line.charAt(0) == '#');
		String[] items = line.split(";");
		if (items == null || items.length < 8)
		{
			throw new IOException("Could not interpret line: " +
				line);
		}
		FormatDescription desc = new FormatDescription();
		desc.setGroup(items[0]);
		desc.setShortName(items[1]);
		desc.setLongName(items[2]);
		desc.addMimeTypes(items[3]);
		desc.addFileExtensions(items[4]);
		desc.setOffset(new Integer(items[5]));
		desc.setMagicBytes(items[6]);
		desc.setMinimumSize(new Integer(items[7]));
		return desc;
	}
}
