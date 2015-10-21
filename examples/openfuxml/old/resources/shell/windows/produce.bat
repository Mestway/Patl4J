@echo off
rem               ********************************************************************
rem		$Id: postinst,v 1.4 2007/03/02 13:17:25 hemmer Exp $
rem		********************************************************************
rem		*******************************************************************************
rem		| openFuXML open source                                                       |
rem		*******************************************************************************
rem		| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
rem		|                                                                             |
rem		| This program is free software; you can redistribute it and/or               |
rem		| modify it under the terms of the GNU General Public License                 |
rem		| as published by the Free Software Foundation; either version 2              |
rem		| of the License, or (at your option) any later version.                      |
rem		|                                                                             |
rem		| This program is distributed in the hope that it will be useful,             |
rem		| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
rem		| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
rem		| GNU General Public License for more details.                                |
rem		|                                                                             |
rem		| You should have received a copy of the GNU General Public License           |
rem		| along with this program; if not, write to the Free Software                 |
rem		| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
rem		*******************************************************************************
rem		******************************************************************** 



rem         Diese Datei startet einen Produktonsprozeß mit folgenden Kommandozeilenparametern:
rem         openfuxml.bat PROJEKTNAME DATEINAME(ohne .xml)
rem         Beispiel: "openfuxml referenzprojekt kurs"
rem         Dabei wird die Datei /share/output/fuxml/sessionpreferences als existent vorausgesetzt
rem         Um Probleme zwischen den Windows Dateinamen mit \ und den URI etc. mit / zu umgehen gibt
rem         es die Variablen FUXML_DIR als Windows Verzeichnisnamen und FUXML:_HOME für URIs


@cls

rem         Variablen Deklaration
CALL config.bat
@set format=latexpdf

@echo ======================================
@echo openFuXML for Windows - QuickStart
@echo ======================================

@echo Starte FuXML Buildprozess des Dokuments %2.xml aus dem Projekt %1

rem         Die nächste Zeile übergibt den aktuellen Verzeichnisnamen an die Variable FUXML_DIR
@for /f %%i in ('chdir') do @Set FUXML_DIR=%%i

rem         Die nächste Zeile benennt die windows-eigene CONVERT.EXE temporär in CONVERT_FS.EXE um, damit es keine Probleme mit dem ImageMagick Convert gibt
@move %SystemRoot%\SYSTEM32\convert.exe %SystemRoot%\SYSTEM32\convert_fs.exe
@echo Windows convert.exe wurde umbenannt in convert_fs.exe

rem         Hier wird nun die Datei "gswin32c.exe" in "gs.exe" kopiert, damit der Aufruf in der Produktion der Bilder mit tex4ht nicht ins Leere läuft
copy %GHOSTSCRIPT%gswin32c.exe %GHOSTSCRIPT%gs.exe

@set ANT_HOME=%FUXML_DIR%\lib\apache-ant-1.7.0
@set classpath=%FUXML_DIR%\lib\saxon-8.1.1.jar;%JAVAJDK%\lib;%FUXML_DIR%\lib\;%ANT_HOME%\lib\ant.jar;%ANT_HOME%\lib\ant-launcher.jar;%FUXML_DIR%\lib\openFuXML-1.0.11s.jar;%classpath%
@set PATH=%GHOSTSCRIPT%;%IMAGEMAGICK%;%LATEX%;%PATH%
set FUXML_HOME=%FUXML_DIR%

rem         Die nächste Zeile ersetzt in der Variablen die Backslahes durch Slashes
set FUXML_HOME=%FUXML_HOME:\=/%

@echo Systemvariablen wurden gesetzt.
@echo Das FuXML Home Verzeichnis ist %FUXML_HOME%.

@%ANT_HOME%\bin\ant.bat -buildfile %FUXML_DIR%\applications\fuxml\formats\html\build.xml -Dilona.home=%FUXML_HOME%/ -Dilona.contentstore=%FUXML_HOME%/share/repository/fuxml -Dsrc.dir=%FUXML_HOME%/share/repository/fuxml/%1/ -Dilona.output=%FUXML_HOME%/share/output/fuxml -Dxsltemp.dir=%FUXML_HOME%/share/output/fuxml/%1/xsl -Dapplication=fuxml -Dcoursename=%1 -Dproject=%1 -Dmasterfile=%2.xml -Dformat=%format% -Dusername=hemmer >> fuxml.log

rem         Die nächste Zeile benennt die windows-eigene CONVERT.EXE wieder zurück
@rename %SystemRoot%\SYSTEM32\convert_fs.exe convert.exe
@echo Windows convert.exe wurde wieder hergestellt

EXIT