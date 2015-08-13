Call init()	
Sub init()
Dim fso, ClientProperties, PathValues, WshShell, Verzeichnis
	Set fso     = WScript.CreateObject("Scripting.FileSystemObject") 
If fso.FileExists("openFuXML-config.xml") = True Then fso.DeleteFile ("openFuXML-config.xml")

	Set ClientProperties = fso.CreateTextFile( "openFuXML-config.xml", true) 
	Set PathValues = fso.CreateTextFile( "config.bat", true) 
	Set WshShell = WScript.CreateObject("WScript.Shell")

	'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
'	Schreiben der simple-client.prop Datei
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	Verzeichnis = Replace(WshShell.CurrentDirectory , "\", "\\")
	Verzeichnis = Replace(Verzeichnis, ":", "\:")
	ClientProperties.Write ("<?xml version='1.0' encoding='UTF-8'?>"  & Chr(13)) 
	ClientProperties.Write ("<properties>" & Chr(13))
	ClientProperties.Write ("<net>"& Chr(13)&" <server typ='socket' host='localhost' port='4455'/>"& Chr(13)&"<report active='false'"& Chr(13)&"from='from@domain.org'"& Chr(13)&"to='bugreporter@fuxml.org'>"& Chr(13)&"<server typ='smtp' host='localhost' port='25'/>"& Chr(13)&"</report>"& Chr(13)&"</net>"& Chr(13))
	
	ClientProperties.Write ("<dirs>"& Chr(13))
		ClientProperties.Write ("<dir typ='basedir'>C\:\\Programme\\openFuXML\\openFuXML-1.2.1\\</dir>"& Chr(13))
		ClientProperties.Write ("<dir typ='log'>logs</dir>"& Chr(13))
		ClientProperties.Write ("<dir typ='output'>share/output</dir>"& Chr(13))
		ClientProperties.Write ("<dir typ='repository'>share/repository</dir>"& Chr(13))
	ClientProperties.Write ("</dirs>"& Chr(13))
	ClientProperties.Write ("<files>"& Chr(13))
		ClientProperties.Write ("<file typ='lib'>ant-1.7.0.jar</file>"& Chr(13))
		ClientProperties.Write ("<file typ='lib'>ant-launcher-1.7.0.jar</file>"& Chr(13))
		ClientProperties.Write ("<file typ='lib'>saxon-8.1.1.jar</file>"& Chr(13))
		ClientProperties.Write ("<file typ='lib'>openFuXML-ss-1.2.1.jar</file>"& Chr(13))
	ClientProperties.Write ("</files>"& Chr(13))
ClientProperties.Write ("</properties>"& Chr(13))
	
	
	
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
'	Schreiben der config.bat Datei
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ghostscript=MsgBox("Sie benötigen das PostScript Tool GhostScript. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "Ghostscript herunterladen?")
url="http://pages.cs.wisc.edu/~ghost/"
if ghostscript = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set GHOSTSCRIPT=" & InputBox("Bitte geben Sie den Pfad zu GhostScript an:", "Pfadkonfiguration", "C:\Programme\gs\gs8.60\bin\"))
	imagemagick=MsgBox("Sie benötigen das kommandozeilen-orientierte Bildbearbeitungstool ImageMagick. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "ImageMagick herunterladen?")
url="http://www.imagemagick.org/"
if imagemagick = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set IMAGEMAGICK=" & InputBox("Bitte geben Sie den Pfad zu Imagemagick an:", "Pfadkonfiguration", "C:\Programme\ImageMagick\")) 
jdk=MsgBox("Da openFuXML zum Teil in Java geschrieben ist benötigen Sie das Java Development Kit -JDK- der Firma Sun. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "Java Development Kit herunterladen?")
url="http://java.sun.com/"
if jdk = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set JAVAJDK=" & InputBox("Bitte geben Sie den Pfad zum Java JDK von Sun an:", "Pfadkonfiguration", "C:\Programme\Java\jdk1.6.0_02")) 
miktex=MsgBox("Die Erstellung der PDF Ausgabe setzt auf ein installiertes LaTex Textsatzsystem. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "MikTex herunterladen?")
url="http://www.miktex.org/"
if jdk = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set LATEX=" & InputBox("Bitte geben Sie den Pfad zu MikTex an:", "Pfadkonfiguration", "C:\Programme\MikTex 2.6\miktex\bin"))
	MsgBox("openFuXML ist nun eingerichtet. Wenn Sie diese Prozedur wiederholen wollen starten Sie einfach die Datei init.vbs in Ihrem openFuXML Verzeichnis z.B. durch einen Doppelklick im Explorer.")
End Sub