package org.openfuxml.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileTranscoder
{

	public static void main(String args[])
	{
		String inFile = "E:\\tmp\\Kurs.tex";
		String inChar = "utf-8";
		
		String toFile = "E:\\tmp\\Kurs_enc.tex";
		String toChar = "latin1";
		
		if(args.length>0)
		{
			inFile = args[0];
			inChar = args[1];
			toFile = args[2];
			toChar = args[3];
		}
		
		String Zeile;
		try
		{
			// Lesen
			File iF = new File(inFile);
			FileInputStream fis = new FileInputStream (iF);
			InputStreamReader isr = new InputStreamReader(fis,inChar);
			BufferedReader br = new BufferedReader(isr);

			//Schreiben
			File of = new File(toFile);
			FileOutputStream fos = new FileOutputStream (of);
			OutputStreamWriter osw = new OutputStreamWriter(fos,toChar);
			BufferedWriter bw = new BufferedWriter(osw);
			
			while(null != (Zeile = br.readLine()))
			{
//				System.out.println(Zeile);
				bw.write(Zeile+"\n");
			}
			bw.close();osw.close();fos.close();
			br.close();isr.close();fis.close();
			
		}
		catch (FileNotFoundException e)	{e.printStackTrace();}
		catch (UnsupportedEncodingException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
}
