<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: html.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
     ********************************************************************

	*******************************************************************************
	| openFuXML open source                                                       |
	*******************************************************************************
	| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
	|                                                                             |
	| This program is free software; you can redistribute it and/or               |
	| modify it under the terms of the GNU General Public License                 |
	| as published by the Free Software Foundation; either version 2              |
	| of the License, or (at your option) any later version.                      |
	|                                                                             |
	| This program is distributed in the hope that it will be useful,             |
	| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
	| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
	| GNU General Public License for more details.                                |
	|                                                                             |
	| You should have received a copy of the GNU General Public License           |
	| along with this program; if not, write to the Free Software                 |
	| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
	*******************************************************************************
     
     Description:
     This file is the headerfile of pass2 in processing. It linera the FILE-elements under a document-element
     
     Use in Pass4: XML->HTML

     ********************************************************************
     Beschreibung:
     Dieses Stylesheet ist das Herz der vierten Prozessstufe, in der aus den FILE-Elementen echte Dateien mit den 	Inhalten der FILE-Elemente werden. Zusätzlich werden hier die einzelnen Elemente mit dem "vorläufigen 	SchlussDesign"	versehen, das noch per CSS-Angepasst werden kann.
     
     ********************************************************************
-->

<!-- Von aussen (ANT-Prozess) mitgebrachte Vorgaben für Variablen -->
	<xsl:param name="medienpfad"/><!-- Set by build file -->
	<xsl:param name="Quellpfad"/><!-- Set by build file -->
	<xsl:param name="logfile"/> <!-- Set by build file -->
	<xsl:param name="document"></xsl:param> <!-- Set by build file -->
	<xsl:param name="documentname"></xsl:param> <!-- Set by build file -->
	<xsl:param name="designpath"/><!-- Set by build file -->
	<xsl:param name="configpath"/><!-- Set by build file -->	
	<xsl:variable name="config" select="document($configpath)"/>
	<xsl:variable name="styles" select="$config/config/styles"/>
	<xsl:variable name="folder" select="$config/config/specialfolder/html"/>

<!-- Einbezug weiterer XSLT-Vorschriften -->
	<xsl:include href="../common/settings.xsl"/>
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:include href="designinterface-html.xsl"/>
	<xsl:include href="templates.xsl"/>
	<xsl:include href="titelseite.xsl"/>
	<xsl:include href="abschnitt.xsl"/>
	<xsl:include href="common.xsl"/>
	<xsl:include href="math.xsl"/>
	<xsl:include href="mathenv.xsl"/>
	<xsl:include href="listen.xsl"/>
	<xsl:include href="toc.xsl"/>
	<xsl:include href="tabelle.xsl"/>
	<xsl:include href="aufgaben2.xsl"/>
	<xsl:include href="medienelemente.xsl"/>
	<xsl:include href="index.xsl"/>
	<xsl:include href="xhtml.xsl"/>
	<xsl:include href="xref.xsl"/>

	<xsl:key name="id_key" match="*[not(ancestor::kurs)]" use="@id"/>
	
<xsl:output encoding="ISO-8859-1" method="xhtml" indent="yes" doctype-system="DTD/xhtml1-strict.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"/>

<!-- Kopf der Ausgabe wird hier definiert

<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html>
	<head>
      <meta http-equiv="Content-Type" content="text/xhtml; charset=ISO-8859-1"/>
....
  -->

<xsl:strip-space elements="*"/>
<!-- Lösche überflüssige Leerstellen in dem Ausgabedokument -->

<xsl:template match="/">
	<xsl:apply-templates/>
</xsl:template>

<!-- Das Herzstueck der HTML-Ausgabe, von hier aus werden die einzelnen HTML-Dateien generiert. -->
<xsl:template match="file">
	<xsl:param name="contextnode"/>
	<xsl:variable name="design" select="document(concat($designpath,@design))"/>
	<!-- Fuers Build.log File 	-->
	<xsl:message>reading file <xsl:value-of select="concat($designpath,@design)"/></xsl:message>
	<xsl:message>producing file <xsl:value-of select="concat(@directory,@filename)"/></xsl:message>
	<!-- Abspalten der Datei -->
	<xsl:result-document href="{concat(@directory,@filename)}">
		<xsl:variable name="NumPfadEbene"><xsl:value-of select="string-length(@directory) - string-length(translate(@directory, '/', ''))"/></xsl:variable>
		<xsl:variable name="Pfad">
			<!-- In welcher Ebene im Bezug zu Root liegt die Datei? Ergebnis ist z.B. ../../../ -->
			<xsl:call-template name="rootPfad">
				<xsl:with-param name="Ebene" select="$NumPfadEbene"/>
			</xsl:call-template>
		</xsl:variable>
		<!-- Audführen des Designtemplates, und Auswertung der darin enthaltenen PI´s -->
		<xsl:apply-templates select="$design/html">
			<!-- Als Content mitgegeben wird hier der ganze FILE-Knoten -->
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="styles" select="$styles"/>
		</xsl:apply-templates>
	</xsl:result-document>
</xsl:template>

<xsl:template match="kurs"/>

<xsl:template match="kurs-nr">
<xsl:param name="contextnode"/>
	<xsl:apply-templates>
		<xsl:with-param name="contextnode" select="$contextnode"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="kurs-titel">
<xsl:param name="contextnode"/>
	<xsl:apply-templates>
		<xsl:with-param name="contextnode" select="$contextnode"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="kurs-autor">
<xsl:param name="contextnode"/>
	<xsl:apply-templates>
		<xsl:with-param name="contextnode" select="$contextnode"/>
	</xsl:apply-templates>
</xsl:template>

<!-- Auswertung von STARTPAGE und IMPRESSUM, das Auftauchen eines dieser beiden Elemente im FILE-Element unter bedeutet, das Einparsen der entsprechenden Datei und Einbau ihrer Inhalte unter der fest eingestellten html/body/div/div/ Kombination -->
<xsl:template match="startpage|impressum">
<xsl:param name="contextnode"/>
<xsl:variable name="design" select="document(concat($designpath,@design))"/>
<xsl:variable name="NumPfadEbene"><xsl:value-of select="string-length(@directory) - string-length(translate(@directory, '/', ''))"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="CSSPfad"/></xsl:variable>
	<xsl:apply-templates select="$design/html/body/div/div/*">
		<xsl:with-param name="contextnode" select="."/>
		<xsl:with-param name="Pfad" select="$Pfad"/>
	</xsl:apply-templates>
</xsl:template>

<!-- Dieses Template dient der Ausgabe einer automath.css, in der alle mathematischen Formeln ein Feintuning für die Web-Ausgabe erhalten können. z.B. ein paar Pixel nach oben/unten, etc.-->
<xsl:template name="mathcss">
<xsl:param name="contextnode"/>
<xsl:result-document href="style/automath.css">
<xsl:for-each select="//formel-imtext[not(.=following::formel-imtext)]
						|//formelarray[not(.=following::formelarray)]
						|//formel-num[not(.=following::formel-num)]">
.m<xsl:value-of select="@hash"/>	{margin-bottom: 0px; margin-top: 0px; margin-left: 0px; margin-right: 0px}	/* <xsl:value-of select="text()"/> */
</xsl:for-each>
</xsl:result-document>

</xsl:template>
</xsl:stylesheet>
