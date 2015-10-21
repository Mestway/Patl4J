package org.openfuxml.producer.utilities;

import java.io.*;

/**
 * This class creates an XML report about Latex errors in the provided TeX File
 * using information from the Log File
 */
public class LatexLog {

	
	public static void main(String[] args) {
		int Errorcounter = 0;

		if (args.length != 3) {
     		System.err.println("Usage: LatexLog <LogFile> <LatexFile> <outputFile>");
      		System.exit(1);
    	}
		
		File f1,f2;
		
		f1 = new File(args[0]);
		f2 = new File(args[1]);
		
		if (!f1.exists()){
     		System.err.println("File " + args[0] + " not exist");
      		System.exit(1);
    	}
    	if (!f2.exists()){
     		System.err.println("File " + args[1] + " not exist");
      		System.exit(1);
    	}
		System.out.println("Start Scan...");
		
		
		LatexLogFile latexLogFile = new LatexLogFile(args[0]);
		LatexTexFile latexTexFile = new LatexTexFile(args[1]);	
//		XMetalLog LogFile = new XMetalLog(args[2]);
		
		LatexLog latexLog = new LatexLog();
		
		XMLLogger XMLLog = new  XMLLogger(args[2]);
			 		 
		if (latexLogFile.checkForError())
		{
			
			String errorMessage;
			int i = 0;

		
			while ( (errorMessage = latexLogFile.getNextError()) != null)
			{
				String Id = "";
				
				int line = latexLog.getLineNumber(errorMessage);
				//String[] splited = latexLog.splitError(errorMessage);
				
				if (line > 0)
				{
					Id = latexTexFile.getID(line);
					if (Id != "")
                                         XMLLog.addError(Id,errorMessage);  // LogFile.addError(Id,splited[0],splited[1],splited[2]);
                                        else
                                        {
                                        	 XMLLog.addError("","Fehler bei der Analyse");
                                            Errorcounter++;
                                          //  LogFile.addError("Fehler bei der Analyse","","","");
                                        }
				}
				
			}
		}	
		XMLLog.Save();

//		LogFile.close();
                if (Errorcounter > 0)
                {
                    System.out.println(Errorcounter +  " Fehler bei Analyse...");
                    System.out.println("Finish...");
                }
                else
                    System.out.println("Finish...");
	}
	public LatexLog()
	{
	}
	private String[] splitError(String ErrorMessage)
	{
            //StringArray wird zur�ck gegeben
            //3 Strings enthalten
            //1. Fehlermeldung
            //2. Formel
            //3. Description
	try
		{
		int t;
		
		String[] sError = ErrorMessage.split("\n");
		String[] vReturn = new String[3];
		
		vReturn[0] = sError[0]; //sError[0] enth�lt immer die Zeile mit dem ! 
		vReturn[1] = "";
		for (t = 1;t < sError.length;t++)
                {
             
                   String sTestError =  sError[t];
                   if (sTestError.length() >= 2)
                   {    
                       if (sTestError.substring(0,2).equalsIgnoreCase("l."))
                       {
                           break;
                       }
                   }   
                   vReturn[1] += sTestError;
                }
		vReturn[1] += sError[t++];
	
		vReturn[2] = "";
		for (int i = ++t;i <sError.length;i++)
		{
			vReturn[2] += sError[i];
		} 
		return vReturn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new String[] {ErrorMessage,"",""};
		}
	}
	private int getLineNumber(String ErrorMessage)
	{
		//--------------------------------------------------
		//Funktion Filter aus einer ErrorMessage 
		// Die Line heraus und gibt sie als int zur�ck
		//--------------------------------------------------
		try
		{
			String[] c = ErrorMessage.split("\nl.");
			if (c.length > 1)
			{
				c = c[1].split(" ");
				return  Integer.parseInt(c[0]);
			}
			else
			{
				return -1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
}

class LatexLogFile
{
	String aktFile;				//String des aktuellen Logfiles
	String aktZeile;			//String der aktuellen Zeile 
	LineNumberReader LNR;		//Leser f�r File
	
	boolean EndOfText;			//True wenn ende des Textes erreicht wurde

	//----------------------------
	// Konstruktor f�r das LogFile
	// 	�bergeben wird Dateiname
	//----------------------------
	LatexLogFile(String Filename)	
	{
		this.aktFile = Filename;	//aktuelles File wird gesetzt
		EndOfText = false;			//File noch nicht gelesen
	}
	
	public boolean checkForError()
	{
		//-------------------------------------------------------
		//		Pr�fung auf Fehler
		//-------------------------------------------------------
		// �berpr�ft ob ein Fehler im Logfile aufgezeichnet wurde
		//	LNR liest solange bis er auf ein "!" erkennt
		//	Dann wird aktZeile auf selbige gesetzt
		//-------------------------------------------------------
		
		String sLine = "";
		try
		{
			File f = new File(aktFile);			//File wird gesetzt
			if (!f.exists())					//Existiert File
				return false;					//wenn nicht wird Funktion verlassen
			
			LNR = new LineNumberReader(new FileReader(aktFile)); //LNR wird erzeugt
		
			//solange das ende des Files nicht erreicht ist
			while((sLine = LNR.readLine()) != null)	
			{
				if (sLine.length() > 0)				//�berpr�fung ob Zeichen in Zeile
				{
					if ( sLine.charAt(0) == '!')	//wenn erstes Zeichen ein "!" 
					{ 
						this.aktZeile = sLine;		//dann setzen der aktuellen Zeile
						return true;				//und r�ckgabe von True
					}//if
				}//if 
			}//while 
		}	
		catch (Exception e) //Fehlerbehandlung
		{
			e.printStackTrace();
			EndOfText = true;
			return false;	//False wird zur�ck gegeben
		}
		
		EndOfText = true;	//Ende des Files erreicht und keinen Fehler gefunden	
		return false;		//False wird zur�ck gegeben
	}
	public String getNextError()
	{
		//--------------------------------------------------
		//			gibt den n�chsten Fehler zur�ck
		//--------------------------------------------------
		
		String vReturn;				//String der Erromeldung
		String line;				//speicher f�r Zeile
		
		if (EndOfText == false)		//�berpr�fung ob ende des Textes schon erreicht wurde
		{
			vReturn = this.aktZeile + "\n";		//erstmal wird aktuelle Zeile geschrieben
			
			while((line = getNextLine()) != null)	//getNextLine 
			{
				vReturn +=  line + "\n";	//Solange was gefunden wird, wird angeh�ngt
			}//while
			if (EndOfText == true)
			{
				String[] vReturnArray = vReturn.split("\n\n");
				return vReturnArray[0];
			}
			else
			{
				return vReturn;
			}
		}//if
		else	//EndOfText == true
		{
			return null;
		}//else
	}
	private String getNextLine()
	{
		//--------------------------------------------------------
		//		gibt die nächste Zeile zurück
		//--------------------------------------------------------
		// Solange kein neuer Fehler erkannt wird
		//  wird neuer Fehler erkannt wird null zurück gegeben
		//--------------------------------------------------------
		
		String sLine;		//speicher für gelesene Zeile
		
		try
		{
			while((sLine = LNR.readLine()) != null)	//Überprüfung ob Ende erreicht
			{
				if (sLine.length() > 0)				//Überprüfung ob Leerzeile
				{
					if ( sLine.charAt(0) == '!')	//Überprüfung ob neue Fehlermeldung anfängt
					{ 
						this.aktZeile = sLine;		//aktZeile wird gesetzt	
						return null;				//null zurückgebeben
					}//if
					else
					{
						return sLine;				//nur die Zeile wird zurück gegeben
					}//else
				}
				else
				{
					return "\n";					//gibt leerzeile zürck wenn eine gefunden wurde
				}//else
			}//while
		}//try
		catch (Exception e)
		{
			e.printStackTrace();
			EndOfText = true;
			return null;
		}//catch
		EndOfText = true;
		return null;
				
	}
	
}
class LatexTexFile
{
	String aktFile;				//String des aktuellen Logfiles							//Aktuelles TexFile
	LineNumberReader LNR;			//Leser f�r File

	
	
	//-------------------Konstruktor---------------------------
	//	FileName des TexFiles wird �bergeben und gesetzt
	//---------------------------------------------------------
	LatexTexFile(String FileName)
	{		
		this.aktFile = FileName;
	}
	
	//----------------------Funktionen---------------------------
	// Funktion gibt Identifiere zur�ck,
	//	der in der �bergebenen Zeile gefunden wird
	//-----------------------------------------------------------
        public int LineCounter;
        
	public String getID(int LineNumber)
	{
		//Funktion �ffnet File
		//Springt zur Zeile
		//gibt ID zur�ck
		try
		{
			File f = new File(aktFile);
			if (!f.exists())
			{
				return null;
			}
			LNR = new LineNumberReader(new FileReader(aktFile));
			
			while (LNR.getLineNumber() < (LineNumber -1))
			{
				LNR.readLine();
                                    
			}
					
			String Line = LNR.readLine();
			if (Line != null)
                        {
                            String[] ID = Line.split("%id:");

                            if (ID.length > 1)
                            {
                                    return ID[ID.length - 1].trim();
                            }
                            else
                            {
                                    if(LineCounter++ > 10)
                                        return "";
                                    String vReturn = getID(LineNumber + 1);
                                    LineCounter--;
                                    return vReturn;
                            }
                        }
                        else
                            return "";
		}	
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
			
	}
}
//class XMetalLog
//{
//	String aktFilename;
//	java.io.FileWriter fw;
//	public XMetalLog(String FileName)
//	{
//		try
//		{
//		aktFilename = FileName;
//		java.io.File f = new java.io.File(FileName);
//		fw = new java.io.FileWriter(f);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	public void addError(String ID,String Error, String Formel, String Description)
//	{
//		try
//		{
//		fw.write("\n");
//		fw.write(ID + "\n");
//		fw.write(Error + "\n");
//		fw.write(Formel + "\n");
//		fw.write(Description + "\n");
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	public void close()
//	{
//		try
//		{
//		fw.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//}






